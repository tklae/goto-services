package com.gotocon.cdworkshop.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gotocon.cdworkshop.configuration.ExampleServiceConfiguration;
import com.yammer.dropwizard.views.View;
import com.yammer.metrics.annotation.Timed;
import com.gotocon.cdworkshop.views.FreemarkerView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

@Path("/html")
public class ExampleHtmlResource {

    private ExampleServiceConfiguration configuration;

    public ExampleHtmlResource(ExampleServiceConfiguration configuration){
        this.configuration = configuration;
    }

    @GET
    @Timed
    @Produces(MediaType.TEXT_HTML)
    public View show() throws JsonProcessingException {
        return getView(configuration);
    }

    private View getView(ExampleServiceConfiguration configuration) throws JsonProcessingException {
        String defaultName = configuration.getDefaultName();
        String versionNumber = configuration.getVersionNumber();

        HashMap<String, String> templateData = new HashMap<String, String>();
        templateData.put("defaultName", defaultName);
        templateData.put("versionNumber", versionNumber);

        return new FreemarkerView("example", templateData);
    }
}
