package com.ciatech.uniconnect.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TbAddressesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static TbAddresses getTbAddressesSample1() {
        return new TbAddresses().id(1L).fullAddress("fullAddress1").codeSectionCommunale(1);
    }

    public static TbAddresses getTbAddressesSample2() {
        return new TbAddresses().id(2L).fullAddress("fullAddress2").codeSectionCommunale(2);
    }

    public static TbAddresses getTbAddressesRandomSampleGenerator() {
        return new TbAddresses()
            .id(longCount.incrementAndGet())
            .fullAddress(UUID.randomUUID().toString())
            .codeSectionCommunale(intCount.incrementAndGet());
    }
}
