package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class FlightListingPage {

	WebDriver driver;
	WebDriverWait wait;
	
// Constructor to initialize Webdriver & Webdriver wait	
	public FlightListingPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 30);
	}

// Below are the fixed(Static) elements of flight listings page	
	@FindBy(xpath="//div[@class='stops']")
	WebElement stops_section;
	
	@FindBy(xpath="//div[@class='fltr tmng']")
	WebElement departure_timing_section;
	
	@FindBy(xpath="//div[@class='fltr u-pos-rel arln']")
	WebElement airlines_filter_section;
	
	@FindBy(xpath="//div[(@class='result-col outr')]/div/div[contains(class, c-flight-listing-split-row )]/div[@class='summary-section']")
	private List<WebElement> departure_date_tickets_container;
	
	@FindBy(xpath="//div[(@class='result-col')]/div/div[contains(class, c-flight-listing-split-row )]/div[@class='summary-section']")
	private List<WebElement> return_date_tickets_container;
	
	@FindBy(xpath = "//div[@class='srt-col'][1]/div[1]/span[1]")
	WebElement departure_place_details;
	
	@FindBy(xpath="//div[@class='srt-col'][1]/div[1]/span[2]")
	WebElement departure_date_details;
	
	@FindBy(xpath = "//div[@class='srt-col'][2]/div[1]/span[1]")
	WebElement return_flight_place_details;
	
	@FindBy(xpath="//div[@class='srt-col'][2]/div[1]/span[2]")
	WebElement return_flight_date_details;
	
//Function Validates the various flight listing page filters such as Stops Filter, Departure Time filter & Airlines Filter
	public void validate_flight_listing_page_filters(){
		Assert.assertTrue(stops_section.isDisplayed(), "Stops - Filters Section Doesn't Exist");
		Assert.assertTrue(departure_timing_section.isDisplayed(), "Departure Time - Filters Section Doesn't Exist");
		Assert.assertTrue(airlines_filter_section.isDisplayed(), "Airlines- Filters Section Doesn't Exist");
	}

	
//	Function selects the desired filter filter from the  flight list page
	public void select_desired_stop_filter_in_flight_listings_page(String desired_stops_filter) {
		WebElement stops_filter =generate_dynamic_xpath_for_desired_stops_filter(desired_stops_filter);
		wait.until(ExpectedConditions.elementToBeClickable(stops_filter));
		stops_filter.click();
	}

//	Function Generates the dynamic xpath for the desires stop filters
	public WebElement generate_dynamic_xpath_for_desired_stops_filter(String desired_stops_filter) {
		String desired_stop_filter_index = "" ;
		if(desired_stops_filter.equalsIgnoreCase("Non stop")){
			 desired_stop_filter_index ="0";}
		else if(desired_stops_filter.equalsIgnoreCase("1 stop")) {
			desired_stop_filter_index = "1";}
		else if(desired_stops_filter.equalsIgnoreCase("1+ stops")){
			desired_stop_filter_index = "2";}
		
		WebElement stops_filter= driver.findElement(By.xpath("//div[@class='stops']/span/div[@data-checkboxindex="+desired_stop_filter_index+"]"));
		return stops_filter;
	}
		
	
// Function prints the available flight ticket details within the amount range passed
	public void print_ticket_details(int available_airlines_count, String airlines_partial_xpath_details, int ticket_range) {
		for(int i = 1; i <= available_airlines_count; i++) {
			String airline_number = driver.findElement(By.xpath(airlines_partial_xpath_details + i + "]/div/div[@class='time-group']/div[@class='airline-text']")).getText();
			String departure_time = driver.findElement(By.xpath(airlines_partial_xpath_details + i + "]/div/div[@class='time-group']/div[@class='time'][1]")).getText();
			departure_time = departure_time.split("\\n")[0];
			String airline_fare = driver.findElement(By.xpath(airlines_partial_xpath_details + i + "]/div/div[@class='price-group']")).getText();

			if (Integer.parseInt(airline_fare) < ticket_range) {
				System.out.print("Airline Number: " + airline_number);
				System.out.print(", Departure Time: " + departure_time);
				System.out.println(", Fare Amount:" + airline_fare + " Rs/-");
			}
		}	
	}
	
// Function to call the ticket printing method with necessary details
	public void print_ticket_details_within_desired_range(int ticket_range){
		int departure_day_tickets_count =departure_date_tickets_container.size();
		String departure_place= departure_place_details.getText();
		String departure_date = departure_date_details.getText();
		
		System.out.println("Total Number of a Flights tickets available on Departure Date "+departure_date+" =>  "+departure_day_tickets_count);
		System.out.println("Departure Place Details(From To) => "+departure_place );
		System.out.println();
				
		
//		As printing all the departure tickets requires to create a dynamic xpath & @Findby stores only static values 
//		i have stored the part of xpath on the below string & passing it to print_ticket_details function to create the full xpath.
		String part_of_depature_tickets_xpath = "//div[(@class='result-col outr')]/div/div[contains(@class,'c-flight-listing-split-row')][";

		System.out.println("Below Are the Requested details of A Departure Date Flights");
		print_ticket_details(departure_day_tickets_count,part_of_depature_tickets_xpath, 7000);
		
		System.out.println();
		System.out.println("*******************************************************************************************");
		
		int return_day_tickets_count= return_date_tickets_container.size();
		String return_flight_place = return_flight_place_details.getText(); 
		String return_flight_date = return_flight_date_details.getText();
		
		System.out.println("Total Number of a Flights tickets available on Return Date "+return_flight_date+" =>  "+return_day_tickets_count);
		System.out.println("Return Flight Place Details(From To) => "+return_flight_place );
		System.out.println();
		
//		As printing all the Return tickets requires to create a dynamic xpath & @Findby stores only static values 
//		i have stored the part of xpath on the below string & passing it to print_ticket_details function to create the full xpath.
		String part_of_return_tickets_xpath = "//div[(@class='result-col')]/div/div[contains(@class,'c-flight-listing-split-row')][";
		
		System.out.println("Below Are the details of A Return Date Flights");
		print_ticket_details(return_day_tickets_count, part_of_return_tickets_xpath, ticket_range);
	}
}