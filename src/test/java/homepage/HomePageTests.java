package homepage;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import base.BaseTests;
import pages.CarrinhoPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ModalProdutoPage;
import pages.PedidoPage;
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
		 loginPage = homePage.clicarBotaoSignIn();
		//Preencher usuario e login logado
		loginPage.preencherEmail("Teste@testador.com");
		loginPage.preencherPassword("12345");
		//Clicar no botão Sing In para logar
		loginPage.clicarBotaoSignIn();;
		//validar se o usuario está logado de fato
		assertThat(homePage.estaLogado("Teste testador"), is(true));
		
		//voltar a pagina inicial
		carregarPaginaInicial();
		
	}
	@ParameterizedTest
	@CsvFileSource(resources = "/massaTeste_Login.csv", numLinesToSkip = 1, delimiter = ';')
	public void testLogin_UsuarioLogadoComDadosValidos(String nomeTeste, String email, 
			String password, String nomeUsuario, String resultado) {
		//Clicar no botao sing in na home page
		 loginPage = homePage.clicarBotaoSignIn();
		//Preencher usuario e login logado
		loginPage.preencherEmail(email);
		loginPage.preencherPassword(password);
		//Clicar no botão Sing In para logar
		loginPage.clicarBotaoSignIn();;
		
		boolean esperado_loginOk;
		if(resultado.equals("positivo"))
			esperado_loginOk = true;
		else
			esperado_loginOk = false;
		
		
		//validar se o usuario está logado de fato
		assertThat(homePage.estaLogado(nomeUsuario), is(esperado_loginOk));
		
		if(esperado_loginOk)
			homePage.clicarBotaoSignOut();
		
		//voltar a pagina inicial
		carregarPaginaInicial();
	}
	
	
	ModalProdutoPage modalProdutoPage;
	@Test
	public void testincluirProdutoNoCarrinho_ProdutoIncluirComSucesso() {
		String tamanhoProduto = "M";
		String corProduto = "Black";
		int quantidadeProduto = 2;
		
		//pre condição
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
		
		//VALIDAÇÕES//testo se o metodo termina com a mensagem produ....
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
	
	//valores esperados
	String esperado_nomeProduto = "Hummingbird printed t-shirt";
	Double esperado_precoProduto = 19.12;
	String esperado_tamanhoProduto = "M";
	String esperado_corProduto = "Black";
	int esperado_input_quantidadeProduto = 2;
	Double esperado_subtotalProduto = esperado_precoProduto * esperado_input_quantidadeProduto;
	
	int esperado_numeroItensTotal = esperado_input_quantidadeProduto;
	Double esperado_subtotalTotal = esperado_subtotalProduto;
	Double esperado_shippingTotal = 7.00;
	Double esperado_totalTaxExclTotal = esperado_subtotalTotal+esperado_shippingTotal;
	Double esperado_totalTaxIncTotal = esperado_totalTaxExclTotal;
	Double esperado_taxesTotal = 00.00;
 	
	String esperado_nomeCliente = "Teste testador";
	
	CarrinhoPage carrinhoPage;
	@Test
	public void testIrParaCarrinho_InformacoesPersistidas() {
		//Pré-condições
		//Produto incluido na tela ModalProduto
		testincluirProdutoNoCarrinho_ProdutoIncluirComSucesso();
		
		carrinhoPage = modalProdutoPage.clicarBotaoProceedToCheckout();
		
		//Teste  --- validar os elementos da tela
		System.out.println("****TELA DO CARRINHO*****");
		
		
		System.out.println(carrinhoPage.obter_nomeProduto());
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoProduto()));
		System.out.println(carrinhoPage.obter_tamanhoProduto());
		System.out.println(carrinhoPage.obter_corProduto());
		System.out.println(carrinhoPage.obter_input_quantidadeProduto());
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalProduto()));
		
		System.out.println("****ITENS TOTAIS*****");
		
		
		System.out.println(Funcoes.removeTextoItemsDevolveInt(carrinhoPage.obter_numeroItensTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxExclTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxIncTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxesTotal()));
		
		
		//ASSERÇOES HAMCREST
		
		assertThat(carrinhoPage.obter_nomeProduto(),is(esperado_nomeProduto));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoProduto()),is(esperado_precoProduto));
		assertThat(carrinhoPage.obter_tamanhoProduto(),is(esperado_tamanhoProduto));
		assertThat(carrinhoPage.obter_corProduto(),is(esperado_corProduto));
		assertThat(Integer.parseInt(carrinhoPage.obter_input_quantidadeProduto()),is(esperado_input_quantidadeProduto));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalProduto()),is(esperado_subtotalProduto));
		
		assertThat(Funcoes.removeTextoItemsDevolveInt(carrinhoPage.obter_numeroItensTotal()),is(esperado_numeroItensTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalTotal()),is(esperado_subtotalTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingTotal()),is(esperado_shippingTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxExclTotal()),is(esperado_totalTaxExclTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxIncTotal()),is(esperado_totalTaxIncTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxesTotal()),is(esperado_taxesTotal));
		
		
		//ASSERÇOES JUNIT
	
		
	}
	
	CheckoutPage checkoutPage;
	
	@Test
	public void testIrParaCheckout_FreteMeioPagamentoEnderecoListadosOk() {
		//Pré-condições
		
		//Produto disponível no carrinho de compras
		testIrParaCarrinho_InformacoesPersistidas();
		
		//Teste
		
		
		//clicar no botao
		checkoutPage = carrinhoPage.clicarBotaoProceedToCheckout();
		//preencher informacoes
		
		
		//validar informacoes na tela
		assertThat(Funcoes.removeCifraoDevolveDouble(checkoutPage.obter_totalTaxIncTotal()),is(esperado_totalTaxIncTotal));
		//assertThat(checkoutPage.obter_nomeCliente(),is(esperado_nomeCliente));
		assertTrue(checkoutPage.obter_nomeCliente().startsWith(esperado_nomeCliente));
		
		checkoutPage.clicarBotaoContinueAddress();
		
		String encontrado_shippingValor =checkoutPage.obter_shippingValor();
		encontrado_shippingValor =Funcoes.removeTexto(encontrado_shippingValor, " tax excl.");
		Double encontrado_shippingValor_Double = Funcoes.removeCifraoDevolveDouble(encontrado_shippingValor);
		
		assertThat(encontrado_shippingValor_Double,is(esperado_shippingTotal));
		
		checkoutPage.clicarBotaoContinueShipping();
		
		//Selecionar opção PayBy check
		checkoutPage.selecionarRadioPlayByCheck();
		
		//Validar valor do cheque (amount)
		String encontrado_amountPayByCheck =checkoutPage.obter_amountPayByCheck();
		encontrado_amountPayByCheck =Funcoes.removeTexto(encontrado_amountPayByCheck , " (tax incl.)");
		Double encontrado_amountPayByCheck_Double = Funcoes.removeCifraoDevolveDouble(encontrado_amountPayByCheck );
		
		assertThat(encontrado_amountPayByCheck_Double,is(esperado_totalTaxIncTotal));
		
		//Clicar na opção "I agree"
		checkoutPage.selecionarCheckboxIAgree();
		
		assertTrue(checkoutPage.estaSelecionadoCheckboxIAgree());
	}
	
	
	@Test 
	public void testfinalizarPedido_pedidoFinalizadoComsucesso() {
		//Pré-condições
		//Checkout completamente concluido
		testIrParaCheckout_FreteMeioPagamentoEnderecoListadosOk();
		
		//Teste
		//Clicar no botao para confirmar pedido
		PedidoPage pedidoPage = checkoutPage.clicaBotaoConfirmaPedido();
		
		
		//Validar valores da tela
		assertTrue(pedidoPage.obter_textoPedidoConfirmado().endsWith("YOUR ORDER IS CONFIRMED"));
		//assertThat(pedidoPage.obter_textoPedidoConfirmado().toUpperCase(),is("YOUR ORDER IS CONFIRMED"));
		assertThat(pedidoPage.obter_email(),is("Teste@testador.com"));
		
		assertThat(pedidoPage.obter_totalProdutos(),is(esperado_subtotalProduto));
		
		assertThat(pedidoPage.obter_totalTaxIncl(),is(esperado_totalTaxIncTotal));
		
		assertThat(pedidoPage.obter_metodoPagamento(),is("check"));
		
	}
	
	
}
