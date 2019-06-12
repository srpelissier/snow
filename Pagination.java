import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Ignore;
import static org.junit.Assert.*;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Random;
import java.util.List;

public class Pagination {

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
      login("admin", "admin");

   }

   // @Ignore
   @Test
   public void branchPagination() {

      createBranches(0);
      List<WebElement> pager = d.findElements(By.className("pager"));
      if (pager.size() != 0) {

         String[] pagerControls = pagerStatus(pager.get(0));

         assertEquals("visible", pagerControls[0]);
         assertEquals("hidden", pagerControls[1]);
         assertEquals("hidden", pagerControls[2]);
         assertEquals("hidden", pagerControls[3]);

      } else {

         logout();
         fail("Pager not found in Branches!");

      }
      // createBranches(1) {}
      // Assert pager controls

      // createBranches(20) {}
      // Assert pager controls

      // Display next page
      // Assert num_rows

      // Display first page
      // Assert num_rows

      // Display last page
      // Assert num_rows

      // Display previous page
      // Assert num_rows

      // Delete alla branches
      // Log out

   }

   // @Ignore
   @Test
   public void staffPagination() {

      createStaffs(0);
      WebElement pager = d.findElement(By.className("pager"));
      String[] pagerControls = pagerStatus(pager);

      assertEquals("visible", pagerControls[0]);
      assertEquals("hidden", pagerControls[1]);
      assertEquals("hidden", pagerControls[2]);
      assertEquals("hidden", pagerControls[3]);

      createStaffs(1);
      pager = d.findElement(By.className("pager"));
      pagerControls = pagerStatus(pager);

      assertEquals("visible", pagerControls[0]);
      assertEquals("hidden", pagerControls[1]);
      assertEquals("hidden", pagerControls[2]);
      assertEquals("visible", pagerControls[3]);

      // 20+ entries to trigger pagination
      createStaffs(20); // Pagination limit
      pager = d.findElement(By.className("pager"));
      pagerControls = pagerStatus(pager);

      assertEquals("visible", pagerControls[0]);
      assertEquals("hidden", pagerControls[1]);
      assertEquals("visible", pagerControls[2]);
      assertEquals("visible", pagerControls[3]);

      // Display next page
      pager = d.findElement(By.className("pager"));
      ((JavascriptExecutor) d).executeScript("arguments[0].scrollIntoView();", pager);
      pager.findElements(By.tagName("li")).get(2).click();
      // There should be one row
      WebElement staffTable = d.findElement(By.className("table-striped"));
      List<WebElement> staffTableRows = staffTable.findElements(By.className("ng-scope"));

      assertEquals(6, staffTableRows.size()); // num_rows x 4 + 2

      // Display first page
      pager = d.findElement(By.className("pager"));
      pager.findElements(By.tagName("li")).get(0).click();
      // There should be 20 rows
      staffTable = d.findElement(By.className("table-striped"));
      staffTableRows = staffTable.findElements(By.className("ng-scope"));

      assertEquals(82, staffTableRows.size()); // num_rows x 4 + 2

      // Display last page
      // There should be one row
      // Display previous page
      // There should be 20 rows
      deleteAllStaffs();
      logout();

   }

   @AfterClass
   public static void tearDown() {

      d.close();

   }

   private void createBranches(int total) {

      d.findElement(By.linkText("Entities")).click();
      d.findElement(By.linkText("Branch")).click();
      String name = "";
      String code = "";
      for (int currentBranch = 0; currentBranch < total; currentBranch++) {

         name = "";
         int nameSize = (int) ((Math.random() * 16) + 5); // between 5 and 20 characters
         for (int i = 0; i < nameSize; i++) {

            name += (char) ((Math.random() * 26) + 97); // from 'a' to 'z'

         }
         code = "";
         int codeSize = (int) ((Math.random() * 9) + 2);
         for (int i = 0; i < codeSize; i++) {

            code += (int) (Math.random() * 10);

         }
         createBranch(name, code);
      }

   }

   private void createBranch(String name, String code) {

      WebElement addBranch = w.until(ExpectedConditions.elementToBeClickable(By.className("btn-primary")));
      addBranch.click();
      WebElement editForm = w.until(ExpectedConditions.visibilityOfElementLocated(By.name("editForm")));
      d.findElement(By.name("name")).sendKeys(name);
      d.findElement(By.name("code")).sendKeys(code);
      editForm.submit();
      w.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-backdrop")));

   }

   private void deleteAllBranches() {

      d.findElement(By.linkText("Entities")).click();
      d.findElement(By.linkText("Branch")).click();
      WebElement branchTable = d.findElement(By.className("table-striped"));
      List<WebElement> branchTableRows = branchTable.findElements(By.className("ng-scope"));
      while (branchTable.findElements(By.className("ng-scope")).size() > 2) {

         deleteBranch();

      }

   }

   private void deleteBranch() {

      WebElement branchTable = d.findElement(By.className("table-striped"));
      WebElement deleteBtn = branchTable.findElement(By.className("btn-danger"));
      deleteBtn.click();
      WebElement deleteForm = w.until(ExpectedConditions.visibilityOfElementLocated(By.name("deleteForm")));
      deleteForm.findElement(By.className("btn-danger")).click();
      w.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-backdrop")));

   }

   private void createStaffs(int total) {

      d.findElement(By.linkText("Entities")).click();
      d.findElement(By.linkText("Staff")).click();
      String name = "";
      for (int currentStaff = 0; currentStaff < total; currentStaff++) {

         name = "";
         int nameSize = (int) ((Math.random() * 50) + 1); // between 1 and 50 characters
         for (int i = 0; i < nameSize; i++) {

            name += (char) ((Math.random() * 26) + 97); // from 'a' to 'z'

         }
         if (total > 0) {

            createStaff(name);

         }

      }

   }

   private void createStaff(String name) {

      WebElement addStaff = w.until(ExpectedConditions.elementToBeClickable(By.className("btn-primary")));
      addStaff.click();
      WebElement editForm = w.until(ExpectedConditions.visibilityOfElementLocated(By.name("editForm")));
      d.findElement(By.name("name")).sendKeys(name);
      editForm.submit();
      w.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-backdrop")));

   }

   private void deleteAllStaffs() {
      d.findElement(By.linkText("Entities")).click();
      d.findElement(By.linkText("Staff")).click();
      WebElement staffTable = d.findElement(By.className("table-striped"));
      List<WebElement> staffTableRows = staffTable.findElements(By.className("ng-scope"));
      while (staffTable.findElements(By.className("ng-scope")).size() > 2) {

         deleteStaff();

      }
   }

   private void deleteStaff() {

      WebElement staffTable = d.findElement(By.className("table-striped"));
      WebElement deleteBtn = staffTable.findElement(By.className("btn-danger"));
      deleteBtn.click();
      WebElement deleteForm = w.until(ExpectedConditions.visibilityOfElementLocated(By.name("deleteForm")));
      deleteForm.findElement(By.className("btn-danger")).click();
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

   private void logout() {

      d.findElement(By.linkText("Account")).click();
      d.findElement(By.linkText("Log out")).click();

   }

   private String[] pagerStatus(WebElement pager) {

      String[] status = new String[4];
      List<WebElement> pagerControls = pager.findElements(By.tagName("li"));
      for (int i = 0; i < 4; i++) {

         String value = pagerControls.get(i).getText();
         status[i] = (value.length() == 0) ? "hidden" : "visible";

      }
      return status;

   }

}
