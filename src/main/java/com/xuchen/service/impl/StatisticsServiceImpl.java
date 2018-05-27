package com.xuchen.service.impl;

import com.xuchen.mapper.StatisticsMapper;
import com.xuchen.model.base.statistics.GoodsSaleModel;
import com.xuchen.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    StatisticsMapper statisticsMapper;

    @Override
    public List<GoodsSaleModel> getMonthGoodsSale(Integer monthTime, Integer goodsType){
        return statisticsMapper.getMonthGoodsSale(monthTime,goodsType);
    }


}
