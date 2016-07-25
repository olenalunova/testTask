package epic;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;

public class FaqPage {
	private static final String PAGE_URL = "https://www.epicgames.com/paragon/faq";
	private static final String LINK_LOC = "//*[@id='faq']/div[2]/div/div[1]/div[@class='quickLink']/ul/li";

	private static final Map<String, By> SELECTORS_MAP;

	static {
		Map<String, By> sectionsMap = new HashMap<String, By>();
		sectionsMap
				.put("Code of Conduct",
						By.cssSelector("#code-of-conduct > div.row.content > div > h2"));
		sectionsMap.put("General",
				By.cssSelector("#general > div.row.content > div > h2"));
		sectionsMap.put("Playstation 4",
				By.cssSelector("#playstation-4 > div.row.content > div > h2"));
		sectionsMap.put("PlayStation Plus", By
				.cssSelector("#playstation-plus > div.row.content > div > h2"));
		sectionsMap
				.put("Essentials Edition",
						By.cssSelector("#essentials-edition > div.row.content > div > h2"));
		sectionsMap.put("Store",
				By.cssSelector("#store > div.row.content > div > h2"));
		sectionsMap
				.put("Early Access And Founder’s Packs",
						By.cssSelector("#early-access-and-founder’s-packs > div.row.content > div > h2"));
		sectionsMap
				.put("Cross-Platform Features",
						By.cssSelector("#cross-platform-features > div.row.content > div > h2"));
		sectionsMap
				.put("Country Support",
						By.cssSelector("#country-support > div.row.content > div > h2"));
		sectionsMap.put("Billing Issues",
				By.cssSelector("#billing-issues > div.row.content > div > h2"));
		sectionsMap.put("Player Support",
				By.cssSelector("#player-support > div.row.content > div > h2"));

		SELECTORS_MAP = sectionsMap;
	}

	private static final Integer waitTime = 3;

	private WebDriver driver;

	public FaqPage(WebDriver driver) {
		this.driver = driver;
		driver.get(PAGE_URL);
		driver.manage().window().setSize(new Dimension(1920, 1080));
	}

	/**
	 * Clicks on link identified by the name
	 * 
	 * @param String
	 *            linkName
	 */
	public FaqPage clickFaqLink(String linkName) {
		new WebDriverWait(driver, waitTime).until(
				ExpectedConditions.elementToBeClickable(By.xpath(LINK_LOC
						+ "[.='" + linkName + "']"))).click();
		return this;
	}

	/**
	 * Checks name of the currently visible section
	 * 
	 * @param String
	 *            expectedSectionName
	 */
	public FaqPage checkSectionIsVisible(String expectedSectionName) {
		By expLocator = FaqPage.getSectionLocator(expectedSectionName);

		new WebDriverWait(driver, waitTime).until((Predicate<WebDriver>)input -> {
			WebElement element = driver.findElement(expLocator);
			
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			Long windowScrollY = (Long) executor.executeScript("return window.scrollY;");
			
			return (element.getLocation().getY() - windowScrollY) < 200;
		});

		return this;
	}

	public static By getSectionLocator(String sectionName) {
		return SELECTORS_MAP.get(sectionName);

	}

}
