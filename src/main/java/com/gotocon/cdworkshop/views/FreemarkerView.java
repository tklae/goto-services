package com.gotocon.cdworkshop.views;

import com.yammer.dropwizard.views.View;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class FreemarkerView extends View {

    private Object templateData;

    public FreemarkerView(String templateName) {
        super(templateName + ".ftl");
    }

    public FreemarkerView(String templateName, Object templateData) {
        super(templateName + ".ftl");
        this.templateData = templateData;
    }

    public Object getTemplateData() {
        return templateData;
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
