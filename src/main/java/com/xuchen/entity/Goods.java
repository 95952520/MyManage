package com.xuchen.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xuchen
 * @since 2018-04-16
 */
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "goods_id", type = IdType.AUTO)
    private Integer goodsId;
    /**
     * 商品名
     */
    private String goodsName;
    /**
     * 0.粉类 1.浆类 2.乳液类 3.沙子 4.小器具 5.其他
     */
    private Integer goodsType;
    /**
     * 0.自己生产 1.转卖
     */
    private Integer isProduct;
    /**
     * 库存量
     */
    private Integer stockCount;
    /**
     * 0.袋 1.桶 2.瓶 3.个 4.公斤
     */
    private Integer unitType;
    /**
     * 每份的重量
     */
    private BigDecimal weight;
    /**
     * 0.克 1.千克 2.吨
     */
    private Integer weightType;
    /**
     * 进价/成本价
     */
    private BigDecimal originalPrice;
    /**
     * 运费
     */
    private BigDecimal freight;
    /**
     * 批发价
     */
    private BigDecimal wholesalePrice;
    /**
     * 零售价
     */
    private BigDecimal retailPrice;


    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public Integer getIsProduct() {
        return isProduct;
    }

    public void setIsProduct(Integer isProduct) {
        this.isProduct = isProduct;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Integer getUnitType() {
        return unitType;
    }

    public void setUnitType(Integer unitType) {
        this.unitType = unitType;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Integer getWeightType() {
        return weightType;
    }

    public void setWeightType(Integer weightType) {
        this.weightType = weightType;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public BigDecimal getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(BigDecimal wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    @Override
    public String toString() {
        return "Goods{" +
        ", goodsId=" + goodsId +
        ", goodsName=" + goodsName +
        ", goodsType=" + goodsType +
        ", isProduct=" + isProduct +
        ", stockCount=" + stockCount +
        ", unitType=" + unitType +
        ", weight=" + weight +
        ", weightType=" + weightType +
        ", originalPrice=" + originalPrice +
        ", freight=" + freight +
        ", wholesalePrice=" + wholesalePrice +
        ", retailPrice=" + retailPrice +
        "}";
    }
}
