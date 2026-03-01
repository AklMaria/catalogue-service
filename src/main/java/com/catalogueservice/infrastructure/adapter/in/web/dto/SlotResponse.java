package com.catalogueservice.infrastructure.adapter.in.web.dto;

import java.time.ZonedDateTime;
import java.util.UUID;

public record SlotResponse(UUID id, ZonedDateTime startDate, ZonedDateTime endDate) {}