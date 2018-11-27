#!/bin/bash
javac -d ../build/ HashMapSecureDataContainer.java InvalidCredentialsException.java \
      SecureDataContainer.java SecureDataContainerTests.java \
      User.java UserAlreadyPresentException.java \
      UserNotPresentException.java
