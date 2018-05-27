package com.xuchen.service;

import com.xuchen.entity.Goods;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xuchen
 * @since 2018-04-16
 */
public interface GoodsService extends IService<Goods> {

    void updateGoodsStock(Integer goodsId,Integer stockCountChange);
}
