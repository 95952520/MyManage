package com.xuchen.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xuchen.BootManage;
import com.xuchen.entity.Goods;
import com.xuchen.util.ExportExcel;
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
    GoodsService goodsService;

    @Test
    public void test() {
        List<Goods> list = goodsService.selectList(new EntityWrapper<Goods>());
//        list.forEach(i-> System.out.println(i));

        ExportExcel.excelExport(true,"sheetName","123456",list,"D://"+System.currentTimeMillis());
    }

}
