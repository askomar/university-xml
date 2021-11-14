package com.solvd.universityxml;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;

public class CustomLocalDateDeserializer extends StdDeserializer<LocalDate> {

    public CustomLocalDateDeserializer() {
        this(null);
    }

    public CustomLocalDateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException {
        String date = jsonparser.getText();
        return LocalDate.parse(date);
    }
}
