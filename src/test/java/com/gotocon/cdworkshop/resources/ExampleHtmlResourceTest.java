package com.gotocon.cdworkshop.resources;

import com.gotocon.cdworkshop.configuration.ExampleServiceConfiguration;
import com.gotocon.cdworkshop.views.FreemarkerView;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExampleHtmlResourceTest {

    @Test
    public void shouldReturnViewWithCorrectTemplateData() throws Exception {
        // Given
        ExampleServiceConfiguration serviceConfiguration = mock(ExampleServiceConfiguration.class);
        when(serviceConfiguration.getDefaultName()).thenReturn("Robert");
        when(serviceConfiguration.getVersionNumber()).thenReturn("20.2");

        ExampleHtmlResource exampleHtmlResource = new ExampleHtmlResource(serviceConfiguration);

        // when
        final FreemarkerView actualView = (FreemarkerView)exampleHtmlResource.show();

        // then
        assertThat((String)((Map)actualView.getTemplateData()).get("defaultName"), is("Robert"));
        assertThat((String)((Map)actualView.getTemplateData()).get("versionNumber"), is("20.2"));
    }

}