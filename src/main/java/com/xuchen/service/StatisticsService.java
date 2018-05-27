package com.xuchen.service;

import com.xuchen.model.base.statistics.GoodsSaleModel;

import java.util.List;

public interface StatisticsService {
    List<GoodsSaleModel> getMonthGoodsSale(Integer monthTime, Integer goodsType);
}
