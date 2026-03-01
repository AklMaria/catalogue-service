package com.catalogueservice.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateAvailabilityCapacityRequest {

    @Min(0)
    private int capacity;

}