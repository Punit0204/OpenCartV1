package utilities;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;


public class ExtentReportManager implements ITestListener{
	
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	String repName;
	public void onStart(ITestContext testContext) {
		

		/*
		 * SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss"); Date dt =
		 * new Date(); String currentdatetimestamp = df.format(dt);
		 */
	
		
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); //Timestamp
		repName = "Test-Report-" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\" +repName); //
		
		sparkReporter.config().setDocumentTitle("Opencart Automation Report");
		sparkReporter.config().setReportName("Opencart Functional Testing");
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "Opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
		}	
	}
	
	
	public void onTestSuccess(ITestResult result) {
		
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS, result.getName()+" Got sucessfully executed");
		
	}
	
	
	public void onTestFailure(ITestResult result) {
		
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.FAIL, result.getName() + " got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());
		try {
			String imgPath = new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath); 
			
		}
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
	}
	
	
	public void onTestSkipped(ITestResult result) {
		
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName() + " got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}
	
	
	public void onFinish(ITestContext testContext) {
		extent.flush();
		
		String pathOfExtentReport = System.getProperty("user.dir") +"\\reports\\"+repName;
		File extentReport = new File(pathOfExtentReport);
		
		// Automatically Open the Extent Report
		try {
			 Desktop.getDesktop().browse(extentReport.toURI());
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		/* Email the Report [Did'nt work due to 2 factor Authorization]
		try {
		    System.setProperty("mail.debug", "true");  // Debug log

		    URL url = new URL("file:///" + System.getProperty("user.dir") + "\\reports\\" + repName);

		    ImageHtmlEmail email = new ImageHtmlEmail();
		    email.setDataSourceResolver(new DataSourceUrlResolver(url));
		    email.setHostName("smtp.gmail.com");  // Use gmail.com instead of googlemail.com
		    email.setSmtpPort(465);
		    email.setAuthenticator(new DefaultAuthenticator("prajapatipunit242@gmail.com", "ktukaewoqyrmrtnh"));
		    email.setSSLOnConnect(true);
		    email.setFrom("prajapatipunit242@gmail.com");
		    email.setSubject("Test Results");
		    email.setMsg("Please find Attached Report....");
		    email.addTo("punitprajapati2020@gmail.com");
		    email.attach(url, "extent report", "please check report ....");
		    email.send();
		} catch (Exception e) {
		    e.printStackTrace();
		} */ 

	} 
}
