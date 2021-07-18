package phase4SwiggyAppiumTesting;

import java.net.URL;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.touch.offset.PointOption;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestHomeSearch {

	static AndroidDriver<WebElement> driver ;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//URL URL = new URL("http://127.0.0.1:4723/wd/hub"); // this is for local machine emulator 
		URL URL = new URL("http://0.0.0.0:4444/wd/hub"); // this is for docker emulator
		DesiredCapabilities cap = new DesiredCapabilities();
		
		
		cap.setCapability("platformName", "Android");
		cap.setCapability("platformVersion", "10");
		cap.setCapability("appPackage", "in.swiggy.android");
		cap.setCapability("appActivity", "in.swiggy.android.activities.HomeActivity");
		cap.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
		
		driver = new AndroidDriver<WebElement>(URL,cap);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	//Set Location test method
	@Test
	public void test_1() throws InterruptedException {
		Thread.sleep(1000);
		WebElement setLoc = driver.findElement(By.id("in.swiggy.android:id/set_location_text"));
		if(setLoc.isDisplayed()) {
			driver.findElement(By.id("in.swiggy.android:id/set_location_text")).click();
			Thread.sleep(20000);
		}

		setLocation("Maytas Hill County");
	
		String location = driver.findElement(By.id("in.swiggy.android:id/address_annotation_textview")).getAttribute("text").toString();
		Assert.assertEquals(location,"Maytas Hill County");
		// path 1
//		driver.findElement(By.id("in.swiggy.android:id/item_menu_top_header_restaurant_name1")).click();
//		Thread.sleep(3000);
//		driver.findElement(By.id("in.swiggy.android:id/location_description")).click();
//		Thread.sleep(3000);
//		driver.findElement(By.id("in.swiggy.android:id/location_description")).sendKeys("Maytas Hill County");
//		Thread.sleep(3000);
//		driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[1]")).click();
//		Thread.sleep(3000);
//		driver.findElement(By.id("in.swiggy.android:id/google_place_search_title_text1")).click();
//		Thread.sleep(3000);
	}

	// Search an Item test method
	@Test
	public void test_2() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.id("in.swiggy.android:id/bottom_bar_explore")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("in.swiggy.android:id/search_query")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("in.swiggy.android:id/search_query")).sendKeys("Dominos Pizza");
		Thread.sleep(16000);
		
		List<WebElement> foodList = driver.findElements(By.xpath("//android.view.ViewGroup"));
		for (WebElement food : foodList) {
			String foodName = food.findElement(By.xpath("//android.widget.TextView")).getAttribute("text");
			if(foodName.equals("Domino's Pizza")) {
				food.findElement(By.xpath("//android.widget.TextView")).click();
				break;
			}
		}
		
		Thread.sleep(3000);
		List<WebElement> restList = driver.findElements(By.xpath("//android.view.ViewGroup"));
		for (WebElement rest : restList) {
			String resName = rest.findElement(By.xpath("//android.widget.TextView")).getAttribute("text");
			if(resName.equals("Domino's Pizza")) {
				rest.click();
				Assert.assertEquals("Domino's Pizza",resName);
				break;
			}
		}
	}
	
	// Add item to the cart test
	@Test
	public void test_3() throws InterruptedException {
		Thread.sleep(5000);
		addToCart("Margherita");
		Thread.sleep(5000);
		driver.findElement(By.id("in.swiggy.android:id/progressive_variants_continue_button")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//android.widget.TextView[@text=\"ADD ITEM\"]")).click();
		Thread.sleep(5000);
		WebElement cart = driver.findElement(By.id("in.swiggy.android:id/tv_checkout"));
		
		if(cart.isDisplayed()) {
			Assert.assertEquals(cart,"VIEW CART");
		}
	}
	
	public void addToCart(String foodItemToOrder) throws InterruptedException {
		Thread.sleep(3000);
	    Dimension size = driver.manage().window().getSize();
	    int anchor = (int) (size.width / 2);
	    // Swipe up to scroll down
	    int startPoint = (int) (size.height - 10);
	    int endPoint = 10;
	    
		TouchAction tc = new TouchAction(driver);
		tc.tap(PointOption.point(600, 1900));
		Thread.sleep(5000);
		tc.longPress(PointOption.point(anchor, startPoint))
        .moveTo(PointOption.point(anchor, endPoint))
        .release()
        .perform();
		
		List<WebElement> foodList = driver.findElements(By.xpath("//android.view.ViewGroup"));
		

		Thread.sleep(3000);
		for (WebElement food : foodList) {
			System.out.println(food.findElement(By.id("in.swiggy.android:id/name")).getAttribute("text"));
			if(food.findElement(By.id("in.swiggy.android:id/name")).getAttribute("text").equals(foodItemToOrder)) {
				food.findElement(By.id("in.swiggy.android:id/add_to_cart_item_add_text")).click();
				break;
			}
		}
	}
	
	public void setLocation(String location) throws InterruptedException {
		Thread.sleep(5000);
		driver.findElement(By.id("in.swiggy.android:id/google_place_search_title_text1")).click();
		Thread.sleep(5000);
		driver.findElement(By.id("in.swiggy.android:id/address_annotation_textview")).click();
		Thread.sleep(5000);
		driver.findElement(By.id("in.swiggy.android:id/location_description")).click();
		Thread.sleep(5000);
		driver.findElement(By.id("in.swiggy.android:id/location_description")).sendKeys(location);
		Thread.sleep(5000);
		driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[1]")).click();
		Thread.sleep(5000);
		driver.findElement(By.id("in.swiggy.android:id/google_place_search_title_text1")).click();
		Thread.sleep(5000);		
	}
}

