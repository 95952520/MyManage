package com.xuchen.base;

public class Result{
    // 响应业务状态
    private boolean success;

    // 响应状态码
    private int code;//0:成功 1:失败

    // 响应的消息
    private String msg;

    // 列表总数
    private int count;

    // 响应的数据  
    private Object data;


    public static Result success(Object data) {
        return new Result(data);
    }

    public static Result success(int count, Object data) {
        return new Result(count,data);
    }

    public static Result success() {
        return new Result(null);
    }

    public static Result fail(String msg) {
        return new Result(false, 1, msg, null);
    }

    public static Result fail() {
        return new Result(false, 1, "error", null);
    }


    private Result(boolean success, int code, String msg, Object data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private Result(boolean success, int code) {
        this.success = success;
        this.code = code;
    }

    private Result(Object data) {
        this.success = true;
        this.code = 0;
        this.data = data;
        this.msg = "success";
    }

    private Result(int count,Object data) {
        this.code = 0;
        this.success = true;
        this.count = count;
        this.data = data;
        this.msg = "success";
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
