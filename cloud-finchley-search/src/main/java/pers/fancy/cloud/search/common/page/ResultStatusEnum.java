package pers.fancy.cloud.search.common.page;

/**
 * 微服务返回状态
 */
public enum ResultStatusEnum {
    /**微服务返回状态*/
    ERROR("-1","内部错误"),
    SUCCESS("0","成功"),
    NO_LOGIN("3001","用户未登录"),
    TOKEN_INVALID("3002","token无效"),
    NO_AUTH("4001","没有权限"),
    NO_AUTH_DATA("4002","没有权限操作该数据");

    private String status;
    private String msg;

    private ResultStatusEnum(String status,String msg)
    {
        this.status=status;
        this.msg=msg;
    }

    public String getMsg()
    {
        return this.msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
