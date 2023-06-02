package com.es.phoneshop.dao.impl;

import com.es.phoneshop.model.IdentifiableItem;
import com.es.phoneshop.utils.ExtendedReadWriteLock;

import java.util.ArrayList;

public abstract class ArrayListDao<E extends IdentifiableItem> {
    protected ArrayList<E> items;
    protected final ExtendedReadWriteLock readWriteLock;
    protected Long lastId = 0L;

    public ArrayListDao() {
        this.items = new ArrayList<>();
        this.readWriteLock = new ExtendedReadWriteLock();
    }

    public E get(Long id) {
        return readWriteLock.read(() -> items.stream()
                .filter(item -> item.getId().equals(id))
                .findAny().orElse(null)
        );
    }

    public void save(E item) {
        readWriteLock.execute(() -> {
            Long id = item.getId();
            if (id == null) {
                item.setId(++lastId);
                items.add(item);
            } else {
                E itemToUpdate = get(id);
                items.set(items.indexOf(itemToUpdate), item);
            }
        });
    }

    protected void clear() {
        readWriteLock.execute(() -> {
            items.clear();
            lastId = 0L;
        });
    }
}
