package pers.fancy.cloud.search.web.rest;


import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pers.fancy.cloud.search.model.es.DcpDoc;
import pers.fancy.cloud.search.service.IndexDcpDocService;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * DcpDoc索引增删改
 * @author 李醴茝
 */
@RestController
@Slf4j
@Api(tags = "DcpDoc索引增删改", value = "DcpDoc索引增删改")
public class IndexDcpDocController {


    @Autowired
    private MessageSource messageSource;

    @Resource
    private RestHighLevelClient esClient;

    @Autowired
    private IndexDcpDocService indexDcpDocService;

    /**
     * 创建索引
     * @param dcpDoc
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/indexDcpDoc/create", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public void createDcpDoc(@RequestBody DcpDoc dcpDoc) throws Exception{
        indexDcpDocService.createDcpDoc(dcpDoc);
    }

    /**
     *
     * 批量创建索引
     * @param dcpDocList
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/indexDcpDoc/createBatch", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public void createDcpDocBatch(@RequestBody List<DcpDoc> dcpDocList) throws Exception{
        indexDcpDocService.createDcpDocBatch(dcpDocList);
    }

    /**
     *
     * 批量删除索引
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/indexDcpDoc/delete", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteDcpDoc(@RequestBody List<String> ids) throws Exception{
        if(!ids.isEmpty()){
            indexDcpDocService.deleteDcpDoc(ids);
        }
    }

    /**
     *
     * 按id删除索引
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/indexDcpDoc/deleteById", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteDcpDocById(@RequestBody String id) throws Exception{
        indexDcpDocService.deleteDcpDocById(id);
    }

    /**
     *
     * 根据id，修改其它不为null的属性
     * @param dcpDoc
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/indexDcpDoc/update", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateDcpDoc(@RequestBody  DcpDoc dcpDoc) throws Exception{
        if(dcpDoc.getId()!=null){
            indexDcpDocService.updateDcpDoc(dcpDoc);
        }else{
            throw new Exception("更新id不能为空");
        }
    }


}

