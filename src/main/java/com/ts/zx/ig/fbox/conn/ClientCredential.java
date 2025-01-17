package com.ts.zx.ig.fbox.conn;

public class ClientCredential {
    public ClientCredential(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    /// <summary>
    /// API账号
    /// </summary>
    public String clientId;

    /// <summary>
    /// API secret
    /// </summary>
    public String clientSecret;
}
