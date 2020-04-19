package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.json.Json;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.JsonObject;

public class ProductPage extends BasePage{

	String colourPicker="//a[contains(@class,'color_pick') and @title='****']";
	
	String pageName;
	int quantity=1;
	String size="S";
	int prize=0;
	public ProductPage(String pageName) {
		super();
		this.pageName=pageName;
	}
	
	public void verifyPageTitle() {
		if(getTitle().contains(pageName)) {
			test.pass("Title of the page contains "+pageName);
		}else {
			test.error("Title of the page is "+getTitle()+" Expected title "+pageName);
		}
	}
	
	public void selectColour(String colour) {
		
		colourPicker=colourPicker.replace("****", colour);
		driver.findElement(By.xpath(colourPicker)).click();
	}
	
	public void selectQuantity(String quantity) {
		try {
		this.quantity=Integer.parseInt(quantity);
		driver.findElement(By.id("quantity_wanted")).clear();
		driver.findElement(By.id("quantity_wanted")).sendKeys(quantity);
		}
		catch (NumberFormatException e) {
			test.fail("Unable to convert "+quantity+" to Integer");
			throw new NumberFormatException("Unable to convert "+quantity+" to Integer");
		}
	}
	
	public SummaryPage addToCart() {
		
		JsonObject product= new JsonObject();
		product.addProperty("quantity", this.quantity);
		product.addProperty("size", size);
		product.addProperty("price", driver.findElement(By.id("our_price_display")).getText().replace("$", ""));
		product.addProperty("name",pageName);
		
		
		driver.findElement(By.xpath("//p[@id='add_to_cart']/button")).click();
		
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title=\"Proceed to checkout\"]")));
		
		driver.findElement(By.xpath("//a[@title=\"Proceed to checkout\"]")).click();
		
		return new SummaryPage(product);
	}
	
	
	
}
