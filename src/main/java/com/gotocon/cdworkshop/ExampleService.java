package com.gotocon.cdworkshop;

import com.gotocon.cdworkshop.resources.WebsiteFragmentProviderResource;
import com.gotocon.cdworkshop.resources.WebsiteFragmentReceiverResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;
import com.gotocon.cdworkshop.configuration.ExampleServiceConfiguration;
import com.gotocon.cdworkshop.filters.LoggingFilter;
import com.gotocon.cdworkshop.health.StatusHealthCheck;
import com.gotocon.cdworkshop.logging.DropwizardLoggerFactory;
import com.gotocon.cdworkshop.resources.ExampleHtmlResource;
import com.gotocon.cdworkshop.resources.ExampleJsonResource;

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
        environment.addResource(new WebsiteFragmentProviderResource());
        environment.addResource(new WebsiteFragmentReceiverResource(configuration));

        environment.addHealthCheck(new StatusHealthCheck(configuration));

        environment.addFilter(new LoggingFilter(), baseUrlFor(ExampleJsonResource.class));
        environment.addFilter(new LoggingFilter(), baseUrlFor(ExampleHtmlResource.class));
        environment.addFilter(new LoggingFilter(), baseUrlFor(WebsiteFragmentProviderResource.class));
        environment.addFilter(new LoggingFilter(), baseUrlFor(WebsiteFragmentReceiverResource.class));
    }

    private String baseUrlFor(Class resource) {
        URI builder = UriBuilder.fromResource(resource).build();
        return builder.toASCIIString();
    }
}
