package com.ciatech.uniconnect.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TbUsersTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static TbUsers getTbUsersSample1() {
        return new TbUsers()
            .id(1L)
            .firstName("firstName1")
            .lastName("lastName1")
            .telephone("telephone1")
            .email("email1")
            .otherTelephone("otherTelephone1")
            .nif("nif1")
            .cin("cin1")
            .ninu("ninu1")
            .passportNumber("passportNumber1")
            .licenceNumber("licenceNumber1")
            .code("code1")
            .failedLoginAttempts(1)
            .password("password1");
    }

    public static TbUsers getTbUsersSample2() {
        return new TbUsers()
            .id(2L)
            .firstName("firstName2")
            .lastName("lastName2")
            .telephone("telephone2")
            .email("email2")
            .otherTelephone("otherTelephone2")
            .nif("nif2")
            .cin("cin2")
            .ninu("ninu2")
            .passportNumber("passportNumber2")
            .licenceNumber("licenceNumber2")
            .code("code2")
            .failedLoginAttempts(2)
            .password("password2");
    }

    public static TbUsers getTbUsersRandomSampleGenerator() {
        return new TbUsers()
            .id(longCount.incrementAndGet())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .telephone(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .otherTelephone(UUID.randomUUID().toString())
            .nif(UUID.randomUUID().toString())
            .cin(UUID.randomUUID().toString())
            .ninu(UUID.randomUUID().toString())
            .passportNumber(UUID.randomUUID().toString())
            .licenceNumber(UUID.randomUUID().toString())
            .code(UUID.randomUUID().toString())
            .failedLoginAttempts(intCount.incrementAndGet())
            .password(UUID.randomUUID().toString());
    }
}
