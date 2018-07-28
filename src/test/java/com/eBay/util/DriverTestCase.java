package com.eBay.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.eBay.PageHelper.eBayPageHelper;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public abstract class DriverTestCase{


	public PropertyReader propertyReader;
	public eBayPageHelper ebayPagehelper;
	public AndroidDriver driver;
	public static AppiumDriverLocalService appiumService;
	public static String appiumServiceUrl;
	public static DesiredCapabilities capabilities;

	
	@Parameters({"deviceUDID","platformVersion","appName_with_apk_extension"})
	@BeforeClass
	public void setUp(String deviceUDID,String platformVersion,String appName_with_apk_extension) {
		
		//Clear all execution logs.
		clearAllLogsAtExecutionLogfolder();
		
		//Clear all screenshots
		clearAllScreenShots("Screenshots");
		clearAllScreenShots("attachedScreenshot");
		
		//Start appium server with set of capabilities
		startServer(deviceUDID, platformVersion, appName_with_apk_extension);
		ebayPagehelper = new eBayPageHelper(driver);
		propertyReader = new PropertyReader();
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {

		if (ITestResult.FAILURE == result.getStatus()) {
			attachedToReport(result.getName());
		}
		
	}
	
	@AfterClass
	public void aftertest(){
		ebayPagehelper = null;
		stopServer();
	}
	
	public AndroidDriver getDriver() {
		return driver;
	}
	
	public void startServer(String deviceUDID,String platformVersion, String appName_with_apk_extension) {
		
		appiumService = AppiumDriverLocalService.buildDefaultService();
		appiumService.start();
		appiumServiceUrl = appiumService.getUrl().toString();
        System.out.println("Appium Service Address : - "+ appiumServiceUrl);
		
    	File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "/app/Android");
        File app = new File (appDir, appName_with_apk_extension);
        
        //Set Capabilities
        
        capabilities = new DesiredCapabilities();
        capabilities.setCapability("noReset", "false");
        capabilities.setCapability(MobileCapabilityType.UDID, deviceUDID);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "ONE E1003");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
        capabilities.setCapability("appWaitActivity", "SplashActivity, SplashActivity,OtherActivity, *, *.SplashActivity");
		try {
			capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
			capabilities.setCapability("adbPort", "5038");
			driver=new AndroidDriver<WebElement>(new URL(appiumServiceUrl),capabilities);
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stopServer() {
    	appiumService.stop();
	}
	
	public String getPath() {
		String path = "";
		File file = new File("");
		String absolutePathOfFirstFile = file.getAbsolutePath();
		path = absolutePathOfFirstFile.replaceAll("\\\\+", "/");
		return path;
	}

	// delete all the file under Execution Log
	public void clearAllLogsAtExecutionLogfolder() {
		String path = getPath();
		File directory = new File(path + "//ExecutionLog");
		for (File f : directory.listFiles())
			f.delete();
	}

	// delete all the file under screenshots folder.
	public void clearAllScreenShots(String folderName) {
		String path = getPath();
		File directory = new File(path + "//"+folderName);
		for (File f : directory.listFiles())
			f.delete();
	}
	
	public String getScreenshot (String screenshotName) throws IOException{
	    DateFormat dateformate = new SimpleDateFormat("dd-mm-yy-hh-mm-ss");
	    Date date = new Date();
	    String currentdate = dateformate.format(date);
	    String imageName =screenshotName+currentdate;
	    TakesScreenshot ts=(TakesScreenshot)getDriver();
	    File source=ts.getScreenshotAs(OutputType.FILE);
	    String location =System.getProperty("user.dir")+"\\attachedScreenshot\\"+imageName+".png";
	    File screenshotLocation =new File (location);
	    FileUtils.copyFile(source, screenshotLocation);
	    return location;

	}
	
	public void attachedToReport(String screenshotName) throws IOException{
		String screenshotPath =getScreenshot(screenshotName);
        System.out.println("Screenshot taken");
        String path = "<img src=\"file://" + screenshotPath + "\" alt=\"\" width='300' height='500'/>";
        System.out.println(screenshotPath+" and path - "+path);
        Reporter.log(path);
	}

}
