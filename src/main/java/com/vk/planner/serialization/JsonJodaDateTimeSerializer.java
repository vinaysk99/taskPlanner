package com.vk.planner.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class JsonJodaDateTimeSerializer extends JsonSerializer<DateTime> {

    @Override
    public void serialize(DateTime dateTime, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeCommon.getDateTimeFormatter();
        jsonGenerator.writeString(dateTimeFormatter.print(dateTime));
    }
}
