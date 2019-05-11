package pers.fancy.cloud.search.model.es;


/**
 * 文档模型状态枚举
 * @author peter
 * @version V1.0 创建时间：18/8/22
 *          Copyright 2018 by landray & STIC
 */
public enum DcpDocStatusEnum {

	/**dcpdoc数据有效状态*/
	INVALID("0","无效"),
    VAILD("1","有效");

	String code;
    String desc;
    DcpDocStatusEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
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
