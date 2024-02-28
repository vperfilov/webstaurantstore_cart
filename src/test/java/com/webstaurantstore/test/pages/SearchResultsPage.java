package com.webstaurantstore.test.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;


public class SearchResultsPage {
    private WebDriver driver;

    // Constructor
    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
    }
    String baseUrl;

 // Method to verify that all product descriptions contain key word
    public boolean verifyProductDescriptionsContain(String word) {
    	baseUrl = driver.getCurrentUrl(); // Base URL of the search page
        // Navigate to the last page to determine the total number of pages
        int totalPages = navigateToLastPageAndGetTotalPages();
        
        // Go through each page and verify descriptions
        for (int page = 1; page <= totalPages+1; page++) {
            // Navigate to the specific page
            navigateToPage(page);
            
            // Verify the descriptions on the current page
            List<WebElement> productDescriptions = driver.findElements(By.cssSelector("[data-testid='itemDescription']"));
            for (WebElement description : productDescriptions) {
                String descText = description.getText().toLowerCase(); // Convert description text to lower case
                if (!descText.contains(word)) { // Check for key word in lower case
                    System.out.println("Description without '"+word+"': " + description.getText()); // Print the original text
                    return false; // If any description without key word is found, return false
                }
            }
        }

        // If all descriptions on all pages contain key word, return true
        return true;
    }

    private int navigateToLastPageAndGetTotalPages() {
        // Click the second-to-last page link to go to the last page
        List<WebElement> pageLinks = driver.findElements(By.cssSelector("a.pagerLink"));
        if (pageLinks.size() > 1) {
            WebElement secondToLastPageLink = pageLinks.get(pageLinks.size() - 2);
            secondToLastPageLink.click();
            
        }
        
        // Wait for the page to load after clicking
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
            webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
        );


        // Extract the total number of pages from the second-to-last link
        pageLinks = driver.findElements(By.cssSelector("a.pagerLink")); // Refresh the list after navigation
        WebElement lastPageLink = pageLinks.get(pageLinks.size() - 2);
        String hrefValue = lastPageLink.getAttribute("href");
        String pageNumPart = hrefValue.substring(hrefValue.indexOf("page=") + 5);
        int totalPages = Integer.parseInt(pageNumPart.split("&")[0]); // Assuming 'page' is the last parameter in the URL

        return totalPages;
    }

    private void navigateToPage(int pageNumber) {
 
        driver.navigate().to(baseUrl + "?page=" + pageNumber);

        // Wait for the page to load after navigating
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
            webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
        );
    }


    
 // Method to click the last 'Add to Cart' button on the page
    public void addLastItemToCart() {
        // Find all 'Add to Cart' buttons on the page
        List<WebElement> addToCartButtons = driver.findElements(By.cssSelector(".btn.btn-cart.btn-small"));

        // Click the last button in the list if the list is not empty
        if (!addToCartButtons.isEmpty()) {
            WebElement lastAddToCartButton = addToCartButtons.get(addToCartButtons.size() - 1);
            lastAddToCartButton.click();
        } else {
            // Handle the case where no 'Add to Cart' buttons were found
            System.out.println("No 'Add to Cart' buttons found on the page.");
        }

        // Wait for any necessary actions to complete after clicking the button
        // This could be a page load, a modal popup, etc. Adjust the wait condition as necessary.
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
            // Adjust this condition based on what occurs after clicking the button
            webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
        );
    }
}
