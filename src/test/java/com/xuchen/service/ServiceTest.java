package com.xuchen.service;

import com.xuchen.BootManage;
import com.xuchen.model.base.statistics.GoodsSaleModel;
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
    StatisticsService service;

    @Test
    public void test() {
        List<GoodsSaleModel> monthGoodsSale =
                service.getMonthGoodsSale(201805, 1);
        System.out.println(monthGoodsSale.size());
    }

}
