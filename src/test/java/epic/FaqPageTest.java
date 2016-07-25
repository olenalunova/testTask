package epic;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

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
public class FaqPageTest {
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
	@Parameters({"General", "Playstation 4", "PlayStation Plus", "Essentials Edition", "Store", 
				"Early Access And Founder’s Packs", "Cross-Platform Features", "Country Support", 
				"Code of Conduct", "Billing Issues", "Player Support"})
	public void testJumpToSection (String sectName) {
		new FaqPage(driver)
			.clickFaqLink(sectName)
			.checkSectionIsVisible(sectName);				
	}

}




	
	


	


	

