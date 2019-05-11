package pers.fancy.cloud.search.model.enums;

/**
 * 搜索范围类型
 */
public enum SearchAppEnum {

    /**搜索范围类型*/
    dcp_app_dcs("文档管控"),
    dcp_app_pcs("个人网盘");

    private String desc;

    SearchAppEnum(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
