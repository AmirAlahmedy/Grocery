package com.amir.inventory.event.listener;

import com.amir.inventory.event.dto.InventoryItemDTO;
import com.amir.inventory.event.dto.InventoryReservedEvent;
import com.amir.inventory.event.dto.OrderPlacedEvent;
import com.mongodb.client.result.UpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    private static final Logger log = LoggerFactory.getLogger(OrderEventListener.class);

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public OrderEventListener(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @KafkaListener(topics = "orders-topic", groupId = "inventory-service-group")
    public void handleOrderPlaced(OrderPlacedEvent event) {
        log.info("Received order event for ID: {}", event.orderId());

        try {
            boolean allReserved = true;

            // Atomically reserve each item in MongoDB
            for (InventoryItemDTO item : event.items()) {
                Query query = new Query(Criteria.where("_id").is(item.productId())
                        .and("quantity").gte(item.quantity()));

                Update update = new Update().inc("stock", -item.quantity());
                UpdateResult result = reactiveMongoTemplate.updateFirst(query, update, "products").block();

                if (result.getModifiedCount() == 0) {
                    allReserved = false;
                    break;
                }
            }

            if (allReserved) {
                log.info("Inventory successfully reserved for order: {}", event.orderId());
                kafkaTemplate.send("inventory-topic", new InventoryReservedEvent(event.orderId(), true, "SUCCESS"));
            } else {
                log.warn("Inventory reservation failed for order: {}. Rolling back changes.", event.orderId());
                // TODO: Execute a rollback method to restock any items that succeeded before the failure
                kafkaTemplate.send("inventory-topic", new InventoryReservedEvent(event.orderId(), false, "OUT_OF_STOCK"));
            }

        } catch (Exception e) {
            log.error("Error processing order inventory reservation", e);
            kafkaTemplate.send("inventory-topic", new InventoryReservedEvent(event.orderId(), false, "SYSTEM_ERROR"));
        }
    }
}

