package com.catalogueservice.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class CreateAvailabilityRequest {

    @NotNull
    private UUID resourceId;

    @NotNull
    private UUID slotId;

    @Min(0)
    private int capacity;


}