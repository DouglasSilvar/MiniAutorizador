package com.mini.autorizador.autorizador.controller;

import com.mini.autorizador.autorizador.DTO.CartaoDTO;
import com.mini.autorizador.autorizador.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(value = "/cartoes")
public class CartoesController {

    @Autowired
    CartaoService service;

    @Transactional
    @PostMapping
    public ResponseEntity<Object> postCartao(@Valid @RequestBody CartaoDTO cartaoDTO){
        service.salvarNovoCartao(cartaoDTO);
        return ResponseEntity.status(422).body(cartaoDTO);
    }

    @Transactional
    @GetMapping(value = "/{numero}")
    public ResponseEntity<Object> getSaldo(@PathVariable String numero) {
        var saldo = service.getSaldo(numero);
        return ResponseEntity.ok().body(saldo);
    }

}
