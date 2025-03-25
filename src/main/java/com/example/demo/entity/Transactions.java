package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@TableName(value = "transactions")
@Data
@Schema(description = "股票交易记录实体")
public class Transactions {
    @TableId(type = IdType.AUTO)
    @Schema(description = "交易流水号（唯一标识）",
            example = "50000001",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long transactionId;

    @Schema(description = "关联用户ID（对应用户表）", example = "1001", required = true)
    private Integer userId;

    @Schema(description = "关联股票ID（对应股票表）", example = "1001", required = true)
    private Integer stockId;

    @Schema(description = "交易类型",
            example = "BUY",
            allowableValues = {"BUY", "SELL"},
            required = true)
    private Object actionType;

    @Schema(description = "委托类型",
            example = "LIMIT",
            allowableValues = {"MARKET", "LIMIT"},
            required = true)
    private Object orderType;

    @Schema(description = "成交单价（精确到4位小数）",
            example = "152.4300",
            format = "decimal(19,4)",
            required = true)
    private BigDecimal price;

    @Schema(description = "交易数量（正整数）",
            example = "100",
            minimum = "1",
            required = true)
    private Integer quantity;

    @Schema(description = "订单状态",
            example = "EXECUTED",
            allowableValues = {"PENDING", "EXECUTED", "CANCELED"},
            accessMode = Schema.AccessMode.READ_ONLY)
    private Object status;

    @Schema(description = "订单创建时间（系统自动生成）",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Date createdAt;

    @Schema(description = "订单执行时间（成交时更新）",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Date executedAt;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Transactions other = (Transactions) that;
        return (this.getTransactionId() == null ? other.getTransactionId() == null : this.getTransactionId().equals(other.getTransactionId()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getStockId() == null ? other.getStockId() == null : this.getStockId().equals(other.getStockId()))
                && (this.getActionType() == null ? other.getActionType() == null : this.getActionType().equals(other.getActionType()))
                && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
                && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
                && (this.getExecutedAt() == null ? other.getExecutedAt() == null : this.getExecutedAt().equals(other.getExecutedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTransactionId() == null) ? 0 : getTransactionId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getStockId() == null) ? 0 : getStockId().hashCode());
        result = prime * result + ((getActionType() == null) ? 0 : getActionType().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getExecutedAt() == null) ? 0 : getExecutedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", transactionId=").append(transactionId);
        sb.append(", userId=").append(userId);
        sb.append(", stockId=").append(stockId);
        sb.append(", actionType=").append(actionType);
        sb.append(", price=").append(price);
        sb.append(", quantity=").append(quantity);
        sb.append(", status=").append(status);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", executedAt=").append(executedAt);
        sb.append("]");
        return sb.toString();
    }
}