import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Ignore;
import static org.junit.Assert.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.TimeoutException;

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

      createBranches(1);
      List<WebElement> pager = d.findElements(By.className("pager"));
      if (pager.size() > 0) {
         String[] pagerControls = pagerStatus(pager.get(0));
         assertEquals("visible", pagerControls[0]);
         assertEquals("hidden", pagerControls[1]);
         assertEquals("hidden", pagerControls[2]);
         assertEquals("visible", pagerControls[3]);
         deleteAllBranches();
         logout();
      } else {
         logout();
         fail("Expected condition failed: waiting for element located by By.className: pager");
      }

   }

   // @Ignore
   @Test
   public void staffPagination() {

      createStaffs(1);
      List<WebElement> pager = d.findElements(By.className("pager"));
      String[] pagerControls = pagerStatus(pager.get(0));
      assertEquals("visible", pagerControls[0]);
      assertEquals("hidden", pagerControls[1]);
      assertEquals("hidden", pagerControls[2]);
      assertEquals("visible", pagerControls[3]);
      deleteAllStaffs();
      logout();

   }

   @AfterClass
   public static void tearDown() {

      d.close();

   }

   private void createBranches(int total) {

      String name = "";
      String code = "";
      for (int currentBranch = 0; currentBranch < total; currentBranch++) {
         name = "";
         int nameSize = (int) ((Math.random() * 16) + 5); // between 5 and 20
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

      d.findElement(By.linkText("Entities")).click();
      d.findElement(By.linkText("Branch")).click();
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
      // System.out.println(branchTableRows.size());
      while (branchTable.findElements(By.className("ng-scope")).size() > 2) {
         deleteTopBranch();
      }
   }

   private void deleteTopBranch() {

      d.findElement(By.linkText("Entities")).click();
      d.findElement(By.linkText("Branch")).click();
      WebElement branchTable = d.findElement(By.className("table-striped"));
      WebElement deleteBtn = branchTable.findElement(By.className("btn-danger"));
      deleteBtn.click();
      WebElement deleteForm = w.until(ExpectedConditions.visibilityOfElementLocated(By.name("deleteForm")));
      deleteForm.findElement(By.className("btn-danger")).click();
      w.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-backdrop")));

   }

   private void createStaffs(int total) {

      String name = "";
      String code = "";
      for (int currentStaff = 0; currentStaff < total; currentStaff++) {
         name = "";
         int nameSize = (int) ((Math.random() * 50) + 1); // between 1 and 50
         for (int i = 0; i < nameSize; i++) {
            name += (char) ((Math.random() * 26) + 97); // from 'a' to 'z'
         }

         createStaff(name);
      }

   }

   private void createStaff(String name) {

      d.findElement(By.linkText("Entities")).click();
      d.findElement(By.linkText("Staff")).click();
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
      // System.out.println(staffTableRows.size());
      while (staffTable.findElements(By.className("ng-scope")).size() > 2) {
         deleteTopStaff();
      }
   }

   private void deleteTopStaff() {

      d.findElement(By.linkText("Entities")).click();
      d.findElement(By.linkText("Staff")).click();
      WebElement staffTable = d.findElement(By.className("table-striped"));
      WebElement deleteBtn = staffTable.findElement(By.className("btn-danger"));
      deleteBtn.click();
      WebElement deleteForm = w.until(ExpectedConditions.visibilityOfElementLocated(By.name("deleteForm")));
      deleteForm.findElement(By.className("btn-danger")).click();
      w.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-backdrop")));

   }

   public void login(String userName, String password) {

      d.findElement(By.linkText("login")).click();
      WebElement authenticateBtn = w.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-primary")));
      d.findElement(By.id("username")).sendKeys(userName);
      d.findElement(By.id("password")).sendKeys(password);
      authenticateBtn.click();

   }

   public void logout() {

      d.findElement(By.linkText("Account")).click();
      d.findElement(By.linkText("Log out")).click();

   }

   public String[] pagerStatus(WebElement pager) {

      String[] status = new String[4];
      List<WebElement> pagerControls = pager.findElements(By.tagName("li"));
      for (int i = 0; i < 4; i++) {

         String value = pagerControls.get(i).getText();
         status[i] = (value.length() == 0) ? "hidden" : "visible";

      }
      return status;

   }

}