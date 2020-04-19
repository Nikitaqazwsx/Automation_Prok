package pages;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class ProductSearchPage extends BasePage {
    
    //method to return the product text
    public List<WebElement> getProductSearchResult() {
        
        List<WebElement> results= driver.findElements(By.xpath("//div[@class='right-block']//a[@class='product-name']"));
        
        return results;
    }
    
    public boolean validateSearchResults(String searchWord) {
    	List<WebElement> results=getProductSearchResult();
    	if(results.size()<=0) {
    		test.fail("There are no Results returned for the serach");
    		return false;
    	}
    	
    	boolean isResultsMatched=true;
    	for(WebElement ele:results) {
    		if(ele.getText().contains(searchWord)) {
    			test.pass("Seach Result"+ele.getText());
    		}
    		else {
    			isResultsMatched=false;
    			test.error("Search Result "+ele.getText()+" does not contain "+searchWord);
    			
    		}
    	}
    	
    	return isResultsMatched;
    	
	}
    
    public ProductPage clickOnProduct(String productName) {
    	
    	WebElement productLink=driver.findElement(By.xpath("//div[@class='right-block']//a[@class='product-name' and @title='"+productName+"']"));
    	productLink.click();
    	return new ProductPage(productName);
    }
    
   

}
