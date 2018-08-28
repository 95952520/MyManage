package com.xuchen.controller;

import com.xuchen.base.Result;
import com.xuchen.controller.base.BaseController;
import com.xuchen.core.redis.RedisStore;
import com.xuchen.util.MessageService;
import com.xuchen.util.MyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("server")
public class ServerController extends BaseController {


    @GetMapping("phone")
    @ResponseBody
    Result phone(String phone, String pwd) {
        if (MyUtils.isEmpty(phone, pwd)) {
            return Result.fail();
        }
        if (!pwd.equals(MyUtils.getMonthDay())) {
            return Result.fail();
        }
        if (!MyUtils.isPhoneNumber(phone)){
            return Result.fail();
        }
        if (RedisStore.hasKey(MyUtils.getIpAddress(getRequest()))) {
            return Result.fail("稍后再试");
        }
        RedisStore.setValue(MyUtils.getIpAddress(getRequest()), phone, 30, TimeUnit.MINUTES);
        MessageService.doSend(phone);
        return Result.success();
    }
}
