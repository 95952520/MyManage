package com.xuchen.core.shiro;

import com.xuchen.entity.SysUser;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ShiroUtils {

    private static SessionDAO sessionDAO;

    @Autowired
    private void setSessionDAO(SessionDAO sessionDAO){
        this.sessionDAO = sessionDAO;
    }

    public static void kickOutUser(String loginName) {
        SysUser sysUser;
        SimplePrincipalCollection attribute;
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            attribute = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (attribute == null){
                continue;
            }
            sysUser = (SysUser) attribute.getPrimaryPrincipal();
            if (loginName.equals(sysUser.getUserName())){
                session.setTimeout(0);
                return;
            }

        }
    }

}
