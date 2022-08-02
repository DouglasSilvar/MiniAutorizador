package com.mini.autorizador.autorizador.exceptions.runtime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetSaldoException extends RuntimeException{

    public GetSaldoException(String msg){
        super(msg);
    }


}
