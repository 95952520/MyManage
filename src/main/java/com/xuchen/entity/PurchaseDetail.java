package com.xuchen.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xuchen
 * @since 2018-05-29
 */
public class PurchaseDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "purchase_detail_id", type = IdType.AUTO)
    private Integer purchaseDetailId;
    private Integer purchaseBaseId;
    /**
     * goodsId
     */
    private Integer goodsId;
    /**
     * 进货量
     */
    private Integer goodsCount;


    public Integer getPurchaseDetailId() {
        return purchaseDetailId;
    }

    public void setPurchaseDetailId(Integer purchaseDetailId) {
        this.purchaseDetailId = purchaseDetailId;
    }

    public Integer getPurchaseBaseId() {
        return purchaseBaseId;
    }

    public void setPurchaseBaseId(Integer purchaseBaseId) {
        this.purchaseBaseId = purchaseBaseId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    @Override
    public String toString() {
        return "PurchaseDetail{" +
        ", purchaseDetailId=" + purchaseDetailId +
        ", purchaseBaseId=" + purchaseBaseId +
        ", goodsId=" + goodsId +
        ", goodsCount=" + goodsCount +
        "}";
    }
}
