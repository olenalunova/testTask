package epic;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import junitparams.JUnitParamsRunner;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


@RunWith(JUnitParamsRunner.class)
public class HomePageTest {
	private WebDriver driver;

	@Before 
	public void beforeTest() throws MalformedURLException {
		 //Create a new instance of the FirefoxDriver
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
	public void testParagonPage () {
		new HomePage(driver).checkParaLogoPresent();
		new WebDriverWait(driver, 3).until(ExpectedConditions.titleIs("Paragon"));		
				
	}

}




	
	


	


	

