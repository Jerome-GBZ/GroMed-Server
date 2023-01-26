package com.g2.gromed.page;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PageJsonConfig {

    @JsonComponent
    public static class PageableJsonDeserialierBean extends PageableJsonDeserializer{}

    @JsonComponent
    public static class PageJsonDeserialierBean extends PageJsonDeserializer{}

    @JsonComponent
    public static class SortJsonDeserialierBean extends SortJsonDeserializer{}

    @JsonComponent
    public static class SortOrderJsonDeserialierBean extends SortOrderJsonDeserializer{}

}
