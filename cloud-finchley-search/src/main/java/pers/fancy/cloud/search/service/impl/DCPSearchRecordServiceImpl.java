package pers.fancy.cloud.search.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pers.fancy.cloud.search.dao.repository.DCPSearchRecordRepository;
import pers.fancy.cloud.search.model.dto.SearchRecordDto;
import pers.fancy.cloud.search.model.entity.SearchRecordEntity;
import pers.fancy.cloud.search.service.DCPSearchRecordService;
import pers.fancy.cloud.search.util.CommonDateUtils;

import java.util.*;


@Service
@Slf4j
public class DCPSearchRecordServiceImpl implements DCPSearchRecordService {

    @Autowired
    DCPSearchRecordRepository dcpSearchRecordRepository;

//    @Resource
//    DcpSysIdApiGenorator dcpSysIdApiGenorator;

    @Override
    public SearchRecordEntity addSearchRecord(String searchRecordWord) throws Exception {
        SearchRecordEntity searchRecordEntity = new SearchRecordEntity();
        if (searchRecordWord == null || StringUtils.isEmpty(searchRecordWord)) {
            log.error("【创建搜索记录】用户输入的搜索内容为空");
            throw new Exception("用户没有输入搜索信息");
        }
        searchRecordEntity.setFdWord(searchRecordWord);
        searchRecordEntity.setFdSearchTime(new Date());

        //为搜索记录添加缓存中的id和登录名
//        String userId = RedisUtil.getUserId();
//        searchRecordEntity.setFdUserId(userId);
//        searchRecordEntity.setFdUserName(RedisUtil.getDetailedUserInfo(userId).getInfo().getFdName());
//        if (searchRecordEntity.getFdUserId() == null || searchRecordEntity.getFdUserName() == null) {
//            log.error("【创建搜索记录】从redis中获取用户id和登录名失败, id={}, userName={}", RedisUtil.getUserId(), RedisUtil.getLoginName());
//            throw new Exception("无法从redis中获取用户名和id");
//        }

        //调用id服务生成搜索记录主键
//        String fdId = dcpSysIdApiGenorator.generate(IDConstant.APP, IDConstant.RECORD, IDConstant.LENGTH, IDConstant.PREFIX, 1);
        String fdId = UUID.randomUUID().toString();
        searchRecordEntity.setFdId(fdId);

        //将数据写入数据库
        log.info(searchRecordEntity.toString());
        dcpSearchRecordRepository.addRecord(searchRecordEntity);
        return searchRecordEntity;
    }

    @Override
    public List<String> findHotWord(Integer time) {
        //调用Commom里面的时间类工具来进行一个时间区间设置
        List<String> stringList = new ArrayList<String>();
        Map<String, String> map = new HashMap<String, String>(5);
        Date endDate = new Date();
        map.put("endTime", CommonDateUtils.format(endDate));
        Date startDate = CommonDateUtils.add(endDate, CommonDateUtils.TimeUnit.DAYS, -time);
        map.put("startTime", CommonDateUtils.format(startDate));

        //抽取返回的记录中的记录内容
        List<SearchRecordDto> searchRecordDtoList = dcpSearchRecordRepository.findHotRecord(map);
        for (SearchRecordDto searchRecordDto : searchRecordDtoList) {
            stringList.add(searchRecordDto.getFdWord());
        }
        return stringList;
    }

    @Override
    public void deleteSearchRecord(String id) throws Exception {
        dcpSearchRecordRepository.deleteRecordByID(id);
    }
}
