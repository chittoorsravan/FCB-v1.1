package com.attestationhub.sailpoint.server.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

public class ForceStringSerializer extends StdScalarSerializer<Object>{

	
	public ForceStringSerializer() {
        super(Object.class);
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value instanceof Integer) {
            try {
                gen.writeNumber(String.valueOf(value));
            } catch (NumberFormatException e) {
                gen.writeString(value.toString());
            }
        } else if (value instanceof Integer) {
            gen.writeNumber(((Integer) value));
        }
    }
}
