package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class SearchPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
//	private By productLink = By.linkText("MacBook Pro");  //if 100 product on the page are we going to create 100 By locator? 
	//No. we have to create 1 dynamic xpath inside the method.so every product is available in the same dynamic xpath.


	private By searchProductResults = By.cssSelector("div#content div.product-layout");
	
	
	public SearchPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	
	
	public int getSearchProductCount() {
        int productCount = eleUtil.waitForElementsVisible(searchProductResults, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
	    System.out.println("Product Count :::::" + productCount);
	    return productCount;
	}
	
	
	public ProductInfoPage selectProduct(String productName) {
		By productLocator = By.linkText(productName);  //dynamic xpath.
		eleUtil.waitForElementVisible(productLocator, AppConstants.DEFAULT_LONG_TIME_OUT).click();
		return new ProductInfoPage(driver);  //page chaining model using TDD approach
	}
	
	
	
	
	
	
	
	
}


//every page should have private WebDriver.
//POM says each and every page should have java class but it does not say each and every page should have test class also.
//for writing test case we created separated test class(loginpageTest,AccounPageTest).

//POM says --> page class should be written on the basis of number of pages. it is not mandatory every feature you have to write separated test class.
//you can combine 100 test in 5 test classes also.
//100 pages in 5 test classes.
//Ex: performing search test in AccountPageTest only