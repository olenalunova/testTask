package epic;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPage {
	private static final String PAGE_URL = "https://www.epicgames.com/paragon/search";
	private static final By SEARCH_INPUT_LOC = By.id("gsc-i-id1");
	private static final By SEARCH_BTN_LOC = By
			.cssSelector("#___gcse_0 > div > div > form > table.gsc-search-box > tbody > tr > td.gsc-search-button > input");
	private static final Integer WAIT_TIME = 5;

	private static final By PAGING_DIV_LOC = By
			.xpath("//div[@class='gsc-resultsbox-visible']/div/div/div[2]/div/div[@class='gsc-cursor']");

	private HashSet<String> foundItems = new HashSet<String>();

	private WebDriver driver;

	public SearchPage(WebDriver driver) {
		this.driver = driver;
		driver.get(PAGE_URL);
		driver.manage().window().setSize(new Dimension(1920, 1080));
	}

	/**
	 * Clicks on Search button
	 */
	public SearchPage clickSearch() {

		new WebDriverWait(driver, WAIT_TIME).until(
				ExpectedConditions.elementToBeClickable(SEARCH_BTN_LOC))
				.click();
		return this;
	}

	/**
	 * Enter Search criteria
	 */
	public SearchPage enterText(final String txt) {

		new WebDriverWait(driver, WAIT_TIME).until(
				ExpectedConditions.elementToBeClickable(SEARCH_INPUT_LOC))
				.sendKeys(txt);
		return this;
	}

	/**
	 * Enters and submits search criteria
	 * 
	 */
	public SearchPage searchFor(final String txt) {
		this.enterText(txt).clickSearch();
		return this;
	}

	/**
	 * Asserts expected page number vs actual
	 * 
	 * @param String
	 */
	public SearchPage checkTotalPagesNumber(final int expPagesNumb) {

		List<WebElement> elems = new WebDriverWait(driver, WAIT_TIME)
				.until(ExpectedConditions.presenceOfNestedElementsLocatedBy(
						PAGING_DIV_LOC,
						By.xpath("//div[contains(@class,'gsc-cursor-page')]")));

		Assert.assertEquals(expPagesNumb, elems.size());
		return this;
	}

	public WebElement fluentWaitTillElementPresent(final By locator) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(10, TimeUnit.SECONDS)
				.pollingEvery(500, TimeUnit.MILLISECONDS)
				.ignoring(StaleElementReferenceException.class);

		return wait.until(driver -> driver.findElement(locator));
	};

	public Boolean fluentWaitTillAttrToBe(final By locator,
			final String attrValue) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(10, TimeUnit.SECONDS)
				.pollingEvery(500, TimeUnit.MILLISECONDS)
				.ignoring(StaleElementReferenceException.class);

		return wait.until(driver -> {
			return driver.findElement(locator).getAttribute("class")
					.equals(attrValue);
		});
	};

	/**
	 * Checks that @class of epxPage div contains gsc-cursor-current-page
	 * 
	 * @param String
	 */
	public SearchPage checkCurrentPageNumber(final int expPageNumber) {

		this.fluentWaitTillAttrToBe(
				By.xpath("//div[@class='gsc-resultsbox-visible']/div/div/div[2]/div/div[@class='gsc-cursor']/div[.='"
						+ Integer.toString(expPageNumber) + "']"),
				"gsc-cursor-page gsc-cursor-current-page");
		return this;
	}

	/**
	 * Clicks N page
	 * 
	 * @param int number of the page to be clicked
	 */
	public SearchPage clickPageNumber(int number) {

		WebElement element = new WebDriverWait(driver, WAIT_TIME)
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector("#footer")));
		new Actions(driver).moveToElement(element).perform();
		new WebDriverWait(driver, WAIT_TIME)
				.until(ExpectedConditions.presenceOfNestedElementLocatedBy(
						PAGING_DIV_LOC,
						By.xpath("//div[.='" + Integer.toString(number) + "']")))
				.click();

		return this;
	}

	/**
	 * Verifies that items present on the page are unique from previously opened
	 * pages
	 * 
	 * @param int number of the page to be clicked
	 */
	public SearchPage checkPageItemsUnique() {
		WebElement firstUrlOnPage = new WebDriverWait(driver, WAIT_TIME)
		.until(ExpectedConditions.presenceOfElementLocated(By
				.xpath("//div[@class='gsc-webResult gsc-result']/div[1]/table/tbody/tr/td[2]/div[1]/a")));
					
		List<WebElement> elems = new WebDriverWait(driver, WAIT_TIME)
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
				.xpath("//div[@class='gsc-expansionArea']/div/div[1]/table/tbody/tr/td[2]/div[1]/a")));
		elems.add(firstUrlOnPage);
						
		for (WebElement el : elems) {
			String linkText = el.getAttribute("href");
			Assert.assertFalse(this.foundItems.contains(linkText));
			foundItems.add(linkText);
		}

		return this;
	}
}
