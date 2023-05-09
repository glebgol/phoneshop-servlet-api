package com.es.phoneshop.dao.impl;

import com.es.phoneshop.comparators.ProductSearchingComparator;
import com.es.phoneshop.comparators.ProductSortingComparator;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.ProductSortingField;
import com.es.phoneshop.model.SortingOrder;
import com.es.phoneshop.predicates.ContainsSearchStringProductPredicate;
import com.es.phoneshop.utils.ExtendedReadWriteLock;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static final Logger log = Logger.getLogger(ArrayListProductDao.class.getName());
    private final List<Product> products;
    private long productId;
    private final ExtendedReadWriteLock readWriteLock;

    private static class SingletonHelper {
        private static final ArrayListProductDao INSTANCE = new ArrayListProductDao();
    }

    public static ArrayListProductDao getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private ArrayListProductDao() {
        readWriteLock = new ExtendedReadWriteLock();
        products = new ArrayList<>();
    }

    @Override
    public Product getProduct(Long id) throws ProductNotFoundException {
        return readWriteLock.read(() -> products.stream()
                .filter(product -> product.getId().equals(id))
                .findAny()
                .orElseThrow(ProductNotFoundException::new)
        );
    }

    @Override
    public List<Product> getAllProducts() {
        return readWriteLock.read(() -> products.stream()
                .filter(this::productIsInStock)
                .filter(this::productPriceIsNotNull)
                .collect(Collectors.toList())
        );
    }

    @Override
    public List<Product> findProducts(String search) {
        return readWriteLock.read(() -> products.stream()
                .filter(this::productIsInStock)
                .filter(this::productPriceIsNotNull)
                .filter(new ContainsSearchStringProductPredicate(search))
                .sorted(new ProductSearchingComparator(search))
                .collect(Collectors.toList())
        );
    }

    @Override
    public List<Product> findProducts(String search, @NonNull ProductSortingField productSortingField, @NonNull SortingOrder sortingOrder) {
        return readWriteLock.read(() -> products.stream()
                .filter(this::productIsInStock)
                .filter(this::productPriceIsNotNull)
                .filter(new ContainsSearchStringProductPredicate(search))
                .sorted(new ProductSortingComparator(productSortingField, sortingOrder)
                        .thenComparing(new ProductSearchingComparator(search)))
                .collect(Collectors.toList())
        );
    }

    private boolean productIsInStock(Product product) {
        return product.getStock() > 0;
    }

    private boolean productPriceIsNotNull(Product product) {
        return product.getPrice() != null;
    }

    @Override
    public void save(Product product) {
        readWriteLock.execute(() -> {
            Long id = product.getId();
            if (id == null) {
                product.setId(++productId);
                products.add(product);
            }
            else {
                Product productToUpdate = getProduct(id);
                updateProduct(productToUpdate, product);
            }
        });
    }

    private static void updateProduct(Product productToUpdate, Product product) {
        productToUpdate.setCode(product.getCode());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setCurrency(product.getCurrency());
        productToUpdate.setStock(product.getStock());
        productToUpdate.setImageUrl(product.getImageUrl());
    }

    @Override
    public void delete(Long id) {
        readWriteLock.execute(() -> {
            try {
                Product productToRemove = getProduct(id);
                products.remove(productToRemove);
            } catch (ProductNotFoundException e) {
                log.warning("There is no element by id=" + id);
            }
        });
    }

    protected void clear() {
        products.clear();
        productId = 0;
    }
}
