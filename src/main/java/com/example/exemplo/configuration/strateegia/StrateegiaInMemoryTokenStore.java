package com.example.exemplo.configuration.strateegia;

import java.util.HashMap;

/**
 * Define uma maneira de armazenar tokens em memória, para evitar refazer a chamada ao strateegia, caso
 * usuário já tenha se autenticado
 */
public class StrateegiaInMemoryTokenStore {

    // padrão singleton: garante que apenas 1 instância desta classe seja instanciada
    private static StrateegiaInMemoryTokenStore INSTANCE;

    // hashmap contendo um token por username
    private final HashMap<String, String> tokenStore;

    public StrateegiaInMemoryTokenStore() {
        tokenStore = new HashMap<>(1);
    }

    public static StrateegiaInMemoryTokenStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StrateegiaInMemoryTokenStore();
        }
        return INSTANCE;
    }

    public HashMap<String, String> getTokenStore() {
        return tokenStore;
    }
}
