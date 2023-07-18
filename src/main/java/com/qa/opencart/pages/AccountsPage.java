package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {
	
	private WebDriver driver;
	ElementUtil eleUtil;
	
	//
	private By logout = By.linkText("Logout");
	private By accHeaders = By.cssSelector("div#content h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");
	
	
	//
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//
	public String  getAccPageTitle() {
	//	String title = driver.getTitle();
		String title = eleUtil.waitForTitleContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.ACCOUNTS_PAGE_TITLE_VALUE);
		System.out.println("Acc page title is : " + title);
		return title;
	}
	
	public String getAccPageURL() {
	//	String url = driver.getCurrentUrl();
		String url = eleUtil.waitForURLContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.ACCOUNTS_PAGE_URL_FRACTION);
		System.out.println("Acc pager url : " + url);
		return url;
	}
	
	public boolean isLogoutLinkExist() {
	//	return driver.findElement(logout).isDisplayed();
		return eleUtil.waitForElementVisible(logout, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}
	
	public boolean isSearchExist() {
	//	return driver.findElement(search).isDisplayed();
		return eleUtil.waitForElementVisible(search, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}
	
	public List<String> getAccountsHeadersList() {
	//	List<WebElement> accHeadersList = driver.findElements(accHeaders);
		List<WebElement> accHeadersList = eleUtil.waitForElementsVisible(accHeaders, AppConstants.DEFAULT_MEDIUM_TIME_OUT);
		List<String> accHeadersValList = new ArrayList<String>();
		
		for(WebElement e : accHeadersList) {
			String text = e.getText();
			accHeadersValList.add(text);
		}
		return accHeadersValList;
	}
	
	
	//--------------- ----------------------------------//
	public SearchPage performSearch(String searchKey) {
		if(isSearchExist()) {
			eleUtil.doSendKeys(search, searchKey);
			eleUtil.doClick(searchIcon);
			return new SearchPage(driver);
			
		}
		else {
			System.out.println("Search field is not present on the page ------- .........");
			return null;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}

//TDD(Test Driven Development) --> on the basis of test cases i am deriving my development.
