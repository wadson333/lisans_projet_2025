package com.ciatech.uniconnect;

import com.ciatech.uniconnect.config.AsyncSyncConfiguration;
import com.ciatech.uniconnect.config.EmbeddedRedis;
import com.ciatech.uniconnect.config.EmbeddedSQL;
import com.ciatech.uniconnect.config.JacksonConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { UniconnectApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class })
@EmbeddedRedis
@EmbeddedSQL
public @interface IntegrationTest {
}
