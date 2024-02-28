package com.webstaurantstore.test.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.NoSuchElementException;

public class CartPage {
    private WebDriver driver;

    // Constructor
    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    // Method to empty the cart
    public void emptyCart() {
    	
    	// Create a WebDriverWait instance with a timeout of 10 seconds
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // If the 'Empty Cart' button does not appear, refresh the page.
        if(driver.findElements(By.cssSelector("button.emptyCartButton")).size()<1) {
        	driver.navigate().refresh();
        }

        // Wait for the "Empty Cart" button to be clickable before clicking it
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.emptyCartButton"))).click();

        // Wait for the "Empty Cart" confirmation button in the modal to be clickable
        // Adjust the CSS selector based on the actual classes for this button
        By confirmButtonSelector = By.cssSelector("button.bg-green-500.btn.align-middle");

        // Wait for the confirmation button to be clickable and then click it
        wait.until(ExpectedConditions.elementToBeClickable(confirmButtonSelector)).click();
    }


}