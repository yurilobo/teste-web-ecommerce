package base;

import java.io.File;
import java.io.IOException;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.common.io.Files;

import pages.HomePage;

public class BaseTests {
	private static WebDriver driver;
	protected HomePage homePage;
	
	@BeforeAll
	public static void inicializar() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\yuri\\Desktop\\teste-web-ecommerce\\ecommerce_prestashop\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@BeforeEach
	public void carregarPaginaInicial() {
		driver.get("https://marcelodebittencourt.com/demoprestashop/");
		homePage =new HomePage(driver);
	}
	
	public void capturarTela(String nomeTeste, String resultado) {
		var camera = (TakesScreenshot)driver;
		File capturaDetela =camera.getScreenshotAs(OutputType.FILE);
		try {
			Files.move(capturaDetela, new File("resources/screenshots/" + nomeTeste + "_" + resultado + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@AfterAll
	public static void finalizar() {
		driver.quit();
	}
}
