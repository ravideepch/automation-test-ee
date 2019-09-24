package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class ConfigHelper
{
	public static WebDriver setupBrowser()
	{
		WebDriver driver = createWebDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

		return driver;
	}

	public static WebDriver createWebDriver() {
        String webdriver = System.getProperty("browser", "chrome");

        if (webdriver.equalsIgnoreCase("firefox"))
        	return new FirefoxDriver();
        else if (webdriver.equalsIgnoreCase("chrome"))
        	return new ChromeDriver();
        else
        	throw new RuntimeException("Unsupported webdriver: " + webdriver);

    }
}
