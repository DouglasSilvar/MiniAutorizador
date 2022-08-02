package com.mini.autorizador.autorizador.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini.autorizador.autorizador.DTO.CartaoDTO;
import com.mini.autorizador.autorizador.DTO.TransacaoDTO;

import java.math.BigDecimal;

public class CartaoDTOMock {

    public static CartaoDTO getAll(){
        var cartaoDto = new CartaoDTO();
        cartaoDto.setNumeroCartao("0000000000000050");
        cartaoDto.setSenha("1234");
        return cartaoDto;
    }
    public static CartaoDTO getAllSenhacriptografada(){
        var cartaoDto = new CartaoDTO();
        cartaoDto.setNumeroCartao("0000000000000050");
        cartaoDto.setSenha("B7H5pvo/rH4hQwrvYHVu7DLs2VKaxyH0NHa1v3qKVqViMgq0Rsxh92NwgXE3ZhdN");
        return cartaoDto;
    }

    public static String json() throws JsonProcessingException {
        var obj = getAll();
        var ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(obj);
    }

    public static String transacaoJson() throws JsonProcessingException {
        var transacaoDTO = getAllTransacao();
        var ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(transacaoDTO);
    }

    public static TransacaoDTO getAllTransacao(){
        var transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao("0000000000000050");
        transacaoDTO.setSenhaCartao("1234");
        transacaoDTO.setValor(new BigDecimal(10));
        return transacaoDTO;
    }

}
