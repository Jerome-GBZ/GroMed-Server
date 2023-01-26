package com.g2.gromed.page;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.g2.gromed.model.dto.presentation.PresentationCardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class PageJsonDeserializer extends JsonDeserializer<Page<?>> implements ContextualDeserializer {
    private JavaType type;

    @Override
    public Page<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        ObjectCodec codec = p.getCodec();
        JsonNode treeNode = codec.readTree(p);
        JsonNode contentNode = treeNode.path("content");
        List<PresentationCardDTO> content = codec.readValue(
                contentNode.traverse(),
                ctxt.getTypeFactory().constructCollectionType(List.class, type));

        int totalElements;
        JsonNode totalElementsNode = treeNode.path("totalElements");
        totalElements = totalElementsNode.isValueNode() ? totalElementsNode.asInt(0) : 0;
        Optional<Pageable> pageable;
        //rajouter totalPage
        JsonNode pageableNode = treeNode.path("pageable");
        pageable = pageableNode.isObject() ? Optional.ofNullable(codec.treeToValue(pageableNode, Pageable.class)) : Optional.empty();

        return new PageImpl<>(content, pageable.orElse(Pageable.unpaged()), totalElements);
    }

    @Override
    public JsonDeserializer<?> createContextual(final DeserializationContext ctxt, final BeanProperty property) {
        final com.fasterxml.jackson.databind.JavaType contentType;
        if (property != null) {
            contentType = property.getType();
        } else {
            contentType = ctxt.getContextualType();
        }
        final PageJsonDeserializer deserializer = new PageJsonDeserializer();
        deserializer.type = contentType.containedType(0);
        return deserializer;
    }
}
