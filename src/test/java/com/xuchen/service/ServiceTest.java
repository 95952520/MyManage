package com.xuchen.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xuchen.BootManage;
import com.xuchen.entity.User;
import com.xuchen.enums.UserTypeEnums;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootManage.class)
public class ServiceTest {

    @Autowired
    UserService service;

    @Test
    public void test() {
        List<User> list = service.selectList(new EntityWrapper<User>().eq("user_type", UserTypeEnums.supplier.getId()));
        for (User user : list) {
            System.out.println(user);
        }

    }

}