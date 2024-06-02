package org.abol.springstarter.controllers;

import com.itextpdf.text.DocumentException;
import org.abol.springstarter.models.BaseUser;
import org.abol.springstarter.models.CartItem;
import org.abol.springstarter.services.CartService;
import org.abol.springstarter.services.PdfService;
import org.abol.springstarter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final PdfService pdfService;

    @Autowired
    public CartController(CartService cartService, UserService userService, PdfService pdfService) {
        this.cartService = cartService;
        this.userService = userService;
        this.pdfService = pdfService;
    }

    @GetMapping("/{userId}")
    public String viewCart(@PathVariable("userId") int userId, Model model) {
        BaseUser user = userService.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Invalid user ID: " + userId);
        }
        model.addAttribute("user", user);
        model.addAttribute("cartItems", cartService.getItemsByUserId(userId));
        return "view-cart";
    }

    @GetMapping("/addItem/{userId}")
    public String showAddItemForm(@PathVariable("userId") int userId, Model model) {
        CartItem item = new CartItem();
        item.setUser(userService.getUserById(userId));
        model.addAttribute("item", item);
        return "add-item";
    }

    @PostMapping("/addItem")
    public String addItem(@ModelAttribute("item") CartItem item) {
        cartService.saveItem(item);
        return "redirect:/cart/" + item.getUser().getId();
    }

    @GetMapping("/edit/{id}")
    public String showEditItemForm(@PathVariable("id") int id, Model model) {
        CartItem item = cartService.getItemById(id);
        model.addAttribute("item", item);
        return "update-item";
    }

    @PostMapping("/edit")
    public String editItem(@ModelAttribute("item") CartItem item) {
        cartService.saveItem(item);
        return "redirect:/cart/" + item.getUser().getId();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteItem(@PathVariable("id") int id) {
        CartItem item = cartService.getItemById(id);
        int userId = item.getUser().getId();
        cartService.deleteItem(id);
        return "redirect:/cart/" + userId;
    }

    @GetMapping("/print/{userId}")
    public String printCart(@PathVariable("userId") int userId, Model model) {
        BaseUser user = userService.getUserById(userId);
        List<CartItem> cartItems = user.getCart();
        StringBuilder receiptContent = new StringBuilder("Receipt Details:\n\n");

        double total = 0;
        for (CartItem item : cartItems) {
            receiptContent.append(item.getName()).append(" - ").append(item.getPrice()).append("\n");
            total += item.getPrice();
        }
        receiptContent.append("\nTotal: ").append(total);

        try {
            String pdfPath = pdfService.generatePdf(receiptContent.toString(), "receipt_" + userId + ".pdf");
            model.addAttribute("pdfPath", pdfPath);
            model.addAttribute("message", "PDF generated successfully!");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Failed to generate PDF!");
        }

        model.addAttribute("user", user); // Ensure the user is added to the model
        return "view-cart";
    }
}