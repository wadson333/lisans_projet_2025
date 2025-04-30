package com.ciatech.uniconnect.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TbPasswordHistoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TbPasswordHistory getTbPasswordHistorySample1() {
        return new TbPasswordHistory().id(1L).passwordHash("passwordHash1").passwordSalt("passwordSalt1").changeReason("changeReason1");
    }

    public static TbPasswordHistory getTbPasswordHistorySample2() {
        return new TbPasswordHistory().id(2L).passwordHash("passwordHash2").passwordSalt("passwordSalt2").changeReason("changeReason2");
    }

    public static TbPasswordHistory getTbPasswordHistoryRandomSampleGenerator() {
        return new TbPasswordHistory()
            .id(longCount.incrementAndGet())
            .passwordHash(UUID.randomUUID().toString())
            .passwordSalt(UUID.randomUUID().toString())
            .changeReason(UUID.randomUUID().toString());
    }
}
