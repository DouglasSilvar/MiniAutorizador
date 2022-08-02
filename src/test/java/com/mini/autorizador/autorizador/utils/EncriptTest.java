package com.mini.autorizador.autorizador.utils;

import com.mini.autorizador.autorizador.mock.CartaoDTOMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EncriptTest {

    @Test
    void testEncript() {
        var senha = CartaoDTOMock.getAll().getSenha();
        var key = CartaoDTOMock.getAll().getNumeroCartao();
        var senhaCriptoGrafada = Encript.encript(senha,key);
        var senhaDescriptografada = Encript.decript(senhaCriptoGrafada,key);
        Assertions.assertEquals(senha,senhaDescriptografada);
    }
}
