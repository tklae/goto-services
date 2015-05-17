package com.gotocon.cdworkshop.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gotocon.cdworkshop.configuration.MediaTypes;
import com.yammer.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/json")
@Produces(MediaTypes.MEDIA_TYPE_JSON)
@Consumes(MediaTypes.MEDIA_TYPE_JSON)
public class ExampleJsonResource {

    private final Logger LOGGER = LoggerFactory.getLogger(ExampleJsonResource.class);

    @GET
    @Produces(MediaTypes.MEDIA_TYPE_JSON)
    @Consumes(MediaTypes.MEDIA_TYPE_JSON)
    @Timed
    public HelloWorldVO fetch() throws JsonProcessingException {
        LOGGER.debug("Fetching HelloWorld!");
        return new HelloWorldVO();
    }

    static class HelloWorldVO {
        private String text;

        public HelloWorldVO() {
            this.text = "Hello, World!";
        }

        public String getText() {
            return text;
        }

        @Override
        public boolean equals(Object other) {
            return org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals(this, other);
        }

        @Override
        public int hashCode() {
            return org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode(this);
        }

        @Override
        public String toString() {
            return org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString(this);
        }
    }
}
