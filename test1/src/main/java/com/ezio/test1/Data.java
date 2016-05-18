package com.ezio.test1;

import java.io.Serializable;

/**
 * Created by Ezio on 2016/5/18.
 */
public class Data implements Serializable {


    /**
     * code : 200
     * userId : null1000
     * token : iunNhpF+u1qfM4x7tz9HEBve9/DUZuCRr503edFHmosZjSZp/95gjnVRPpouzdJIP59B0oN+lsN3q5qESq3XuQ==
     */
    private int code;
    private String userId;
    private String token;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
