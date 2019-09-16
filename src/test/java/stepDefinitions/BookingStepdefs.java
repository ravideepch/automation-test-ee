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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import pageObjects.CreateBookingPage;

import java.util.List;
import java.util.Map;

public class BookingStepdefs {
	private WebDriver driver;
	private CreateBookingPage bookingPage;
	private String firstNameEntered, lastNameEntered, priceEntered,
			checkInDateEntered, checkOutDateEntered, isDepositPaid;
	private String invalidField;
	private String invalidValue;
	private static final String VALID_FIRST_NAME = "Tom";
	private static final String VALID_LAST_NAME = "Nelson";
	private static final String VALID_PRICE = "20";
	private static final String VALID_CHECK_IN_DATE = "2017-01-01";
	private static final String VALID_CHECK_OUT_DATE = "2017-01-02";

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

	@Given("^Booking form is filled$")
	public void bookingFormIsFilled(DataTable forms)
	{
		List<Map<String, String>> bookings = forms.asMaps(String.class, String.class);

		bookingPage.deleteBookings();
		fillTheBookingForm(bookings);
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


	@Given("^I have a booking$")
	public void iHaveABooking()
	{
		bookingPage.loadBookingPage();
		bookingPage.checkIfBookingPageLoaded();
		bookingPage.fillAllFieldsInBookingForm("Mary",  "Stanton", "20", "2017-01-01", "2017-01-01");
		bookingPage.saveBooking();
	}

	@And("^Deposit is paid")
	public void depositIsPaid()
	{
		bookingPage.selectDepositPaid("Yes");
	}

	@And("^Deposit is not paid")
	public void depositIsNotPaid()
	{
		bookingPage.selectDepositPaid("No");
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
			System.out.println("delete button on booking page doesn't exist and no booking exist");
		}
	}

	@Given("^Invalid value entered in \"([^\"]*)\" field$")
	public void invalidValueEnteredInFirstNameField(String fieldName, DataTable fieldValuesTab)
	{
		invalidField = fieldName;

		List<String> fieldValues = fieldValuesTab.asList(String.class);

		for (int i = 0; i < fieldValues.size() ; i++) {
			if(invalidField.equalsIgnoreCase("first name"))
				bookingWithInvalidFirstName(fieldValues.get(i));
			else if (invalidField.equalsIgnoreCase("last name"))
				bookingWithInvalidLastName(fieldValues.get(i));
			else if (invalidField.equalsIgnoreCase("price"))
				bookingWithInvalidPrice(fieldValues.get(i));
			else  if (invalidField.equalsIgnoreCase("check in date"))
				bookingWithInvalidCheckInDate(fieldValues.get(i));
			else if (invalidField.equalsIgnoreCase("check out date"))
				bookingWithInvalidCheckOutDate(fieldValues.get(i));
			else
				Assert.fail("Incorrect field name entered for invalid field values feature in Given keyword");
		}

		bookingPage.saveBooking();
	}

	@Then("^Booking should not be saved$")
	public void bookingShouldNotBeSaved()
	{
		try{
			bookingPage.checkForDeleteElement();
			Assert.fail("Booking with invalid value: " + invalidValue +
							" in the field: " + invalidField + ", should not be saved"
			);
		}catch (NoSuchElementException expectedException){
			System.out.println("No booking is saved as invalid value of: " + invalidValue +
									" is entered in the field: " + invalidField
			);
		}
	}

	private void bookingWithInvalidFirstName(String invalidFirstName)
	{
		invalidValue = invalidFirstName;
		bookingPage.fillAllFieldsInBookingForm(invalidFirstName,  VALID_LAST_NAME, VALID_PRICE,
				VALID_CHECK_IN_DATE, VALID_CHECK_OUT_DATE);
	}

	private void bookingWithInvalidLastName(String invalidLastName)
	{
		invalidValue = invalidLastName;
		bookingPage.fillAllFieldsInBookingForm(VALID_FIRST_NAME,  invalidLastName, VALID_PRICE,
				VALID_CHECK_IN_DATE, VALID_CHECK_OUT_DATE);
		System.out.println("print the name");
	}

	private void bookingWithInvalidPrice(String invalidPrice)
	{
		invalidValue = invalidPrice;
		bookingPage.fillAllFieldsInBookingForm(VALID_FIRST_NAME,  VALID_LAST_NAME, invalidPrice,
				VALID_CHECK_IN_DATE, VALID_CHECK_OUT_DATE);
	}

	private void bookingWithInvalidCheckInDate(String invalidCheckInDate)
	{
		invalidValue = invalidCheckInDate;
		bookingPage.fillAllFieldsInBookingForm(VALID_FIRST_NAME,  VALID_LAST_NAME, VALID_PRICE,
				invalidCheckInDate, VALID_CHECK_OUT_DATE);
	}

	private void bookingWithInvalidCheckOutDate(String invalidCheckOutDate)
	{
		invalidValue = invalidCheckOutDate;
		bookingPage.fillAllFieldsInBookingForm(VALID_FIRST_NAME,  VALID_LAST_NAME, VALID_PRICE,
				VALID_CHECK_IN_DATE, invalidCheckOutDate);
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
			String depositEntered = currentForm.get("bookingPaid");
			isDepositPaid = depositEntered.equalsIgnoreCase("yes")
					? bookingPage.DEPOSIT_PAID
					: bookingPage.DEPOSIT_NOT_PAID;

			bookingPage.fillAllFieldsInBookingForm(firstNameEntered,  lastNameEntered, priceEntered, depositEntered,
										   checkInDateEntered, checkOutDateEntered);
		}
	}
}
