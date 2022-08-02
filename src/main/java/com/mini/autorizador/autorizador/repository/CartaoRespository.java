package com.mini.autorizador.autorizador.repository;

import com.mini.autorizador.autorizador.domain.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRespository extends JpaRepository<Cartao,String> {
}
