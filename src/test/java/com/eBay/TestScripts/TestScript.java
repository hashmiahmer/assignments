package com.eBay.TestScripts;

import org.testng.annotations.Test;
import com.eBay.util.DriverTestCase;
import com.eBay.util.ExecutionLog;

public class TestScript extends DriverTestCase{
	
	@Test
	public void homePage_TestCase_001(){
		
		ExecutionLog.LogAddClass(this.getClass().getName()
				+ " and Test method "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());		
		
		// Check Orientation and change to Portrait view.
		ebayPagehelper.checkOrientation();
		
		// Verify home page.
		ebayPagehelper.verifyHomePage();
		
		//Search for a product by  keyword.
		ebayPagehelper.searchForProduct_byKeyword(propertyReader.readApplicationFile("searchKeyword"));
		
		//Clicked on searched item
		ebayPagehelper.clickedOnSearchItem_byText(propertyReader.readApplicationFile("text"));
		
	}
	
	@Test
	public void searchResultPage_TestCase_002(){
		
		ExecutionLog.LogAddClass(this.getClass().getName()
				+ " and Test method "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		
		//Verify search result page.
		ebayPagehelper.verifySearchResultPage();
						
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
