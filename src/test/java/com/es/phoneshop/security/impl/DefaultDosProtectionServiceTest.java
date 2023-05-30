package com.es.phoneshop.security.impl;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class DefaultDosProtectionServiceTest {
    private final DefaultDosProtectionService service = DefaultDosProtectionService.getInstance();
    private static final String IP = "127.0.0.1";
    private static final int REQUESTS_COUNT = 21;

    @After
    public void tearDown() {
        service.timesMap.clear();
        service.countMap.clear();
    }

    @Test
    public void isAllowedRequest() {
        boolean test = service.isAllowed(IP);

        assertTrue(test);
    }

    @Test
    public void isAllowedManyRequest() {
        boolean test = true;
        for (int i = 0; i <= REQUESTS_COUNT; i++) {
            test = service.isAllowed(IP);
        }

        assertFalse(test);
    }
}
