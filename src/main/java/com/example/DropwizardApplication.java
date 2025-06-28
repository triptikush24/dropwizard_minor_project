package com.example;

import com.example.config.DropwizardConfiguration;
import com.example.health.TemplateHealthCheck;
import com.example.resources.HelloWorldResource;
import com.example.resources.UserResource;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.assets.AssetsBundle;
import org.jdbi.v3.core.Jdbi;

public class DropwizardApplication extends Application<DropwizardConfiguration> {

    public static void main(String[] args) throws Exception {
        new DropwizardApplication().run(args);
    }

    @Override
    public String getName() {
        return "dropwizard-app";
    }

    @Override
    public void initialize(Bootstrap<DropwizardConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets", "/assets", "index.html"));
    }

    @Override
    public void run(DropwizardConfiguration configuration, Environment environment) {
        // Create JDBI instance
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "database");

        // Register resources
        final HelloWorldResource helloWorldResource = new HelloWorldResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
        environment.jersey().register(helloWorldResource);

        final UserResource userResource = new UserResource(jdbi);
        environment.jersey().register(userResource);

        // Register health checks
        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
    }
} 