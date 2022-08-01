package com.mini.autorizador.autorizador.exceptions.runtime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TransacaoCartaoInexistenteException extends RuntimeException{

    public TransacaoCartaoInexistenteException(String msg){
        super(msg);
    }
}
