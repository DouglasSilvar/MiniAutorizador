package com.mini.autorizador.autorizador.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Proxy(lazy=false)
@Table(name = "cartao")
public class Cartao implements Serializable {

    @Id
    private String numeroCartao;
    private String senha;
    private BigDecimal saldo;
}
