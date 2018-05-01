package com.xuchen.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xuchen.BootManage;
import com.xuchen.entity.Goods;
import com.xuchen.entity.OrderGoods;
import com.xuchen.entity.User;
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
    OrderGoodsService service;

    @Test
    public void test() {
        List<OrderGoods> list = service.selectList(new EntityWrapper<OrderGoods>().eq("order_id", 1).eq("goods_id",2).ne("order_goods_id",19));
        System.out.println(list.size());
    }

}
