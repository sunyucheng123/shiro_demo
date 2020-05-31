package com.syc.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class JsonResult implements Serializable {

    /*状态码*/
    private int state = 1;//1.表示SUCCESS,0.表示ERROR

    /*状态信息*/
    private String message = "ok";

    /*正确数据*/
    private Object data;

    public JsonResult(String message) {
        this.message = message;
    }

    public JsonResult(Object data) {
        this.data = data;
    }

    public JsonResult(int state, String message) {
        this.state = state;
        this.message = message;
    }

    public JsonResult(Throwable e) {
        this.state = 0;
        this.message = e.getMessage();
    }

    public JsonResult() {
        super();
    }
}
