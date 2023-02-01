package com.g2.gromed.page;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
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
