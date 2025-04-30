package com.ciatech.uniconnect.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TbRolesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TbRoles getTbRolesSample1() {
        return new TbRoles().id(1L).name("name1").description("description1");
    }

    public static TbRoles getTbRolesSample2() {
        return new TbRoles().id(2L).name("name2").description("description2");
    }

    public static TbRoles getTbRolesRandomSampleGenerator() {
        return new TbRoles().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).description(UUID.randomUUID().toString());
    }
}
