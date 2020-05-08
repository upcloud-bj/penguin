/*
 * @author KouKi
 * Copyright (c) 2020.
 */

package com.upcloud_bj.penguin.common.controller.exchange;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class ResponseMessage implements Serializable {
    private int code;
    private String message;
    private String messageDetail;
    private Object data;

    public ResponseMessage() {
        code = ResponseCode.EXP_SYS.getCode();
        message= ResponseCode.EXP_SYS.getMessage();
    }

    public ResponseMessage(ResponseCode code) {
        this(code, null);
    }

    public ResponseMessage(ResponseCode code, String messageDetail) {
        this(code, messageDetail, null);

    }

    public ResponseMessage(ResponseCode code, Object data) {
        this.code = code.getCode();
        this.message =code.getMessage();
        this.data = data;
    }

    public ResponseMessage(ResponseCode code, String messageDetail, Object data) {
        this.code = code.getCode();
        this.message =code.getMessage();
        this.messageDetail = messageDetail;
        this.data = data;
    }

    public static ResponseMessage newInstance(ResponseCode code) {
        ResponseMessage message = new ResponseMessage(code);
        return message;
    }

    public static ResponseMessage newInstance(ResponseCode code, String messageDetail) {
        ResponseMessage message = new ResponseMessage(code, messageDetail);
        return message;
    }

    public static ResponseMessage newInstance(ResponseCode code, String messageDetail, Object data) {
        ResponseMessage message = new ResponseMessage(code, messageDetail, data);
        return message;
    }

    public static ResponseMessage newInstance(ResponseCode code, Object data) {
        ResponseMessage message = new ResponseMessage(code, data);
        return message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageDetail() {
        return messageDetail;
    }

    public void setMessageDetail(String messageDetail) {
        this.messageDetail = messageDetail;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
