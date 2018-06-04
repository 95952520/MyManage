package com.xuchen.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.xuchen.base.BaseQuery;
import com.xuchen.base.Result;
import com.xuchen.controller.base.BaseController;
import com.xuchen.core.annotation.RequestLog;
import com.xuchen.entity.PurchaseBase;
import com.xuchen.entity.PurchaseDetail;
import com.xuchen.entity.User;
import com.xuchen.entity.base.MyEntityWrapper;
import com.xuchen.enums.PayTypeEnum;
import com.xuchen.enums.UserTypeEnums;
import com.xuchen.service.PurchaseBaseService;
import com.xuchen.service.PurchaseDetailService;
import com.xuchen.service.UserService;
import com.xuchen.util.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("purchaseBase")
public class PurchaseBaseController extends BaseController {


    @Autowired
    PurchaseBaseService purchaseBaseService;
    @Autowired
    PurchaseDetailService purchaseDetailService;
    @Autowired
    UserService userService;

    @GetMapping(value = "")
    String index(HttpServletRequest request) {
        setAttributeEnums(request);
        return "purchase/base/purchase-base-list";
    }

    @GetMapping("list")
    @ResponseBody
    Result list(BaseQuery baseQuery, PurchaseBase myEntity, String params) {
        if (MyUtils.isNotEmpty(params)) {
            myEntity = JSONObject.parseObject(params).toJavaObject(PurchaseBase.class);
        }
        MyEntityWrapper wrapper = new MyEntityWrapper(baseQuery, myEntity);
        wrapper.eq("supplier_id").between("purchase_time").eq("pay_type");
        List<PurchaseBase> list = purchaseBaseService.selectList(wrapper);
        return Result.success(PageHelper.freeTotal(), list);
    }

    @PostMapping("editText")
    @ResponseBody
    @RequestLog
    Result editText(PurchaseBase myEntity) {
        purchaseBaseService.updateById(myEntity);
        return Result.success();
    }

    @GetMapping(value = "toAdd")
    String toAdd(HttpServletRequest request) {
        setAttributeEnums(request);
        return "purchase/base/purchase-base-add";
    }

    @PostMapping("doAdd")
    @ResponseBody
    @RequestLog
    Result doAdd(PurchaseBase myEntity) {
        purchaseBaseService.insert(myEntity);
        return Result.success();
    }

    @GetMapping(value = "toEdit")
    String toEdit(PurchaseBase myEntity, HttpServletRequest request) {
        setAttributeEnums(request);
        request.setAttribute("myEntity", purchaseBaseService.selectById(myEntity));
        return "purchase/base/purchase-base-edit";
    }

    @PostMapping("doEdit")
    @ResponseBody
    @RequestLog
    Result doEdit(PurchaseBase myEntity) {
        purchaseBaseService.updateById(myEntity);
        return Result.success();
    }

    @PostMapping("delete")
    @ResponseBody
    @RequestLog
    Result delete(PurchaseBase myEntity) {
        purchaseBaseService.deleteById(myEntity);
        purchaseBaseService.updateGoodsCountForDel(myEntity.getPurchaseId());
        purchaseDetailService.delete(new EntityWrapper<PurchaseDetail>().eq("purchase_base_id",myEntity.getPurchaseId()));
        return Result.success();
    }

    private void setAttributeEnums(HttpServletRequest request) {
        request.setAttribute("payType", PayTypeEnum.getMap());
        request.setAttribute("supplierList",userService.selectList(new EntityWrapper<User>().eq("user_type",UserTypeEnums.supplier.getId())));
    }
}
