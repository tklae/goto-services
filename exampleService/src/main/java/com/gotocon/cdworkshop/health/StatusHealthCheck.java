package com.gotocon.cdworkshop.health;

import com.yammer.metrics.core.HealthCheck;
import com.gotocon.cdworkshop.configuration.ExampleServiceConfiguration;

public class StatusHealthCheck extends HealthCheck{

    private ExampleServiceConfiguration configuration;

    public StatusHealthCheck(ExampleServiceConfiguration configuration) {
        super("StatusHealthCheck");
        this.configuration = configuration;
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy(String.format("Service Version: %s", configuration.getVersionNumber()));
    }
}
