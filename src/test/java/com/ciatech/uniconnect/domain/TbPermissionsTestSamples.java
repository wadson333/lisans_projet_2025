package com.ciatech.uniconnect.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TbPermissionsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TbPermissions getTbPermissionsSample1() {
        return new TbPermissions().id(1L).action("action1").description("description1");
    }

    public static TbPermissions getTbPermissionsSample2() {
        return new TbPermissions().id(2L).action("action2").description("description2");
    }

    public static TbPermissions getTbPermissionsRandomSampleGenerator() {
        return new TbPermissions()
            .id(longCount.incrementAndGet())
            .action(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
