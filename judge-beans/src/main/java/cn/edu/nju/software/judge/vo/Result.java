package cn.edu.nju.software.judge.vo;

import cn.edu.nju.software.judge.submission.ResultCode;

import java.io.Serializable;

public class Result<T> implements Serializable {


    private static final long serialVersionUID = 444059347993399276L;
    private Integer code = 1;

    private String msg;

    private T data;

    public Result() {

    }

    public Result(Integer code) {
        this.code = code;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success(){
        return new Result(0,"成功");
    }
    public static Result failure(String msg){
        return new Result(400,msg);
    }

    public static  <T> Result success(T data){
        Result<T> result = new Result();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(data);
        return result;
    }
    public static Result failure(Integer code,String msg){
        return new Result(code,msg);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
