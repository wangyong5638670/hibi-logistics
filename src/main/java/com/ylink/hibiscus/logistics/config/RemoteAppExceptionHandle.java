/*
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: lilinjun 2019-03-12
 */
package com.ylink.hibiscus.logistics.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ylink.hibiscus.common.base.exception.BusinessException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author lilinjun
 * @date 2019-07-01
 */
@RestControllerAdvice(value = "com.ylink.hibiscus.depot.app")
@Slf4j
public class RemoteAppExceptionHandle {



    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handle(Exception e, HttpServletRequest request){
        Response response = new Response();
        response.setError("Internal Server Error");
        response.setStatus("500");
        response.setTimestamp(new Date());
        response.setMessage(e instanceof BusinessException ? e.toString() : e.getMessage());
        response.setException(e.getClass());
        response.setPath(request.getServletPath());
        return response;
    }

    @Getter
    @Setter
    public class Response{

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
        private Date timestamp;

        private String status;

        private String error;

        private Class<? extends Exception> exception;

        private String message;

        private String path;

    }

}
