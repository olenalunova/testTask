package epic;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import org.junit.Assert;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


@RunWith(JUnitParamsRunner.class)
public class BlogPageTest {
	private WebDriver driver;

	@Before 
	public void beforeTest() throws MalformedURLException {
	
		LoggingPreferences logs = new LoggingPreferences();
		logs.enable(LogType.BROWSER, Level.ALL);
		logs.enable(LogType.CLIENT, Level.SEVERE);
		logs.enable(LogType.DRIVER, Level.WARNING);
		logs.enable(LogType.PERFORMANCE, Level.INFO);
		logs.enable(LogType.SERVER, Level.ALL);

		DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
		desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, logs); 
		
		driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), desiredCapabilities);
	}

	@After
	public void afterTest() {
		driver.quit();
	}

	
	@Test 
	@Parameters({"announcements", "fan made", "hero race", "paragons of community", 
				"patch notes", "tips", "video"})
	public void testFilterBlogBy(String name) {
		new BlogPage(driver)
			.clickFilterLink(name)
			.clickLoadMoreButton()
			.checkBlogItemsCategory(name);
	}
	
	@Test 
	@Parameters({"all|123"})
	public void testNonFilteredBlog(String name, int expTotalNumber) {
		System.out.println("-----------------------------------------------!!!!");
		int actTotalNumb = new BlogPage(driver)
			.clickFilterLink(name)
			.clickLoadMoreButton()
			.getBlogItemsNumber();
		Assert.assertEquals(expTotalNumber, actTotalNumb);
	}

}




	
	


	


	

