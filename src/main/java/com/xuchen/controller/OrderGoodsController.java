package com.xuchen.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xuchen.base.Result;
import com.xuchen.controller.base.BaseController;
import com.xuchen.core.annotation.RequestLog;
import com.xuchen.entity.Goods;
import com.xuchen.entity.OrderGoods;
import com.xuchen.enums.SaleTypeEnum;
import com.xuchen.enums.StatusEnum;
import com.xuchen.service.GoodsService;
import com.xuchen.service.OrderGoodsService;
import com.xuchen.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("orderGoods")
public class OrderGoodsController extends BaseController {


    @Autowired
    OrderGoodsService orderGoodsService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    String index(Integer orderId, HttpServletRequest request) {
        request.setAttribute("orderId", orderId);
        List<Integer> goodsIds = getGoodsIdByOrderId(orderId);
        if (goodsIds.size() > 0) {
            request.setAttribute("goodsList", goodsService.selectBatchIds(goodsIds));
        }
        return "order/goods/order-goods-list";
    }

    @RequestMapping("list")
    @ResponseBody
    Result list(Integer orderId) {
        List<OrderGoods> list = orderGoodsService.selectList(new EntityWrapper<OrderGoods>().eq("order_id", orderId));
        return Result.success(list);
    }


    @RequestMapping(value = "toAdd", method = RequestMethod.GET)
    String toAdd(Integer orderId, HttpServletRequest request) {
        request.setAttribute("orderId", orderId);
        List<Integer> ids = getGoodsIdByOrderId(orderId);
        request.setAttribute("goodsList", goodsService.selectList(new EntityWrapper<Goods>()
                .eq("status", StatusEnum.useable.getId())
                .ne("sale_type", SaleTypeEnum.unsaleable.getId())
                .notIn("goods_id", ids)));//查询 未添加的、未失效的、商品属性的
        return "order/goods/order-goods-add";
    }


    @RequestMapping("doAdd")
    @ResponseBody
    @RequestLog
    Result doAdd(OrderGoods myEntity) {
        myEntity.setCreateUser(getSessionUserName());
        orderGoodsService.insert(myEntity);
        orderService.updateTotalMoney(myEntity.getOrderId());
        goodsService.updateGoodsStock(myEntity.getGoodsId(), -myEntity.getGoodsCount());
        return Result.success();
    }

    @RequestMapping(value = "toEdit", method = RequestMethod.GET)
    String toEdit(OrderGoods myEntity, HttpServletRequest request) {
        myEntity = orderGoodsService.selectById(myEntity);
        List<Integer> ids =getGoodsIdByOrderId(myEntity.getOrderId());
        //编辑时查询 未添加、当前选中的、未失效、商品属性
        request.setAttribute("goodsList", goodsService.selectList(new EntityWrapper<Goods>()
                .eq("status", StatusEnum.useable.getId())
                .ne("sale_type", SaleTypeEnum.unsaleable.getId())
                .notIn("goods_id", ids)
                .orNew()
                .eq("goods_id",myEntity.getGoodsId())));
        request.setAttribute("myEntity", orderGoodsService.selectById(myEntity));
        return "order/goods/order-goods-edit";
    }

    @RequestMapping("doEdit")
    @ResponseBody
    @RequestLog
    Result doEdit(OrderGoods myEntity) {
        OrderGoods originalOrderGoods = orderGoodsService.selectById(myEntity);
        orderGoodsService.updateById(myEntity);
        orderService.updateTotalMoney(myEntity.getOrderId());
        if (myEntity.getGoodsId().equals(originalOrderGoods.getGoodsId())){
            if (!myEntity.getGoodsCount().equals(originalOrderGoods.getGoodsCount())){
                goodsService.updateGoodsStock(myEntity.getGoodsId(), originalOrderGoods.getGoodsCount() - myEntity.getGoodsCount());
            }
        }else {
            goodsService.updateGoodsStock(myEntity.getGoodsId(),-myEntity.getGoodsCount());
            goodsService.updateGoodsStock(originalOrderGoods.getGoodsId(),originalOrderGoods.getGoodsCount());
        }
        return Result.success();
    }

    @RequestMapping("delete")
    @ResponseBody
    @RequestLog
    Result delete(OrderGoods myEntity) {
        myEntity = orderGoodsService.selectById(myEntity);
        orderGoodsService.deleteById(myEntity);
        orderService.updateTotalMoney(myEntity.getOrderId());
        goodsService.updateGoodsStock(myEntity.getGoodsId(), myEntity.getGoodsCount());
        return Result.success();
    }

    private List<Integer> getGoodsIdByOrderId(Integer orderId) {
        List<Integer> list = new ArrayList<>();
        List<OrderGoods> orderGoodsList = orderGoodsService.selectList(new EntityWrapper<OrderGoods>().eq("order_id", orderId));
        for (OrderGoods orderGoods : orderGoodsList) {
            list.add(orderGoods.getGoodsId());
        }
        return list;
    }
}
