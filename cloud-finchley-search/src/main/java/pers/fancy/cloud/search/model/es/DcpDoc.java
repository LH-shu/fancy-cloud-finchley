package pers.fancy.cloud.search.model.es;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * dcp搜索系统-文档模型
 */
@Data
public class DcpDoc {

    /**存业务id*/
    private String id;
    /**父id*/
    private String parentId;
    /**法人单位*/
    private String orgId;

    /**.html .pdf .docx .doc .xlsx .jpg .ppt .txt  unknown*/
    private String docType;
    /**标题 */
    private String title;
    /**内容、正文 */
    private String content;
    /**附件的路径 */
    private String filePath;
    /**创建时间 */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**修改时间 */
    @JSONField (format="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**标签*/
    private String tags;
    /**文档状态*/
    private String docStatus=DcpDocStatusEnum.VAILD.getCode();
    /**作者id*/
    private String owner;
    /**作者姓名*/
    private String ownerName;

    /**密级*/
    private String security;
    /**可搜索人员id*/
    private List<String> canSearchUser;
    /**可搜索人员角色*/
    private List<String> canSearchRole;

    /**哪个应用*/
    private String app;
    /**哪个模块*/
    private String module;
    /**访问路径、方式*/
    private String url;

    /**文档包含关系，一个主文档包含多个附件*/
    private List<String> children;



}