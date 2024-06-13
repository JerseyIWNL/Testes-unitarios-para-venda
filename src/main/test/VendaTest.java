import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VendaTest {

    private Venda venda;

    @BeforeEach
    public void setUp() {
        venda = new Venda("Cliente Teste");
    }

    @Test
    public void testVendaInicializadaCorretamente() {
        assertEquals("Cliente Teste", venda.getCliente());
        assertEquals(Venda.SITUACAO_NAO_INICIADA, venda.getSituacao());
        assertEquals(0.0, venda.getValor());
    }

    @Test
    public void testAdicionarItem() {
        venda.vender("Produto Teste", 10.0, 2);
        assertEquals(20.0, venda.getValor());
        assertEquals(Venda.SITUACAO_EM_ANDAMENTO, venda.getSituacao());
    }

    @Test
    public void testAplicarDesconto() {
        venda.vender("Produto Teste", 10.0, 2);
        boolean descontoAplicado = venda.aplicarDesconto(10);
        assertTrue(descontoAplicado);
        assertEquals(18.0, venda.getValor());
    }

    @Test
    public void testAplicarDescontoInvalido() {
        venda.vender("Produto Teste", 10.0, 2);
        boolean descontoAplicado = venda.aplicarDesconto(80);
        assertFalse(descontoAplicado);
        assertEquals(20.0, venda.getValor());
    }

    @Test
    public void testFinalizarVenda() {
        venda.vender("Produto Teste", 10.0, 2);
        venda.finalizar();
        assertEquals(Venda.SITUACAO_ENCERRADA, venda.getSituacao());
    }

    @Test
    public void testVendaEncerradaNaoPermiteNovosItens() {
        venda.vender("Produto Teste", 10.0, 2);
        venda.finalizar();
        RuntimeException exception = assertThrows(RuntimeException.class, () -> venda.vender("Outro Produto", 5.0, 1));
        assertEquals("venda encerrada", exception.getMessage());
    }
}
