package org.abol.springstarter.services;

import org.abol.springstarter.models.CartItem;
import org.abol.springstarter.repositories.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public void saveItem(CartItem item) {
        cartItemRepository.save(item);
    }

    @Override
    public List<CartItem> getItemsByUserId(int userId) {
        return cartItemRepository.findByUserId(userId);
    }

    @Override
    public CartItem getItemById(int id) {
        return cartItemRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteItem(int id) {
        cartItemRepository.deleteById(id);
    }
}