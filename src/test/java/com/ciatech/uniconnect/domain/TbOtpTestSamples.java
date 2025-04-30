package com.ciatech.uniconnect.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TbOtpTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static TbOtp getTbOtpSample1() {
        return new TbOtp().id(1L).otpCode("otpCode1").attemptCount(1).maxAttempts(1);
    }

    public static TbOtp getTbOtpSample2() {
        return new TbOtp().id(2L).otpCode("otpCode2").attemptCount(2).maxAttempts(2);
    }

    public static TbOtp getTbOtpRandomSampleGenerator() {
        return new TbOtp()
            .id(longCount.incrementAndGet())
            .otpCode(UUID.randomUUID().toString())
            .attemptCount(intCount.incrementAndGet())
            .maxAttempts(intCount.incrementAndGet());
    }
}
