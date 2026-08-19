package runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)

@CucumberOptions(

    // Feature files
    features = "src/test/resources/features",

    // Step definitions
    glue = "stepdefinitions",

    // Run smoke tests
    tags = "@smoke",

    // Reports
    plugin = {
        "pretty",
        "html:target/cucumber-reports.html",
        "json:target/cucumber.json"
    },

    // Clean console output
    monochrome = true
)

public class TestRunner {

}