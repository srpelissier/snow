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

The source file is `MaintainBranchesAndStaffs.java` and can be built using the shell script `MaintainBranchesdAndStaffs.sh`.

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