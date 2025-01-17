package com.ts.zx.ig.fbox.conn;

public interface CredentialProvider {

    /// <summary>
    /// 获取用户密钥信息
    /// </summary>
    /// <returns></returns>
    ClientCredential getClientCredential();

    /// <summary>
    /// 获取用户信息
    /// </summary>
    /// <returns></returns>
    UserCredential getUserCredential();

}
