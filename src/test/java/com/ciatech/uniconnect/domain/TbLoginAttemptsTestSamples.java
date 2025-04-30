package com.ciatech.uniconnect.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TbLoginAttemptsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TbLoginAttempts getTbLoginAttemptsSample1() {
        return new TbLoginAttempts().id(1L).ipAddress("ipAddress1").userAgent("userAgent1").deviceType("deviceType1");
    }

    public static TbLoginAttempts getTbLoginAttemptsSample2() {
        return new TbLoginAttempts().id(2L).ipAddress("ipAddress2").userAgent("userAgent2").deviceType("deviceType2");
    }

    public static TbLoginAttempts getTbLoginAttemptsRandomSampleGenerator() {
        return new TbLoginAttempts()
            .id(longCount.incrementAndGet())
            .ipAddress(UUID.randomUUID().toString())
            .userAgent(UUID.randomUUID().toString())
            .deviceType(UUID.randomUUID().toString());
    }
}
