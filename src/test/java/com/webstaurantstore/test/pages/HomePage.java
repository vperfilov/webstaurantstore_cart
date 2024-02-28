package com.webstaurantstore.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
    private WebDriver driver;

    // Locators
    private By searchBox = By.id("searchval");
    private By searchButton = By.xpath("//button[@value='Search']");

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Searches for an item on the home page.
     * @param item The item to search for.
     */
    public void searchForItem(String item) {
        // Find and fill the search box
        WebElement searchBoxElement = driver.findElement(searchBox);
        searchBoxElement.clear(); // Clear any pre-existing text
        searchBoxElement.sendKeys(item);

        // Find and click the search button
        driver.findElement(searchButton).click();
    }
}
