package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.InventoryRequestDto;
import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }

    public void addProductInInventory(InventoryRequestDto inventoryRequestDto) {
        Inventory inventory = inventoryRepository.save(mapToInventory(inventoryRequestDto));

        log.info("Inventory has been populated, id = {}", inventory.getId());
    }

    private Inventory mapToInventory(InventoryRequestDto inventoryRequestDto) {
        return Inventory.builder()
               .skuCode(inventoryRequestDto.getSkuCode())
               .quantity(inventoryRequestDto.getQuantity())
               .build();
    }
}
