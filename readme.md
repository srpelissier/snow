# readme.md

## Dependencies

Use cases are automated in Java using Selenium and JUnit4. Test scripts expect Firefox Developer Edition on Linux but this can be changed by edit of the init() method in each.

Selenium Java libraries are linked from [Selenium Client & WebDriver Language Bindings](https://www.seleniumhq.org/download/). Currently using version `3.141.59`.

JUnit 4 libraries are linked from [junit-team/junit4](https://github.com/junit-team/junit4/wiki/Download-and-Install). Currently using `junit-4.13-beta-3.jar` and `hamcrest-core-1.3.jar`.

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

Use `build.sh` from the project's root.

## Running

Start "gurukula", and use `run.sh` from the project's root.

## Details

Please see `task.md` for the initial description of the task

### MaintainBranchesAndStaffs.java

The file `MaintainBranchesAndStaffs.java` consists of a series of test and support methods corresponding to the prescribed test cases (minus pagination).

#### Test case: Viewing, editing, deleting and querying both Staff and Branches

That is implemented by the `crudBranch()` and `crudStaff()` test methods following the "CRUD model" (Create, Update, Retrieve, Delete) as indicated by inline comments.

#### Test case: Logged in account information can be viewed/edited from the Account menu

This is implemented by the `accountSettings()` test method.

#### Test case: Login/Logout as existing user

This is implemented by the `loginAsAdmin()` and `logOut()` test methods using the "**admin**" account.

#### Test case: Register a new user

This is implemented by the `registerUser()` test method.

### Pagination.java

Curently a separate, manageable file, `Pagination.java` will eventually be merged with `MaintainBranchesAndStaffs.java` as they have a lot in common.

#### Test case: Pagination

Testing Pagination is partly implemented for Staffs (TODO: the Previous and Last buttons' actions) and Branches.

### Failing Tests

1. `MaintainBranchesAndStaff.accountSettings`: there is a message in Tomcat log about a CORS violation.
1. `MaintainBranchesAndStaff.registerUser`: there is a message in Tomcat log about a mismatched regular expression for the value of "password".
1. `Pagination.branchPagination`: there is no "pager".
