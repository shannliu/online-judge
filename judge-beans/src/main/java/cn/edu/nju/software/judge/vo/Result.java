package cn.edu.nju.software.judge.vo;

import java.io.Serializable;

public class Result<T> implements Serializable {


    private static final long serialVersionUID = 444059347993399276L;
    private Integer code = 1;

    private String msg;

    private T result;

    public Result() {

    }

    public Result(Integer code) {
        this.code = code;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public static Result success(){
        return new Result(0,"成功");
    }
    public static Result failure(String msg){
        return new Result(400,msg);
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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
