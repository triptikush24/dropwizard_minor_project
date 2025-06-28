package com.example;

import com.example.api.Saying;
import com.example.config.DropwizardConfiguration;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(DropwizardExtensionsSupport.class)
class DropwizardApplicationTest {

    private static final DropwizardAppExtension<DropwizardConfiguration> APP = new DropwizardAppExtension<>(
            DropwizardApplication.class,
            ResourceHelpers.resourceFilePath("test-config.yml")
    );

    @Test
    void testHelloWorld() {
        final Saying response = APP.client().target("http://localhost:" + APP.getLocalPort() + "/hello-world")
                .queryParam("name", "TestUser")
                .request()
                .get(Saying.class);

        assertEquals("Hello, TestUser!", response.getContent());
    }
} 