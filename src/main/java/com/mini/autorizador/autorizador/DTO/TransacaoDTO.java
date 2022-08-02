package com.mini.autorizador.autorizador.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoDTO implements Serializable {

    @NotEmpty(message = "VALOR NAO PODE SER VAZIO")
    @NotNull(message = "FALTA CAMPO CARTAO")
    @Size(min=16,max=16,message = "VALOR COM 16 CARACTERES")
    private String numeroCartao;
    @NotNull(message = "FALTA CAMPO SENHA")
    @NotEmpty(message = "VALOR NAO PODE SER VAZIO")
    private String senhaCartao;
    @Positive(message = "VALOR DEVE SER POSITIVO MAIOR QUE ZERO")
    @NotNull(message = "FALTA CAMPO VALOR")
    private BigDecimal valor;

    public Boolean senhaIgual(String senhaPersistida){
        return this.senhaCartao.equals(senhaPersistida);
    }
}
