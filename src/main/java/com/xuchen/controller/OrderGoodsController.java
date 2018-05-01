package com.xuchen.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xuchen.base.Result;
import com.xuchen.controller.base.BaseController;
import com.xuchen.core.annotation.RequestLog;
import com.xuchen.entity.OrderGoods;
import com.xuchen.service.GoodsService;
import com.xuchen.service.OrderGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("orderGoods")
public class OrderGoodsController extends BaseController {


    @Autowired
    OrderGoodsService orderGoodsService;
    @Autowired
    GoodsService goodsService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    String index(Integer orderId ,HttpServletRequest request) {
        request.setAttribute("orderId",orderId);
        setAttributeEnums(request);
        return "order/goods/order-goods-list";
    }

    @RequestMapping("list")
    @ResponseBody
    Result list(Integer orderId, HttpServletRequest request) {
        List<OrderGoods> list = orderGoodsService.selectList(new EntityWrapper<OrderGoods>().eq("order_id",orderId));
        return Result.success(list);
    }

    @RequestMapping("editText")
    @ResponseBody
    @RequestLog
    Result editText(OrderGoods myEntity) {
        orderGoodsService.updateById(myEntity);
        return Result.success();
    }

    @RequestMapping(value = "toAdd", method = RequestMethod.GET)
    String toAdd(Integer orderId,HttpServletRequest request) {
        request.setAttribute("orderId",orderId);
        setAttributeEnums(request);
        return "order/goods/order-goods-add";
    }

    @RequestMapping("doAdd")
    @ResponseBody
    @RequestLog
    Result doAdd(OrderGoods myEntity) {
        List<OrderGoods> list = orderGoodsService.selectList(new EntityWrapper<OrderGoods>().eq("order_id", myEntity.getOrderId()).eq("goods_id",myEntity.getGoodsId()));
        if (list.size()==1){
            return Result.fail("该订单已存在该商品");
        }
        myEntity.setCreateUser(getSessionUserName());
        orderGoodsService.insert(myEntity);
        goodsService.updateTotalMoney(myEntity.getOrderId());
        return Result.success();
    }

    @RequestMapping(value = "toEdit", method = RequestMethod.GET)
    String toEdit(OrderGoods myEntity, HttpServletRequest request) {
        setAttributeEnums(request);
        request.setAttribute("myEntity", orderGoodsService.selectById(myEntity));
        return "order/goods/order-goods-edit";
    }

    @RequestMapping("doEdit")
    @ResponseBody
    @RequestLog
    Result doEdit(OrderGoods myEntity) {
        List<OrderGoods> list = orderGoodsService.selectList(new EntityWrapper<OrderGoods>()
                .eq("order_id", myEntity.getOrderId()).eq("goods_id",myEntity.getGoodsId()).ne("order_goods_id",myEntity.getOrderGoodsId()));
        if (list.size()==1){
            return Result.fail("该订单已存在该商品");
        }
        orderGoodsService.updateById(myEntity);
        goodsService.updateTotalMoney(myEntity.getOrderId());
        return Result.success();
    }

    @RequestMapping("delete")
    @ResponseBody
    @RequestLog
    Result delete(OrderGoods myEntity) {
        orderGoodsService.deleteById(myEntity);
        goodsService.updateTotalMoney(myEntity.getOrderId());
        return Result.success();
    }

    private void setAttributeEnums(HttpServletRequest request) {
        request.setAttribute("goodsList", goodsService.selectList(null));
    }
}
