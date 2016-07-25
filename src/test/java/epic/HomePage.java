package epic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
	private static final String PAGE_URL = "https://www.epicgames.com/paragon/";
	private static final String LOGO_LOC = "propLogo";
	private WebDriver driver;
	private static final Integer WAIT_TIME = 2;
	
	public HomePage(WebDriver driver) {
		this.driver = driver; 
		driver.get(PAGE_URL);
	}
	
	/**
	 * checks paragon logo on main page
	 */
	public HomePage checkParaLogoPresent () {
		new WebDriverWait(driver, WAIT_TIME).until(ExpectedConditions.presenceOfElementLocated(By.id(LOGO_LOC)));
	   	return this;
	}
		

	
}