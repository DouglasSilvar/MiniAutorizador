package com.mini.autorizador.autorizador.exceptions.runtime;

import com.mini.autorizador.autorizador.DTO.CartaoDTO;
import lombok.Getter;

@Getter
public class CartaoNaoExistenteException extends RuntimeException{

    private CartaoDTO cartaoDTO;
    public CartaoNaoExistenteException(String msg){
        super(msg);
    }

    public CartaoNaoExistenteException(CartaoDTO cartaoDTO) {
        this.cartaoDTO = cartaoDTO;
    }
}
