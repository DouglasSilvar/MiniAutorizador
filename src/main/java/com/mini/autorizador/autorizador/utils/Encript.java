package com.mini.autorizador.autorizador.utils;

import lombok.experimental.UtilityClass;
import org.jasypt.util.text.AES256TextEncryptor;

@UtilityClass
public class Encript {

    public static String encript(String senha, String key){
        AES256TextEncryptor aesEncryptor = new AES256TextEncryptor();
        aesEncryptor.setPassword(key);
        return aesEncryptor.encrypt(senha);

    }
    public static String decript(String senhaCriptografada, String key){
        AES256TextEncryptor aesEncryptor = new AES256TextEncryptor();
        aesEncryptor.setPassword(key);
        return aesEncryptor.decrypt(senhaCriptografada);
    }
}
