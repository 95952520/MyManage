package com.xuchen.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xuchen
 * @since 2018-04-29
 */
public class OrderGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "order_goods_id", type = IdType.AUTO)
    private Integer orderGoodsId;
    private Integer orderId;
    /**
     * 商品id
     */
    private Integer goodsId;
    /**
     * 商品数量
     */
    private Integer goodsCount;
    /**
     * 实际出售单价
     */
    private BigDecimal goodsSaleMoney;
    /**
     * 创建人
     */
    private String createUser;
    private Date createTime;
    private String updateUser;
    private Date updateTime;


    public Integer getOrderGoodsId() {
        return orderGoodsId;
    }

    public void setOrderGoodsId(Integer orderGoodsId) {
        this.orderGoodsId = orderGoodsId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public BigDecimal getGoodsSaleMoney() {
        return goodsSaleMoney;
    }

    public void setGoodsSaleMoney(BigDecimal goodsSaleMoney) {
        this.goodsSaleMoney = goodsSaleMoney;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "OrderGoods{" +
        ", orderGoodsId=" + orderGoodsId +
        ", orderId=" + orderId +
        ", goodsId=" + goodsId +
        ", goodsCount=" + goodsCount +
        ", goodsSaleMoney=" + goodsSaleMoney +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
