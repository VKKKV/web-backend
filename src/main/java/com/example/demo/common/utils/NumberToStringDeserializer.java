package com.example.demo.common.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

// 处理数值类型转String的逻辑
public class NumberToStringDeserializer extends StdDeserializer<String> {
    public NumberToStringDeserializer() {
        super(String.class);
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.currentToken() == JsonToken.VALUE_NUMBER_FLOAT
                || p.currentToken() == JsonToken.VALUE_NUMBER_INT) {
            return p.getNumberValue().toString();
        }
        return p.getValueAsString();
    }
}
