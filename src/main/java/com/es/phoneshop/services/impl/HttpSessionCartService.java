package com.es.phoneshop.services.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.services.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.util.WebUtils;

import java.math.BigDecimal;
import java.util.Optional;

public class HttpSessionCartService implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = HttpSessionCartService.class.getName();
    protected ProductDao productDao;
    private final Object lock = new Object();

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
    public Cart getCart(HttpSession session) {
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
        synchronized (lock) {
            Product product = productDao.get(productId);
            Optional<CartItem> cartItemOptional = findCartItemByProduct(cart, product);
            if (cartItemOptional.isPresent()) {
                CartItem cartItem = cartItemOptional.get();
                int availableStock = getAvailableStock(cartItem, product);
                addCartItemProductQuantity(cartItem, product, quantity, availableStock);
            } else {
                createNewCartItem(cart, product, quantity);
            }
            recalculateCart(cart);
        }
    }

    @Override
    public void update(Cart cart, Long productId, int quantity) throws OutOfStockException {
        synchronized (lock) {
            Product product = productDao.get(productId);
            Optional<CartItem> cartItemOptional = findCartItemByProduct(cart, product);
            if (cartItemOptional.isPresent()) {
                CartItem cartItem = cartItemOptional.get();
                updateCartItemProductQuantity(cartItem, product, quantity);
            }
            recalculateCart(cart);
        }
    }

    @Override
    public void delete(Cart cart, Long productId) {
        synchronized (lock) {
            cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
            recalculateCart(cart);
        }
    }

    @Override
    public void clear(Cart cart) {
        synchronized (lock) {
            cart.getItems().clear();
            recalculateCart(cart);
        }
    }

    private void recalculateCart(Cart cart) {
        cart.setTotalQuantity(cart.getItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum());

        cart.setTotalCost(cart.getItems().stream()
                .map(item -> item.getProduct().getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    private boolean isNotAvailableToAdd(int quantity, int availableStock) {
        return quantity > availableStock;
    }

    private int getAvailableStock(CartItem cartItem, Product product) {
        return product.getStock() - cartItem.getQuantity();
    }

    private void createNewCartItem(Cart cart, Product product, int quantity) throws OutOfStockException {
        checkAddingPossibility(product, product.getStock(), quantity);
        cart.getItems().add(new CartItem(product, quantity));
    }

    private void addCartItemProductQuantity(CartItem cartItem, Product product, int quantity, int availableStock) throws OutOfStockException {
        checkAddingPossibility(product, availableStock, quantity);
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
    }

    private void checkAddingPossibility(Product product, int availableStock, int quantity) throws OutOfStockException {
        if (isNotAvailableToAdd(quantity, availableStock)) {
            throw new OutOfStockException(product, availableStock, quantity);
        }
    }

    private void updateCartItemProductQuantity(CartItem cartItem, Product product, int quantity) throws OutOfStockException {
        int availableStock = product.getStock();
        if (isNotAvailableToAdd(quantity, availableStock)) {
            throw new OutOfStockException(product, availableStock, quantity);
        }
        cartItem.setQuantity(quantity);
    }

    private Optional<CartItem> findCartItemByProduct(Cart cart, Product product) {
        return cart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct().equals(product))
                .findAny();
    }
}
