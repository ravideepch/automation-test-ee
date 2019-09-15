package testRunners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/test/features/validateBookings.feature"},
		glue = {"stepDefinitions"}
)
public class CreateAndDeleteBookingsRunner
{

}
