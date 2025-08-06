package stepdefs;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/**
    * This class manages setup and teardown operations for each scenario.
    * It initializes Extent Reports and ensures test logging is properly flushed.
 */

public class Hooks {

    public static ExtentReports extent;
    public static ExtentTest test;

    @Before
    public void setup(Scenario scenario) {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("target/extent-reports/ExtentReport.html");
            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        // Create a new test in the report for each scenario
        test = extent.createTest(scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            Hooks.test.fail("Scenario failed: " + scenario.getName());
        }
        if (extent != null) {
            extent.flush();
        }
    }

}
