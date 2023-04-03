package com.mini.autorizador.autorizador.exceptions.runtime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SenhaDiferenteException extends RuntimeException{

    public SenhaDiferenteException(String msg){
        super(msg);
    }
}