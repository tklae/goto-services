package com.gotocon.cdworkshop.resources;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

public class ExampleJsonResourceTest {

    @Test
    public void shouldReturnHelloWorldVO() throws Exception {
        // Given
        ExampleJsonResource exampleJsonResource = new ExampleJsonResource();

        // when
        final ExampleJsonResource.HelloWorldVO result = exampleJsonResource.fetch();

        // then
        assertThat(result, is(equalTo(new ExampleJsonResource.HelloWorldVO())));
    }

}