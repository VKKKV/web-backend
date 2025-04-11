package com.example.demo.common.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

// 自定义数值转字符串适配器（处理科学计数法/浮点型等问题）
public class SafeNumberToStringDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonToken token = p.currentToken();
        
        if (token.isNumeric()) {
            BigDecimal num = p.getDecimalValue();
            // 去除末尾.0
            if (num.scale() <= 0) return num.toPlainString();
            // 保留小数数值完整
            return num.stripTrailingZeros().toPlainString(); 
        }

        return p.getValueAsString();
    }
}
