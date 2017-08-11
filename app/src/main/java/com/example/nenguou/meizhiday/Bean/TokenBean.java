package com.example.nenguou.meizhiday.Bean;

/**
 * Created by b3 on 2017/8/5.
 */

public class TokenBean {

    /**
     * access_token : 4b78cd1c22d7fdd972a02d17cf0190ac39864fd4
     * token_type : bearer
     * scope : public_repo,user
     */

    private String access_token;
    private String token_type;
    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
