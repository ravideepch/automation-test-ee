package testRunners;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
		features = {"src/test/features/deleteBooking.feature"},
		glue = {"stepDefinitions"}
)
public class DeleteBookingsTest extends AbstractTestNGCucumberTests
{

}
