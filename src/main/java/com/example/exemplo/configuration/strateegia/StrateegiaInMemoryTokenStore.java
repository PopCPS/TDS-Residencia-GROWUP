package com.example.exemplo.configuration.strateegia;

import java.util.HashMap;

public class StrateegiaInMemoryTokenStore {

    private static StrateegiaInMemoryTokenStore INSTANCE;

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
