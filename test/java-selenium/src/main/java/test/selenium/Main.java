package test.selenium;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
	public static void main(String args[]) throws Exception {
		String url;
		if(args.length == 0){
			url = "http://localhost:8000/";
		}else{
			url = args[0];
		}
		new Main().checkTitle(url);
	}

	@Test
	public void checkTitle(String url) throws Exception{
		WebDriver driver=new ChromeDriver();
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