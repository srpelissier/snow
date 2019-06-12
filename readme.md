# Instructions

## Dependencies

Use cases are automated in Java using Selenium and JUnit4. No other dependency is required.

Selenium Java libraries are linked from [Selenium Client & WebDriver Language Bindings](https://www.seleniumhq.org/download/). Currently using version `3.141.59`.

JUnit 4 libraries are linked from [junit-team/junit4
](https://github.com/junit-team/junit4/wiki/Download-and-Install). Currently using `junit-4.13-beta-3.jar` and `hamcrest-core-1.3.jar`.

Please  install them as shown:

```text
snow/
├── ...
├── client-combined-3.141.59.jar
├── libs
│   ├── byte-buddy-1.8.15.jar
│   ├── commons-exec-1.3.jar
│   ├── guava-25.0-jre.jar
│   ├── hamcrest-core-1.3.jar
│   ├── junit-4.13-beta-3.jar
│   ├── okhttp-3.11.0.jar
│   └── okio-1.14.0.jar
└── ...
```

## Building

The main source file is `MaintainBranchesAndStaffs.java` and can be built using the shell script `buildMaintainBranchesdAndStaffs.sh`. It is using Firefox Developer Edition on Linux (see line #26).

## Running

Start "gurukula", build `MaintainBranchesAndStaffs.class` then execute `runMaintainBranchesAndStaffs.sh`.

## Information

Please see `task.md` for the initial description of the task

The file `MaintainBranchesAndStaffs.java` consists of a series of tests and support methods corresponding to the described test cases.

`Pagination.java` takes care of that other test case in a separate file. This work is in progress (see below).

### Details

#### Viewing, editing, deleting and querying both Staff and Branches

That is implemented by the `crudBranch()` anmd `crudStaff()` test methods following the "CRUD model" (Create, Update, Retrieve, Delete) as indicated by inline comments.

#### Pagination is enabled when viewing the Staff/Branch

**Pagination** is not fully implemented as an automated test because the details of the feature are unclear. However it's been observed that up to 25 Branches or Staffs there is no pagination. Moreover, while under Staff one can find the necessary navigation controls for pagination, these are absent from under Branch. The file `Pagination.java` is an attempt at testing this which could eventually be merged into `MaintainBranchesAndStaffs.java` after the requirements are made clearer. `Pagination.java` is essentially an extract of the relevant methods from `MaintainBranchesAndStaffs.java` with the ability to generate more Branch/Staff entries. See also the associated scripts `buildPagination.sh` and `RunPagination.sh`.

#### Logged in account information can be viewed/edited from the Account menu

This is implemented by the `accountSettings()` test method.

#### Login/Logout as existing user

This is implemented by the `loginAsAdmin()` and `logOut()` test methods using the "**admin**" account.

#### Register a new user

This is implemented by the `registerUser()` test method.

### Remarks

1. Both `accountSettings()` and `registerUser()` tests fail.

1. Right now, when a test fails it might leave some data behind to be somehow manually cleaned. To prevent this from happening, it would be good to investigate a method to wipe out any leftover upon starting the test. Restarting tomcat is time consuming.

1. The choices made in this solution are very simple: no integrated environment was used, no sophisticated JUnit runner or build system. This would need to be addressed for future convenient use.

1. Should, hypothetically, this work make it to a production environment, in order to improve its maintainability, the underlying code would need to be refactored:

   1. to help its legibility
   1. to help its extensibility

1. Therefore a few options exist including applying the PageObject pattern or a gherkin-based test case definition, for example.

