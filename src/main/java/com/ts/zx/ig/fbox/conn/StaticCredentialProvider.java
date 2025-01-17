package com.ts.zx.ig.fbox.conn;

public class StaticCredentialProvider implements CredentialProvider {
    private final ClientCredential clientCred;
    private final UserCredential userCred;

    public StaticCredentialProvider(String clientId, String clientSecret, String userName, String password) {
        this.clientCred = new ClientCredential(clientId, clientSecret);
        this.userCred = new UserCredential(userName, password);
    }

    @Override
    public ClientCredential getClientCredential() {
        return clientCred;
    }

    @Override
    public UserCredential getUserCredential() {
        return userCred;
    }
}
