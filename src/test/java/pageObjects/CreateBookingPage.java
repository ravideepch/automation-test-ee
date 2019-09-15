package pageObjects;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class CreateBookingPage
{
	public static final String BOOKING_PAGE_URL = "http://hotel-test.equalexperts.io/";
	private final String DEPOSIT_PAID = "true";
	private final String DEPOSIT_NOT_PAID = "false";
	public static final String BOOKING_PAGE_TITLE = "Hotel booking form";
	private final String BOOKING_PAGE_HEADER_TEXT  = "Hotel booking form";
	public final String DELETE_BUTTON_LOCATOR = "[type=\"button\"][value=\"Delete\"]";
	private final WebDriver driver;

	//Constants for saved bookings locators
	//If columns are changed or re arranged below constants should be updated
	private final String FIRST_ROW_SELECTOR = "div#bookings div.row:nth-child(2)";
	private final String FIRST_NAME_COLUMN = "div[class^=\"col-md-\"]:nth-child(1)";
	private final String LAST_NAME_COLUMN = "div[class^=\"col-md-\"]:nth-child(2)";
	private final String PRICE_COLUMN = "div[class^=\"col-md-\"]:nth-child(3)";
	private final String DEPOSIT_COLUMN = "div[class^=\"col-md-\"]:nth-child(4)";
	private final String CHECK_IN_COLUMN = "div[class^=\"col-md-\"]:nth-child(5)";
	private final String CHECK_OUT_COLUMN = "div[class^=\"col-md-\"]:nth-child(6)";

	public CreateBookingPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	//input field web elements
	@FindBy(how = How.CSS, using = "input#firstname")
	private WebElement firstNameInputElement;

	@FindBy(how = How.CSS, using = "input#lastname")
	private WebElement lastNameInputElement;

	@FindBy(how = How.CSS, using = "input#totalprice")
	private WebElement priceInputElement;

	@FindBy(how = How.CSS, using = "select#depositpaid")
	private WebElement depositDropDownElement;

	@FindBy(how = How.CSS, using = "input#checkin")
	private WebElement checkInInputElement;

	@FindBy(how = How.CSS, using = "input#checkout")
	private WebElement checkoutInputElement;

	@FindBy(how = How.CSS, using = "[type=\"button\"][value~=\"Save\"]")
	private WebElement saveButtonElement;

	@FindAll(@FindBy(how = How.CSS, using = DELETE_BUTTON_LOCATOR))
	private List<WebElement> deleteButtonElements;

	@FindBy(how = How.CSS, using = DELETE_BUTTON_LOCATOR)
	private WebElement deleteButtonElement;

	@FindBy(how = How.CSS, using = "div.jumbotron")
	private WebElement bookingPageHeaderElement;

	@FindBy(how = How.CSS, using = "div#bookings")
	private WebElement savedBookingTableElement;

	//saved form web elements
	@FindBy(how = How.CSS, using = FIRST_ROW_SELECTOR + " " + FIRST_NAME_COLUMN)
	private WebElement firstNameSavedElement;

	@FindBy(how = How.CSS, using = FIRST_ROW_SELECTOR + " " + LAST_NAME_COLUMN)
	private WebElement lastNameSavedElement;

	@FindBy(how = How.CSS, using = FIRST_ROW_SELECTOR + " " + PRICE_COLUMN)
	private WebElement priceSavedElement;

	@FindBy(how = How.CSS, using = FIRST_ROW_SELECTOR + " " + DEPOSIT_COLUMN)
	private WebElement depositSavedElement;

	@FindBy(how = How.CSS, using = FIRST_ROW_SELECTOR + " " + CHECK_IN_COLUMN)
	private WebElement checkInSavedElement;

	@FindBy(how = How.CSS, using = FIRST_ROW_SELECTOR + " " + CHECK_OUT_COLUMN)
	private WebElement checkOutSavedElement;

	public void loadBookingPage()
	{
		driver.get(BOOKING_PAGE_URL);
	}

	public void  checkIfBookingPageLoaded()
	{
		String actualPageTitle = driver.getTitle();

		Assert.assertEquals("Booking page not loaded",
				BOOKING_PAGE_TITLE,
				actualPageTitle);
		Assert.assertEquals("Booking page header text not found , incorrect page loaded?",
				BOOKING_PAGE_HEADER_TEXT,
				bookingPageHeaderElement.getText()
		);
	}

	public void enterFirstName(String firstName)
	{
		firstNameInputElement.sendKeys(firstName);
	}

	public void enterLastName(String lastName)
	{
		lastNameInputElement.sendKeys(lastName);
	}

	public void enterPrice(String price)
	{
		priceInputElement.sendKeys(price);
	}

	public void selectDepositPaid(boolean bookingPaid)
	{
		Select depositDropDown = new Select(depositDropDownElement);
		if (bookingPaid)
		{
			depositDropDown.selectByVisibleText(DEPOSIT_PAID);
		}else {
			depositDropDown.selectByVisibleText(DEPOSIT_NOT_PAID);
		}
	}

	public void enterCheckInDate(String checkInDate)
	{
		checkInInputElement.sendKeys(checkInDate);
	}

	public void enterCheckOutDate(String checkOutDate)
	{
		checkoutInputElement.sendKeys(checkOutDate);
	}

	public void saveBooking()
	{
		saveButtonElement.click();
	}

	public void deleteBookings()
	{
//		try {
//				for (int i = 0; i < deleteButtonElements.size(); i++)
//					deleteButtonElements.get(i).click();
//			}
//		catch (IndexOutOfBoundsException ignoreException){
//			System.out.println("No delete button is found");
//		}

		if(isDeleteButtonPresent())
		{
			for (int i = 0; i < deleteButtonElements.size(); i++)
					deleteButtonElements.get(i).click();
		}
	}

	public Boolean isDeleteButtonPresent()
	{
		return (deleteButtonElements.size() !=0);
	}

	public String getSavedTextInBooking()
	{
		return savedBookingTableElement.getText();
	}

	public String getFirstName()
	{
		return firstNameSavedElement.getText();
	}

	public String getLastName()
	{
		return lastNameSavedElement.getText();
	}

	public String getPrice()
	{
		return priceSavedElement.getText();
	}

	public String isDepositPaid()
	{
		return depositSavedElement.getText();
	}

	public String getCheckInDate()
	{
		return checkInSavedElement.getText();
	}

	public String getCheckOutDate()
	{
		return checkOutSavedElement.getText();
	}

	public void fillAllFieldsInBookingForm(String firstName,  String lastName, String price,
										   String checkInDate, String checkOutDate)
	{
		enterFirstName(firstName);
		enterLastName(lastName);
		enterPrice(price);
		enterCheckInDate(checkInDate);
		enterCheckOutDate(checkOutDate);
	}

	public void checkForDeleteElement()
	{
		//Note: if delete element not found NoSuchElementException will be thrown
		driver.findElement(By.cssSelector(DELETE_BUTTON_LOCATOR)).isDisplayed();
	}
}
