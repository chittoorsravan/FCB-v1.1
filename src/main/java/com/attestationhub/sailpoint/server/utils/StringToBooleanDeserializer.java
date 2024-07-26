package com.attestationhub.sailpoint.server.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class StringToBooleanDeserializer extends JsonDeserializer<Boolean> {
    @Override
    public Boolean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        return "1".equals(value);
    }
}