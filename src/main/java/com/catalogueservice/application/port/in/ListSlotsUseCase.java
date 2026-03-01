package com.catalogueservice.application.port.in;

import com.catalogueservice.domain.model.Slot;

import java.util.List;

public interface ListSlotsUseCase {
    List<Slot> listAll();
}