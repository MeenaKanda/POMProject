package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	// the structure of page class according to page object module we have to follow
	//1. we have to maintain private By locators
	
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//locators are private so no one can change or used by other pages.
	// 1.private By locators:
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By logo = By.xpath("//img[@class='img-responsive']");
	private By continueBtn = By.linkText("Continue");
	private By PrivacyPol = By.linkText("Privacy Policy");
	private By transactions = By.linkText("Transactions");
	private By registerLink = By.linkText("Register");
	
	
	//2. page constructor:
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);  //elementutil also getting same driver123
	}
	
	
	//3. page actions/methods:
	@Step(" .... getting the login page title....")
	public String getLoginPageTitle() {
	//	String title = driver.getTitle();
		String title = eleUtil.waitForTitleAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.LOGIN_PAGE_TITLE_VALUE);
		System.out.println("Login page title is : " + title);
		return title;  
	}
	
	@Step(" .... getting the login page url....")
	public String getLoginPageURL() {
	//	String url = driver.getCurrentUrl();
		String url = eleUtil.waitForURLContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.LOGIN_PAGE_URL_FRACTION);
		System.out.println("Login page url is  : " + url);
		return url;
	}
	@Step(" .... getting the forgot pwd link....")
	public boolean isForgotPwdLinkExist() {
	//	return driver.findElement(forgotPwdLink).isDisplayed();
		return eleUtil.waitForElementVisible(forgotPwdLink, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}
	
	@Step("login with username: {0} and password: {1}")
	public AccountsPage doLogin(String un, String pwd) {  //dologin method responsible to return next landing page class object
	/*	driver.findElement(emailId).sendKeys(un);
		driver.findElement(password).sendKeys(pwd);
		driver.findElement(loginBtn).click();  //after click taking to new page.
	*/
		
		System.out.println("App creds are : " + un + " : " + pwd);
		eleUtil.waitForElementVisible(emailId, AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(un);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);  //page chaining concepts
	}
	
	
	
	
	
	//My work remaining:
	
	public String getLogoPage() {
		String logoPage = driver.findElement(logo).getAttribute("src");
		return logoPage;
	}
	
	public boolean IsContinueBtnExist() {
		return driver.findElement(continueBtn).isDisplayed();
	}
	
	public boolean IsPrivacyPolicyExist() {
		return driver.findElement(PrivacyPol).isDisplayed();
	}
	
	public boolean IsTransactionsExist() {
		return driver.findElement(transactions).isDisplayed();
	}
	
	@Step("navigating to register page")
	public RegisterPage navigateToRegisterPage() {
		eleUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}
	

}


//whenever you create any page every page have its own driver also.so we have to create webdriver.
//how to initialize driver? by creating constructor
//page actions --> means functionality of particular page
//private class variable used inside public method --> encaptulation concepts
//page locators are private --> we dont give any access to page locators from outside class.
//if they want to access, they access through public layer(public method).they are not allowed to use 
//private locators , only allowed to use public methods.

//page actions/methods: should not have Assertion. 
//According to Page Object Model(POM) if you are creating page, the page should not have any assertio at all.
//assertion is a responsibility of Testng.Test should give assert.
// responsibility of page is that will give behavior.this is called POM.

//if page have assertion then it is violation of POM.

//whenever we click on any button/link if we landing on new page, now this(doLogin) method responsibility to return next landing page class object(new AccountPage)
//this approach is called TDD/ Test Driven Development approach.
//TDD -> to fulfill my end to end test whatever the new classes or methods i have to create i keep creating it. this module is called Page Chaining model.
//TDD -> to  drive a test i am doing new development.new development is creating AccountPage object.
