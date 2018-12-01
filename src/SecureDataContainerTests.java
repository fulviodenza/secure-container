import java.util.Iterator;

public class SecureDataContainerTests {

    public static class ComplexClassType {
        public String str;
        public int num;
        public String differentStr;

        public ComplexClassType(String a, int n, String b) {
            str = a;
            num = n;
            differentStr = b;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof ComplexClassType)) throw new ClassCastException();

            ComplexClassType o = (ComplexClassType) other;

            return (o.str.equals(this.str) && (o.num == this.num)); //Not comparing on differentStr;
        }
    }

    public static class Tester {

        private static class TestFailedException extends RuntimeException {
            public TestFailedException(String msg) {
                super("[ TEST FALLITO ] " + msg);
            }
        }

        @FunctionalInterface
        public interface ExceptionRunner {

            void exec() throws Exception;
        }

        public static void expectExceptions(ExceptionRunner runner) {
            try {
                runner.exec();
            } catch (Exception e) {
                System.out.println("[ TEST OK ] Eccezione generata: " + e.getMessage());
            }
        }

        public static void expectNoExceptions(ExceptionRunner runner) {
            try {
                runner.exec();
            } catch (Exception e) {
                throw new TestFailedException(e.getMessage());
            }
        }

        public static void assertEquals(Object left, Object right) {
            if (!left.equals(right))
                throw new TestFailedException(left.toString() + " diverso da " + right.toString());
        }

        public static void assertNotEquals(Object left, Object right) {
            if (left.equals(right))
                throw new TestFailedException(left.toString() + " uguale a" + right.toString());
        }

        public static void assertNotNull(Object left) {
            if (left == null)
                throw new TestFailedException("L' oggetto è null");
        }

        public static void assertNull(Object left) {
            if (left != null)
                throw new TestFailedException("L'oggetto non è null");
        }
    }

    public static void testContainerComplex(SecureDataContainer<ComplexClassType> container) {

        String CRAX_PASSWORD = "asd123";
        String FULVIO_PASS = "dsa321";
        String LUIGI_PASS = "aaabbb";
        Tester.expectNoExceptions(() -> container.createUser("crax", CRAX_PASSWORD));
        Tester.expectNoExceptions(() -> container.createUser("fulvio", FULVIO_PASS));
        Tester.expectNoExceptions(() -> container.createUser("luigi", LUIGI_PASS));

        Tester.expectExceptions(() -> container.createUser("crax", CRAX_PASSWORD));

        ComplexClassType comp1 = new ComplexClassType("complex1", 10, "abc");
        ComplexClassType comp2 = new ComplexClassType("complex1", 11, "abc");
        ComplexClassType comp3 = new ComplexClassType("complex3", 124, "abc");

        container.put("crax", CRAX_PASSWORD, comp1);
        container.put("crax", CRAX_PASSWORD, comp2);
        container.put("crax", CRAX_PASSWORD, comp3);

        ComplexClassType diffContained = container.get("crax", CRAX_PASSWORD, new ComplexClassType("complex1", 10, "def"));
        Tester.assertNotNull(diffContained);
        Tester.assertEquals(diffContained.differentStr, comp1.differentStr);
        Tester.assertNotNull(container.remove("crax", CRAX_PASSWORD, new ComplexClassType("complex1", 10, "def")));
        diffContained = container.get("crax", CRAX_PASSWORD, new ComplexClassType("complex1", 10, "def"));
        Tester.assertNull(diffContained);
    }

    public static void testContainerString(SecureDataContainer<String> container) {

        String CRAX_PASSWORD = "asd123";
        String FULVIO_PASS = "dsa321";
        String LUIGI_PASS = "aaabbb";
        Tester.expectNoExceptions(() -> container.createUser("crax", CRAX_PASSWORD));
        Tester.expectNoExceptions(() -> container.createUser("fulvio", FULVIO_PASS));
        Tester.expectNoExceptions(() -> container.createUser("luigi", LUIGI_PASS));

        Tester.expectExceptions(() -> container.createUser("crax", CRAX_PASSWORD));

        container.put("crax", CRAX_PASSWORD, "Meizu");
        Tester.expectNoExceptions(() -> Tester.assertEquals(container.getSize("crax", CRAX_PASSWORD), 1));
        Tester.assertNotNull(container.get("crax", CRAX_PASSWORD, "Meizu"));

        Tester.expectNoExceptions(() -> container.copy("crax", CRAX_PASSWORD, "Meizu"));
        Tester.expectNoExceptions(() -> Tester.assertEquals(container.getSize("crax", CRAX_PASSWORD), 2));
        Tester.assertNotNull(container.get("crax", CRAX_PASSWORD, "Meizu"));

        container.put("crax", CRAX_PASSWORD, "Huawei");
        Tester.expectNoExceptions(() -> Tester.assertEquals(container.getSize("crax", CRAX_PASSWORD), 3));
        Tester.assertNotNull(container.get("crax", CRAX_PASSWORD, "Huawei"));

        Tester.expectNoExceptions(() -> container.share("crax", CRAX_PASSWORD, "luigi", "Meizu"));
        Tester.assertNotNull(container.get("luigi", LUIGI_PASS, "Meizu"));

        Tester.expectNoExceptions(() -> Tester.assertEquals(container.getSize("luigi", LUIGI_PASS), 1));
        Tester.assertNotNull(container.get("crax", CRAX_PASSWORD, "Huawei"));

        Tester.assertNotNull(container.remove("crax", CRAX_PASSWORD, "Huawei"));
        Tester.assertNull(container.remove("crax", CRAX_PASSWORD, "Huawei"));

        Tester.expectNoExceptions(() -> Tester.assertEquals(container.getSize("crax", CRAX_PASSWORD), 2));
        Tester.expectExceptions(() -> container.remove("luigi", LUIGI_PASS, "Meizu"));
        Tester.assertNotNull(container.get("luigi", LUIGI_PASS, "Meizu"));

        Tester.expectNoExceptions(() -> Tester.assertEquals(container.getSize("luigi", LUIGI_PASS), 1));

        printUserPhones("crax", CRAX_PASSWORD, container);

        printUserPhones("luigi", LUIGI_PASS, container);


        Tester.expectNoExceptions(() -> container.removeUser("crax", CRAX_PASSWORD));

        printUserPhones("luigi", LUIGI_PASS, container);
        Tester.assertNull(container.get("luigi", LUIGI_PASS, "Meizu"));

    }

    private static void printUserPhones(String who, String pass, SecureDataContainer container) {
        System.out.println("-----------------------Telefoni di " + who + "----------------------------");
        Tester.expectNoExceptions(() -> {
            Iterator phoneIt = container.getIterator(who, pass);
            while (phoneIt.hasNext()) {
                String phone = (String) phoneIt.next();
                System.out.println(phone);
            }
        });
    }

    public static void main(String[] args) {
        SecureDataContainer<String> hashContainer = new ListSecureDataContainer<>();
        TreeMapSecureDataContainer<String> treeContainer = new TreeMapSecureDataContainer<>();
        testContainerString(hashContainer);
        testContainerString(treeContainer);


        SecureDataContainer<ComplexClassType> listComplexContainer = new ListSecureDataContainer<>();
        SecureDataContainer<ComplexClassType> treeComplexContainer = new TreeMapSecureDataContainer<>();
        testContainerComplex(listComplexContainer);
        testContainerComplex(treeComplexContainer);

    }

}
