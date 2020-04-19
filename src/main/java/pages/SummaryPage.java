package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.google.gson.JsonObject;

public class SummaryPage extends BasePage{
	
	JsonObject productDetails;
	public SummaryPage(JsonObject obj) {
		super();
		productDetails=obj;
	}
	
	public int verifyProductDescription() {
		return verifyProductDescription(productDetails.get("name").getAsString());
	}
	
	public int verifyProductDescription(String productName) {
		int descriptionColNum=getColumnNumber(driver.findElement(By.id("cart_summary")), "Description");
		List<WebElement> rows= driver.findElements(By.xpath("//tr[contains(@id,'product')]"));
		return searchInColumn(rows, descriptionColNum, productName);
	}
	
	
	public int getColumnNumber(WebElement table,String columnName) {
		List<WebElement> columns=table.findElements(By.xpath("//th"));
		for(int i=0;i<columns.size();i++) {
			if(columns.get(i).getText().equalsIgnoreCase(columnName)) {
				test.pass(columnName+" is found at "+i);
				return (i+1);
			}
		}
		return -1;
	}
	
	public boolean removeItem(String productName) {
		int rowNum=verifyProductDescription(productName);
		List<WebElement> rows= driver.findElements(By.xpath("//tr[contains(@id,'product')]"));
		if(rowNum>=0) {
			rows.get(rowNum).findElement(By.xpath("./td[@class=\"cart_delete text-center\"]//a")).click();
			test.pass("Deleted the Product "+productName);
			return true;
		}
		test.error(productName+" Not found i Table");
		return false;
	}
	
	public JsonObject getProductDetails(String productName) {
		List<WebElement> rows= driver.findElements(By.xpath("//tr[contains(@id,'product')]"));
		int rowNum=verifyProductDescription(productName);
		JsonObject product=new JsonObject();
		int descriptioncolNum=getColumnNumber(driver.findElement(By.id("cart_summary")), "Description");
		int unitPriceColNum=getColumnNumber(driver.findElement(By.id("cart_summary")), "Unit price");
		int qtyColNum=getColumnNumber(driver.findElement(By.id("cart_summary")), "Qty");
		int totalColNum=getColumnNumber(driver.findElement(By.id("cart_summary")), "Total");
		System.out.println("Row Number "+rowNum);
		if(rowNum>=0) {
			product.addProperty("name",rows.get(rowNum).findElement(By.xpath("./td["+descriptioncolNum+"]//p/a")).getText());
			product.addProperty("color",rows.get(rowNum).findElement(By.xpath("./td["+descriptioncolNum+"]//small/a")).getText());
			product.addProperty("price", rows.get(rowNum).findElement(By.xpath("./td["+unitPriceColNum+"]//span[@class='price']")).getText());
			product.addProperty("qty", rows.get(rowNum).findElement(By.xpath("./td["+qtyColNum+"]//input[contains(@type,'hidden')]")).getAttribute("value"));
			product.addProperty("total", rows.get(rowNum).findElement(By.xpath("./td["+totalColNum+"]/span")).getText());
			
		}else {
			test.info("No data found in Table with Name "+productName);
			return null;
		}
		
		test.info("Product Info"+product.toString());
		return product;
	}
	
	public int searchInColumn(List<WebElement> rows,int colNumber,String stringToFind) {
		int i=0;
		for(WebElement ele:rows) {
			
			String xpath="./td["+colNumber+"]//p/a";
			System.out.println(xpath);
			String description=ele.findElement(By.xpath(xpath)).getText();
			if(stringToFind.equalsIgnoreCase(description)) {
				test.pass(stringToFind+" is found in table");
				return i;
			}
			i++;
		}
		test.error(stringToFind+" not found in table");
		return -1;
	}

}
