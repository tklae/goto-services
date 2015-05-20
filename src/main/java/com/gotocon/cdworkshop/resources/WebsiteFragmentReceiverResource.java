package com.gotocon.cdworkshop.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gotocon.cdworkshop.configuration.ExampleServiceConfiguration;
import com.gotocon.cdworkshop.exception.ApiExceptionMapper;
import com.gotocon.cdworkshop.http.ApiClient;
import com.gotocon.cdworkshop.http.ApiHttpResponse;
import com.gotocon.cdworkshop.model.WebsiteFragmentVO;
import com.gotocon.cdworkshop.views.FreemarkerView;
import com.yammer.dropwizard.views.View;
import com.yammer.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Path("/website")
public class WebsiteFragmentReceiverResource {

    private final Logger LOGGER = LoggerFactory.getLogger(WebsiteFragmentReceiverResource.class);
    private final ExampleServiceConfiguration configuration;

    public WebsiteFragmentReceiverResource(ExampleServiceConfiguration configuration) {
        this.configuration = configuration;
    }

    @GET
    @Timed
    @Produces(MediaType.TEXT_HTML)
    public View show() throws IOException {
        HashMap<String, Object> templateData = new HashMap<>();

        final ApiClient apiClient = new ApiClient(ApiClient.MEDIA_TYPE_JSON, new ApiExceptionMapper());
        final String[] clientEndpoints = configuration.getClientEndpoints();
        ArrayList<WebsiteFragmentVO> websiteFragments = new ArrayList<>();

        for (String clientEndpoint : clientEndpoints) {
            final ApiHttpResponse httpResponse = apiClient.get(clientEndpoint);
            if (httpResponse.isOk()) {
                final WebsiteFragmentVO websiteFragment = new ObjectMapper().readValue(httpResponse.getPayload(), WebsiteFragmentVO.class);
                LOGGER.info("Received fragment from client({}): {}", clientEndpoint, websiteFragment);
                websiteFragments.add(websiteFragment);
            }
        }

        templateData.put("fragments", websiteFragments);


        final FreemarkerView view = new FreemarkerView("website", templateData);
        LOGGER.info("View: {}", view);
        return view;
    }
}
