package stepDefinitions;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helpers.ConfigHelper;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import pageObjects.CreateBookingPage;

public class DeleteBookingStepdefs
{
	private static WebDriver driver;
	private CreateBookingPage bookingPage;

	@Before
	public void setupTests()
	{
		driver = ConfigHelper.setupBrowser();

		//initialise booking page object
 		bookingPage = new CreateBookingPage(driver);

 		//delete all existing bookings so that tests are performed on clean page
		bookingPage.deleteBookings();
	}

	@After
	public void tearDown()
	{
		//delete all existing bookings created in the tests
		bookingPage.deleteBookings();

		driver.close();
	}

	@Given("^I have a booking$")
	public void iHaveABooking()
	{
		bookingPage.loadBookingPage();
		bookingPage.checkIfBookingPageLoaded();
		bookingPage.fillAllFieldsInBookingForm("Mary",  "Stanton", "20", "2017-01-01", "2017-01-01");
		bookingPage.saveBooking();
	}

	@When("^I delete booked$")
	public void iDeleteBooked() {
		bookingPage.deleteBookings();
	}

	@Then("^Booking should be deleted$")
	public void bookingShouldBeDeleted()
	{
		driver.navigate().refresh();
		try{
			bookingPage.checkForDeleteElement();
			Assert.fail("As all the bookings should be deleted there should be no delete button");
		}catch (NoSuchElementException expectedException){
			System.out.println("delete button on booking page doesn't exist");
		}
	}
}
