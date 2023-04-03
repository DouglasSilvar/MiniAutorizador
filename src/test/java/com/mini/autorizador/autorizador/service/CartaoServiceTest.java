package com.mini.autorizador.autorizador.service;

import com.mini.autorizador.autorizador.domain.Cartao;
import com.mini.autorizador.autorizador.exceptions.runtime.CartaoNaoExistenteException;
import com.mini.autorizador.autorizador.exceptions.runtime.GetSaldoException;
import com.mini.autorizador.autorizador.exceptions.runtime.TransacaoCartaoInexistenteException;
import com.mini.autorizador.autorizador.mock.CartaoDTOMock;
import com.mini.autorizador.autorizador.repository.CartaoRespository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CartaoServiceTest {

    private CartaoService cartaoServiceUnderTest;

    @BeforeEach
    void setUp() {
        cartaoServiceUnderTest = new CartaoService();
        cartaoServiceUnderTest.respository = mock(CartaoRespository.class);
    }

    @Test
    void testSalvarNovoCartao() {
        // Setup
        var cartaoDTO = CartaoDTOMock.getAll();

        // Configure CartaoRespository.findById(...).
        var cartao1 = new Cartao();
        cartao1.setNumeroCartao(cartaoDTO.getNumeroCartao());
        cartao1.setSenha(cartaoDTO.getSenha());
        cartao1.setSaldo(new BigDecimal("0.00"));
        final Optional<Cartao> cartao = Optional.of(cartao1);
        when(cartaoServiceUnderTest.respository.findById(cartaoDTO.getNumeroCartao())).thenReturn(cartao);

        // Run the test
        var result = cartaoServiceUnderTest.salvarNovoCartao(cartaoDTO);

        Assertions.assertEquals(result,cartaoDTO);
    }

    @Test
    void testSalvarNovoCartao_CartaoRespositoryReturnsAbsent() {
        // Setup
        var cartaoDTO = CartaoDTOMock.getAll();
        when(cartaoServiceUnderTest.respository.findById(cartaoDTO.getNumeroCartao())).thenReturn(Optional.empty());

        Assertions.assertThrows(CartaoNaoExistenteException.class,()-> cartaoServiceUnderTest.salvarNovoCartao(cartaoDTO));
    }

    @Test
    void testGetSaldo() {
        // Setup
        // Configure CartaoRespository.findById(...).
        var cartaoDTO = CartaoDTOMock.getAll();

        // Configure CartaoRespository.findById(...).
        var cartao1 = new Cartao();
        cartao1.setNumeroCartao(cartaoDTO.getNumeroCartao());
        cartao1.setSenha(cartaoDTO.getSenha());
        cartao1.setSaldo(new BigDecimal("50"));
        final Optional<Cartao> cartao = Optional.of(cartao1);
        when(cartaoServiceUnderTest.respository.findById(cartaoDTO.getNumeroCartao())).thenReturn(cartao);

        // Run the test
        var result = cartaoServiceUnderTest.getSaldo(cartaoDTO.getNumeroCartao());

        // Verify the results
        assertThat(result).isEqualTo(cartao1.getSaldo());
    }

    @Test
    void testGetSaldo_CartaoRespositoryReturnsAbsent() {
        // Setup
        var cartaoDTO = CartaoDTOMock.getAll();
        when(cartaoServiceUnderTest.respository.findById(cartaoDTO.getNumeroCartao())).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> cartaoServiceUnderTest.getSaldo(cartaoDTO.getNumeroCartao()))
                .isInstanceOf(GetSaldoException.class);
    }

    @Test
    void testTransacao() {
        // Setup
        var dto = CartaoDTOMock.getAllTransacao();
        var dtoCripto = CartaoDTOMock.getAllSenhacriptografada();

        // Configure CartaoRespository.findById(...).
        final Cartao cartao1 = new Cartao();
        cartao1.setNumeroCartao(dtoCripto.getNumeroCartao());
        cartao1.setSenha(dtoCripto.getSenha());
        cartao1.setSaldo(dto.getValor());
        final Optional<Cartao> cartao = Optional.of(cartao1);
        when(cartaoServiceUnderTest.respository.findById(dto.getNumeroCartao())).thenReturn(cartao);

        // Configure CartaoRespository.save(...).
        var cartao2 = new Cartao();
        cartao2.setNumeroCartao(dto.getNumeroCartao());
        cartao2.setSenha(dto.getSenhaCartao());
        cartao2.setSaldo(dto.getValor());
        when(cartaoServiceUnderTest.respository.save(new Cartao())).thenReturn(cartao2);

        // Run the test
        cartaoServiceUnderTest.transacao(dto);

        // Verify the results
        verify(cartaoServiceUnderTest.respository).save(cartao1);
    }

    @Test
    void testTransacao_CartaoRespositoryFindByIdReturnsAbsent() {
        // Setup
        var dto = CartaoDTOMock.getAllTransacao();
        when(cartaoServiceUnderTest.respository.findById(dto.getNumeroCartao())).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> cartaoServiceUnderTest.transacao(dto))
                .isInstanceOf(TransacaoCartaoInexistenteException.class);
    }
}
