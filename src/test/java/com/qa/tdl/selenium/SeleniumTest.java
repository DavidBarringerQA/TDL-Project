package com.qa.tdl.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.concurrent.TimeUnit;
import com.qa.tdl.page.TDLPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@Sql(scripts = {"classpath:item-schema.sql", "classpath:item-data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
class SeleniumTest{
	
	private WebDriver driver;

	private TDLPage openPage(){
		TDLPage page = PageFactory.initElements(driver, TDLPage.class);

		driver.get(page.URL);
		if(page.URL.contains("/imp/")){
			driver.switchTo().frame(0);
		}
		return page;
	}

	@BeforeEach
	void setup() {
		ChromeOptions options = new ChromeOptions();
		options.setHeadless(true);
		this.driver = new ChromeDriver(options);
		this.driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		this.driver.manage().window().maximize();

	}

	@Test
	void testAdd(){
		TDLPage page = openPage();
		//Thread.sleep(200);
		page.addItem("TEST", 2, "05122022", "1111");
		assertEquals("TEST", page.getFromList(2, 2));
		assertEquals("Medium", page.getFromList(2, 3));
		assertEquals("false", page.getFromList(2, 5));
	}

	@Test
	void testEdit() {
		TDLPage page = openPage();

		page.editItem("EDITED", 4, 1);
		assertEquals("EDITED", page.getFromList(1,2));
		assertEquals("Lowest", page.getFromList(1,3));
		assertEquals("true", page.getFromList(1,5));
	}

	@Test
	void testDelete() {
		TDLPage page = openPage();
		page.deleteItem();
		assertThrows(NoSuchElementException.class, () -> { page.getFromList(1,1); });
	}

	@Test
	void testPersists() throws InterruptedException{
		TDLPage page = openPage();
		page.addItem("TEST", 2, "05122022", "1111");
		Thread.sleep(1000);
		driver.navigate().refresh();
		if(page.URL.contains("/imp/")){
			driver.switchTo().frame(0);
		}
		assertEquals("TEST", page.getFromList(2, 2));
		assertEquals("Medium", page.getFromList(2, 3));
		assertEquals("false", page.getFromList(2, 5));
	}
	
	@AfterEach
	void teardown() throws InterruptedException{
		driver.close();
	}
}
