package basetest;

// Import statements
import java.io.File;
import java.lang.reflect.Method;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class baseclass {

    // WebDriver instance for browser automation
    public static WebDriver driver;

    // Extent reporting variables for test result tracking and reporting
    public ExtentSparkReporter sparkReporter;
    public static ExtentReports extent;
    public static ExtentTest logger;

    // Log4j logger instance
    public static Logger log = LogManager.getLogger(baseclass.class);

    // Method for adding logs passed from test cases
    public static void reportLog(String message) {
        log.info(message); // Log to Log4j log file
        Reporter.log(message); // Log to TestNG reporter output
    }

    @BeforeTest
    public void beforeTestmethod() {
        log.info("Initializing Extent Reports...");
        
        // Setting up the Extent report with SparkReporter to generate HTML reports
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + File.separator + "reports" + File.separator + "ExtentReports.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Adding system-level details for the report
        extent.setSystemInfo("Hostname", "RHEL8");
        extent.setSystemInfo("Username", "root");

        // Configuring report details such as title and name
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Project Test Results");
    }

    @BeforeMethod
    @Parameters("browser") // Accepts browser parameter from the test suite
    public void beforeMethod(@Optional("chrome") String browser, Method testMethod) {
        log.info("Initializing WebDriver for browser: " + browser);
        
        // Creating a test log in the extent report for the current test method
        logger = extent.createTest(testMethod.getName());

        // Initializing the browser based on the parameter
        setupDriver(browser);

        // Browser configurations and navigation
        driver.manage().window().maximize();
        driver.get(constants.url); // Navigating to the URL specified in constants
        driver.manage().deleteAllCookies(); // Deleting all cookies
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60)); // Setting page load timeout
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50)); // Setting implicit wait

        log.info("Browser is launched and navigated to: " + constants.url);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        // Logging the test case result to the extent report
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
            log.error(result.getName() + " failed due to: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
            log.warn(result.getName() + " was skipped");
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " - Test Case Passed", ExtentColor.GREEN));
            log.info(result.getName() + " passed successfully!");
        }
        // Quitting the browser instance after each test case
        driver.quit();
    }

    @AfterTest
    public void afterTest() {
    	if (driver != null) {
            driver.quit();
            log.info("Browser closed successfully.");
        }
        log.info("Flushing Extent Reports...");
        // Flushing the extent report to ensure the report is saved with all logged details
        extent.flush();
    }
    
    // Method to initialize the WebDriver instance based on the browser type
    public void setupDriver(String browser) {
        log.info("Setting up WebDriver for browser: " + browser);
        
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("IE")) {
            WebDriverManager.iedriver().setup();
            driver = new EdgeDriver();
        }
    }
}
