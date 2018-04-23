package com.xuchen.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xuchen.BootManage;
import com.xuchen.entity.User;
import com.xuchen.enums.UserTypeEnums;
import com.xuchen.util.MyUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootManage.class)
public class ServiceTest {

    @Autowired
    UserService service;

    @Test
    public void test() {
        LocalDateTime beginDate = LocalDateTime.of(2018,4,10,17,18);
        LocalDateTime endDate = LocalDateTime.of(2018,4,20,17,18);
//        List<User> list = service.selectList(new EntityWrapper<User>().between(true,"create_time",beginDate,null));
//        List<User> list = service.selectList(new EntityWrapper<User>().gt(true,"create_time",beginDate));
        List<User> list = service.selectList(new EntityWrapper<User>().lt(true,"create_time",endDate));
        for (User user : list) {
            System.out.println(user);
        }

    }

}
