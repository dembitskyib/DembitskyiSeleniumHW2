package com.epam.lab;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EmailSendingTest {
	private WebDriver chromeDriver;
	private final String url = "https://www.google.com.ua/";
	private final String senderAddress = "lab.test.sending@gmail.com";
	private final String password = "a1234567890a";
	private final String receiverAddress = "dembitskyib@gmail.com";
	private final String mailSubject = "Some subject";
	private final String mailMessage = "Some message";

	@BeforeClass
	public void driverSetup() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		chromeDriver = new ChromeDriver();
		chromeDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test
	public void testSend() {
		chromeDriver.get(url);
		loginAccount();
		writeMail();
		Assert.assertTrue(true);
	}

	private void loginAccount() {
		WebElement loginButton = chromeDriver.findElement(By.cssSelector(
				"a[href=\"https://accounts.google.com/ServiceLogin?hl=ru&passive=true&continue=https://www.google.com.ua/\"]"));
		loginButton.click();
		WebElement emailInput = chromeDriver.findElement(By.xpath("//input[@type = 'email']"));
		emailInput.sendKeys(senderAddress);
		WebElement emailNextButton = chromeDriver.findElement(By.xpath("//div[@id = 'identifierNext']//span"));
		emailNextButton.click();
		WebElement passwordInput = chromeDriver.findElement(By.name("password"));
		passwordInput.sendKeys(password);
		WebElement passwordNextButton = chromeDriver.findElement(By.cssSelector("#passwordNext"));
		JavascriptExecutor executor = (JavascriptExecutor) chromeDriver;
		executor.executeScript("arguments[0].click();", passwordNextButton);
		WebElement gmailButton = chromeDriver
				.findElement(By.xpath("//a[@href = 'https://mail.google.com/mail/?tab=wm']"));
		gmailButton.click();
	}
	
	private void writeMail(){
		WebElement writeMailButton = chromeDriver
				.findElement(By.xpath("(//*[@role='navigation']/..//div)[1]//div[@role='button']"));
		writeMailButton.click();
		WebElement receiverInput = chromeDriver.findElement(By.cssSelector("*[name='to']"));
		receiverInput.sendKeys(receiverAddress);
		WebElement subjectInput = chromeDriver.findElement(By.cssSelector("*[name='subjectbox']"));
		subjectInput.sendKeys(mailSubject);
		WebElement messageInput = chromeDriver.findElement(By.xpath("//*[@role='textbox']"));
		messageInput.sendKeys(mailMessage);
		WebElement sendButton = chromeDriver
				.findElement(By.xpath("((//*[@role='dialog']//table)[last()]//*[@role='button'])[1]"));
		sendButton.click();
		WebDriverWait wait = new WebDriverWait(chromeDriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("link_vsm")));
	}
	
	@AfterClass
	public void driverQuit() {
		chromeDriver.quit();
	}
}
