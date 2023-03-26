package com.qa.opencart.pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By productHeader = By.tagName("h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productDescriptions = By.cssSelector("div.cpt_product_description p");
	private By productMetaData = By.xpath("(//div[@id='content' ]//ul[@class='list-unstyled'])[position()=1]/li");
	private By productPriceData = By.xpath("(//div[@id='content' ]//ul[@class='list-unstyled'])[position()=2]/li");
    private By quantity = By.id("input-quantity");
    private By addToCartBtn = By.id("button-cart");
    private By cartSuccessMessg = By.cssSelector("div.alert.alert-success");
	
	
	
	private Map<String, String> productInfoMap;
	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	
	public String getProductHeaderValue() {
		String productHeaderVal = eleUtil.doElementGetText(productHeader);
		System.out.println("product header is :: " + productHeaderVal);
		return productHeaderVal;
	}
	
	
	public int getProductImagesCount() {
		int imagesCount = eleUtil.waitForElementsVisible(productImages, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
	    System.out.println("product images count is : " + imagesCount);
	    return imagesCount;
	}
	
	
	//my work
	public int getDescriptionCount() {
		List<WebElement> desElements = eleUtil.waitForElementsVisible(productDescriptions, AppConstants.DEFAULT_MEDIUM_TIME_OUT);
		int desEleCount = desElements.size();
		return desEleCount;
	}
	
/*	public boolean getDescriptionTextExist() {
		List<WebElement> desElements = eleUtil.waitForElementsVisible(productDescriptions, AppConstants.DEFAULT_MEDIUM_TIME_OUT);
	
		boolean flag = true;
		for(WebElement e : desElements) {
			String text = e.getText();
			int textLength = text.length();
			if(textLength > 0) {
				System.out.println("description length  is : " + textLength);
			}
			else {
				System.out.println(" no description ");
			}
		}  
		return true;
	}*/
	
	public void enterQuantity(int qty) {
		System.out.println("Product Quantity : " + qty);
		eleUtil.doSendKeys(quantity, String.valueOf(qty));
	}
	
	public String addProductToCart() {
		eleUtil.doClick(addToCartBtn);
		String successMessg = eleUtil.waitForElementVisible(cartSuccessMessg, AppConstants.DEFAULT_SHORT_TIME_OUT).getText();
	
		StringBuilder sb = new StringBuilder(successMessg);
		String mesg = sb.substring(0, successMessg.length()-1).replace("\n", "").toString();
	    System.out.println("Cart Success Mesg : " + mesg);
	    return mesg;
	}
	
	

	
	public Map<String, String> getProductInfo() { 
	//	productInfoMap = new HashMap<String, String>();   //HashMap doesn't maintain order.
		productInfoMap = new LinkedHashMap<String, String>();  //LInkedHashMap maintain same order you enter.it print same order. //better
	//	productInfoMap = new TreeMap<String, String>();  //it maintain the sorted order of key
		
		//header:
		productInfoMap.put("productName", getProductHeaderValue());
		getProductMetaData();  //public method calling private method : encapsulation
		getProductPriceData();
        System.out.println(productInfoMap);  
		return productInfoMap;
	}
	
	
	//fetching meta data:
	private void getProductMetaData() {
//		Brand: Apple
//		Product Code: Product 18
//		Reward Points: 800
//		Availability: In Stock
		
		//meta data:
		List<WebElement> metaList =  eleUtil.getElements(productMetaData);
		for(WebElement e :metaList) {
			String meta = e.getText();
			String metaInfo[] = meta.split(":");
			String key = metaInfo[0].trim();
			String value = metaInfo[1].trim();
			productInfoMap.put(key, value);
		}
	}
	
	//fetching price data:
	private void getProductPriceData() {
		//price:
//		$2,000.00
//		Ex Tax: $2,000.00
		List<WebElement> priceList =  eleUtil.getElements(productPriceData);
		String price = priceList.get(0).getText(); //$2,000.00
		String exTax = priceList.get(1).getText();  //Ex Tax: $2,000.00
		String exTaxVal = exTax.split(":")[1].trim();
		
		
		productInfoMap.put("productprice", price);  //productprice is custom key.
		productInfoMap.put("exTax", exTaxVal);  //exTax is custom key
	}
	
}


//  \n-> means for new line
