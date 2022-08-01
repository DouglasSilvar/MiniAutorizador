package com.mini.autorizador.autorizador.exceptions;

import com.mini.autorizador.autorizador.DTO.CartaoDTO;
import com.mini.autorizador.autorizador.domain.Cartao;
import com.mini.autorizador.autorizador.exceptions.runtime.*;
import com.mini.autorizador.autorizador.repository.CartaoRespository;
import com.mini.autorizador.autorizador.utils.Encript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.Instant;

import static com.mini.autorizador.autorizador.enums.ResponseEnum.*;

@ControllerAdvice
public class ResourceExceptionHandler {

    @Autowired
    CartaoRespository respository;

    private static BigDecimal VALOR_INICIAL = new BigDecimal(500);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError err = new ValidationError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Validation exception");
        err.setPath(request.getRequestURI());
        for (FieldError f : e.getBindingResult().getFieldErrors()){
            err.addError(f.getField(),f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(CartaoNaoExistenteException.class)
    public ResponseEntity<Object> cartaoNaoExiste(CartaoNaoExistenteException e) {
        var cartaoDTO = e.getCartaoDTO();
        var cartao = new Cartao();
        var senhaCriptografada = Encript.encript(cartaoDTO.getSenha(),cartaoDTO.getNumeroCartao());
        cartao.setNumeroCartao(cartaoDTO.getNumeroCartao());
        cartao.setSenha(senhaCriptografada);
        cartao.setSaldo(VALOR_INICIAL);
        respository.save(cartao);
        cartao.setSenha(Encript.decript(senhaCriptografada,cartaoDTO.getNumeroCartao()));
        return ResponseEntity.status(201).body(new CartaoDTO(cartao));
    }

    @ExceptionHandler(GetSaldoException.class)
    public ResponseEntity<Object> getSaldo(GetSaldoException e) {
        return ResponseEntity.status(404).build();
    }

    @ExceptionHandler(TransacaoCartaoInexistenteException.class)
    public ResponseEntity<Object> transacaoCartaoInexistente(TransacaoCartaoInexistenteException e) {
        return ResponseEntity.status(422).body(CARTAO_INEXISTENTE);
    }

    @ExceptionHandler(SenhaDiferenteException.class)
    public ResponseEntity<Object> senhaDiferente(SenhaDiferenteException e) {
        return ResponseEntity.status(422).body(SENHA_INVALIDA);
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<Object> saldoInsulficiente(SaldoInsuficienteException e) {
        return ResponseEntity.status(422).body(SALDO_INSUFICIENTE);
    }


}
