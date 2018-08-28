package com.xuchen.controller.base;

import com.alibaba.druid.support.json.JSONUtils;
import com.xuchen.base.Result;
import com.xuchen.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    @Value("${imgPath}")
    public String imgPath;
    @Value("${imgDomain}")
    public String imgDomain;

    protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    protected static SysUser getSessionUser() {
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    protected static String getSessionUserName() {
        return getSessionUser().getUserName();
    }

    protected static Integer getSessionUserId() {
        return getSessionUser().getId();
    }

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @ExceptionHandler
    @ResponseBody
    public Result exp(HttpServletRequest request, Exception e) {
        logger.error("接口报错:[" + request.getRequestURI() + "],调用者[" + getSessionUserName() + "],method[" + request.getMethod() + "]");
        logger.error("参数:" + getJsonParams(request.getParameterMap()));
        e.printStackTrace();
        return Result.fail(e.getMessage());
    }

    protected HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    private static String getJsonParams(Map<String, String[]> map) {
        Map<String, String> paramsMap = new HashMap<>();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            paramsMap.put(entry.getKey(), entry.getValue()[0]);
        }
        return JSONUtils.toJSONString(paramsMap);
    }


}
