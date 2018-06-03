package com.xuchen.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xuchen.base.Result;
import com.xuchen.controller.base.BaseController;
import com.xuchen.core.annotation.RequestLog;
import com.xuchen.entity.Goods;
import com.xuchen.entity.PurchaseDetail;
import com.xuchen.enums.SaleTypeEnum;
import com.xuchen.enums.StatusEnum;
import com.xuchen.service.GoodsService;
import com.xuchen.service.PurchaseBaseService;
import com.xuchen.service.PurchaseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("purchaseDetail")
public class PurchaseDetailController extends BaseController {


    @Autowired
    PurchaseDetailService purchaseDetailService;
    @Autowired
    PurchaseBaseService purchaseBaseService;
    @Autowired
    GoodsService goodsService;

    @GetMapping(value = "")
    String index(Integer purchaseId,HttpServletRequest request) {
        request.setAttribute("purchaseBaseId", purchaseId);
        List<Integer> goodsIds = getGoodsIdByPurchaseId(purchaseId);
        if (goodsIds.size() > 0) {
            request.setAttribute("goodsList", goodsService.selectBatchIds(goodsIds));
        }
        return "purchase/detail/purchase-detail-list";
    }

    @GetMapping("list")
    @ResponseBody
    Result list(Integer purchaseBaseId) {
        List<PurchaseDetail> list = purchaseDetailService.selectList(new EntityWrapper<PurchaseDetail>().eq("purchase_base_id", purchaseBaseId));
        return Result.success(list);
    }

    @GetMapping(value = "toAdd")
    String toAdd(Integer purchaseBaseId, HttpServletRequest request) {
        request.setAttribute("purchaseBaseId", purchaseBaseId);
        List<Integer> ids = getGoodsIdByPurchaseId(purchaseBaseId);
        request.setAttribute("goodsList", goodsService.selectList(new EntityWrapper<Goods>()
                .eq("status", StatusEnum.useable.getId())
                .ne("sale_type", SaleTypeEnum.product.getId())
                .notIn("goods_id", ids)));//查询 未添加的、未失效的、原料属性或转卖商品的的
        return "purchase/detail/purchase-detail-add";
    }

    @PostMapping("doAdd")
    @ResponseBody
    @RequestLog
    Result doAdd(PurchaseDetail myEntity) {
        purchaseDetailService.insert(myEntity);
        goodsService.updateGoodsStock(myEntity.getGoodsId(),myEntity.getGoodsCount());
        purchaseBaseService.updateTotalMoney(myEntity.getPurchaseBaseId());
        return Result.success();
    }

    @GetMapping(value = "toEdit")
    String toEdit(PurchaseDetail myEntity, HttpServletRequest request) {
        myEntity = purchaseDetailService.selectById(myEntity);
        List<Integer> ids = getGoodsIdByPurchaseId(myEntity.getPurchaseBaseId());
        //编辑时查询 未添加、当前选中的、未失效、原料属性或转卖商品属性的
        request.setAttribute("goodsList", goodsService.selectList(new EntityWrapper<Goods>()
                .eq("status", StatusEnum.useable.getId())
                .ne("sale_type", SaleTypeEnum.product.getId())
                .notIn("goods_id", ids)
                .orNew()
                .eq("goods_id",myEntity.getGoodsId())));
        request.setAttribute("myEntity", purchaseDetailService.selectById(myEntity));
        return "purchase/detail/purchase-detail-edit";
    }

    @PostMapping("doEdit")
    @ResponseBody
    @RequestLog
    Result doEdit(PurchaseDetail myEntity) {
        PurchaseDetail originalPurchaseDetail = purchaseDetailService.selectById(myEntity);
        purchaseDetailService.updateById(myEntity);
        purchaseBaseService.updateTotalMoney(originalPurchaseDetail.getPurchaseBaseId());
        if (originalPurchaseDetail.getGoodsId().equals(myEntity.getGoodsId())){
            if (!myEntity.getGoodsCount().equals(originalPurchaseDetail.getGoodsCount())){
                goodsService.updateGoodsStock(myEntity.getGoodsId(),myEntity.getGoodsCount()-originalPurchaseDetail.getGoodsCount());
            }
        }else {
            goodsService.updateGoodsStock(myEntity.getGoodsId(),myEntity.getGoodsCount());
            goodsService.updateGoodsStock(originalPurchaseDetail.getGoodsId(),-myEntity.getGoodsCount());
        }
        return Result.success();
    }

    @RequestMapping("delete")
    @ResponseBody
    @RequestLog
    Result delete(PurchaseDetail myEntity) {
        myEntity = purchaseDetailService.selectById(myEntity);
        purchaseDetailService.deleteById(myEntity);
        goodsService.updateGoodsStock(myEntity.getGoodsId(),-myEntity.getGoodsCount());
        purchaseBaseService.updateTotalMoney(myEntity.getPurchaseBaseId());
        return Result.success();
    }


    private List<Integer> getGoodsIdByPurchaseId(Integer purchaseId) {
        List<Integer> list = new ArrayList<>();
        List<PurchaseDetail> purchaseDetailList = purchaseDetailService.selectList(new EntityWrapper<PurchaseDetail>().eq("purchase_base_id", purchaseId));
        for (PurchaseDetail detail : purchaseDetailList) {
            list.add(detail.getGoodsId());
        }
        return list;
    }
}
