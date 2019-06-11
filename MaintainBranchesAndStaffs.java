import org.junit.Test;
import org.junit.Ignore;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.ComparisonFailure;
import static org.junit.Assert.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class MaintainBranchesAndStaffs {

   static WebDriver d;
   static WebDriverWait w;

   @BeforeClass
   public static void init() {

      System.setProperty("webdriver.firefox.bin", "/usr/bin/firefox-developer-edition");
      d = new FirefoxDriver();
      w = new WebDriverWait(d, 5);

      // System.out.println("init");

   }

   @Before
   public void welcome() {

      d.get("http://localhost:8080");
      assertEquals("gurukula", d.getTitle());

      // System.out.println("Welcome");

   }

   // @Ignore
   @Test
   public void loginAsAdmin() {

      d.findElement(By.linkText("login")).click();
      WebElement authenticateBtn = w.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-primary")));
      assertEquals("Authentication", d.getTitle());
      d.findElement(By.id("username")).sendKeys("admin");
      d.findElement(By.id("password")).sendKeys("admin");
      authenticateBtn.click();
      WebElement alertSuccess = d.findElement(By.className("alert-success"));
      assertEquals("You are logged in as user \"admin\".", alertSuccess.getText());
      logout();

      // System.out.println("Login As Admin");

   }

   // @Ignore
   @Test
   public void logOut() {

      login();
      assertEquals("gurukula", d.getTitle());
      logout();
      WebElement alertWarning = d.findElement(By.className("alert-warning"));
      assertEquals("Click here to login", alertWarning.getText());

      // System.out.println("Logout");

   }

   // @Ignore
   @Test
   public void registerUser() {

      d.findElement(By.linkText("Register a new account")).click();
      WebElement registerBtn = w.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-primary")));
      assertEquals("Registration", d.getTitle());
      d.findElement(By.name("login")).sendKeys("testuser");
      d.findElement(By.name("email")).sendKeys("test_user@domain.net");
      d.findElement(By.name("password")).sendKeys("01234");
      d.findElement(By.name("confirmPassword")).sendKeys("01234");
      registerBtn.click();
      WebElement alertSuccess = d.findElement(By.className("alert-success"));
      if (alertSuccess.isDisplayed()) {
         assertEquals("User registered!", alertSuccess.getText());
      } else {
         WebElement alertDanger = d.findElement(By.className("alert-danger"));
         assertEquals("Registration failed! Please try again later.", alertDanger.getText());
         fail("Registration failed!");
      }

      // System.out.println("Register User");

   }

   // @Ignore
   @Test
   public void accountSettings() {

      login();

      assertEquals("gurukula", d.getTitle());
      d.findElement(By.linkText("Account")).click();
      d.findElement(By.linkText("Settings")).click();
      assertEquals("Settings", d.getTitle());
      d.findElement(By.name("firstName")).sendKeys("New");
      d.findElement(By.className("btn-primary")).click();
      WebElement alertSuccess = d.findElement(By.className("alert-success"));
      if (alertSuccess.isDisplayed()) {
         assertEquals("Settings saved!", alertSuccess.getText());
         logout();
      } else {
         WebElement alertDanger = d.findElements(By.className("alert-danger")).get(1);
         assertEquals("An error has occurred! Settings could not be saved.", alertDanger.getText());
         logout();
         fail("Settings could not be saved.");
      }

      // System.out.println("Account Settings");

   }

   // @Ignore
   @Test
   public void crudBranch() {

      login();
      createBranch();

      // C
      WebElement branchTable = d.findElement(By.className("table-striped"));
      List<WebElement> branchTableRows = branchTable.findElements(By.className("ng-binding"));
      String id = branchTableRows.get(0).getText();
      assertEquals("Branch", branchTableRows.get(1).getText());
      assertEquals("BR", branchTableRows.get(2).getText());
      // R
      WebElement viewBtn = branchTable.findElement(By.className("btn-info"));
      viewBtn.click();
      assertEquals("Branch", d.getTitle());
      WebElement banner = d.findElement(By.className("ng-binding"));
      assertEquals("Branch " + id, banner.getText());
      d.findElement(By.className("btn-info")).click();
      // U
      branchTable = d.findElement(By.className("table-striped"));
      WebElement editBtn = branchTable.findElement(By.className("btn-primary"));
      editBtn.click();
      WebElement editForm = w.until(ExpectedConditions.visibilityOfElementLocated(By.name("editForm")));
      editForm.findElement(By.name("name")).sendKeys("renamed");
      WebElement saveBtn = editForm.findElement(By.className("btn-primary"));
      saveBtn.click();
      w.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-backdrop")));
      branchTableRows = branchTable.findElements(By.className("ng-binding"));
      assertEquals("Branchrenamed", branchTableRows.get(1).getText());
      // D
      branchTable = d.findElement(By.className("table-striped"));
      WebElement deleteBtn = branchTable.findElement(By.className("btn-danger"));
      deleteBtn.click();
      WebElement deleteForm = w.until(ExpectedConditions.visibilityOfElementLocated(By.name("deleteForm")));
      banner = deleteForm.findElement(By.className("modal-body"));
      assertEquals("Are you sure you want to delete Branch " + id + "?", banner.getText());
      deleteForm.findElement(By.className("btn-danger")).click();
      w.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-backdrop")));
      branchTable = d.findElement(By.className("table-striped"));
      branchTableRows = branchTable.findElements(By.className("ng-binding"));
      assertEquals(0, branchTableRows.size());

      logout();

      // System.out.println("C, R, U, D Branches");

   }

   // @Ignore
   @Test
   public void crudStaff() {

      login();
      createBranch();
      createStaff();

      // C
      WebElement staffTable = d.findElement(By.className("table-striped"));
      List<WebElement> staffTableRows = staffTable.findElements(By.className("ng-binding"));
      String id = staffTableRows.get(0).getText();
      assertEquals("Staff Staff", staffTableRows.get(1).getText());
      assertEquals("Branch", staffTableRows.get(2).getText());
      // R
      WebElement viewBtn = staffTable.findElement(By.className("btn-info"));
      viewBtn.click();
      assertEquals("Staff", d.getTitle());
      WebElement banner = d.findElement(By.className("ng-binding"));
      assertEquals("Staff " + id, banner.getText());
      d.findElement(By.className("btn-info")).click();
      // U
      staffTable = d.findElement(By.className("table-striped"));
      WebElement editBtn = staffTable.findElement(By.className("btn-primary"));
      editBtn.click();
      WebElement editForm = w.until(ExpectedConditions.visibilityOfElementLocated(By.name("editForm")));
      editForm.findElement(By.name("name")).sendKeys("renamed");
      WebElement saveBtn = editForm.findElement(By.className("btn-primary"));
      saveBtn.click();
      w.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-backdrop")));
      staffTableRows = staffTable.findElements(By.className("ng-binding"));
      assertEquals("Staff Staffrenamed", staffTableRows.get(1).getText());
      // D
      staffTable = d.findElement(By.className("table-striped"));
      WebElement deleteBtn = staffTable.findElement(By.className("btn-danger"));
      deleteBtn.click();
      WebElement deleteForm = w.until(ExpectedConditions.visibilityOfElementLocated(By.name("deleteForm")));
      banner = deleteForm.findElement(By.className("modal-body"));
      assertEquals("Are you sure you want to delete Staff " + id + "?", banner.getText());
      deleteForm.findElement(By.className("btn-danger")).click();
      w.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-backdrop")));
      staffTable = d.findElement(By.className("table-striped"));
      staffTableRows = staffTable.findElements(By.className("ng-binding"));
      assertEquals(0, staffTableRows.size());

      deleteBranch();
      logout();

      // System.out.println("C, R, U, D Staffs");

   }

   @AfterClass
   public static void tearDown() {

      d.close();
      System.out.println("Teardown");

   }

   private void logout() {

      d.findElement(By.linkText("Account")).click();
      d.findElement(By.linkText("Log out")).click();

   }

   private void createStaff() {

      d.findElement(By.linkText("Entities")).click();
      d.findElement(By.linkText("Staff")).click();
      WebElement addStaff = w.until(ExpectedConditions.elementToBeClickable(By.className("btn-primary")));
      addStaff.click();
      WebElement editForm = w.until(ExpectedConditions.visibilityOfElementLocated(By.name("editForm")));
      d.findElement(By.name("name")).sendKeys("Staff Staff");
      Select branchList = new Select(d.findElement(By.name("related_branch")));
      branchList.selectByVisibleText("Branch");
      editForm.submit();
      w.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-backdrop")));

   }

   private void login() {

      d.findElement(By.linkText("login")).click();
      WebElement authenticateBtn = w.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-primary")));
      d.findElement(By.id("username")).sendKeys("admin");
      d.findElement(By.id("password")).sendKeys("admin");
      authenticateBtn.click();

   }

   private void createBranch() {

      d.findElement(By.linkText("Entities")).click();
      d.findElement(By.linkText("Branch")).click();
      WebElement addBranch = w.until(ExpectedConditions.elementToBeClickable(By.className("btn-primary")));
      addBranch.click();
      WebElement editForm = w.until(ExpectedConditions.visibilityOfElementLocated(By.name("editForm")));
      d.findElement(By.name("name")).sendKeys("Branch");
      d.findElement(By.name("code")).sendKeys("BR");
      editForm.submit();
      w.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-backdrop")));

   }

   private void deleteBranch() {

      d.findElement(By.linkText("Entities")).click();
      d.findElement(By.linkText("Branch")).click();
      WebElement branchTable = d.findElement(By.className("table-striped"));
      WebElement deleteBtn = branchTable.findElement(By.className("btn-danger"));
      deleteBtn.click();
      WebElement deleteForm = w.until(ExpectedConditions.visibilityOfElementLocated(By.name("deleteForm")));
      deleteForm.findElement(By.className("btn-danger")).click();
      w.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-backdrop")));

   }

}