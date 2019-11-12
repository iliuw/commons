package com.robby.app.commons.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created @ 2019/11/12
 *
 * @author liuwei
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class BaseException extends Exception implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 8408697132231869968L;
    int code;
    String message;

    public BaseException(int code) {
        this.code = code;
    }

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public BaseException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.message = message;
    }
}
