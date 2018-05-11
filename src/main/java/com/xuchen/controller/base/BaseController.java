package com.xuchen.controller.base;

import com.alibaba.druid.support.json.JSONUtils;
import com.xuchen.base.Result;
import com.xuchen.entity.SysUser;
import com.xuchen.util.MyUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController {

    @Value("${imgPath}")
    public String imgPath;
    @Value("${imgDomain}")
    public String imgDomain;

    protected static final Logger logger = Logger.getLogger(BaseController.class);

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
        logger.error("接口报错:[" + request.getRequestURI() + "],调用者[" + getSessionUserId() + "],method[" + request.getMethod() + "]");
        logger.error("参数:" + getJsonParams(request.getParameterMap()));
        e.printStackTrace();
        return Result.fail(e.getLocalizedMessage());
    }

    /**
     * 根据成员变量名从集合中提取成map
     */
    protected static HashMap<Object,Object> getMapByList(List<? extends Serializable> list, String keyName, String valueName) {
        HashMap<Object,Object> map = new HashMap<>();
        if (list != null && list.size()>0){
            try {
                for (Serializable entity : list) {
                    map.put(getValueByInvoke(entity,keyName),getValueByInvoke(entity,valueName));
                }
            }catch (Exception e){
                logger.error("从集合中提取成map失败");
            }
        }
        return map;
    }

    private static String getJsonParams(Map<String, String[]> map) {
        Map<String, String> paramsMap = new HashMap<>();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            paramsMap.put(entry.getKey(), entry.getValue()[0]);
        }
        return JSONUtils.toJSONString(paramsMap);
    }

    private static Object getValueByInvoke(Object obj ,String str) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = obj.getClass().getMethod("get" + Character.toUpperCase(str.charAt(0)) + str.substring(1));
        return method.invoke(obj, new Object[]{});
    }


    protected static String loggerArray(Integer[] ids) {
        if (ids == null || ids.length == 0) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (Integer id : ids) {
                sb.append(id + ",");
            }
            sb.append("]");
            return sb.toString();
        }
    }

    protected static void checkNullUpdate(Object... obj) throws Exception {
        if (MyUtils.isEmpty(obj)){
            throw new Exception("该字段不能为空");
        }
        for (Object o : obj) {
            if (MyUtils.isEmpty(o)){
                throw new Exception("该字段不能为空");
            }
        }
    }
}
