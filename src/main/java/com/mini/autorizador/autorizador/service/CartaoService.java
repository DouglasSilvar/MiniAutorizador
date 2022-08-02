package com.mini.autorizador.autorizador.service;

import com.mini.autorizador.autorizador.DTO.CartaoDTO;
import com.mini.autorizador.autorizador.DTO.TransacaoDTO;
import com.mini.autorizador.autorizador.exceptions.runtime.*;
import com.mini.autorizador.autorizador.repository.CartaoRespository;
import com.mini.autorizador.autorizador.utils.Encript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartaoService {

    @Autowired
    CartaoRespository respository;

    private static BigDecimal ZERO = new BigDecimal(0.0);

    public CartaoDTO salvarNovoCartao(CartaoDTO cartaoDTO){
            respository.findById(cartaoDTO.getNumeroCartao())
                    .orElseThrow(()-> new CartaoNaoExistenteException(cartaoDTO));
            return cartaoDTO;
    }

    public BigDecimal getSaldo(String numero) {
            return respository.findById(numero)
                    .orElseThrow(GetSaldoException::new).getSaldo();
    }

    public void transacao(TransacaoDTO dto) {
            var cartaoBuscado = respository.findById(dto.getNumeroCartao())
                     .orElseThrow(TransacaoCartaoInexistenteException::new);
             if (!dto.senhaIgual(Encript.decript(cartaoBuscado.getSenha(),dto.getNumeroCartao()))){
                throw new SenhaDiferenteException();
             }
             var result = cartaoBuscado.getSaldo().subtract(dto.getValor());
             if(result.compareTo(ZERO) >= 0) {
                 cartaoBuscado.setSaldo(result);
                 respository.save(cartaoBuscado);
             }else{
                 throw new SaldoInsuficienteException();
             }
    }

}
