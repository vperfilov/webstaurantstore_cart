package com.webstaurantstore.test;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.webstaurantstore.test.pages.HomePage;
import com.webstaurantstore.test.pages.CartPage;
import com.webstaurantstore.test.pages.SearchResultsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebstaurantStoreTest {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Setup ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        // Navigate to the home page
        driver.get("https://www.webstaurantstore.com/");
    }

    @Test
    public void testSearchForStainlessWorkTable() {
        // Initialize page objects
        HomePage homePage = new HomePage(driver);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        CartPage cartPage = new CartPage(driver);

        // Conduct a search on the home page
        homePage.searchForItem("stainless work table");

        // Verify all product titles contain 'Table'
        boolean allTitlesContainTable = searchResultsPage.verifyProductDescriptionsContain("table");
        Assert.assertTrue(allTitlesContainTable, "Not all product titles contain 'Table'.");

        // Add the last item found to the cart and navigate to the cart page
        searchResultsPage.addLastItemToCart();
        driver.get("https://www.webstaurantstore.com/cart"); // Consider making this URL a property of CartPage

        // Empty the cart
        cartPage.emptyCart();
    }

    @AfterMethod
    public void tearDown() {
        // Close and quit the browser
        if (driver != null) {
            driver.quit();
        }
    }
}
