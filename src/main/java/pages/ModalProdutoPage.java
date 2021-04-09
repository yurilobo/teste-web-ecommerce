package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;


public class ModalProdutoPage {
	private WebDriver driver;
	
	private By mensagemProdutoAdicionado = By.id("myModalLabel");
	
	public ModalProdutoPage(WebDriver driver) {
		this.driver =driver;
	}
	public String obterMensagemProdutoAdicionado() {
		//a um atraso na pagina usando esse metodo e possivel corresponder esse atraso e fazer a accao pedida
		FluentWait wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(5))
				.pollingEvery(Duration.ofSeconds(1))
				.ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated(mensagemProdutoAdicionado));
		return driver.findElement(mensagemProdutoAdicionado).getText();
	}
}
