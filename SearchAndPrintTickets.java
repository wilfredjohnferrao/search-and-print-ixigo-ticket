package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import library.BrowserFactory;
import pages.FlightListingPage;
import pages.Ixigo_Home_Page;

public class SearchAndPrintTickets {
	
	@Test
	public void search_for_flight_ticket(){
//	Launches the browser & Navigates to ixigo.com	
	WebDriver driver= BrowserFactory.open_browser("chrome", "https://www.ixigo.com");
	
//	Creating the ixigo home page object using page factory
	Ixigo_Home_Page home=  PageFactory.initElements(driver, Ixigo_Home_Page.class);
	
// Calling the Search flight ticket Method
	home.ixigo_enter_from_and_to_details("DEL - New Delhi", "BLR - Bengaluru");
	
// Selecting Departure & Return Dates from Calendar
	home.select_date_from_calendar("Departure", "April 2021", "27");
	home.select_date_from_calendar("Return", "June 2021", "24");
	
//	Pass one Valid Passenger Type Code from 1,  2 or 3 (1 = Adult, 2 = Child, 3 = Infant)
//  Pass Valid passenger number from 1 to 9		
	home.select_number_of_passengers("1", "2");
	
// Click on the search button & Validate the ticket Search results	
	home.click_on_search_button_and_validate_ticket_search_results_screen_load();
	
// Creating the Flight Listing page object using page factory	
	FlightListingPage listingpage =  PageFactory.initElements(driver, FlightListingPage.class);
	
// Validate the flight listing page filters	
		listingpage.validate_flight_listing_page_filters();
		
// select the desired stops filter. (Valid filters are "Non stop" , "1 stop" or "1+ stops")
	listingpage.select_desired_stop_filter_in_flight_listings_page("Non stop");	
	
// Print Airline ticket details within desired range.(Pass the desired max range in the argument)
	listingpage.print_ticket_details_within_desired_range(7000);
	
//	Close the browser
	BrowserFactory.close_browser();
	}

}
