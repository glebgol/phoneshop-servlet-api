package com.es.phoneshop.services.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.services.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.util.WebUtils;

import java.util.Optional;

public class HttpSessionCartService implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = HttpSessionCartService.class.getName();
    protected ProductDao productDao;

    private HttpSessionCartService() {
        productDao = ArrayListProductDao.getInstance();
    }

    private static class SingletonHelper {
        private static final HttpSessionCartService INSTANCE = new HttpSessionCartService();
    }

    public static HttpSessionCartService getInstance() {
        return HttpSessionCartService.SingletonHelper.INSTANCE;
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            synchronized (WebUtils.getSessionMutex(session)) {
                Cart cart = (Cart) session.getAttribute(CART_SESSION_ATTRIBUTE);
                if (cart == null) {
                    session.setAttribute(CART_SESSION_ATTRIBUTE, cart = new Cart());
                }
                return cart;
            }
        }
        return new Cart();
    }

    @Override
    public void add(Cart cart, Long productId, int quantity) throws OutOfStockException {
        Product product = productDao.getProduct(productId);
        Optional<CartItem> cartItemOptional = findCartItemByProduct(cart, product);
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            int availableStock = getAvailableStock(cartItem, product);
            updateCartItemQuantity(cartItem, product, quantity, availableStock);
        }
        else {
            createNewCartItem(cart, product, quantity);
        }
    }

    private boolean isNotAvailableToAdd(int quantity, int availableStock) {
        return quantity > availableStock;
    }

    private int getAvailableStock(CartItem cartItem, Product product) {
        return product.getStock() - cartItem.getQuantity();
    }

    private void createNewCartItem(Cart cart, Product product, int quantity) throws OutOfStockException {
        int productStock = product.getStock();
        if (isNotAvailableToAdd(quantity, productStock)) {
            throw new OutOfStockException(product, productStock, quantity);
        }
        cart.getItems().add(new CartItem(product, quantity));
    }

    private void updateCartItemQuantity(CartItem cartItem, Product product, int quantity, int availableStock) throws OutOfStockException {
        if (isNotAvailableToAdd(quantity, availableStock)) {
            throw new OutOfStockException(product, availableStock, quantity);
        }
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
    }

    private Optional<CartItem> findCartItemByProduct(Cart cart, Product product) {
        return cart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct().equals(product))
                .findAny();
    }
}
