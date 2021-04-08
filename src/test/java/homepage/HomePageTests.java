package homepage;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import base.BaseTests;
import pages.LoginPage;
import pages.ProdutoPage;

public class HomePageTests extends BaseTests{
	@Test
	public void testContarProdutos_oitoProdutosDiferentes() {
		carregarPaginaInicial();
		assertThat(homePage.contarProdutos(), is(8));
	}
	@Test
	public void testValidarCarrinhoZerado_ZeroItensNoCarrinho() {
		int produtosNoCarrinho = homePage.obterQuantidadeProdutosNoCarrinho();
		//System.out.println(produtosNoCarrinho);
		assertThat(produtosNoCarrinho, is(0));
	}
	
	String nomeProduto_ProdutoPage;
	@Test
	//retorno do nome do produto e do preco
	
	public void testValidarDetalhesDoProduto_DescricaoEValorIguais() {
		int indice = 0;
		String nomeProduto_HomePage = homePage.obterNomeProduto(indice);
		String precoProduto_HomePage = homePage.obterPrecoProduto(indice);
		
		System.out.println(nomeProduto_HomePage);
		System.out.println(precoProduto_HomePage);
		
		ProdutoPage produtoPage =homePage.clicarProduto(indice);
		
		nomeProduto_ProdutoPage = produtoPage.obterNomeProduto();
		String precoProduto_ProdutoPage = produtoPage.obterPrecoProduto();
		
		System.out.println(nomeProduto_ProdutoPage);
		System.out.println(precoProduto_ProdutoPage);
		
		assertThat((nomeProduto_HomePage.toUpperCase()), is(nomeProduto_ProdutoPage.toUpperCase()));
		assertThat((precoProduto_HomePage), is(precoProduto_ProdutoPage));
	
	}
	
	
	@Test
	public void testLoginComSucesso_UsuarioLogado() {
		//Clicar no botao sing in na home page
		LoginPage loginPage = homePage.clicarBotaoSingIn();
		//Preencher usuario e login logado
		loginPage.preencherEmail("Teste@testador.com");
		loginPage.preencherPassword("12345");
		//Clicar no botão Sing In para logar
		loginPage.clicarBotaoSingIn();
		//validar se o usuario está logado de fato
		assertThat(homePage.estaLogado("Teste testador"), is(true));
		
		//voltar a pagina inicial
		carregarPaginaInicial();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
