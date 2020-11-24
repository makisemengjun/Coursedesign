package com.company;

// 为了与内部异常类区分开来创建了自己的异常类
// 这样在try&catch处理异常时比较方便
public class CException extends Exception {
    String message;

    CException(String msg) {
        message = msg;
    }

    public String getCMessage() {
        return message;
    }
}
