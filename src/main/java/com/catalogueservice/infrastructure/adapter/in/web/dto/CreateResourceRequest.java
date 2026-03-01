package com.catalogueservice.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateResourceRequest {

    @NotBlank
    private String name;

    private String description;

}
