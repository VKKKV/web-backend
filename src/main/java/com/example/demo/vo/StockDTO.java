package com.example.demo.vo;

import com.example.demo.common.utils.SafeNumberToStringDeserializer;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) // 忽略未定义字段
@Schema(description = "股票数据传输对象")
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class StockDTO {

    @JsonDeserialize(using = SafeNumberToStringDeserializer.class)
    @JsonAlias({"lotSize", "lotsize"})
    @Schema(description = "每手数量", example = "100")
    private String lotSize;

    @Schema(description = "成交量", example = "1641463.0")
    @JsonDeserialize(using = SafeNumberToStringDeserializer.class)
    private String amount; // 股票成交量

    @JsonDeserialize(using = SafeNumberToStringDeserializer.class)
    @Schema(description = "当前价格", example = "97.20")
    private String price;

    @JsonDeserialize(using = SafeNumberToStringDeserializer.class)
    @Schema(description = "当天最高价格", example = "98.05")
    private String high; // 当天最高价格

    @JsonDeserialize(using = SafeNumberToStringDeserializer.class)
    @Schema(description = "当天最低价格", example = "97.15")
    private String low;

    @Schema(description = "股票代码", example = "00001")
    private String stockCode; // 必须通过逻辑设置该字段

    @Schema(description = "股票名称", example = "长和")
    private String name; // 股票名称

    @Schema(description = "昨日收盘价格", example = "97.75")
    private String lastPrice; // 股票昨天收盘价格

    @Schema(description = "开盘价", example = "97.75")
    private String openPrice; // 股票今天开盘价格


    @Schema(description = "时间", example = "2017/11/29 15:38:58")
    private String time; // 当前时间
}
