package org.springframework.samples.petclinic;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.lang.model.util.Elements;
import java.util.List;
import java.util.Map;

public class FindOwners {

	private WebDriver driver;
	private String lastName ;

	@Given("an owner with last name {string}")
	public void an_owner_with_last_name(String lastName) {
		driver = CucumberTests.openHomePage();
		WebElement findOwnerLink = driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul/li[2]/a"));
		findOwnerLink.click();
		new WebDriverWait(driver, 1)
				.until(ExpectedConditions.presenceOfElementLocated(By.id("lastName")));
		this.lastName = lastName;
	}

	@When("find owner with the last name")
	public void find_owner_with_the_last_name() {
		WebElement lastNameElement = driver.findElement(By.id("lastName"));
		lastNameElement.sendKeys(this.lastName);
		driver.findElement(By.className("btn-default")).click();
	}

	@Then("The name of owner, {string} will be shown in the result")
	public void the_name_of_owner_will_be_shown_in_result(String ownerName) {
		waitForOwnerInformation();
		String actualName = driver.findElement(By.xpath("/html/body/div[1]/div/table[1]/tbody/tr[1]/td/b")).getText();
		Assertions.assertEquals(ownerName,actualName);
	}

	private void waitForOwnerInformation(){
		WebElement information = new WebDriverWait(driver, 2)
			.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div/h2[1]")));
		Assertions.assertEquals("Owner Information", information.getText());
	}

	@Then("The address of owner, {string} will be shown in the result")
	public void the_address_of_owner_will_be_shown_in_the_result(String address) {
		waitForOwnerInformation();
		String actualAddress = driver.findElement(By.xpath("/html/body/div[1]/div/table[1]/tbody/tr[2]/td")).getText();
		Assertions.assertEquals(address, actualAddress);
	}

	@Then("multiple owners will be shown")
	public void multiple_owners_will_be_shown(DataTable dataTable) {
		List<WebElement> rows =  driver.findElements(By.xpath("/html/body/div[1]/div/table[contains(@id,'owners')]/tbody/tr"));
		List<Map<String,String>> table = dataTable.asMaps();

		Assertions.assertEquals(table.size() ,rows.size() ,"Size of result is not matched");

		for (int i = 0; i < rows.size(); i++) {
			String actualOwnerName = rows.get(i).findElements(By.tagName("td"))
				.get(0).findElement(By.tagName("a")).getText();
			Assertions.assertEquals(table.get(i).get("Name"),actualOwnerName);

			String actualAddress = rows.get(i).findElements(By.tagName("td")).get(1).getText();
			Assertions.assertEquals(table.get(i).get("Address"),actualAddress);
		}
	}

	@Then("no owners found")
	public void no_owners_found() {
		WebElement hint = new WebDriverWait(driver, 1)
			.until(ExpectedConditions.presenceOfElementLocated(By.xpath("html/body/div[1]/div/form/div[1]/div/div/span/div/p")));
		Assertions.assertEquals("has not been found", hint.getText());
	}

}
