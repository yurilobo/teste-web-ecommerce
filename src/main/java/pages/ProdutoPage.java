package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ProdutoPage {
	private WebDriver driver;
	
	private By nomeProduto =By.className("h1");
	
	private By precoProduto = By.cssSelector(".current-price span:nth-child(1)");
	
	private By tamanhoProduto = By.id("group_1");
	
	public ProdutoPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String obterNomeProduto() {
		return driver.findElement(nomeProduto).getText();
		
	}
	public String obterPrecoProduto() {
		return driver.findElement(precoProduto).getText();
	}
	public void selecionarOpcaoDropdown(String opcao) {
		encontrarDropdownSize().selectByVisibleText(opcao);
	}
	public List<String> obterOpcoesSelecionadas(){
		List<WebElement> elementosSelecionados =
		encontrarDropdownSize().getAllSelectedOptions();
		
		List<String> listaOpcoesSelecionadas = new ArrayList();
		for(WebElement elemento : elementosSelecionados) {
			listaOpcoesSelecionadas.add(elemento.getText());
		}
		return listaOpcoesSelecionadas;
	}
	
	public Select encontrarDropdownSize() {
		return new Select(driver.findElement(tamanhoProduto));
	}
}
