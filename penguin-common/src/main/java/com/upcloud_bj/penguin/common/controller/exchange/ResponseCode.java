/*
 * @author KouKi
 * Copyright (c) 2020.
 */

package com.upcloud_bj.penguin.common.controller.exchange;

public enum ResponseCode {

    /* 成功返回 */
    SUCCESS(1, "成功"),

    /* 业务错误 [1000 - 8999]*/
    ERR_BUSINESS(1000, "业务错误"),

    /* 系统错误 [9000 - 9999] */
    EXP_SYS(9000, "系统错误");

    private int code;
    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
