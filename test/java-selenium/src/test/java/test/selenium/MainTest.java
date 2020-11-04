package test.selenium;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;

public class MainTest {

	@Test
	public void checkTitle() throws Exception{
		String url;
		//url = "http://localhost:8000/";
		url = "http://mkdocs-mkdocs-dev.apps.cluster-c7ee.c7ee.sandbox1840.opentlc.com";

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1200, 1000));

		driver.get(url);
		try {
			WebElement elements = driver.findElement(By.name("query"));
			elements.sendKeys("nishipy");
			Thread.sleep(3000);

			WebElement content = driver.findElement(By.className("md-search-result__link"));
			content.click();
			Thread.sleep(3000);

			assertEquals("About - Test Docs", driver.getTitle());
        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
			driver.close();
		}
	}
}