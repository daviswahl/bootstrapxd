package com.wahld.bootstrapxd;

import org.springframework.web.client.RestTemplate;
import org.springframework.xd.rest.client.impl.SpringXDTemplate;

/**
 * Created by dwahl on 5/26/15.
 */
public class ModuleDefinition extends ResourceDefinition {
    public ModuleDefinition(String name, String definition, boolean deploy) {
        super(name, definition, deploy);
    }

    @Override
    public void dispatch(SpringXDTemplate xdTemplate) {
        RestTemplate t = new RestTemplate()
    }
}
