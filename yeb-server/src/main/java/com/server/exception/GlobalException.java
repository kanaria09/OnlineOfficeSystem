package com.server.exception;

import com.server.pojo.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理类
 * @author: Bug
 */

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(SQLException.class)
    public RespBean mySqlException(SQLException e){
        String error = "";
        if(e instanceof SQLIntegrityConstraintViolationException){
            error = "数据存在关联！";
        }
        return RespBean.error("数据库异常！" + error);
    }
}
