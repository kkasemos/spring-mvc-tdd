package com.mycompany.ppms;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductSearchIntegrationTest {
	
    static WebDriver driver;
    
	static String appPath;
	
	@BeforeClass
	public static void setUpOnce() throws Exception {
		driver = new FirefoxDriver(new FirefoxProfile());
		appPath = "http://localhost:8080/ppms";
	}
	
	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@AfterClass
	public static void tearDownOnce() throws Exception {
		driver.quit();
	}
	
	@Test
	public void testProductSearchFormDisplayCorrectly() {
		
		driver.get(appPath + "/product/searchForm");
		
		WebElement searchForm = driver.findElement(By.id("searchForm"));
		
		/* form exist and valid? */
		assertNotNull(searchForm);
		assertEquals("form", searchForm.getTagName());
		
		WebElement searchInputText = searchForm.findElement(By.id("q"));
		
		assertNotNull(searchInputText);
		assertEquals("input", searchInputText.getTagName());
		assertEquals("text", searchInputText.getAttribute("type"));

		WebElement searchInputButton = searchForm.findElement(By.id("searchButton"));
		
		assertNotNull(searchInputButton);
		assertEquals("input", searchInputButton.getTagName());
		assertEquals("button", searchInputButton.getAttribute("type"));
		assertEquals("search", searchInputButton.getAttribute("value"));
		
		WebElement searchResults = driver.findElement(By.id("searchResults"));
		
		/* the section to show a search result must be empty */
		assertNotNull(searchResults);
		assertEquals("div", searchResults.getTagName());
		assertEquals("", searchResults.getText());
	}
	
	@Test
	public void testProductSearchFormSearchAndResultsDisplay() {
		
		driver.get(appPath + "/product/searchForm");
		
		WebElement searchForm = driver.findElement(By.id("searchForm"));
		WebElement searchInputText = searchForm.findElement(By.id("q"));
		WebElement searchInputButton = searchForm.findElement(By.id("searchButton"));
		
		searchInputText.sendKeys("Shoes");
		searchInputButton.click();
		
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='searchResults']/li[1]")));
		
		WebElement searchResultsSection = driver.findElement(By.id("searchResults"));
		
		assertEquals("Very Nice Shoes", searchResultsSection.findElement(By.xpath("li[1]")).getText());
		assertEquals("Cool Shoes", searchResultsSection.findElement(By.xpath("li[2]")).getText());
	}
}
