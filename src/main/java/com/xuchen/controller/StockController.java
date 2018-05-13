package com.xuchen.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xuchen.base.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.xuchen.base.BaseQuery;
import com.xuchen.controller.base.BaseController;
import com.xuchen.core.annotation.CheckNullUpdate;
import com.xuchen.core.annotation.RequestLog;
import com.xuchen.entity.Stock;
import com.xuchen.entity.User;
import com.xuchen.entity.base.MyEntityWrapper;
import com.xuchen.enums.StatusEnum;
import com.xuchen.enums.StockTypeEnum;
import com.xuchen.enums.UnitTypeEnum;
import com.xuchen.enums.UserTypeEnums;
import com.xuchen.enums.WeightTypeEnum;
import com.xuchen.service.StockService;
import com.xuchen.service.UserService;
import com.xuchen.util.MyUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("stock")
@RequiresPermissions("stock")
public class StockController extends BaseController {


    @Autowired
    StockService stockService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    String index(HttpServletRequest request) {
        setAttributeEnums(request);
        return "stock/stock-list";
    }

    @RequestMapping("list")
    @ResponseBody
    Result list(BaseQuery baseQuery, Stock myEntity, String params, HttpServletRequest request) {
        if (MyUtils.isNotEmpty(params)) {
            myEntity = JSONObject.parseObject(params).toJavaObject(Stock.class);
        }
        MyEntityWrapper wrapper = new MyEntityWrapper(baseQuery, myEntity);
        wrapper.like("stock_name").eq("stock_type").eq("unit_type").eq("weight_type").eq("supplier");
        List<Stock> list = stockService.selectList(wrapper);
        return Result.success(PageHelper.freeTotal(), list);
    }

    @RequestMapping("editText")
    @ResponseBody
    @RequestLog
    @CheckNullUpdate(checkFiled = "stockName")
    Result editText(Stock myEntity){
        stockService.updateById(myEntity);
        return Result.success();
    }

    @RequestMapping(value = "toAdd", method = RequestMethod.GET)
    String toAdd(Integer id, HttpServletRequest request) {
        setAttributeEnums(request);
        return "stock/stock-add";
    }

    @RequestMapping("doAdd")
    @ResponseBody
    @RequestLog
    Result doAdd(Stock myEntity) {
        myEntity.setCreateUser(getSessionUserName());
        stockService.insert(myEntity);
        return Result.success();
    }

    @RequestMapping(value = "toEdit", method = RequestMethod.GET)
    String toEdit(Stock myEntity, HttpServletRequest request) {
        setAttributeEnums(request);
        request.setAttribute("myEntity", stockService.selectById(myEntity));
        return "stock/stock-edit";
    }

    @RequestMapping("doEdit")
    @ResponseBody
    @RequestLog
    Result doEdit(Stock myEntity) {
        stockService.updateById(myEntity);
        myEntity.setCreateUser(getSessionUserName());
        return Result.success();
    }

    @RequestMapping("delete")
    @ResponseBody
    Result delete(Stock myEntity) {
        stockService.deleteById(myEntity);
        return Result.success();
    }

    @RequestMapping("deleteList")
    @ResponseBody
    @RequestLog
    Result deleteList(String jsonObj) {
//        List<Stock> list = JSONArray.parseArray(jsonObj,Stock.class);
//        List<Integer> ids = new ArrayList<>();
//        for(Stock obj : list) {
//            ids.add(obj.getId());
//        }
//        stockService.deleteBatchIds(ids);
        return Result.success();
    }

    private void setAttributeEnums(HttpServletRequest request) {
        List<User> list = userService.selectList(new EntityWrapper<User>().eq(("user_type"),
                UserTypeEnums.supplier.getId()).eq("status", StatusEnum.useable.getId()));
        request.setAttribute("supplierList", getMapByList(list,"userId","userName"));
        request.setAttribute("stockType", StockTypeEnum.getMap());
        request.setAttribute("unitType", UnitTypeEnum.getMap());
        request.setAttribute("weightType", WeightTypeEnum.getMap());
    }
}
