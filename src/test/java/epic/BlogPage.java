package epic;

import java.util.List;
import org.junit.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BlogPage {
	private static final String BLOG_PAGE_URL = "https://www.epicgames.com/paragon/blog";
	private static final By LOAD_MORE_BTN = By
			.cssSelector("#paragonReactWrapper > div > div.paragon-wrapper > span > div > div.load-more > div");
	private static final By categLinksLocator = By.xpath("//li[@class='grid-item']/div[@class='item-wrap']/div[@class='blog-info']/div[@class='blog-category']/a");
	
	private static final Integer WAIT_TIME = 5;

	private WebDriver driver;

	public BlogPage(WebDriver driver) {
		this.driver = driver;
		driver.get(BLOG_PAGE_URL);
	}

	/**
	 * Clicks on link identified by the name
	 * 
	 * @param String linkName
	 *            
	 */
	public BlogPage clickFilterLink(String linkName) {
		driver.manage().window().setSize(new Dimension(1920, 1080)); ;
		
		new WebDriverWait(driver, WAIT_TIME)
			.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='stamp-wrap']/div/ul/li/a[contains(text(),'" + linkName +"')]")))
			.click();
		
		return this;
	}

	/**
	 * Clicks Load More button till button\'s label becomes \'no more\' or timeout is reached
	 * 
	 */
	public BlogPage clickLoadMoreButton() {
		(new WebDriverWait(driver, WAIT_TIME * 10))
				.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver drv) {
				drv.findElement(LOAD_MORE_BTN).click();
				return drv.findElement(LOAD_MORE_BTN).getText().equalsIgnoreCase("no more");
			}
		});
		return this;
	}

	/**
	 * Checks name of the currently visible section
	 * @param String
	 */
	public BlogPage checkBlogItemsCategory(String name) {
		new WebDriverWait(driver, WAIT_TIME)
			.until(ExpectedConditions.attributeToBe(LOAD_MORE_BTN, "class", "btn-load disabled"));
		
		List<WebElement> elems = new WebDriverWait(driver, WAIT_TIME)
			.until(ExpectedConditions.presenceOfAllElementsLocatedBy(categLinksLocator));
		System.out.println("Total number of blog items for category " + name + " is " + elems.size());
		
		for (WebElement el : elems) {
			Assert.assertEquals(name.toUpperCase(), el.getText());
		}

		return this;
	}
	
	/**
	 * Returns total number of articles on the page
	 * 
	 */
	public int getBlogItemsNumber() {
		new WebDriverWait(driver, WAIT_TIME)
			.until(ExpectedConditions.attributeToBe(LOAD_MORE_BTN, "class", "btn-load disabled"));
		
		List<WebElement> elems = new WebDriverWait(driver, WAIT_TIME)
			.until(ExpectedConditions.presenceOfAllElementsLocatedBy(categLinksLocator));

		return elems.size();
	}


}
