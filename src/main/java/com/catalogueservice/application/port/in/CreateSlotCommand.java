package com.catalogueservice.application.port.in;

import java.time.ZonedDateTime;

public record CreateSlotCommand(ZonedDateTime startDate, ZonedDateTime endDate) {}