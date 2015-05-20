package com.gotocon.cdworkshop.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gotocon.cdworkshop.logging.CustomDropwizardLoggingConfiguration;
import com.yammer.dropwizard.config.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ExampleServiceConfiguration extends Configuration {
    @NotEmpty
    @JsonProperty
    private String versionNumber = "undefined";

    @NotEmpty
    @JsonProperty
    private String defaultName = "undefined";

    @Valid
    @NotNull
    @JsonProperty("logging")
    private CustomDropwizardLoggingConfiguration loggingConfig = new CustomDropwizardLoggingConfiguration();

    @NotEmpty
    @JsonProperty("clientEndpoints")
    private String[] clientEndpoints = new String[0];

    public String getDefaultName() {
        return defaultName;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public CustomDropwizardLoggingConfiguration getLoggingConfig() {
        return loggingConfig;
    }

    public String[] getClientEndpoints() {
        return clientEndpoints;
    }
}
