package com.es.phoneshop.security.impl;

import com.es.phoneshop.security.DosProtectionService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultDosProtectionService implements DosProtectionService {
    private static final long THRESHOLD = 20;
    private static final long TIME_IN_MILLISECONDS = 60000;
    protected final Map<String, Long> countMap = new ConcurrentHashMap<>();
    protected final Map<String, Long> timesMap = new ConcurrentHashMap<>();

    private DefaultDosProtectionService() {
    }

    private static class SingletonHelper {
        private static final DefaultDosProtectionService INSTANCE = new DefaultDosProtectionService();
    }

    public static DefaultDosProtectionService getInstance() {
        return DefaultDosProtectionService.SingletonHelper.INSTANCE;
    }

    @Override
    public boolean isAllowed(String ip) {
        Long count = countMap.get(ip);
        if (count == null || count == 0L) {
            countMap.put(ip, 1L);
            timesMap.put(ip, System.currentTimeMillis());
        } else if (isAvailableToRestart(ip)) {
            countMap.put(ip, 0L);
            return true;
        } else if (count > THRESHOLD) {
            return false;
        } else {
            countMap.put(ip, ++count);
        }
        return true;
    }

    private boolean isAvailableToRestart(String ip) {
        return TIME_IN_MILLISECONDS <= System.currentTimeMillis() - timesMap.get(ip);
    }
}
