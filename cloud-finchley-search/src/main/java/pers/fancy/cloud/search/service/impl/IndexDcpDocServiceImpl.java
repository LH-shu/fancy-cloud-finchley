package pers.fancy.cloud.search.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import pers.fancy.cloud.search.dao.repository.IndexDcpDocRepository;
import pers.fancy.cloud.search.model.es.DcpDoc;
import pers.fancy.cloud.search.service.IndexDcpDocService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 索引增删改-接口实现类
 */
@Service("indexDcpDocService")
public class IndexDcpDocServiceImpl implements IndexDcpDocService {

    @Resource
    private IndexDcpDocRepository indexDcpDocRepository;

    @Override
    public void createDcpDoc(@RequestBody DcpDoc dcpDoc) throws Exception{
        indexDcpDocRepository.createDcpDoc(dcpDoc);
    }

    @Override
    public void createDcpDocBatch(@RequestBody List<DcpDoc> dcpDocList) throws Exception{
        indexDcpDocRepository.createDcpDocBatch(dcpDocList);
    }

    @Override
    public void deleteDcpDoc(List<String> ids) throws Exception{
        indexDcpDocRepository.deleteDcpDoc(ids);
    }

    @Override
    public void deleteDcpDocById(String id) throws Exception{
        indexDcpDocRepository.deleteDcpDocById(id);
    }

    @Override
    public void updateDcpDoc(@RequestBody DcpDoc dcpDoc) throws Exception{
        indexDcpDocRepository.updateDcpDoc(dcpDoc);
    }

}
