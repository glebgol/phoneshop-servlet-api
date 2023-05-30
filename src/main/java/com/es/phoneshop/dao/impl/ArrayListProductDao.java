package com.es.phoneshop.dao.impl;

import com.es.phoneshop.comparators.ProductSearchingComparator;
import com.es.phoneshop.comparators.ProductSortingComparator;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductSortingField;
import com.es.phoneshop.model.SortingOrder;
import com.es.phoneshop.predicates.ContainsSearchStringProductPredicate;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListProductDao extends ArrayListDao<Product> implements ProductDao {
    private static final Logger log = Logger.getLogger(ArrayListProductDao.class.getName());

    private ArrayListProductDao() {
    }

    private static class SingletonHelper {
        private static final ArrayListProductDao INSTANCE = new ArrayListProductDao();
    }

    public static ArrayListProductDao getInstance() {
        return SingletonHelper.INSTANCE;
    }


    @Override
    public Product get(Long id) {
        return Optional.ofNullable(super.get(id))
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public List<Product> getAllProducts() {
        return readWriteLock.read(() -> findProducts(items)
                .collect(Collectors.toList())
        );
    }

    @Override
    public List<Product> findProducts(String search) {
        return readWriteLock.read(() -> findProducts(items)
                .filter(new ContainsSearchStringProductPredicate(search))
                .sorted(new ProductSearchingComparator(search))
                .collect(Collectors.toList())
        );
    }

    @Override
    public List<Product> findProducts(String search, @NonNull ProductSortingField productSortingField, @NonNull SortingOrder sortingOrder) {
        return readWriteLock.read(() -> findProducts(items)
                .filter(new ContainsSearchStringProductPredicate(search))
                .sorted(new ProductSortingComparator(productSortingField, sortingOrder)
                        .thenComparing(new ProductSearchingComparator(search)))
                .collect(Collectors.toList())
        );
    }

    @Override
    public void delete(Long id) {
        readWriteLock.execute(() -> {
            try {
                Product productToRemove = get(id);
                items.remove(productToRemove);
            } catch (ProductNotFoundException e) {
                log.warning("There is no element by id=" + id);
            }
        });
    }

    private Stream<Product> findProducts(List<Product> products) {
        return products.stream()
                .filter(this::productIsInStock)
                .filter(this::productPriceIsNotNull);
    }

    private boolean productIsInStock(Product product) {
        return product.getStock() > 0;
    }

    private boolean productPriceIsNotNull(Product product) {
        return product.getPrice() != null;
    }
}
