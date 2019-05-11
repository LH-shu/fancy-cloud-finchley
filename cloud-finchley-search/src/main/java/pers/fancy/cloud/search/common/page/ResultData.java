package pers.fancy.cloud.search.common.page;


/**
 * 微服务返回数据封装
 *
 */
public class ResultData<T> {

    /**返回状态码*/
    private String status;
    /**返回消息提示*/
    private String msg;
    /**返回数据结构*/
    private T data;
    public ResultData(){
    }

    public ResultData(String status, String msg){
        this.status = status;
        this.msg = msg;
    }
    public ResultData(String status, String msg,T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static ResultData DATA(Object data) {
        ResultData resultData = new ResultData();
        resultData.status = ResultStatusEnum.SUCCESS.getStatus();
        resultData.msg = ResultStatusEnum.SUCCESS.getMsg();
        resultData.data = data;
        return resultData;
    }
    
    public static ResultData ERROR() {
        ResultData resultData = new ResultData();
        resultData.status = ResultStatusEnum.ERROR.getStatus();
        resultData.msg = ResultStatusEnum.ERROR.getMsg();
        resultData.data = null;
        return resultData;
    }
    public static ResultData ERROR(String msg) {
        ResultData resultData = new ResultData();
        resultData.status = ResultStatusEnum.ERROR.getStatus();
        resultData.msg = msg;
        resultData.data = null;
        return resultData;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getInfo() {
        return msg;
    }
    public void setInfo(String info) {
        this.msg = info;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}
