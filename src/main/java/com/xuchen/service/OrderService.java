package com.xuchen.service;

import com.xuchen.entity.OrderBase;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xuchen
 * @since 2018-04-18
 */
public interface OrderService extends IService<OrderBase> {
    void updateTotalMoney(Integer orderId);

    void updateGoodsCountForDel(Integer orderId);
}
