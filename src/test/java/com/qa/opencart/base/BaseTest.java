package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchPage;

public class BaseTest {
	
	DriverFactory df;
	WebDriver driver;
	protected Properties prop;
	protected LoginPage loginPage;  //child class in same and different package can use it .public can also be used but anyone can access, so avoid using public here.
	protected AccountsPage accPage;  //in POM all reference maintain in BaseTest
	protected SearchPage searchPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;
	
	
	protected SoftAssert softAssert;
	
	@Parameters({"browser", "browserversion", "testcasename"})
	@BeforeTest
	public void setup(String browserName, String browserVersion, String testCaseName) {
		df = new DriverFactory();
		prop = df.initProp();  //return prop which have all the properties of file in key and value format
		
		    if(browserName!=null) {
		    	prop.setProperty("browser",browserName);
		    	prop.setProperty("browserversion", browserVersion);
		    	prop.setProperty("testcasename", testCaseName);
		    }
		  
		
		
		driver = df.initDriver(prop);
	//	driver = df.initDriver("chrome");    //initDriver() returns driver
		loginPage = new LoginPage(driver);   // here we create LoginPage object in BaseTest so unnecessarily don't need to create object in LoginBaseTest.
	
	    softAssert = new SoftAssert();
	
	}
	

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
	
}


//in prop reference all the properties(8)stored in key value format.

//in the frame work BaseTest running first