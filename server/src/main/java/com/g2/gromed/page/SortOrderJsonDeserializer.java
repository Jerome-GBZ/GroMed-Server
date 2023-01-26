package com.g2.gromed.page;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.NullHandling;
import org.springframework.data.domain.Sort.Order;

import java.io.IOException;
import java.util.Optional;

public class SortOrderJsonDeserializer extends JsonDeserializer<Order> {

    @Override
    public Order deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        ObjectCodec codec = p.getCodec();
        JsonNode treeNode = codec.readTree(p);

        String property;
        JsonNode propertyNode = treeNode.path("property");
        property = propertyNode.isValueNode() ? propertyNode.asText() : null;
        if (property == null) {
            return null;
        }

        Direction direction;
        JsonNode directionNode = treeNode.path("direction");
        direction = directionNode.isValueNode() ? codec.treeToValue(directionNode, Direction.class) : null;

        Optional<NullHandling> nullHandling;
        JsonNode nullHandlingNode = treeNode.path("nullHandling");
        nullHandling = nullHandlingNode.isValueNode() ? Optional.ofNullable(codec.treeToValue(nullHandlingNode, NullHandling.class)) : Optional.empty();

        return new Order(direction, property, nullHandling.orElse(NullHandling.NATIVE));
    }

}
