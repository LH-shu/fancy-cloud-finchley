package pers.fancy.cloud.search.model.enums;

/**
 * 搜索时间类型
 */
public enum SearchTimeTypeEnum {

    /**搜索时间类型*/
    ONEDAY("一天内"),
    ONEWEEK("一周内"),
    ONEMONTH("一月内"),
    ONEYEAR("一年内");

    private String desc;

    SearchTimeTypeEnum(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
