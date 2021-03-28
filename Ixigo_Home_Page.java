package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import net.bytebuddy.asm.Advice.Enter;

public class Ixigo_Home_Page {

	WebDriver driver;
	WebDriverWait wait;
	static String calendar_xpath;
	static String current_month_text_xpath;
	static String calendar_next_button_xpath;
	static String calendar_date_xpath;

// Constructor to initialize webdriver & webdriverwait	
	public Ixigo_Home_Page(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 30);
	}

//	Below are the non changeable(static) elements of the ixigo home page
	@FindBy(xpath = "//div[text()='From']/following-sibling::input[@class='c-input u-v-align-middle']")
	WebElement from_field;

	@FindBy(xpath = "//div[text()='To']/following-sibling::input[@class='c-input u-v-align-middle']")
	WebElement to_field;

	@FindBy(xpath = "//div[text()='Travellers | Class']/following-sibling::input[@class='c-input u-v-align-middle']")
	WebElement travellers_or_class_field;

	@FindBy(xpath = "//div[@class='close-btn u-pos-abs ixi-icon-cross']")
	WebElement travellers_or_class_close_button;

	@FindBy(xpath = "//div[@class='search u-ib u-v-align-bottom']")
	WebElement search_button;

	@FindBy(xpath = "//div[@class='flight-listing-page header-displayed']")
	WebElement flight_listing_page_header;

// function is used to enter the Details into From & To fields	
	public void ixigo_enter_from_and_to_details(String from, String to){
		Assert.assertEquals(driver.getTitle(),"ixigo - Flights, IRCTC Train Booking, Bus Booking, Air Tickets & Hotels", "ixigo Home page did not load properly");
        try {
			Thread.sleep(1000);
			from_field.click();
			Thread.sleep(1000);
			from_field.sendKeys(from);
			Thread.sleep(1000);
			from_field.sendKeys(Keys.ENTER);
			Thread.sleep(1000);
			to_field.click();
			Thread.sleep(1000);
			to_field.sendKeys(to);
			Thread.sleep(1000);
			to_field.sendKeys(Keys.ENTER);}
		catch(InterruptedException e){System.out.println(e);}
	}

//  Generic Function used to select the desired date & month on the calendar	
	public void select_date_from_calendar(String calendar_name, String desired_month, String desired_date) {
		generate_xpath_for_calendar(calendar_name, desired_date);
		driver.findElement(By.xpath(calendar_xpath)).click();
		String current_month = driver.findElement(By.xpath(current_month_text_xpath)).getText();

		while (!(current_month.equals(desired_month))) {
			driver.findElement(By.xpath(calendar_next_button_xpath)).click();
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(current_month_text_xpath))));
			String current_month2 = driver.findElement(By.xpath(current_month_text_xpath)).getText();
			current_month = current_month2;
		}
		driver.findElement(By.xpath(calendar_date_xpath)).click();
	}

// This function is used to generate the xpath based on the calendar type	
	public void generate_xpath_for_calendar(String calendar_name, String desired_date) {
		if (calendar_name.equals("Departure")) {
			Ixigo_Home_Page.calendar_xpath = "//div[text()='Departure']/following-sibling::input[@class='c-input u-v-align-middle']";
			Ixigo_Home_Page.current_month_text_xpath = "//div[@class='rd-month'][1]/div";
			Ixigo_Home_Page.calendar_next_button_xpath = "//div[@class='rd-month'][2]/button";
			Ixigo_Home_Page.calendar_date_xpath = "//div[@class='rd-month'][1]/table[@class='rd-days']/tbody/tr/td/div[text()="
					+ desired_date + "]";
		} else if (calendar_name.equals("Return")) {
			Ixigo_Home_Page.calendar_xpath = "//div[text()='Return']/following-sibling::input[@class='c-input u-v-align-middle']";
			Ixigo_Home_Page.current_month_text_xpath = "//div[@class='rd-container flight-ret-cal extra-bottom rd-container-attachment'] //div[@class='rd-month'][1]/div";
			Ixigo_Home_Page.calendar_next_button_xpath = "//div[@class='rd-container flight-ret-cal extra-bottom rd-container-attachment']/div[2]/div[2]/button";
			Ixigo_Home_Page.calendar_date_xpath = "//div[@class='rd-container flight-ret-cal extra-bottom rd-container-attachment']//div[@class='rd-month'][1]/table[@class='rd-days']/tbody/tr/td/div[text()="
					+ desired_date + "]";
		}
	}

// Function is used to select Type & Number of Passengers
	public void select_number_of_passengers(String passenger_type_code, String number_of_passengers) {
		travellers_or_class_field.click();
		driver.findElement(By.xpath("//div[@class='number-counter'][" + passenger_type_code + "]/div[2]/span[@data-val="
				+ number_of_passengers + "]")).click();
		travellers_or_class_close_button.click();
	}

// click on the search button & validate the ticket search results screen
	public void click_on_search_button_and_validate_ticket_search_results_screen_load() {
		search_button.click();
		Assert.assertTrue((flight_listing_page_header.isDisplayed()), "Fail- Flight Listing Page Did Not Load");
	}
}
