package com.xuchen.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xuchen.base.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.xuchen.base.BaseQuery;
import com.xuchen.controller.base.BaseController;
import com.xuchen.entity.OrderBase;
import com.xuchen.entity.User;
import com.xuchen.entity.base.MyEntityWrapper;
import com.xuchen.enums.DeliveryTypeEnum;
import com.xuchen.enums.OrderTypeEnum;
import com.xuchen.enums.PayTypeEnum;
import com.xuchen.enums.UserTypeEnums;
import com.xuchen.service.OrderService;
import com.xuchen.service.UserService;
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
    UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    String index(HttpServletRequest request) {
        setAttributeEnums(request);
        return "order/base/order-list";
    }

    @RequestMapping("list")
    @ResponseBody
    Result list(BaseQuery baseQuery, OrderBase myEntity, String params, HttpServletRequest request) {
        if (params != null) {
            myEntity = JSONObject.parseObject(params).toJavaObject(OrderBase.class);
        }
        MyEntityWrapper wrapper = new MyEntityWrapper(baseQuery, myEntity);
        wrapper.eq("customer_id").eq("order_type").eq("delivery_type").eq("delivery_user_id")
                .eq("pay_type").between("create_time");
        List<OrderBase> list = orderService.selectList(wrapper);
        return Result.success(PageHelper.freeTotal(), list);
    }

    @RequestMapping("editText")
    @ResponseBody
    Result editText(OrderBase myEntity) {
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
    Result doEdit(OrderBase myEntity) {
        orderService.updateById(myEntity);
        return Result.success();
    }

    @RequestMapping("delete")
    @ResponseBody
    Result delete(OrderBase myEntity) {
        orderService.deleteById(myEntity);
        return Result.success();
    }

    @RequestMapping("deleteList")
    @ResponseBody
    Result deleteList(String jsonObj) {
//        List<OrderBase> list = JSONArray.parseArray(jsonObj,OrderBase.class);
//        List<Integer> ids = new ArrayList<>();
//        for(OrderBase obj : list) {
//            ids.add(obj.getId());
//        }
//        orderService.deleteBatchIds(ids);
        return Result.success();
    }

    private void setAttributeEnums(HttpServletRequest request) {
        List<User> deliverList = userService.selectList(new EntityWrapper<User>().where("user_type ="+ UserTypeEnums.home.getId()).or("user_type="+UserTypeEnums.deliver.getId()));
        List<User> customerList = userService.selectList(new EntityWrapper<User>().where("user_type in ("+ UserTypeEnums.shop.getId()+
                ","+UserTypeEnums.worker.getId()+","+UserTypeEnums.factory.getId()+","+UserTypeEnums.littleBuyer.getId()+","+UserTypeEnums.elseType.getId()+")"));
        request.setAttribute("customerList", customerList);
        request.setAttribute("deliverList", deliverList);
        request.setAttribute("DeliveryTypeEnum", DeliveryTypeEnum.getMap());
        request.setAttribute("OrderTypeEnum", OrderTypeEnum.getMap());
        request.setAttribute("PayTypeEnum", PayTypeEnum.getMap());
    }
}
