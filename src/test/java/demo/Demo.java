package demo;

import com.xuchen.entity.SysUser;
import com.xuchen.util.MyUtils;

public class Demo {
    public static void main(String[] args) throws Exception {
        SysUser user = new SysUser();
        user.setUserName("1");
        user.setPassword("1");
        MyUtils.encrypPassword(user);
        System.out.println(user.getPassword());
    }
}
