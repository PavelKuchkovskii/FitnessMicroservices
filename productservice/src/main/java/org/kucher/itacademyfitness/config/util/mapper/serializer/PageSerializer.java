package org.kucher.itacademyfitness.config.util.mapper.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

@JsonComponent
public class PageSerializer extends JsonSerializer<Page> {

    @Override
    public void serialize(Page value, JsonGenerator gen, SerializerProvider serializer) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("number", value.getNumber());
        gen.writeNumberField("size", value.getSize());
        gen.writeNumberField("total_values", value.getTotalPages());
        gen.writeNumberField("total_elements", value.getTotalElements());
        gen.writeBooleanField("first", value.isFirst());
        gen.writeNumberField("number_of_elements", value.getNumberOfElements());
        gen.writeBooleanField("last", value.isLast());
        gen.writeFieldName("content");
        serializer.defaultSerializeValue(value.getContent(), gen);
        gen.writeEndObject();
    }
}
