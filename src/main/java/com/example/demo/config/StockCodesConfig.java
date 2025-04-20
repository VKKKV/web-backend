package com.example.demo.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class StockCodesConfig {
    @JsonProperty("stock")
    private List<String> stockCodes;

}
