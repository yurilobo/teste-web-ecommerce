package steps;

import java.util.concurrent.TimeUnit;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.HomePage;

public class ComprarProdutoSteps {
	
	private static WebDriver driver;
	private HomePage homePage = new HomePage(driver);
	
	@Before
	public static void inicializar() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\yuri\\Desktop\\teste-web-ecommerce\\ecommerce_prestashop\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	
	@Dado("que estou na pagina inicial")
	public void que_estou_na_pagina_inicial() {
	    homePage.carregarPaginaInicial();
	    assertThat(homePage.obterTituloPagina(),is("Loja de Teste"));
	    }

	@Quando("nao estou logado")
	public void nao_estou_logado() {
	   assertThat( homePage.estaLogado(), is (false));
	}

	@Entao("visualizo {int} produtos disponiveis")
	public void visualizo_produtos_disponiveis(Integer int1) {
	    assertThat(homePage.contarProdutos(), is(int1));
	}

	@Entao("carrinho esta zerado")
	public void carrinho_esta_zerado() {
		assertThat(homePage.obterQuantidadeProdutosNoCarrinho(), is(0));
	}
	
	
	@Quando("estou logado")
	public void estou_logado() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Quando("seleciono um produto na posicao {int}")
	public void seleciono_um_produto_na_posicao(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Quando("nome do produto na tela principla eh {string}")
	public void nome_do_produto_na_tela_principla_eh(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Quando("preco do produto na tela principla eh {string}")
	public void preco_do_produto_na_tela_principla_eh(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Quando("adiciono o produto no carrrinho com tamanho {string} cor {string} e quantidade {int}")
	public void adiciono_o_produto_no_carrrinho_com_tamanho_cor_e_quantidade(String string, String string2, Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Entao("o produto aparece na confirmacao com o nome {string} preco\"$19.{int}\" tamanho \"M\" cor \"Black\" e quantidade {int}")
	public void o_produto_aparece_na_confirmacao_com_o_nome_preco_$19_tamanho_m_cor_black_e_quantidade(String string, Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}



	
	@After
	public static void finalizar() {
		driver.quit();
	}

}
