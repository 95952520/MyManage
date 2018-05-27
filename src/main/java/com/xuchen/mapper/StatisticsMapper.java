package com.xuchen.mapper;

import com.xuchen.model.base.statistics.GoodsSaleModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatisticsMapper {

    List<GoodsSaleModel> getMonthGoodsSale(@Param("monthTime") Integer monthTime,@Param("goodsType") Integer goodsType);
}
