package testRunners;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
		features = {"src/test/features/"},
		glue = {"stepDefinitions"}
)

public class CreateBookingsTest extends AbstractTestNGCucumberTests
{

}
