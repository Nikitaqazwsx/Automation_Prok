package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.Status;

public class LandingPage extends BasePage {
    
	//First Test. This title should be verified in LandingPageTest class
    
    
    
    //Searching for a product at landing page returns (displays) product search page. Hence 
    //the return type is ProductSearchPage and return value is new ProductSearchPage() 
    public ProductSearchPage searchProduct(String searchString) {
        
        //Enter text dress in the search text box
        driver.findElement(By.id("search_query_top")).sendKeys(searchString);
        log.info("Entered dress in the Serach");
        test.log(Status.PASS, "Entered dress in the Serach");
        //Click on Search button
        driver.findElement(By.name("submit_search")).click();
        
        test.log(Status.PASS, "Clicked on Search");
        
        //Product search page is displayed
        return new ProductSearchPage();
        
    }
    
	/*
	 * //Click on Sign In button public ProductPage clickOnSignIn() {
	 * 
	 * driver.findElement(By.xpath("//a[@class='login']")).click();
	 * 
	 * //clicking on Sign In button will display Sign In page //return new
	 * ProductPage(); }
	 */
    

}