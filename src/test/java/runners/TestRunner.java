
package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * This class is the main entry point for running Cucumber test scenarios with TestNG.
 * It integrates Cucumber feature files with step definitions and generates test reports.
 */
@CucumberOptions(
        features = "src/test/resources/features/",       // Path to feature files
        glue = {"stepdefs", "utils"},                    // Step definition package
        plugin = {
                "pretty",                               // Console output
                "html:target/cucumber-reports.html",    // HTML report
                "json:target/cucumber.json",            // JSON report
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"  // Extent Reports
        }
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
