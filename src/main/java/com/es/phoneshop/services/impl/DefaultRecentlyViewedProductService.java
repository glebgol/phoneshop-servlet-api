package com.es.phoneshop.services.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.RecentlyViewedProducts;
import com.es.phoneshop.services.RecentlyViewedProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.util.WebUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DefaultRecentlyViewedProductService implements RecentlyViewedProductService {
    private static final String RECENTLY_VIEWED_PRODUCT_SESSION_ATTRIBUTE =
            DefaultRecentlyViewedProductService.class.getName();
    protected ProductDao productDao;
    private static final int RECENTLY_VIEWED_PRODUCT_COUNT = 3;

    private DefaultRecentlyViewedProductService() {
        productDao = ArrayListProductDao.getInstance();
    }

    private static final class SingletonHolder {
        private static final DefaultRecentlyViewedProductService INSTANCE = new DefaultRecentlyViewedProductService();
    }

    public static DefaultRecentlyViewedProductService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public List<Product> getRecentlyViewedProducts(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            synchronized (WebUtils.getSessionMutex(session)) {
                RecentlyViewedProducts recentlyViewedProducts =
                        (RecentlyViewedProducts) session.getAttribute(RECENTLY_VIEWED_PRODUCT_SESSION_ATTRIBUTE);
                if (recentlyViewedProducts == null) {
                    recentlyViewedProducts = new RecentlyViewedProducts();
                    session.setAttribute(RECENTLY_VIEWED_PRODUCT_SESSION_ATTRIBUTE, recentlyViewedProducts);
                }
                return recentlyViewedProducts.getProducts();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public void addProduct(Long productId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            synchronized (WebUtils.getSessionMutex(session)) {
                RecentlyViewedProducts recentlyViewedProducts = (RecentlyViewedProducts) session
                        .getAttribute(RECENTLY_VIEWED_PRODUCT_SESSION_ATTRIBUTE);

                if (recentlyViewedProducts == null) {
                    recentlyViewedProducts = new RecentlyViewedProducts();
                }
                LinkedList<Product> products = recentlyViewedProducts.getProducts();
                Product product = productDao.getProduct(productId);
                products.remove(product);
                products.addFirst(product);
                if (products.size() > RECENTLY_VIEWED_PRODUCT_COUNT) {
                    products.removeLast();
                }
                session.setAttribute(RECENTLY_VIEWED_PRODUCT_SESSION_ATTRIBUTE, recentlyViewedProducts);
            }
        }
    }
}
