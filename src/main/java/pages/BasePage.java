package pages;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import utils.ExtentReporting;

public class BasePage {
    
    
    //Declare the WebDriver
    public static WebDriver driver;
    
    public static Logger log = Logger.getLogger(BasePage.class);
  //declare variable for config.properties 
    public static Properties config = new Properties();
    
    //File input stream to read from properties file
    public static FileInputStream fis;
    
    
    //get the reference of ExtentReports object to instantiate HTML reporting
    public static ExtentReports report =new ExtentReports();
    
    public static ExtentHtmlReporter htmlReporter;
    //indicate which test to include in report
    public static ExtentTest test;
    
    
    public BasePage() {
    	super();
    }
    //constructor of base class
    public BasePage(String s) {
    	PropertyConfigurator.configure(System.getProperty("user.dir")+"/src/main/resources/log4j.properties");
    	System.setProperty("webdriver.chrome.driver","chromeDriver.exe");
        if (true) {
			
        	try {
                log.debug("Checking if logs are displayed or not");
                //Take reference to the file
                fis = new FileInputStream(System.getProperty("user.dir")
                        + "/src/test/resources/properties/config.properties");
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                
                //load the config.properties file
                config.load(fis);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        	if(config.getProperty("browser").equals("chrome")) {
        		driver= new ChromeDriver();
        	}else {
        		System.out.println("Browser Type is unknown");
        	}
            
            
            //go to application
            driver.get(config.getProperty("appurl"));
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            driver.manage().window().maximize();
        }
            
    }
    
    public static void quitDriver() {
        driver.close();
    }
    public String getTitle() {
    	String title=driver.getTitle();
    	log.info("Title of the Page is "+title);
    	test.log(Status.PASS, "Title of the Page is "+title);
        return title;
    }
    

}
