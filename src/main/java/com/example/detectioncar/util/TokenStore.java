package com.example.detectioncar.util;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 很简单的 token 存储（内存版）。
 * 优点：好写、好懂；缺点：重启后 token 会失效。
 */
public class TokenStore {

    private static final Map<String, TokenInfo> TOKENS = new ConcurrentHashMap<>();

    private TokenStore() {}

    public static String createToken(String username) {
        String token = UUID.randomUUID().toString().replace("-", "");
        TOKENS.put(token, new TokenInfo(username, System.currentTimeMillis()));
        return token;
    }

    public static TokenInfo get(String token) {
        return TOKENS.get(token);
    }

    public static void remove(String token) {
        TOKENS.remove(token);
    }
}
