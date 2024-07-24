package com.attestationhub.sailpoint.server.utils;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		if (value != null) {
			gen.writeNumber(String.valueOf(value.getTime()));
		} else {
			gen.writeNull();
		}
	}
}
