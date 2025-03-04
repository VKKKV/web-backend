package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import lombok.Data;


@TableName(value ="trading_strategies")
@Data
@Schema(description = "交易策略实体")
/**
 * 用户交易策略实体
 * 对应 trading_strategies 表，存储用户自定义交易策略
 * 使用MyBatis Plus逻辑删除（见application.yml配置）
 */
public class TradingStrategies {
    @TableId(type = IdType.AUTO)
    @Schema(description = "策略唯一ID", example = "2001")
    private Integer strategyId;

    @Schema(description = "关联用户ID", example = "1001")
    private Integer userId;

    @Schema(description = "策略名称", example = "均线突破策略")
    private String name;

    @Schema(description = "JSON格式触发条件", 
           example = "{\"conditionType\": \"MA\", \"period\": 5}")
    private Object triggerCondition;

    @Schema(description = "策略状态: 1-启用, 0-停用", 
           allowableValues = {"1", "0"})
    private Object status;

    @Schema(description = "策略创建时间（自动生成）", 
           accessMode = Schema.AccessMode.READ_ONLY)
    private Date createdAt;

    @Schema(description = "最后修改时间（自动更新）", 
           accessMode = Schema.AccessMode.READ_ONLY)
    private Date updatedAt;

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
        TradingStrategies other = (TradingStrategies) that;
        return (this.getStrategyId() == null ? other.getStrategyId() == null : this.getStrategyId().equals(other.getStrategyId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getTriggerCondition() == null ? other.getTriggerCondition() == null : this.getTriggerCondition().equals(other.getTriggerCondition()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getStrategyId() == null) ? 0 : getStrategyId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getTriggerCondition() == null) ? 0 : getTriggerCondition().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getUpdatedAt() == null) ? 0 : getUpdatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", strategyId=").append(strategyId);
        sb.append(", userId=").append(userId);
        sb.append(", name=").append(name);
        sb.append(", triggerCondition=").append(triggerCondition);
        sb.append(", status=").append(status);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append("]");
        return sb.toString();
    }
}