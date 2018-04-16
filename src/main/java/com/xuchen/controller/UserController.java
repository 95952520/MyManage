package com.xuchen.controller;

import com.xuchen.base.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.xuchen.base.BaseCheckBox;
import com.xuchen.base.BaseQuery;
import com.xuchen.controller.base.BaseController;
import com.xuchen.entity.User;
import com.xuchen.entity.base.MyEntityWrapper;
import com.xuchen.enums.UserTypeEnums;
import com.xuchen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {


    @Autowired
    UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    String index(HttpServletRequest request) {
        setAttributeEnums(request);
        return "user/user-list";
    }

    @RequestMapping("list")
    @ResponseBody
    Result list(BaseQuery baseQuery, User myEntity, String params, HttpServletRequest request) {
        if (params != null) {
            myEntity = JSONObject.parseObject(params).toJavaObject(User.class);
        }
        MyEntityWrapper wrapper = new MyEntityWrapper(baseQuery, myEntity);
        wrapper.eq("user_type").like("user_name").like("shop_name");
        List<User> list = userService.selectList(wrapper);
        return Result.success(PageHelper.freeTotal(), list);
    }

    @RequestMapping("editText")
    @ResponseBody
    Result editText(User myEntity) {
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
    Result doAdd(User myEntity) {
        myEntity.setCreateUser(getSessionUserName());
        userService.insert(myEntity);
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
    Result doEdit(User myEntity) {
        myEntity.setUpdateUser(getSessionUserName());
        userService.updateById(myEntity);
        return Result.success();
    }

//    @RequestMapping("delete")
//    @ResponseBody
//    Result delete(User myEntity) {
//        userService.deleteById(myEntity);
//        return Result.success();
//    }
//
//    @RequestMapping("deleteList")
//    @ResponseBody
//    Result deleteList(String jsonObj) {
//        List<User> list = JSONArray.parseArray(jsonObj,User.class);
//        List<Integer> ids = new ArrayList<>();
//        for(User obj : list) {
//            ids.add(obj.getId());
//        }
//        userService.deleteBatchIds(ids);
//        return Result.success();
//    }

    private void setAttributeEnums(HttpServletRequest request) {
        request.setAttribute("userType", UserTypeEnums.getMap());
    }
}
