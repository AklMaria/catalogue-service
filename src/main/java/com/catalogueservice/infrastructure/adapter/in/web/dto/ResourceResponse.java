package com.catalogueservice.infrastructure.adapter.in.web.dto;

import java.util.UUID;

public record ResourceResponse(UUID id, String name, String description) {}
