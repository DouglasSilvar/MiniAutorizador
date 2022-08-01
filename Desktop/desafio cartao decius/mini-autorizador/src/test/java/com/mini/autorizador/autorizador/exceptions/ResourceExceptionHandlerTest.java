package com.mini.autorizador.autorizador.exceptions;

import com.mini.autorizador.autorizador.domain.Cartao;
import com.mini.autorizador.autorizador.exceptions.runtime.*;
import com.mini.autorizador.autorizador.mock.CartaoDTOMock;
import com.mini.autorizador.autorizador.repository.CartaoRespository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

import static com.mini.autorizador.autorizador.enums.ResponseEnum.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ResourceExceptionHandlerTest {

    private ResourceExceptionHandler resourceExceptionHandlerUnderTest;


    @MockBean
    private MethodParameter methodParameter;

    @MockBean
    private BindingResult bindingResult;

    @MockBean
    MethodArgumentNotValidException e;

    @BeforeEach
    void setUp() {
        resourceExceptionHandlerUnderTest = new ResourceExceptionHandler();
        resourceExceptionHandlerUnderTest.respository = mock(CartaoRespository.class);
    }

    @Test
    void testValidation() {
        // Setup
        var e = new MethodArgumentNotValidException(methodParameter,bindingResult);
        final HttpServletRequest request = new MockHttpServletRequest();

        // Run the test
        var result = resourceExceptionHandlerUnderTest.validation(e, request);

        // Verify the results
        Assertions.assertEquals(result.getStatusCode(),HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    void testCartaoNaoExiste() {
        var dto = CartaoDTOMock.getAll();
        var e = new CartaoNaoExistenteException(dto);

        // Configure CartaoRespository.save(...).
        var cartao = new Cartao();
        cartao.setNumeroCartao(dto.getNumeroCartao());
        cartao.setSenha(dto.getSenha());
        cartao.setSaldo(new BigDecimal(500));
        when(resourceExceptionHandlerUnderTest.respository.save(cartao)).thenReturn(cartao);

        // Run the test
        var result = resourceExceptionHandlerUnderTest.cartaoNaoExiste(e);
        Assertions.assertEquals(result.getStatusCode(),HttpStatus.CREATED);
    }

    @Test
    void testGetSaldo() {
        // Setup
        final GetSaldoException e = new GetSaldoException("msg");

        // Run the test
        var result = resourceExceptionHandlerUnderTest.getSaldo(e);

        // Verify the results
        Assertions.assertEquals(result,ResponseEntity.status(404).build());
    }

    @Test
    void testTransacaoCartaoInexistente() {
        // Setup
        final TransacaoCartaoInexistenteException e = new TransacaoCartaoInexistenteException("msg");

        // Run the test
        var result = resourceExceptionHandlerUnderTest.transacaoCartaoInexistente(e);

        // Verify the results
        Assertions.assertEquals(result,ResponseEntity.status(422).body(CARTAO_INEXISTENTE));
    }

    @Test
    void testSenhaDiferente() {
        // Setup
        final SenhaDiferenteException e = new SenhaDiferenteException("msg");

        // Run the test
        var result = resourceExceptionHandlerUnderTest.senhaDiferente(e);

        // Verify the results
        Assertions.assertEquals(result,ResponseEntity.status(422).body(SENHA_INVALIDA));
    }

    @Test
    void testSaldoInsulficiente() {
        // Setup
        final SaldoInsuficienteException e = new SaldoInsuficienteException("msg");

        // Run the test
        var result = resourceExceptionHandlerUnderTest.saldoInsulficiente(e);

        // Verify the results
        Assertions.assertEquals(result,ResponseEntity.status(422).body(SALDO_INSUFICIENTE));
    }
}
