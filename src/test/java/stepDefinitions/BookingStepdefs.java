package stepDefinitions;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helpers.ConfigHelper;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pageObjects.CreateBookingPage;

import java.util.List;
import java.util.Map;

public class BookingStepdefs {
	private WebDriver driver;
	private CreateBookingPage bookingPage;
	private String firstNameEntered, lastNameEntered, priceEntered,
			checkInDateEntered, checkOutDateEntered;
	private String isDepositPaid;

	@Before
	public void setup()
	{
		driver = ConfigHelper.setupBrowser();

		//initialise booking page object
 		bookingPage = new CreateBookingPage(driver);

 		//delete all existing bookings so that tests are performed on clean page
		bookingPage.deleteBookings();
	}

	@After
	public void clearTestData()
	{
		//delete all existing bookings created in the tests
		bookingPage.deleteBookings();

		driver.quit();
	}

	@Given("^I am on the booking site$")
	public void iAmOnTheBookingSite()
	{
		bookingPage.loadBookingPage();
		bookingPage.checkIfBookingPageLoaded();
	}

	@Given("^Fill form with valid values$")
	public void fillFormWithValidValues(DataTable forms)
	{
		List<Map<String, String>> bookings = forms.asMaps(String.class, String.class);

		bookingPage.deleteBookings();
		fillTheBookingForm(bookings);
	}

	@And("^Deposit is paid")
	public void depositIsPaid()
	{
		bookingPage.selectDepositPaid("Yes");
		isDepositPaid = bookingPage.DEPOSIT_PAID;
	}

	@And("^Deposit is not paid")
	public void depositIsNotPaid()
	{
		bookingPage.selectDepositPaid("No");
		isDepositPaid = bookingPage.DEPOSIT_NOT_PAID;
	}

	@When("^I save the booking$")
	public void iSaveTheBooking()
	{
		bookingPage.saveBooking();
	}

	@Then("^booking should be saved$")
	public void bookingShouldBeSaved()
	{
		checkValuesStoredInTheForm();
	}

	private void checkValuesStoredInTheForm()
	{
		Assert.assertEquals("Incorrect value in first name field is saved",
				firstNameEntered,
				bookingPage.getFirstName()
		);

		Assert.assertEquals("Incorrect value in last name field is saved",
				lastNameEntered,
				bookingPage.getLastName()
		);

		Assert.assertTrue("Expected price: " + priceEntered +
							"but found: " + bookingPage.getPrice(),
							bookingPage.getPrice().contains(priceEntered)
		);

		Assert.assertEquals("Incorrect deposit value is saved",
				isDepositPaid,
				bookingPage.isDepositPaid()
		);

		Assert.assertEquals("Incorrect check in date is saved",
				checkInDateEntered,
				bookingPage.getCheckInDate()
		);

		Assert.assertEquals("Incorrect check out date is saved",
				checkOutDateEntered,
				bookingPage.getCheckOutDate()
		);
	}

	private void fillTheBookingForm(List<Map<String, String>> bookings)
	{
		for (int i = 0; i < bookings.size() ; i++)
		{
			Map<String, String> currentForm = bookings.get(i);

			//get values for booking form from test data
			firstNameEntered = currentForm.get("firstName");
			lastNameEntered = currentForm.get("lastName");
			priceEntered = currentForm.get("price");
			checkInDateEntered = currentForm.get("checkInDate");
			checkOutDateEntered = currentForm.get("checkOutDate");
			isDepositPaid = currentForm.get("bookingPaid");

			bookingPage.fillAllFieldsInBookingForm(firstNameEntered,  lastNameEntered, priceEntered, isDepositPaid,
										   checkInDateEntered, checkOutDateEntered);
		}
	}
}
