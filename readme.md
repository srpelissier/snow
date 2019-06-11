# Instructions

## Dependencies

Use cases are automated in Java with selenium and junit4. There is no other dependency required.

Selenium Java libraries are linked from [Selenium Client & WebDriver Language Bindings](https://www.seleniumhq.org/download/). Currently using version `3.141.59`.



JUnit 4 libraries are linked from [junit-team/junit4
](https://github.com/junit-team/junit4/wiki/Download-and-Install). Currently using `junit-4.13-beta-3.jar` and `hamcrest-core-1.3.jar`.

What to expect:

```text
.
├── client-combined-3.141.59.jar
└── libs
    ├── byte-buddy-1.8.15.jar
    ├── commons-exec-1.3.jar
    ├── guava-25.0-jre.jar
    ├── hamcrest-core-1.3.jar
    ├── junit-4.13-beta-3.jar
    ├── okhttp-3.11.0.jar
    └── okio-1.14.0.jar
```

## Building

The source file is `MaintainBranchesAndStaffs.java` and can be built using the shell script `buildMaintainBranchesdAndStaffs.sh`.

## Running

Start "gurukula" then execute `runMaintainBranchesAndStaffs.sh`.

## Information

Please see `task.md` for the initial description of the task

The file `MaintainBranchesAndStaffs.java` consists of a series of tests and support methods corresponding to the described test cases.

### Details

#### Viewing, editing, deleting and querying both Staff and Branches

That is implemented by the `crudBranch()` anmd `crudStaff()` test methods following the "CRUD"  model (Create, Update, Retrieve,. Delete) as indicated by inline comments.

#### Pagination is enabled when viewing the Staff/Branch

#### Logged in account information can be viewed/edited from the Account menu

This is implemented by the `accountSettings()` test method.

#### Login/Logout as existing user

This is implemented by the `loginAsAdmin()` and `logOut()` test methods using the "**admin**" account.

#### Register a new user

This is implemented by the `registerUser()` test method.

### Remarks

Both `accountSettings()` and `registerUser()` tests fail.

Right now, if a test fails it might leave some data behind to be somehow manually cleaned. To prevent this from happening, it would be good to investigate a method to wipe out any leftover upon starting the test. Restarting tomcat is time consuming.

The choices made in this solution are very simple: no integrated environment was used, no sophisticated JUnit runner or build system. This would need to be addressed for future convenient use.

Should, hypothetically, this work make it to a production environment, in order to improve its maintainability, the underlying code would need to be refactored:

1. to help its legibility
2. to help its extensibility

Therefore a few options exist including applying the PageObject pattern or a gherkin-based test case definition, for example.

