package com.xuchen.controller.sys;

import com.xuchen.controller.base.BaseController;
import com.xuchen.core.redis.RedisKey;
import com.xuchen.core.redis.RedisStore;
import com.xuchen.entity.SysUser;
import com.xuchen.model.ParentMenu;
import com.xuchen.service.SysMenuService;
import com.xuchen.util.MyUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class LoginController extends BaseController {


    @Autowired
    SysMenuService sysMenuService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    String login() {
        return "login";
    }


    @RequestMapping(value = "/main", method = RequestMethod.GET)
    String main() {
        return "main";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    String error() {
        return "error";
    }


    @RequestMapping(value = {"/", "/index", ""}, method = RequestMethod.GET)
    String menuList(HttpServletRequest request) {
        List<ParentMenu> list = sysMenuService.getMenuByUserId(getSessionUser().getId());
        request.setAttribute("menuList", list);
        request.setAttribute("userName", getSessionUser().getUserName());
        return "index";
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    String login(SysUser loginEntity, HttpServletRequest request, RedirectAttributes attributes) {
        logger.info("[" + loginEntity.getUserName() + "]登录,IP["+ MyUtils.getIpAddress(request)+"]");
        if (MyUtils.isEmpty(loginEntity.getUserName()) || MyUtils.isEmpty(loginEntity.getPassword())) {
            return "redirect:login";
        }
        Integer loginCount;
        loginCount = RedisStore.getValue(RedisKey.LOGIN_USER_NAME + loginEntity.getUserName());
        if (loginCount != null && loginCount == 5) {
            attributes.addFlashAttribute("msg", "用户尝试登录次数过多，请30分钟后再试");
            return "redirect:login";
        }
        UsernamePasswordToken token = new UsernamePasswordToken(loginEntity.getUserName(), loginEntity.getPassword());
        try {
            SecurityUtils.getSubject().login(token);
        } catch (LockedAccountException lae) {
            token.clear();
            attributes.addFlashAttribute("msg", "用户已经被锁定不能登录，请与管理员联系！");
            return "redirect:login";
        } catch (AuthenticationException e) {
            token.clear();
            loginCount = RedisStore.getValue(RedisKey.LOGIN_USER_NAME + loginEntity.getUserName());
            RedisStore.setValue(RedisKey.LOGIN_USER_NAME + loginEntity.getUserName(), (loginCount == null ? 0 : loginCount) + 1, 30, TimeUnit.MINUTES);
            attributes.addFlashAttribute("msg", "用户或密码不正确！");
            return "redirect:login";
        }
        RedisStore.delValue(RedisKey.LOGIN_USER_NAME + loginEntity.getUserName());
        logger.info("[" + loginEntity.getUserName() + "]登录成功,IP["+ MyUtils.getIpAddress(request)+"]");
        return "redirect:index";
    }


}
