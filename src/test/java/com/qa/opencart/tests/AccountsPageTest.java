package com.qa.opencart.tests;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.MDC;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class AccountsPageTest extends BaseTest{
	
	private final Logger logger = Logger.getLogger(AccountsPageTest.class);
	
    @BeforeClass
    public void accPageSetup() {
    	accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
    }
    
    @Test
    public void accPageTitleTest() {
    	MDC.put("testClassName", this.getClass().getSimpleName());
		logger.info("This is a log message form AccountsPageTitleTest");
		
    	String actTitle = accPage.getAccPageTitle();
    	Assert.assertEquals(actTitle, AppConstants.ACCOUNTS_PAGE_TITLE_VALUE);
    }
    
    @Test
    public void accPageURLTest() {
    	MDC.put("testClassName", this.getClass().getSimpleName());
		logger.info("This is a log message form AccountsPageURLTest");
		
    	String actURL = accPage.getAccPageURL();
    	Assert.assertTrue(actURL.contains(AppConstants.ACCOUNTS_PAGE_URL_FRACTION));
    }
    
    @Test
    public void isLogoutLinkExistTest() {
    	Assert.assertTrue(accPage.isLogoutLinkExist());
    }
    
    @Test
    public void accPageHeaderCountTest() {
    	List<String> actualAccPageHeadersList = accPage.getAccountsHeadersList();
    	System.out.println("Acc Page Header LIst; " + actualAccPageHeadersList);
    	Assert.assertEquals(actualAccPageHeadersList.size(), AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);
    }
    
    @Test
    public void accPageHeaderValueTest() {
    	List<String> actualAccPageHeadersList = accPage.getAccountsHeadersList();
    	System.out.println("Acctual Acc Page Header List :  " + actualAccPageHeadersList);
    	System.out.println("Expected Acc Page Header List : " + AppConstants.EXPECTED_ACCOUNTS_PAGE_HEADERS_LIST);
    	Assert.assertEquals(actualAccPageHeadersList, AppConstants.EXPECTED_ACCOUNTS_PAGE_HEADERS_LIST);
    }
    
    
    
    //---------//
    @DataProvider
    public Object[][] getProductData() {
    	return new Object[][] {
    		{"Macbook"},
    		{"iMac"},
    		{"Apple"},
    		{"Samsung"},
    		
    	};
    }
    
    @Test(dataProvider = "getProductData")
    public void searchProductCountTest(String searchKey) {
    	searchPage = accPage.performSearch(searchKey);
    	Assert.assertTrue(searchPage.getSearchProductCount() > 0);
    }
    
    
    
    @DataProvider
    public Object[][] getProductTestData() {
    	return new Object[][] {
    		{"Macbook", "MacBook Pro"},
    		{"Macbook", "MacBook Air"},
    		{"iMac", "iMac"},
    		{"Apple", "Apple Cinema 30\""},
    		{"Samsung", "Samsung SyncMaster 941BW"},
    		{"Samsung", "Samsung Galaxy Tab 10.1"},
    		};
    }
    
    
    @Test(dataProvider = "getProductTestData")
    public void searchProductTest(String searchKey, String productName) {
    	searchPage = accPage.performSearch(searchKey);
    	
    	if(searchPage.getSearchProductCount()>0) {
    		productInfoPage = searchPage.selectProduct(productName);
    		String actProductHeader = productInfoPage.getProductHeaderValue();
    		Assert.assertEquals(actProductHeader, productName);
    	}
    	
    }
    
}

