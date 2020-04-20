package automationPractise;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;

import pages.BasePage;
import pages.LandingPage;
import pages.ProductPage;
import pages.ProductSearchPage;
import pages.SummaryPage;

public class LandingPageTest extends DriverTest {
 

	@Test
  public void searchAProduct() {
	//Declare an object of class ProductSearchPage class
      ProductSearchPage pr;
      
      //Instantiate LandingPage class object
      LandingPage landingPage = new LandingPage();
      
      //Verify the titles
     /* if(landingPage.getTitle().equals(landingPage.getTitle())) {
         BasePage.test.pass("Title Matched - Test Passes");
      }else {
    	  BasePage.test.pass("Title doesn't match - Test Failed");
      }*/
      
      if(landingPage.getTitle().equals("My Store")) {
          BasePage.test.pass("Title Matched - Test Passes");
       }else {
     	  BasePage.test.pass("Title doesn't match - Test Failed");
       }
      
      //Navigate to Product Search page
      pr=landingPage.searchProduct("Dress");
      
      if(!pr.validateSearchResults("Dress")) {
    	  Assert.fail("Results shown on Page does not Contain the seach Keyword");
      }
  }
  
 
@Test
  public void AddingToACart() {
	  
	  LandingPage landingPage = new LandingPage();
      
      //Verify the titles
      /*if(landingPage.getTitle().equals(landingPage.getTitle())) {
         BasePage.test.pass("Title Matched - Test Passes");
      }else {
    	  BasePage.test.pass("Title doesn't match - Test Failed");
      }*/
      
      //Navigate to Product Search page
      ProductSearchPage pr=landingPage.searchProduct("Dress");
      ProductPage pg=pr.clickOnProduct("Printed Summer Dress");
      pg.verifyPageTitle();
      
      SummaryPage sp=pg.addToCart();
      
      sp.verifyProductDescription();
      
  }
  
  @Test(testName = "Modify The Cart Contents")
  public void ModifyingCartItems() {

	  LandingPage landingPage = new LandingPage();
      
      /*//Verify the titles
      if(landingPage.getTitle().equals(landingPage.getTitle())) {
         BasePage.test.pass("Title Matched - Test Passes");
      }else {
    	  BasePage.test.pass("Title doesn't match - Test Failed");
      }*/
      
      //Navigate to Product Search page
      ProductSearchPage pr=landingPage.searchProduct("Dress");
      ProductPage pg=pr.clickOnProduct("Printed Summer Dress");
      pg.verifyPageTitle();
      
      SummaryPage sp=pg.addToCart();
	
	  sp.verifyProductDescription();
	  
	  
	  //Adding second Dress
	  pr=landingPage.searchProduct("Dress"); 
	  pg=pr.clickOnProduct("Printed Dress");
	  pg.selectColour("Pink"); 
	  pg.selectQuantity("3"); 
	  pg.addToCart();
	 
	//Adding Third Dress
	  pr=landingPage.searchProduct("Dress");
	  pg=pr.clickOnProduct("Printed Chiffon Dress"); sp=pg.addToCart();
	 
	  
      JsonObject printeddressDetails=sp.getProductDetails("Printed Dress");
      Boolean isFailed=true;
      isFailed=matchValue(printeddressDetails.get("qty").getAsString().replace("\"", ""), "3")&&isFailed;
      isFailed=matchValue(printeddressDetails.get("total").getAsString().replace("\"", ""), "$152.97")&&isFailed;
      isFailed=matchValue(printeddressDetails.get("color").getAsString().replace("\"", ""), "Color : Pink, Size : S")&&isFailed;
      
      JsonObject printedChiffDressDetails=sp.getProductDetails("Printed Chiffon Dress");
      isFailed=matchValue(printedChiffDressDetails.get("qty").getAsString().replace("\"", ""), "1")&&isFailed;
      
      
      //Removing the dress from summary
      isFailed=sp.removeItem("Printed Chiffon Dress")&&isFailed;
      
      
      //updating the Printed Dress QTY
      
   
	  pr=landingPage.searchProduct("Dress"); 
	  pg=pr.clickOnProduct("Printed Dress");
	  pg.selectColour("Pink"); 
	  pg.selectQuantity("1"); 
	  pg.addToCart();
	  
	  printeddressDetails=sp.getProductDetails("Printed Dress");
      isFailed=matchValue(printeddressDetails.get("qty").getAsString().replace("\"", ""), "4")&&isFailed;
      isFailed=matchValue(printeddressDetails.get("total").getAsString().replace("\"", ""), "$203.96")&&isFailed;
      
      
      if(!isFailed) {
    	  BasePage.test.fatal("Test Failed Values did not match");
    	  Assert.fail("Values did not match with Expected values");
      }
      
  }
  
  
  public boolean matchValue(String val1,String val2) {
	  
	  if(val1.equalsIgnoreCase(val2)) {
		  BasePage.test.pass("Expected <Strong>"+val1+"</Strong> Actual <Strong>"+val2+"</Strong>");
		  return true;
	  }else {
		  BasePage.test.error("Expected <Strong>"+val1+"</Strong> Actual <Strong>"+val2+"</Strong>");
		  return false;
	  }
  }
  
  
}
