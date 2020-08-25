package com.vk.planner.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class JsonJodaDateTimeDeserializer extends JsonDeserializer<DateTime> {

    @Override
    public DateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeCommon.getDateTimeFormatter();
        String dateValueAsString = jp.getValueAsString();
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateValueAsString);
        return dateTime;
    }
}
