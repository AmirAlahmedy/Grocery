package com.amir.inventory.event.dto;

import java.util.List;

public record OrderPlacedEvent(String orderId, List<InventoryItemDTO> items) {}
