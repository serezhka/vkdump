package com.github.serezhka.vkdump.controller.rest.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class JQueryDataTablesDTO<T> implements Serializable {

    @JsonProperty("draw")
    private int draw;

    @JsonProperty("recordsTotal")
    private long recordsTotal;

    @JsonProperty("recordsFiltered")
    private long recordsFiltered;

    @JsonProperty("data")
    private List<T> data;

    @JsonProperty("error")
    private String error;

    public JQueryDataTablesDTO(Page<T> page, int draw) {
        recordsTotal = page.getTotalElements();
        recordsFiltered = page.getTotalElements();
        this.data = page.getContent();
        this.draw = draw;
    }
}
