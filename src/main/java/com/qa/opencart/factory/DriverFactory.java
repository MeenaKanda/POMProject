package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exception.FrameworkException;

public class DriverFactory {
	
	int i = 0;
	
	public WebDriver driver;
	public Properties prop;  //make it public so other class can use it.
	public OptionsManager optionsManager;
	
	
	public static String highlight;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	
	/**
	 * this method is initializing the driver on the basis of given browser name.
	 * @param browserName
	 * @return this returns driver
	 */
	
	public WebDriver initDriver(Properties prop) {   //prop have all the properties
		
		optionsManager = new OptionsManager(prop);
		
		highlight  = prop.getProperty("highlight").trim();
		String browserName = prop.getProperty("browser").toLowerCase().trim();  //key is browser and getProperty()  give respective value
//		String browserName = System.getProperty("browser");  // passs in maven commant 
		
		System.out.println("browser name is : " + browserName);
		
/*		if(browserName.equalsIgnoreCase("chrome")) {
		//	driver = new ChromeDriver();
		//	driver = new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));  //initialize with new chromeDriver
		}
		else if(browserName.equalsIgnoreCase("firefox")) {
		//	driver = new FirefoxDriver(optionsManager.getFirefoxOptions());   //here driver is normal driver
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));  //here driver is thread local driver.
		}
		else if(browserName.equalsIgnoreCase("safari")) {
			driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
		}
		else if(browserName.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver(optionsManager.getEdgeOptions());
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
		}
		else {
			System.out.println("plz pass the right browser name ...... " + browserName);
		}
		*/
		
		//chrome:
		if(browserName.equalsIgnoreCase("chrome")) {
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				//run on remote/grid:
				init_remoteDriver("chrome");
			}
			else {
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));  //initialize with new chromeDriver
			}
		    }
		
		//firefox:
			else if(browserName.equalsIgnoreCase("firefox")) {
			    if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			    	//run on remote/grid:
			    	init_remoteDriver("firefox");
			    }
			    else {
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));  //here driver is thread local driver.
			}
			}
			
		
		
		//edge:
			else if(browserName.equalsIgnoreCase("edge")) {
				if(Boolean.parseBoolean(prop.getProperty("remote"))) {
					//run on remote/grid:
					init_remoteDriver("edge");
				}
				else {
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
				}
			}
		
		//safari:	
			else if(browserName.equalsIgnoreCase("safari")) {  
				tlDriver.set(new SafariDriver());
			}
		
			else {
				System.out.println("plz pass the right browser name ...... " + browserName);
				throw new FrameworkException("NO BROWSER FOUND EXCEPTION...");
			}
			
		
		
		
		
		
/*		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
	//	driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
		driver.get(prop.getProperty("url"));
		return driver;
		*/
		
		//who is giving driver? theadlocal driver copy giving driver.so replace driver-> getDriver()
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
	//	driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
		getDriver().get(prop.getProperty("url"));
		return getDriver();   //now the driver is a threadlocal driver
		
	}
	
	
	/**
	 * this method is called internally to initialize the driver with
	 * RemoteWebDriver
	 * 
	 * @param browser
	 */
	
	private void init_remoteDriver(String browser) {
		System.out.println("Running tests on grid server::::" + browser);
		try {
		switch (browser.toLowerCase()) {
		case "chrome":
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
			break;
		case "firefox":
			tlDriver.set(
					new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
			break;
		case "edge":
			tlDriver.set(
					new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
			break;
		default:
			System.out.println("Plz pass the right browser for remote execution..." + browser);
			throw new FrameworkException("NOREMOTEBROGWSEREXCEPTION");
		}
		
	} catch (MalformedURLException e) {
		e.printStackTrace();
	}
	}	
	


	/**
	 * get the local thread copy of the driver
	 * @return 
	 */
	public synchronized static WebDriver getDriver() { //synchronized-> so every thread get their own individual copy. so no deadlock condition.
		return tlDriver.get();
	}
	
	
	/**
	 * this method is reading the properties from the .properties file
	 * @return
	 */
	
	/*public Properties initProp() {
		prop = new Properties();  //Properties object got created.
		try {
			FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties"); //pass path of file.
	        prop.load(ip);  //load all the properties into Properties object  // load()->Reads a property list (key and element pairs) from the inputbyte stream
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return prop;
	}*/
	
	
	public Properties initProp() {
		
		// mvn clean install -Devn="stage"   //-Devn="stage" is environment variable
		// mvn clean install -Devn="qa"
	    //mvn clean install   -->if not passing any environment , envName is null
		
		prop = new Properties();
	 	FileInputStream ip = null;
		
		String envName = System.getProperty("env");  //read the environment variable with the help of System class  //this env we can pass from command line terminal mvn clean install -Denv ="stage" //or send env from ecllpse
		System.out.println("Running test cases on Env: " + envName);
		
		try {
		if(envName == null) {
			System.out.println("no env is passed..... Running tests on QA env....");
			ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
		}
		
		else {
			switch (envName.toLowerCase().trim()) {
			case "qa":
			ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;
			case "stage":
		    ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
				break;
			case "dev":
			ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;
			case "prod":
			ip = new FileInputStream("./src/test/resources/config/config.properties");
				break;
			default:
			{
				System.out.println("....Wrong env is passed ....No need to run the test cases....");
				throw new FrameworkException("WRONG ENV IS  PASSED.....");   // throw own exception if wrong env is passed
			//	break; //break not needed here because throw keyword
			}
			}
		}
		}
		catch (FileNotFoundException e) {
			
		}
		
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	
	/**
	 * take screeshot
	 * @return 
	 */
	
	public static String getScreenshot() {
	File srcFile	= ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);  //covert driver into TakeScreenshot Interface.
	String path = System.getProperty("user.dir")+"/screenshot/"+System.currentTimeMillis()+".png";  //this is string path ,not a file
	File destination = new File(path);  //create a file and add path into file
	try {
		FileUtil.copyFile(srcFile, destination);
	} catch (IOException e) {
		e.printStackTrace();
	}
	return path;
	
	
	}
}









