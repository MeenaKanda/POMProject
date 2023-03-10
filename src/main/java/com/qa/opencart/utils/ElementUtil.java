package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

public class ElementUtil {

	private WebDriver driver; // if you make it public anyone can create obj of ElementUtil class ,start using
								// this driver which is null
	// if they perform any action on driver they get nullpointerException. so we
	// make it private
	
	private JavaScriptUtil jsUtil;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}
	

/*	public WebElement getElement(By locator) { // driver.fe we write only once even if we write for 100 by locator.//reusability
		return driver.findElement(locator);   //call the element without wait
	}*/
	

	public WebElement getElement(By locator) { // driver.fe we write only once even if we write for 100 by locator.//reusability
		WebElement element =  driver.findElement(locator);   //call the element without wait
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
		jsUtil.flash(element);
		}
		return element;
	}
	
	public WebElement getElement(By locator, int timeOut) {  //call the element with wait  //it looks like auto wait in selenium
		return waitForElementVisible(locator, timeOut);
		
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public void doSendKeys(By locator, String value) { // fe and sendKeys method created only once
	    WebElement element = getElement(locator);
	    element.clear();
		element.sendKeys(value);
	}
	
	public  void doActionsSeneKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();
	}
	

	public void doClick(By locator) {
		getElement(locator).click();
	}
	
	public  void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).build().perform();
	}
		
	public String doElementGetText(By locator) {
		return getElement(locator).getText();
	}

	public boolean doElementIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public String getElementAttribute(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}

	public void getElementAttributes(By locator, String value) {
		List<WebElement> eleList = getElements(locator);

		for (WebElement e : eleList) {
			String attrVal = e.getAttribute(value);
			System.out.println(attrVal);
		}
	}

	public int getTotalElementsCount(By locator) {
		int eleCount = getElements(locator).size();
		System.out.println("total element for : " + locator + "------>" + eleCount);
		return eleCount;
	}

	public List<String> getElementsTextList(By locator) {

		List<String> eleTextList = new ArrayList<String>();
		List<WebElement> eleList = getElements(locator);
		for (WebElement e : eleList) {
			String text = e.getText();
			eleTextList.add(text);
		}
		return eleTextList;
	}

	
	//*****************************Select based drop down utils *************************************//
	
	public  void doSelectDropDownByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}
	
	public  void doSelectDropDownByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}
	
	public void doSelectDropDownByVisibleText(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);
	}
	
			
	public  List<WebElement> getDropDownOptionsList(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions();
	}
	
	public  List<String> getDropDownOptionsTextList(By locator) {
		List<WebElement> optionsList = getDropDownOptionsList(locator);
		List<String> optionsTextList = new ArrayList<String>();
		for(WebElement e : optionsList) {
			String text = e.getText();
			optionsTextList.add(text);
		}
		return optionsTextList;
	}
	
	// if the normal 3 method not working we can use this method also. but this one little slower than other 3 methods.
	public  void selectDropDownValue(By locator, String expValue) {
		List<WebElement> optionsList =getDropDownOptionsList(locator);
		for(WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			if(text.equals(expValue)) {
				e.click();
				break;
			}
		}
	}
	
	
	public  int getTotalDropDownOptions(By locator) {
		int optionsCount = getDropDownOptionsList(locator).size();
		System.out.println("total options ==> " + optionsCount);
		return optionsCount;
	}
	
	
	public  void selectDropDownValueWithNoSelection(By locator, String expValue) {
		 List<WebElement> optionsList = getElements(locator);
		 
		 for(WebElement e : optionsList) {
			 String text = e.getText();
			 System.out.println(text);
			      if(text.equals(expValue)) {
			    	  e.click();
			    	  break;
			      } 	  
	          }
		 }
	
	
	public  List<String> getDropDownOptionsListWithNoSelection(By locator) {
		List<WebElement> optionsList = getElements(locator);
		List<String> optionsTextList = new ArrayList<String>();

		for(WebElement e : optionsList) {
			String text = e.getText();
			optionsTextList.add(text);
		}
		return optionsTextList;
	}
	
	
	
	public  void doSearch(By suggListlocator, String suggName) {
		List<WebElement> suggList = getElements(suggListlocator);
        System.out.println(suggList.size());   //11
	
	    for(WebElement e : suggList) {
	    	String text = e.getText();
	    	if(text.length()>0) {
		    	System.out.println(text);
		    	}
	    	if(text.contains(suggName)) {   // or equal()
	    		e.click();
	    		break;
	    	}
	    }
	}
	
	
	//****************************** Wait Utils **********************************//
	/**
	 * An expectation for checking that an element is present on the DOM of a page. 
	 * This does notnecessarily mean that the element is visible.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	 
    public WebElement waitForElementPresence(By locator, int timeOut) {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));  // sele 4.x
	    return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    /**
     * An expectation for checking that an element is present on the DOM of a page and visible.
     * Visibility means that the element is not only displayed but also has a height and width that isgreater than 0.
     * @param locator
     * @param timeOut
     * @return
     */
    
    public WebElement waitForElementVisible(By locator, int timeOut) {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));  // sele 4.x
	    WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));    //finding element wihtout findElement()
	    return element;
    }
    
    /**
     * An expectation for checking that all elements present on the web page that match the locatorare visible.
     *  Visibility means that the elements are not only displayed but also have a heightand width that is greater than 0.
     * @param locator
     * @param timeOut
     * @return
     */
    public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
		WebDriverWait  wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
    
    /**
     * An expectation for checking that there is at least one element present on a web page.
     * @param locator
     * @param timeOut
     * @return
     */
	
	
	public List<WebElement> waitForElementsPresence(By locator, int timeOut) {
		WebDriverWait  wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	
    
    public Alert waitForAlertPresence(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	    return wait.until(ExpectedConditions.alertIsPresent()); 
	}
		
	public String getAlertText(int timeOut) {
		return waitForAlertPresence(timeOut).getText();
	}
	
	public void acceptAlert(int timeOut) {
		waitForAlertPresence(timeOut).accept();
	}
	
	public void dismissAlert(int timeOut) {
		waitForAlertPresence(timeOut).dismiss();
	}
	
	public void acceptSendKeys(int timeOut, String value) {
		waitForAlertPresence(timeOut).sendKeys(value);
	}
	
	
	public String waitForTitleContainsAndFetch(int timeOut, String titleFractionValue) {
		  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		    wait.until(ExpectedConditions.titleContains(titleFractionValue));   //supply part of title
		    return driver.getTitle();
	}
	
	
	public String waitForTitleAndFetch(int timeOut, String titleValue) {
		  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		    wait.until(ExpectedConditions.titleIs(titleValue));     //supply full title here
		    return driver.getTitle();
	}
	
	public String waitForURLContainsAndFetch(int timeOut, String urlFractionValue) {
		  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		    wait.until(ExpectedConditions.urlContains(urlFractionValue));   //supply part of url
		    return driver.getCurrentUrl();
	}
	
	
	public boolean waitForURLContains(int timeOut, String urlFractionValue) {
		  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		  return wait.until(ExpectedConditions.urlContains(urlFractionValue));   //supply part of 
		  }
	

	public String waitForURLAndFetch(int timeOut, String urlValue) {
		  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		    wait.until(ExpectedConditions.urlToBe(urlValue));   //supply complete url
		    return driver.getTitle();
	}
	
	public void waitForFrameSwitchToItByIDOrName(int timeOut, String IDOrName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(IDOrName)); 
	}

	public void waitForFrameSwitchToItByIndex(int timeOut, String frameIndex) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex)); 
	}
	

	public void waitForFrameSwitchToItByFrameElement(int timeOut, WebElement frameElement) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement)); 
	}
	
	public void waitForFrameSwitchToItByFrameLocator(int timeOut, By frameLocator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator)); 
	}
	
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param timeOut
	 * @param locator
	 */
	public void clickWhenReady(int timeOut, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
	public WebElement waitForElementToBeClickable(int timeOut, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	//click the element using wait and Actions class
	public void doClickWithActionsAndWait(int timeOut, By locator) {
		WebElement ele = waitForElementToBeClickable(timeOut, locator);
		Actions act = new Actions(driver);
		act.click().build().perform(); 
	}
	
	
	public  WebElement waitForElementPresenceWithFluentWait(int timeOut, int pollingTime, By locator) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeOut))
                .ignoring(NoSuchElementException.class)          
                .ignoring(StaleElementReferenceException.class)   
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .withMessage(".....element is not found on the page......");
		
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	
	public void waitForAlertWithFluentWait(int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeOut))
                .ignoring(NoAlertPresentException.class) 
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .withMessage(".....Alert is not found on the page......");
		
		 wait.until(ExpectedConditions.alertIsPresent());
	}
	
}
