package com.g2.gromed.page;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.util.List;

public class SortJsonDeserializer extends JsonDeserializer<Sort> {

    @Override
    public Sort deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        ObjectCodec codec = p.getCodec();
        List<Order> orders = codec.readValue(p, new TypeReference<>() {});

        return Sort.by(orders);
    }
}
