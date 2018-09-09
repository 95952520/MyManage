package com.xuchen.controller;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xuchen.base.Result;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.xuchen.base.BaseQuery;
import com.xuchen.controller.base.BaseController;
import com.xuchen.core.annotation.RequestLog;
import com.xuchen.entity.OrderBase;
import com.xuchen.entity.OrderGoods;
import com.xuchen.entity.User;
import com.xuchen.entity.base.MyEntityWrapper;
import com.xuchen.enums.DeliveryTypeEnum;
import com.xuchen.enums.OrderTypeEnum;
import com.xuchen.enums.PayTypeEnum;
import com.xuchen.enums.StatusEnum;
import com.xuchen.enums.UserTypeEnums;
import com.xuchen.service.OrderGoodsService;
import com.xuchen.service.OrderService;
import com.xuchen.service.UserService;
import com.xuchen.util.MyUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("order")
@RequiresPermissions("order")
public class OrderController extends BaseController {


    @Autowired
    OrderService orderService;
    @Autowired
    OrderGoodsService orderGoodsService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    String index(HttpServletRequest request) {
        setAttributeEnums(request);
        return "order/base/order-list";
    }

    @RequestMapping("list")
    @ResponseBody
    Result list(BaseQuery baseQuery, OrderBase myEntity, String params, HttpServletRequest request) {
        if (MyUtils.isNotEmpty(params)) {
            myEntity = JSONObject.parseObject(params).toJavaObject(OrderBase.class);
        }
        MyEntityWrapper wrapper = new MyEntityWrapper(baseQuery, myEntity);
        wrapper.eq("customer_id").eq("order_type").eq("delivery_type").eq("delivery_user_id")
                .eq("pay_type").between("order_time");
        List<OrderBase> list = orderService.selectList(wrapper);
        return Result.success(PageHelper.freeTotal(), list);
    }

    @RequestMapping("editText")
    @ResponseBody
    @RequestLog
    Result editText(OrderBase myEntity) {
        myEntity.setUpdateTime(new Date());
        myEntity.setUpdateUser(getSessionUserName());
        orderService.updateById(myEntity);
        return Result.success();
    }

    @RequestMapping(value = "toAdd", method = RequestMethod.GET)
    String toAdd(HttpServletRequest request) {
        setAttributeEnums(request);
        return "order/base/order-add";
    }

    @RequestMapping("doAdd")
    @ResponseBody
    @RequestLog
    Result doAdd(OrderBase myEntity) {
        myEntity.setCreateUser(getSessionUserName());
        orderService.insert(myEntity);
        return Result.success();
    }

    @RequestMapping(value = "toEdit", method = RequestMethod.GET)
    String toEdit(OrderBase myEntity, HttpServletRequest request) {
        setAttributeEnums(request);
        request.setAttribute("myEntity", orderService.selectById(myEntity));
        return "order/base/order-edit";
    }

    @RequestMapping("doEdit")
    @ResponseBody
    @RequestLog
    Result doEdit(OrderBase myEntity) {
        myEntity.setUpdateTime(new Date());
        myEntity.setUpdateUser(getSessionUserName());
        orderService.updateById(myEntity);
        return Result.success();
    }

    @RequestMapping("delete")
    @ResponseBody
    @RequestLog
    Result delete(OrderBase myEntity) {
        orderService.deleteById(myEntity);
        orderService.updateGoodsCountForDel(myEntity.getOrderId());
        orderGoodsService.delete(new EntityWrapper<OrderGoods>().eq("order_id", myEntity.getOrderId()));
        return Result.success();
    }

    private void setAttributeEnums(HttpServletRequest request) {
        List<User> deliverList = userService.selectList(new EntityWrapper<User>()
                .eq("user_type", UserTypeEnums.HOME.getId()).or().eq("user_type", UserTypeEnums.DELIVER.getId())
                .andNew().eq("status", StatusEnum.USEABLE.getId()));
        List<Integer> ids = new ArrayList<>(5);
        ids.add(UserTypeEnums.SHOP.getId());
        ids.add(UserTypeEnums.WORKER.getId());
        ids.add(UserTypeEnums.FACTORY.getId());
        ids.add(UserTypeEnums.ELSE_TYPE.getId());
        ids.add(UserTypeEnums.LITTLE_BUYER.getId());
        List<User> customerList = userService.selectList(new EntityWrapper<User>().in("user_type", ids));
        request.setAttribute("customerList", customerList);
        request.setAttribute("deliverList", deliverList);
        request.setAttribute("DeliveryTypeEnum", DeliveryTypeEnum.getMap());
        request.setAttribute("OrderTypeEnum", OrderTypeEnum.getMap());
        request.setAttribute("PayTypeEnum", PayTypeEnum.getMap());
    }
}
