package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductPageInfoTest extends BaseTest{

	 @BeforeClass
	    public void ProductInfoPageSetup() {
	    	accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
	    }
	
	 
	 
	 @DataProvider
	    public Object[][] getProductImagesTestData() {
	    	return new Object[][] {
	    		{"Macbook", "MacBook Pro", 4},
	    		{"iMac", "iMac",3},
	    		{"Apple", "Apple Cinema 30\"", 6},
	    		{"Samsung", "Samsung SyncMaster 941BW", 1},
	    		};
	    }
	
	 @Test(dataProvider = "getProductImagesTestData")
	 public void productImagesCountTest(String searchKey, String productName, int imagesCount) {
		searchPage =  accPage.performSearch(searchKey);
		productInfoPage = searchPage.selectProduct(productName);
		int actImagesCount = productInfoPage.getProductImagesCount();
		Assert.assertEquals(actImagesCount, imagesCount);
	 }
	 
	 @DataProvider
	 public Object[][] getProductDescriptionTestData() {
	    	return new Object[][] {
	    		{"Macbook", "MacBook Pro", 10}
	    	};
	 }
	 
	 
	 @Test(dataProvider = "getProductDescriptionTestData")
	 public void descriptionCountTest(String searchKey, String productName, int descriptionCount) {
		 searchPage = accPage.performSearch(searchKey);
		 productInfoPage = searchPage.selectProduct(productName);
		 int actDesCount = productInfoPage.getDescriptionCount();
		 Assert.assertEquals(actDesCount, descriptionCount);
	 }
	 
	 @Test
	 public void productInfoTest() {
		 searchPage = accPage.performSearch("MacBook");
         productInfoPage = searchPage.selectProduct("MacBook Pro");
		 Map<String, String> actProductInfoMap = productInfoPage.getProductInfo();
		 softAssert.assertEquals(actProductInfoMap.get("Brand"), "Apple");
		 softAssert.assertEquals(actProductInfoMap.get("Product Code"), "Product 18");
		 softAssert.assertEquals(actProductInfoMap.get("productName"), "MacBook Pro");
		 softAssert.assertEquals(actProductInfoMap.get("productprice"), "$2,000.00");
		 
		 softAssert.assertAll();
	 }
	 
	 
	 @DataProvider
	 public Object[][] getProductTestData(){
		return new Object[][]{
				 {"MacBook", "MacBook Pro"},
				 {"MacBook", "MacBook Air"},
				 {"Samsung", "Samsung Galaxy Tab 10.1"},
				 {"Samsung", "Samsung SyncMaster 941BW"},
				 {"iMac", "iMac"},
				 {"phone", "iPhone"}
	 };
	 }
	 
	 @Test(dataProvider = "getProductTestData")
	 public void addToCartTest(String searchKey, String productName) {
		 searchPage = accPage.performSearch(searchKey);
		 productInfoPage = searchPage.selectProduct(productName);
		 productInfoPage.enterQuantity(2);
		 String actCartMesg =  productInfoPage.addProductToCart();
		 //Success: You have added MacBook Pro to your shopping cart!
	//	 softAssert.assertTrue(actCartMesg.contains("Success"));
	//	 softAssert.assertTrue(actCartMesg.contains(productName));
		 softAssert.assertTrue(actCartMesg.indexOf("Success")>=0);
		 softAssert.assertTrue(actCartMesg.indexOf(productName)>=0);
		 
		 softAssert.assertEquals(actCartMesg,"Success: You have added " + productName + " to your shopping cart!");
		 softAssert.assertAll();
		 
		 
	 }
		
	
	
}
