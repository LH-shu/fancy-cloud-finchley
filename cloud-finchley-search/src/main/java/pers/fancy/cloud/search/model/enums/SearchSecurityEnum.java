package pers.fancy.cloud.search.model.enums;

/**
 * 搜索密级类型
 */
public enum SearchSecurityEnum {

    /**统一搜索查询密级*/
    OPEN("1","公开"),
    INTERIOR("2","内部"),
    SECRET("3","秘密"),
    CONFIDENTIAL("4","机密");

    SearchSecurityEnum(String code,String desc){
        this.code=code;
        this.desc=desc;
    };
    String code;
    String desc;

    /**
     * 根据code获取desc
     *
     * @param code : 键值key
     * @return String
     */
    public static String getDescByCode(String code) {
        SearchSecurityEnum[] enums = SearchSecurityEnum.values();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].getCode().toString().equals(code)) {
                return enums[i].getDesc();
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
