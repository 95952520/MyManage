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
import com.xuchen.entity.User;
import com.xuchen.entity.base.MyEntityWrapper;
import com.xuchen.enums.GoodsTypeEnum;
import com.xuchen.enums.SaleTypeEnum;
import com.xuchen.enums.UnitTypeEnum;
import com.xuchen.enums.WeightTypeEnum;
import com.xuchen.service.GoodsService;
import com.xuchen.util.MyUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("goods")
@RequiresPermissions("goods")
public class GoodsController extends BaseController {


    @Autowired
    GoodsService goodsService;
    @Value("${goodsImgDir}")
    String goodsImgDir;

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
        wrapper.eq("goods_type").eq("sale_type").eq("unit_type").eq("weight_type").like("goods_name");
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
    Result doAdd(Goods myEntity, String imgFile) throws IOException {
        logger.info(myEntity.toString());
        goodsService.insert(myEntity);
        if (MyUtils.isNotEmpty(imgFile)) {
            File dir = new File(imgPath + goodsImgDir);
            if (!dir.exists()){
                dir.mkdirs();
            }
            File file = new File(imgPath + goodsImgDir + myEntity.getGoodsId() + ".jpg");
            MyUtils.createFileFromStr(imgFile, file);
            myEntity.setGoodsImg(imgDomain + goodsImgDir + myEntity.getGoodsId() + ".jpg");
            goodsService.updateById(myEntity);
        }
        return Result.success();
    }

    @GetMapping(value = "toEdit")
    String toEdit(Goods myEntity, HttpServletRequest request) {
        setAttributeEnums(request);
        request.setAttribute("myEntity", goodsService.selectById(myEntity));
        return "goods/goods-edit";
    }

    @RequestMapping("doEdit")
    @ResponseBody
    Result doEdit(Goods myEntity,String imgFile) throws IOException {
        logger.info("url:[goods/doEdit];"+myEntity);
        if (MyUtils.isNotEmpty(imgFile)) {
            logger.info("商品图片更新");
            File dir = new File(imgPath + goodsImgDir);
            if (!dir.exists()){
                dir.mkdirs();
            }
            File file = new File(imgPath + goodsImgDir + myEntity.getGoodsId() + ".jpg");
            MyUtils.createFileFromStr(imgFile, file);
            myEntity.setGoodsImg(imgDomain + goodsImgDir + myEntity.getGoodsId() + ".jpg");
        }
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


    @GetMapping("deleteImg")
    @ResponseBody
    @RequestLog
    Result deleteImg(Integer id) {
        Goods myEntity = new Goods();
        myEntity.setGoodsId(id);
        myEntity = goodsService.selectById(myEntity);
        myEntity.setGoodsImg(null);
        goodsService.updateAllColumnById(myEntity);
        File file = new File(imgPath + goodsImgDir + myEntity.getGoodsId() + ".jpg");
        if (file.exists()) {
            file.delete();
        }
        return Result.success();
    }

//    @GetMapping("excel")
//    @RequestLog
//    void exportExcel(HttpServletResponse response) {
//        List<Goods> list = goodsService.selectList(new EntityWrapper<>());
//        ExportExcel.excelHttpExport(response,"商品",null,"标题行",list);
//    }

    private void setAttributeEnums(HttpServletRequest request) {
        request.setAttribute("goodsType", GoodsTypeEnum.getMap());
        request.setAttribute("saleType", SaleTypeEnum.getMap());
        request.setAttribute("unitType", UnitTypeEnum.getMap());
        request.setAttribute("weightType", WeightTypeEnum.getMap());
    }
}
