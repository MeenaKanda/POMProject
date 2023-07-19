package com.qa.opencart.tests;

import org.apache.log4j.MDC;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic - 100: desigh login for open cart app")
@Story("US-Login: 101: design login page features for open cart")

public class LoginPageTest extends BaseTest {
	
	private final Logger logger = Logger.getLogger(LoginPageTest.class);
	
	@Severity(SeverityLevel.TRIVIAL)
	@Description("...checking the title of the page.... tester:Meena")
	@Test(priority = 1)
	public void loginPageTitleTest() {
		MDC.put("testClassName", this.getClass().getSimpleName());
		logger.info("This is a log message form loginPageTitleTest");
		
		String actualTitle = loginPage.getLoginPageTitle();
		logger.info("actual login page title: " +actualTitle);
		Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE_VALUE);
	}
	
	@Severity(SeverityLevel.NORMAL)
	@Description("...checking the url of the page.... tester:Meena")
	@Test(priority = 2)
	public void loginPageUrlTest() {
		MDC.put("testClassName", this.getClass().getSimpleName());
		logger.info("This is a log message form loginPageURLTest");
		
		String actualURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actualURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION));
		//Assert.assertTrue(false);
	}
	
	@Severity(SeverityLevel.CRITICAL)
	@Description("...checking forgot pwd link exist... tester:Meena")
	@Test(priority = 3)
	public void forgotPwdLinkExist() {
		MDC.put("testClassName", this.getClass().getSimpleName());
		logger.info("This is a log message form forgotPwdLinkExistTest");
		
		
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	
	@Severity(SeverityLevel.BLOCKER)
	@Description("...checking user is able to login to app with correct username and password")
	@Test (priority = 8)
	public void loginTest() {
	//	accPage = loginPage.doLogin("qameena123@gamil.com", "Meena123");  //doLogin() return AccountsPage class obj ref
	    accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}

	
	////My work remaining:
	@Test(priority = 4)
	public void logoTest() {
		String actualLogo = loginPage.getLogoPage();
		Assert.assertEquals(actualLogo, "https://naveenautomationlabs.com/opencart/image/catalog/opencart-logo.png");
	}
	
	
	@Test(priority = 5)
	public void continueBtnTest() {
		Assert.assertTrue(loginPage.IsContinueBtnExist());
	}
	
	@Test (priority = 6)
	public void privacyPolicyTest() {
		Assert.assertTrue(loginPage.IsPrivacyPolicyExist());
	}
	
	@Test(priority = 7)
	public void transactionsTest() {
		Assert.assertTrue(loginPage.IsTransactionsExist());
	}
}


