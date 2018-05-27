package com.xuchen.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.xuchen.base.BaseCheckBox;
import com.xuchen.base.BaseQuery;
import com.xuchen.base.Result;
import com.xuchen.controller.base.BaseController;
import com.xuchen.core.annotation.CheckNullUpdate;
import com.xuchen.core.annotation.RequestLog;
import com.xuchen.entity.Goods;
import com.xuchen.entity.base.MyEntityWrapper;
import com.xuchen.enums.GoodsTypeEnum;
import com.xuchen.enums.ProductTypeEnum;
import com.xuchen.enums.StockTypeEnum;
import com.xuchen.enums.UnitTypeEnum;
import com.xuchen.enums.WeightTypeEnum;
import com.xuchen.service.GoodsService;
import com.xuchen.util.MyUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("goods")
@RequiresPermissions("goods")
public class GoodsController extends BaseController {


    @Autowired
    GoodsService goodsService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    String index(HttpServletRequest request) {
        setAttributeEnums(request);
        return "goods/goods-list";
    }

    @RequestMapping("list")
    @ResponseBody
    Result list(BaseQuery baseQuery, Goods myEntity, String params, HttpServletRequest request) {
        if (MyUtils.isNotEmpty(params)) {
            myEntity = JSONObject.parseObject(params).toJavaObject(Goods.class);
        }
        MyEntityWrapper wrapper = new MyEntityWrapper(baseQuery, myEntity);
        wrapper.eq("goods_type").eq("is_product").eq("unit_type").eq("weight_type").like("goods_name").eq("is_stock");
        List<Goods> list = goodsService.selectList(wrapper);
        return Result.success(PageHelper.freeTotal(), list);
    }

    @RequestMapping("selectById")
    @ResponseBody
    Result selectById(Goods myEntity) {
        myEntity = goodsService.selectById(myEntity);
        return Result.success(myEntity);
    }

    @CheckNullUpdate("goodsName")
    @RequestMapping("editText")
    @ResponseBody
    @RequestLog
    Result editText(Goods myEntity) {
        goodsService.updateById(myEntity);
        return Result.success();
    }

    @RequestMapping(value = "toAdd", method = RequestMethod.GET)
    String toAdd(HttpServletRequest request) {
        setAttributeEnums(request);
        return "goods/goods-add";
    }

    @RequestMapping("doAdd")
    @ResponseBody
    @RequestLog
    Result doAdd(Goods myEntity) {
        goodsService.insert(myEntity);
        return Result.success();
    }

    @RequestMapping(value = "toEdit", method = RequestMethod.GET)
    String toEdit(Goods myEntity, HttpServletRequest request) {
        setAttributeEnums(request);
        request.setAttribute("myEntity", goodsService.selectById(myEntity));
        return "goods/goods-edit";
    }

    @RequestMapping("doEdit")
    @ResponseBody
    @RequestLog
    Result doEdit(Goods myEntity) {
        goodsService.updateById(myEntity);
        return Result.success();
    }


    @RequestMapping("checkboxUpdate")
    @ResponseBody
    @RequestLog
    Result checkboxUpdate(BaseCheckBox checkBox) {
        Goods myEntity = new Goods();
        myEntity.setGoodsId(checkBox.getId());
        myEntity.setStatus(checkBox.isChecked() ? 1 : 0);
        goodsService.updateById(myEntity);
        return Result.success();
    }

    private void setAttributeEnums(HttpServletRequest request) {
        request.setAttribute("goodsType", GoodsTypeEnum.getMap());
        request.setAttribute("productType", ProductTypeEnum.getMap());
        request.setAttribute("unitType", UnitTypeEnum.getMap());
        request.setAttribute("weightType", WeightTypeEnum.getMap());
        request.setAttribute("stockType", StockTypeEnum.getMap());
    }
}
