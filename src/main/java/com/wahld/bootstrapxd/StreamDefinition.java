package com.wahld.bootstrapxd;

import org.springframework.web.client.RestOperations;
import org.springframework.xd.rest.client.SpringXDOperations;
import org.springframework.xd.rest.client.impl.SpringXDTemplate;

public class StreamDefinition extends ResourceDefinition{
    public StreamDefinition(String name, String definition, boolean deploy) {
        super(name, definition, deploy);
    }

    @Override
    public void dispatch(SpringXDTemplate xdTemplate) {
        xdTemplate.streamOperations().createStream(name, definition, deploy);
    }
}
