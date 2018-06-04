package com.xuchen.controller;

import com.xuchen.base.Result;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.xuchen.base.BaseCheckBox;
import com.xuchen.base.BaseQuery;
import com.xuchen.controller.base.BaseController;
import com.xuchen.core.annotation.CheckNullUpdate;
import com.xuchen.core.annotation.RequestLog;
import com.xuchen.entity.User;
import com.xuchen.entity.base.MyEntityWrapper;
import com.xuchen.enums.UserTypeEnums;
import com.xuchen.service.UserService;
import com.xuchen.util.MyUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("user")
@RequiresPermissions("user")
public class UserController extends BaseController {

    @Autowired
    UserService userService;
    @Value("${userImgDir}")
    String userImgDir;

    @RequestMapping(value = "", method = RequestMethod.GET)
    String index(HttpServletRequest request) {
        setAttributeEnums(request);
        return "user/user-list";
    }

    @CheckNullUpdate({"userName","address","phone","shopName"})
    @RequestMapping("editText")
    @ResponseBody
    @RequestLog
    Result editText(User myEntity) {
        myEntity.setUpdateUser(getSessionUserName());
        myEntity.setUpdateTime(new Date());
        userService.updateById(myEntity);
        return Result.success();
    }

    @RequestMapping("list")
    @ResponseBody
    Result list(BaseQuery baseQuery, User myEntity, String params, HttpServletRequest request) {
        if (MyUtils.isNotEmpty(params)) {
            myEntity = JSONObject.parseObject(params).toJavaObject(User.class);
        }
        MyEntityWrapper wrapper = new MyEntityWrapper(baseQuery, myEntity);
        wrapper.eq("user_type").like("user_name").like("shop_name").eq("status");
        List<User> list = userService.selectList(wrapper);
        return Result.success(PageHelper.freeTotal(), list);
    }

    @RequestMapping("checkboxUpdate")
    @ResponseBody
    @RequestLog
    Result checkboxUpdate(BaseCheckBox checkBox) {
        User myEntity = new User();
        myEntity.setUserId(checkBox.getId());
        myEntity.setStatus(checkBox.isChecked() ? 1 : 0);
        userService.updateById(myEntity);
        return Result.success();
    }

    @RequestMapping(value = "toAdd", method = RequestMethod.GET)
    String toAdd(HttpServletRequest request) {
        setAttributeEnums(request);
        return "user/user-add";
    }

    @RequestMapping("doAdd")
    @ResponseBody
    Result doAdd(User myEntity, String imgFile) throws IOException {
        logger.info(myEntity.toString());
        myEntity.setCreateUser(getSessionUserName());
        userService.insert(myEntity);
        if (MyUtils.isNotEmpty(imgFile)) {
            File file = new File(imgPath + userImgDir + myEntity.getUserId() + ".jpg");
            MyUtils.createFileFromStr(imgFile, file);
            myEntity.setUserImg(imgDomain + userImgDir + myEntity.getUserId() + ".jpg");
            userService.updateById(myEntity);
        }
        return Result.success();
    }

    @RequestMapping(value = "toEdit", method = RequestMethod.GET)
    String toEdit(User myEntity, HttpServletRequest request) {
        setAttributeEnums(request);
        request.setAttribute("myEntity", userService.selectById(myEntity));
        return "user/user-edit";
    }

    @RequestMapping("doEdit")
    @ResponseBody
    Result doEdit(User myEntity, String imgFile) throws IOException {
        logger.info("url:[user/doEdit];"+myEntity);
        myEntity.setUpdateUser(getSessionUserName());
        myEntity.setUpdateTime(new Date());
        if (MyUtils.isNotEmpty(imgFile)) {
            logger.info("用户头像更新");
            File file = new File(imgPath + userImgDir + myEntity.getUserId() + ".jpg");
            MyUtils.createFileFromStr(imgFile, file);
            myEntity.setUserImg(imgDomain + userImgDir + myEntity.getUserId() + ".jpg");
        }
        userService.updateById(myEntity);
        return Result.success();
    }

    @RequestMapping("deleteImg")
    @ResponseBody
    @RequestLog
    Result deleteImg(Integer id) {
        User myEntity = new User();
        myEntity.setUserId(id);
        myEntity = userService.selectById(myEntity);
        myEntity.setUpdateUser(getSessionUserName());
        myEntity.setUpdateTime(new Date());
        myEntity.setUserImg(null);
        userService.updateAllColumnById(myEntity);
        File file = new File(imgPath + userImgDir + myEntity.getUserId() + ".jpg");
        if (file.exists()) {
            file.delete();
        }
        return Result.success();
    }

    private void setAttributeEnums(HttpServletRequest request) {
        request.setAttribute("userType", UserTypeEnums.getMap());
    }
}
