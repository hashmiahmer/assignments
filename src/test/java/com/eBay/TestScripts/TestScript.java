package com.eBay.TestScripts;

import java.io.IOException;

import org.testng.Reporter;
import org.testng.annotations.Test;
import com.eBay.util.DriverTestCase;
import com.eBay.util.ExecutionLog;

public class TestScript extends DriverTestCase{
	
	@Test
	public void homePage_TestCase_001() throws IOException{
		
		ExecutionLog.LogAddClass(this.getClass().getName()
				+ " and Test method "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());		
		
		// Check Orientation and change to Portrait view.
		ebayPagehelper.checkOrientation();
		
		// Verify home page.
		ebayPagehelper.verifyHomePage();
		Reporter.log("Successfully land on application home page..");
		attachedToReport("homePage");
		//Search for a product by  keyword.
		ebayPagehelper.searchForProduct_byKeyword(propertyReader.readApplicationFile("searchKeyword"));
		Reporter.log(propertyReader.readApplicationFile("text")+" search keyword entered");
		attachedToReport("searchField");
		
		//Clicked on searched item
		ebayPagehelper.clickedOnSearchItem_byText(propertyReader.readApplicationFile("text"));
		Reporter.log("Clicked on search item from auto-suggestion drop list.");
		attachedToReport("autoSuggestion");
	}
	
	@Test
	public void searchResultPage_TestCase_002() throws IOException{
		
		ExecutionLog.LogAddClass(this.getClass().getName()
				+ " and Test method "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		
		//Verify search result page.
		ebayPagehelper.verifySearchResultPage();
		Reporter.log("Pop-up is appeared.");
		attachedToReport("popup");
		
		// Perform sort action - Low to high
		ebayPagehelper.sortSearchItem(propertyReader.readApplicationFile("subFilterText"));
						
		//Click on item from sorted list.
		ebayPagehelper.selectItemFromSortedList(propertyReader.readApplicationFile("price"));
	}
	
	@Test
	public void productDetailsPage_TestCase_003(){
	
		ExecutionLog.LogAddClass(this.getClass().getName()
				+ " and Test method "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// Verify PDP
		ebayPagehelper.verifyPDP();
				
		// Swipe image from product image carousel from right to left.  
		ebayPagehelper.swipeImages();
				
		//Select color variation.
		ebayPagehelper.selectColor(propertyReader.readApplicationFile("color"));
				
		//Click on buy it now button to add item to cart page.
		ebayPagehelper.addToCart();
		
	}
	
	@Test
	public void signInPage_TestCase_004(){
		
		ExecutionLog.LogAddClass(this.getClass().getName()
				+ " and Test method "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		
		//Change orientation to landscape view.
		ebayPagehelper.orientationTest("Landscape");
				
		//Change screen orientation to Portrait view.
		ebayPagehelper.orientationTest("PORTRAIT");
				
		// Back to PDP page.
		ebayPagehelper.backToPreviousPage();
	}
	
}
