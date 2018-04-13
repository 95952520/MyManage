package com.xuchen.service;

import com.xuchen.BootManage;
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
    SysRoleService service;

    @Test
    public void test() {
        List<String> list = service.findRolesByUserId(1);
        System.out.println(list.size());
        for (String s : list) {
            System.out.println(s);
        }

    }

}
