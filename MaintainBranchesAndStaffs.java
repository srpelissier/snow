import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MaintainBranchesAndStaffs {

   static WebDriver d;
   static WebDriverWait w;

   @BeforeClass
   public static void init() {

      System.setProperty("webdriver.firefox.bin", "/usr/bin/firefox-developer-edition");
      d = new FirefoxDriver();
      w = new WebDriverWait(d, 5);

   }

   @Before
   public void welcome() {

      d.get("http://localhost:8080");
      assertEquals("gurukula", d.getTitle());

   }

   // @Ignore
   @Test
   public void logIn() {

      login("admin", "admin");
      WebElement alertSuccess = d.findElement(By.className("alert-success"));
      assertEquals("You are logged in as user \"admin\".", alertSuccess.getText());
      logout();

   }

   // @Ignore
   @Test
   public void logOut() {

      login("admin", "admin");

      assertEquals("gurukula", d.getTitle());

      logout();
      WebElement alertWarning = d.findElement(By.className("alert-warning"));

      assertEquals("Click here to login", alertWarning.getText());

   }

   // @Ignore
   @Test
   public void registerUser() throws Exception {

      w.until(ExpectedConditions.elementToBeClickable(By.linkText("Register a new account"))).click();
      WebElement registerBtn = w.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-primary")));
      d.findElement(By.name("login")).sendKeys("testuser");
      d.findElement(By.name("email")).sendKeys("test_user@domain.net");
      d.findElement(By.name("password")).sendKeys("01234");
      d.findElement(By.name("confirmPassword")).sendKeys("01234");
      registerBtn.click();

      WebElement alertSuccess = d.findElement(By.className("alert-success"));
      if (alertSuccess.isDisplayed()) {

         assertEquals("Registration sa ved! Please check your email for confirmation.", alertSuccess.getText());

      } else {

         WebElement alertDanger = d.findElement(By.className("alert-danger"));

         assertEquals("Registration failed! Please try again later.", alertDanger.getText());

         fail("Registration failed!");

      }

   }

   // @Ignore
   @Test
   public void accountSettings() {

      login("admin", "admin");
      w.until(ExpectedConditions.elementToBeClickable(By.linkText("Account"))).click();
      w.until(ExpectedConditions.elementToBeClickable(By.linkText("Settings"))).click();
      d.findElement(By.name("firstName")).sendKeys("New");
      d.findElement(By.className("btn-primary")).click();

      WebElement alertSuccess = d.findElement(By.className("alert-success"));
      if (alertSuccess.isDisplayed()) {

         assertEquals("Settings saved!", alertSuccess.getText());

         logout();

      } else {

         WebElement alertOther = d.findElements(By.className("alert-danger")).get(1); // get(0) is "email in use"

         assertEquals("An error has occurred! Settings could not be saved.", alertOther.getText());

         logout();
         fail("Settings could not be saved.");

      }

   }

   // @Ignore
   @Test
   public void crudBranch() {

      login("admin", "admin");
      createBranch();
      // C
      WebElement branchTable = d.findElement(By.className("table-striped"));
      List<WebElement> branchTableRows = branchTable.findElements(By.className("ng-binding"));
      String id = branchTableRows.get(0).getText(); // Compare with Pagination's method

      assertEquals("Branch", branchTableRows.get(1).getText());
      assertEquals("BR", branchTableRows.get(2).getText());
      // R
      WebElement viewBtn = branchTable.findElement(By.className("btn-info"));
      viewBtn.click();
      WebElement banner = d.findElement(By.className("ng-binding"));

      assertEquals("Branch " + id, banner.getText());
      d.findElement(By.className("btn-info")).click(); // form's Back button
      // U
      branchTable = d.findElement(By.className("table-striped"));
      WebElement editBtn = branchTable.findElement(By.className("btn-primary"));
      editBtn.click();
      WebElement editForm = w.until(ExpectedConditions.visibilityOfElementLocated(By.name("editForm")));
      editForm.findElement(By.name("name")).sendKeys("renamed"); // appending
      WebElement saveBtn = editForm.findElement(By.className("btn-primary"));
      saveBtn.click();
      w.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-backdrop")));
      branchTable = d.findElement(By.className("table-striped"));
      branchTableRows = branchTable.findElements(By.className("ng-binding"));

      assertEquals("Branchrenamed", branchTableRows.get(1).getText()); // assuming this is the most recent branch
      // D
      WebElement deleteBtn = branchTable.findElement(By.className("btn-danger")); // assuming it is unique
      deleteBtn.click();
      WebElement deleteForm = w.until(ExpectedConditions.visibilityOfElementLocated(By.name("deleteForm")));
      banner = deleteForm.findElement(By.className("modal-body"));

      assertEquals("Are you sure you want to delete Branch " + id + "?", banner.getText());

      deleteForm.findElement(By.className("btn-danger")).click();
      w.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-backdrop")));
      branchTable = d.findElement(By.className("table-striped"));
      branchTableRows = branchTable.findElements(By.className("ng-binding"));

      assertEquals(0, branchTableRows.size()); // no more branch

      logout();

   }

   // @Ignore
   @Test
   public void crudStaff() {

      login("admin", "admin");
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

   }

   @AfterClass
   public static void tearDown() {

      d.close();

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

   private void login(String userName, String password) {

      WebElement loginBtn = w.until(ExpectedConditions.elementToBeClickable(By.linkText("login")));
      loginBtn.click();
      WebElement authenticateBtn = w.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-primary")));
      d.findElement(By.id("username")).sendKeys(userName);
      d.findElement(By.id("password")).sendKeys(password);
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