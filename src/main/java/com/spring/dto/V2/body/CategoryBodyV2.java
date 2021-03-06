package com.spring.dto.V2.body;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryBodyV2 {
    private int shift;
    private int to;
    private String id;
    private String name;
}
