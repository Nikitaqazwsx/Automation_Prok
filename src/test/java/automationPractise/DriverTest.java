package automationPractise;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import pages.BasePage;

import org.testng.annotations.BeforeTest;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class DriverTest {
  
  @BeforeMethod
  public void beforeMethod(Method method) {
	  BasePage.test=BasePage.report.createTest(method.getName());
	  new BasePage("s");
  }

  @AfterMethod
  public void afterMethod() {
	  
	  
		/*
		 * if(result.getStatus()==ITestResult.SUCCESS) {
		 * BasePage.test.pass(result.getTestName()+" Passed"); } else
		 * if(result.getStatus()==ITestResult.FAILURE) {
		 * BasePage.test.fatal(result.getTestName()+" Failed"); }
		 */
	  
	 BasePage.quitDriver();
  }

  @BeforeSuite
  public void beforeSuite() {
	  BasePage.htmlReporter=new ExtentHtmlReporter("TestReport.html");
	  BasePage.report.attachReporter(BasePage.htmlReporter);
	  BasePage.report.setSystemInfo("Host Name", "TTT");
	  BasePage.report.setSystemInfo("Environment", "YYY");
	  BasePage.report.setSystemInfo("User Name", "JJJ");
  		BasePage.htmlReporter.config().setDocumentTitle("Pro-Karma"); 
          // Name of the report
  		BasePage.htmlReporter.config().setReportName("Automation Practise Tests"); 
          // Dark Theme
  		BasePage.htmlReporter.config().setTheme(Theme.DARK); 
  }
  
  @AfterSuite
  public void afterSuite() {
	  //quitDriver();
	  BasePage.report.flush();
	  
  }
  
}
