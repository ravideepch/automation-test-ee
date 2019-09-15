package stepDefinitions;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import helpers.ConfigHelper;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import pageObjects.CreateBookingPage;

import java.util.List;

public class ValidateBookingsStepdefs
{
	WebDriver driver;
	CreateBookingPage bookingPage;
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

 		bookingPage.loadBookingPage();
		bookingPage.checkIfBookingPageLoaded();

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
}