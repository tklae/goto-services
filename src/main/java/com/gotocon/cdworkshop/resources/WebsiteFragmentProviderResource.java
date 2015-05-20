package com.gotocon.cdworkshop.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gotocon.cdworkshop.configuration.MediaTypes;
import com.gotocon.cdworkshop.model.WebsiteFragmentVO;
import com.yammer.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/provider")
public class WebsiteFragmentProviderResource {

    private final Logger LOGGER = LoggerFactory.getLogger(WebsiteFragmentProviderResource.class);

    @GET
    @Produces(MediaTypes.MEDIA_TYPE_JSON)
    @Consumes(MediaTypes.MEDIA_TYPE_JSON)
    @Timed
    public WebsiteFragmentVO fetch() throws JsonProcessingException {
        LOGGER.debug("Fetching HelloWorld!");
        return new WebsiteFragmentVO("Tim", "This is a very beautiful div", "<div>Hello, World!</div>");
    }
}
