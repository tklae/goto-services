package net.klaesener.api;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;
import net.klaesener.api.configuration.ExampleServiceConfiguration;
import net.klaesener.api.filters.LoggingFilter;
import net.klaesener.api.health.StatusHealthCheck;
import net.klaesener.api.logging.DropwizardLoggerFactory;
import net.klaesener.api.resources.ExampleHtmlResource;
import net.klaesener.api.resources.ExampleJsonResource;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class ExampleService extends Service<ExampleServiceConfiguration> {
    public static void main(String[] args) throws Exception {
        new ExampleService().run(args);
    }

    @Override
    public void initialize(Bootstrap<ExampleServiceConfiguration> bootstrap) {
        bootstrap.setName("example-service");
        bootstrap.addBundle(new ViewBundle());
    }

    @Override
    public void run(ExampleServiceConfiguration configuration, Environment environment) throws Exception {
        DropwizardLoggerFactory.createFromDropwizardConfiguration("ServiceLoggingContext", configuration.getLoggingConfig());

        environment.addResource(new ExampleJsonResource());
        environment.addResource(new ExampleHtmlResource(configuration));

        environment.addHealthCheck(new StatusHealthCheck(configuration));

        environment.addFilter(new LoggingFilter(), baseUrlFor(ExampleJsonResource.class));
        environment.addFilter(new LoggingFilter(), baseUrlFor(ExampleHtmlResource.class));
    }

    private String baseUrlFor(Class resource) {
        URI builder = UriBuilder.fromResource(resource).build();
        return builder.toASCIIString();
    }
}
