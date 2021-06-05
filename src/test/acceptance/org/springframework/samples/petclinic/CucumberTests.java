package org.springframework.samples.petclinic;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.junit.platform.engine.Cucumber;
import io.cucumber.spring.CucumberContextConfiguration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Set;

@Cucumber
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CucumberTests {

	static WebDriver webDriver;
	static{
		WebDriverManager.getInstance(FirefoxDriver.class).setup();
	}
	final static String MAIN_URL = "http://127.0.0.1:8080";

	@Before
	public void startSelenium() {
		webDriver = new FirefoxDriver();
	}

	@After
	public void stopSelenium() {
		webDriver.quit();
	}

	public static WebDriver openHomePage() {
		webDriver.get(MAIN_URL);
		return webDriver;
	}

}
