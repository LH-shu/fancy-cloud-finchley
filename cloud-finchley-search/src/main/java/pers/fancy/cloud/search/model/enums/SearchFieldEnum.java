package pers.fancy.cloud.search.model.enums;

/**
 * 搜索域类型
 */
public enum SearchFieldEnum {

    /**搜索域类型*/
    title("标题"),
    ownerName("创建者"),
    content("正文"),
    docType("附件");

    private String desc;

    SearchFieldEnum(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
