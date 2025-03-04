package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@TableName(value = "stocks")
@Data
@Schema(description = "股票信息实体")
public class Stocks {

    @TableId(type = IdType.AUTO)
    @Schema(description = "股票唯一标识", example = "1001")
    private Integer stockId;

    @Schema(description = "股票代码（唯一）", example = "600519")
    private String stockCode;

    @Schema(description = "股票全称", example = "贵州茅台酒股份有限公司")
    private String stockName;

    @Schema(description = "所属交易所: SH-上交所, SZ-深交所", example = "SH", allowableValues = { "SH", "SZ" })
    private String market;

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
        Stocks other = (Stocks) that;
        return (this.getStockId() == null ? other.getStockId() == null : this.getStockId().equals(other.getStockId()))
                && (this.getStockCode() == null ? other.getStockCode() == null
                        : this.getStockCode().equals(other.getStockCode()))
                && (this.getStockName() == null ? other.getStockName() == null
                        : this.getStockName().equals(other.getStockName()))
                && (this.getMarket() == null ? other.getMarket() == null : this.getMarket().equals(other.getMarket()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getStockId() == null) ? 0 : getStockId().hashCode());
        result = prime * result + ((getStockCode() == null) ? 0 : getStockCode().hashCode());
        result = prime * result + ((getStockName() == null) ? 0 : getStockName().hashCode());
        result = prime * result + ((getMarket() == null) ? 0 : getMarket().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", stockId=").append(stockId);
        sb.append(", stockCode=").append(stockCode);
        sb.append(", stockName=").append(stockName);
        sb.append(", market=").append(market);
        sb.append("]");
        return sb.toString();
    }
}