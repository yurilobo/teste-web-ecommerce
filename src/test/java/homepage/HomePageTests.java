package homepage;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import base.BaseTests;
import pages.CarrinhoPage;
import pages.LoginPage;
import pages.ModalProdutoPage;
import pages.ProdutoPage;
import util.Funcoes;

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
	
	ProdutoPage produtoPage;
	String nomeProduto_ProdutoPage;
	@Test
	//retorno do nome do produto e do preco
	
	public void testValidarDetalhesDoProduto_DescricaoEValorIguais() {
		int indice = 0;
		String nomeProduto_HomePage = homePage.obterNomeProduto(indice);
		String precoProduto_HomePage = homePage.obterPrecoProduto(indice);
		
		System.out.println(nomeProduto_HomePage);
		System.out.println(precoProduto_HomePage);
		
		produtoPage = homePage.clicarProduto(indice);
		
		nomeProduto_ProdutoPage = produtoPage.obterNomeProduto();
		String precoProduto_ProdutoPage = produtoPage.obterPrecoProduto();
		
		System.out.println(nomeProduto_ProdutoPage);
		System.out.println(precoProduto_ProdutoPage);
		
		assertThat((nomeProduto_HomePage.toUpperCase()), is(nomeProduto_ProdutoPage.toUpperCase()));
		assertThat((precoProduto_HomePage), is(precoProduto_ProdutoPage));
	
	}
	
	LoginPage loginPage;
	@Test
	public void testLoginComSucesso_UsuarioLogado() {
		//Clicar no botao sing in na home page
		 loginPage = homePage.clicarBotaoSingIn();
		//Preencher usuario e login logado
		loginPage.preencherEmail("Teste@testador.com");
		loginPage.preencherPassword("12345");
		//Clicar no bot�o Sing In para logar
		loginPage.clicarBotaoSingIn();
		//validar se o usuario est� logado de fato
		assertThat(homePage.estaLogado("Teste testador"), is(true));
		
		//voltar a pagina inicial
		carregarPaginaInicial();
		
	}
	
	ModalProdutoPage modalProdutoPage;
	@Test
	public void incluirProdutoNoCarrinho_ProdutoIncluirComSucesso() {
		String tamanhoProduto = "M";
		String corProduto = "Black";
		int quantidadeProduto = 2;
		
		//pre condi��o
		if(!homePage.estaLogado("Teste testador")) {
				testLoginComSucesso_UsuarioLogado();
		}
		//Teste selecionando produto
		testValidarDetalhesDoProduto_DescricaoEValorIguais();
		
		//selecionar tamanho
		List<String>listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		System.out.println(listaOpcoes.get(0));
		System.out.println("Tamanho da lista :" + listaOpcoes.size());
		
		produtoPage.selecionarOpcaoDropdown(tamanhoProduto);
		
		listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		
		System.out.println(listaOpcoes.get(0));
		System.out.println("Tamanho da lista :" + listaOpcoes.size());
		
		//selecionar cor preta
		produtoPage.selecionarCorPreta();
		
		//selecionar a quantidade
		produtoPage.alterarQuantidade(quantidadeProduto);
		
		//adicionar carrinh
		modalProdutoPage =  produtoPage.clicarBotaoAddToCart();
		
		//com essa mensagem abaixo ele falha pois existe mais um icone alem da mensagemlogo vamos so testar se aparece a mensagem 
		//assertThat(modalProdutoPage.obterMensagemProdutoAdicionado(), is ("Product successfully added to your shopping cart"));
		
		//VALIDA��ES//testo se o metodo termina com a mensagem produ....
		assertTrue(modalProdutoPage.obterMensagemProdutoAdicionado().endsWith("Product successfully added to your shopping cart"));
			
		
		assertThat(modalProdutoPage.obterDescricaoProduto().toUpperCase(), is(nomeProduto_ProdutoPage.toUpperCase()));
		
		String precoProdutoString = modalProdutoPage.obterPrecoProduto();
		precoProdutoString = precoProdutoString.replace("$", "");
		Double precoProduto = Double.parseDouble(precoProdutoString);
		
		assertThat(modalProdutoPage.obterTamanhoProduto(), is(tamanhoProduto));
		assertThat(modalProdutoPage.obterCorProduto(),is(corProduto));
		assertThat(modalProdutoPage.obterQuantidadeProduto(),is(Integer.toString( quantidadeProduto)));
		
		String subTotalString = modalProdutoPage.obterSubtotal();
		subTotalString = subTotalString.replace("$", "");
		Double subTotal = Double.parseDouble(subTotalString);
		
		Double subtotalCalculado = quantidadeProduto * precoProduto;
		
		assertThat(subTotal, is(subtotalCalculado));
	}
	
	@Test
	public void IrParaCarrinho_InformacoesPersistidas() {
		//Pr�-condi��es
		//Produto incluido na tela ModalProduto
		incluirProdutoNoCarrinho_ProdutoIncluirComSucesso();
		
		CarrinhoPage carrinhoPage = modalProdutoPage.clicarBotaoProceedToCheckout();
		
		//Teste  --- validar os elementos da tela
		System.out.println("****TELA DO CARRINHO*****");
		
		
		System.out.println(carrinhoPage.obter_nomeProduto());
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoProduto()));
		System.out.println(carrinhoPage.obter_tamanhoProduto());
		System.out.println(carrinhoPage.obter_corProduto());
		System.out.println(carrinhoPage.obter_input_quantidadeProduto());
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalProduto()));
		
		System.out.println("****ITENS TOTAIS*****");
		
		
		System.out.println(carrinhoPage.obter_numeroItensTotal());
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxExclTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxIncTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxesTotal()));
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
