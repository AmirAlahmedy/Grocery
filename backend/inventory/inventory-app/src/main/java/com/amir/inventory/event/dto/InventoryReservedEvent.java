package com.amir.inventory.event.dto;

public record InventoryReservedEvent(String orderId, boolean success, String reason) {}
