package com.xuchen.controller;

import com.xuchen.base.Result;
import com.xuchen.controller.base.BaseController;
import com.xuchen.enums.GoodsTypeEnum;
import com.xuchen.model.base.statistics.GoodsSaleModel;
import com.xuchen.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("statistics")
public class StatisticsController extends BaseController {

    @Autowired
    StatisticsService statisticsService;


    @RequestMapping(value = "/main", method = RequestMethod.GET)
    String index(HttpServletRequest request) {
        request.setAttribute("goodsType",GoodsTypeEnum.getMap());
        return "main";
    }

    /**
     * 商品每月销售统计
     * @param monthTime yyyyMM
     * @param goodsType 增加goodsType参数，为了避免商品种类过多，影响美观
     * @return
     */
    @RequestMapping("getMonthGoodsSale")
    @ResponseBody
    Result getMonthGoodsSale(Integer monthTime,Integer goodsType) {
        List<GoodsSaleModel> list = statisticsService.getMonthGoodsSale(monthTime,goodsType);
        return Result.success(list);
    }

}
