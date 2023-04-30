package com.es.phoneshop.utils;

import com.es.phoneshop.interfaces.Executor;
import com.es.phoneshop.interfaces.Reader;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ExtendedReadWriteLock {
    private final Lock readLock;
    private final Lock writeLock;

    public ExtendedReadWriteLock() {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        readLock = readWriteLock.readLock();
        writeLock = readWriteLock.writeLock();
    }

    public <T> T read(Reader<T> function) {
        readLock.lock();
        try {
            return function.read();
        } finally {
            readLock.unlock();
        }
    }

    public void execute(Executor function) {
        writeLock.lock();
        try {
            function.execute();
        } finally {
            writeLock.unlock();
        }
    }
}
