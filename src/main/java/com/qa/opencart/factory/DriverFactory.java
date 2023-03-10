package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exception.FrameworkException;

public class DriverFactory {
	
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
		
		System.out.println("browser name is : " + browserName);
		
		if(browserName.equalsIgnoreCase("chrome")) {
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
			System.out.println("plz pass the right browser ... " + browserName);
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
		
		String envName = System.getProperty("env");  //read the environment variable with the help of System class
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
				throw new FrameworkException("WRONG ENV IS  PASSED.....");
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



//TakeScreenshot -> is an interface in selenium ->
//getScrrenshotAs(OUtputType.File) ->it capture the screenshot and store it in the specified location.
//System.getProperty("user.dir")-> user.dir is current user/project directry.
//System.getProperty("user.dir")+"/screenshot" ==> create screenshot folder under particular project



//don't use static keyword. multiple thread going to use this method. if it it is static
//this method can be used by one thread at a time, so multiple thread can not use this method at a time.
//so don't make it static.

//Properties -> is a class from java 
//FileInputStream -> is a class from java and is used to interact with non java files(.xl or .properties file like non-java file)
//FileInputStream make connection with config.Properties


//"./src/test/resources/config/config.properties"
//./   ---=>mean go to current project directory(NOv2022PomSeries)

//FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");  --> if the file is not available/not able to read it throw FileNOtFountexception.
//so we put it in try catch block. 


//prop.load(ip); while loading Propeties also if any exception(IOException) may come .so we put in try catch block.
//in Properties OBject all the values are stored in key and value format.


//The properties object contains key and value pair both as a string. The java.util.Properties class is the subclass of Hashtable.

//Properties can be used to get property value based on the property key. The Properties class provides methods
//to get data from the properties file and store data into the properties file.
//Moreover, it can be used to get the properties of a system.


//one thread is waiting for driver,but driver is in the wait state that is deadlock condition.To make our execution smooth,and working fine with different threads, we have to
//implement thread local concepts.thread local not coming from selenium or testng. it is coming from java which claims that if we initialize any reference with the thread local we will 
//give you the local copy of that particular instance.
//initialize driver with threadlocal. threadlocal have 2 methods get() and set()
//set() -> means get the driver value. and get() --> means get the local value of the driver.

//System.getProperty("env"); --> getting the value of the environment.here we have to pass the key
//if no one passing environment, for Qa team, by default qa is the environment 

//passing the evironment in Testng.xml is bad approach. we have to pass env parameter for every test. that's what maven will help






