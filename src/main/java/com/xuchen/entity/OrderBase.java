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
 * @since 2018-04-18
 */
public class OrderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "order_id", type = IdType.AUTO)
    private Integer orderId;
    /**
     * 顾客id
     */
    private Integer customerId;
    /**
     * 各个商品总价
     */
    private BigDecimal totalPrice;
    /**
     * 0.未配送 1.配送中 2.配送完成
     * 保留此字段为保证送货完记录账单信息
     */
    private Integer orderType;
    /**
     * 0.上门取货 1.自己配送 2.找人配送
     */
    private Integer deliveryType;
    /**
     * 配送地址
     */
    private String deliveryAddress;
    /**
     * 配送者
     */
    private Integer deliveryUserId;
    /**
     * 配送费
     */
    private BigDecimal deliveryPrice;
    /**
     * 0.未付款 1.付部分 2.已付款
     */
    private Integer payType;
    /**
     * 已付款金额
     */
    private BigDecimal payMoney;
    /**
     * 未付款金额
     */
    private BigDecimal unpayMoney;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建者
     */
    private String createUser;
    /**
     * 创建时间
     */
    private Date createTime;
    private String updateUser;
    /**
     * 更新时间
     */
    private Date updateTime;


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Integer getDeliveryUserId() {
        return deliveryUserId;
    }

    public void setDeliveryUserId(Integer deliveryUserId) {
        this.deliveryUserId = deliveryUserId;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public BigDecimal getUnpayMoney() {
        return unpayMoney;
    }

    public void setUnpayMoney(BigDecimal unpayMoney) {
        this.unpayMoney = unpayMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return "OrderBase{" +
        ", orderId=" + orderId +
        ", customerId=" + customerId +
        ", totalPrice=" + totalPrice +
        ", orderType=" + orderType +
        ", deliveryType=" + deliveryType +
        ", deliveryAddress=" + deliveryAddress +
        ", deliveryUserId=" + deliveryUserId +
        ", deliveryPrice=" + deliveryPrice +
        ", payType=" + payType +
        ", payMoney=" + payMoney +
        ", unpayMoney=" + unpayMoney +
        ", remark=" + remark +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
