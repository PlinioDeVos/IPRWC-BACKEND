package nl.plinio.backend.endpoints.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.plinio.backend.endpoints.item.model.Item;
import nl.plinio.backend.endpoints.item.model.ItemDto;
import nl.plinio.backend.exception.EnitityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Item findItem(UUID id) {
        return itemRepository.findById(id).orElseThrow(() -> new EnitityNotFoundException(Item.class));
    }

    public Page<ItemDto> getAllItems(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        List<ItemDto> itemResponseList = new ArrayList<>();
        Page<Item> existingItems = itemRepository.findAll(pageable);
        existingItems.forEach(item -> itemResponseList.add(new ItemDto(item)));
        return new PageImpl<>(itemResponseList, pageable, existingItems.getTotalElements());
    }

    public List<Item> getAllByCartId(UUID cartId) {
        return itemRepository.findAllByCartId(cartId);
    }

    public ItemDto createItem(Item item) {
        return new ItemDto(itemRepository.save(item));
    }

    public ItemDto updateItem(UUID id, Item item) {
        Item existingItem = findItem(id);
        existingItem.setBuyPrice(item.getBuyPrice());
        return new ItemDto(itemRepository.save(existingItem));
    }

    public void deleteItem(UUID id) {
        try {
            itemRepository.deleteById(id);
        } catch (IllegalArgumentException ex) {
            throw new EnitityNotFoundException(Item.class);
        }
    }
}
