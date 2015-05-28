package com.wahld.bootstrapxd;

import org.springframework.xd.rest.client.impl.SpringXDTemplate;

public class JobDefinition extends ResourceDefinition{
    public JobDefinition(String name, String definition, boolean deploy) {
        super(name, definition, deploy);
    }

    @Override
    public void dispatch(SpringXDTemplate xdTemplate) {
        xdTemplate.jobOperations().createJob(name, definition,deploy);
    }
}
