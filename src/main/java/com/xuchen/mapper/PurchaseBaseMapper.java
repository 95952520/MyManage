package com.xuchen.mapper;

import com.xuchen.entity.PurchaseBase;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 进货表 Mapper 接口
 * </p>
 *
 * @author xuchen
 * @since 2018-05-29
 */
public interface PurchaseBaseMapper extends BaseMapper<PurchaseBase> {

    void updateTotalMoney(Integer purchaseBaseId);

    void updateGoodsCountForDel(Integer purchaseId);
}
