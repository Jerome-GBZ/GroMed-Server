package com.g2.gromed.page;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.IOException;

public class PageableJsonDeserializer extends JsonDeserializer<Pageable> {

    @Override
    public Pageable deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        ObjectCodec codec = p.getCodec();
        JsonNode treeNode = codec.readTree(p);
        int pageSize;
        JsonNode pageSizeNode = treeNode.path("pageSize");
        pageSize = pageSizeNode.isValueNode() ? pageSizeNode.asInt(20) : 0;

        int pageNumber;
        JsonNode pageNumberNode = treeNode.path("pageNumber");
        pageNumber = pageNumberNode.isValueNode() ? pageNumberNode.asInt(0) : 0;

        Sort sort;
        JsonNode sortNode = treeNode.path("sort");
        sort = sortNode.isArray() ? codec.treeToValue(sortNode, Sort.class) : null;

        return sort == null ? PageRequest.of(pageNumber, pageSize) : PageRequest.of(pageNumber, pageSize, sort);
    }
}
