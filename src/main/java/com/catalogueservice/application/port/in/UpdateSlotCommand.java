package com.catalogueservice.application.port.in;

import java.time.ZonedDateTime;

public record UpdateSlotCommand(ZonedDateTime startDate, ZonedDateTime endDate) {}