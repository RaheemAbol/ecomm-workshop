package org.abol.springstarter.services;

import org.abol.springstarter.models.CartItem;

import java.util.List;

public interface CartService {
    void saveItem(CartItem item);
    List<CartItem> getItemsByUserId(int userId);
    CartItem getItemById(int id);
    void deleteItem(int id);
}