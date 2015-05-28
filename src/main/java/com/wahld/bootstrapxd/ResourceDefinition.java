package com.wahld.bootstrapxd;

import lombok.Getter;
import org.springframework.xd.rest.client.impl.SpringXDTemplate;

/**
 * Created by dwahl on 5/26/15.
 */

    abstract class ResourceDefinition{
        @Getter
        public final String name;
        @Getter
        public final String definition;
        @Getter
        public final boolean deploy;

        public ResourceDefinition (String name, String definition, boolean deploy ){
            this.name = name;
            this.definition = definition;
            this.deploy = deploy;
        }

    public abstract void dispatch(SpringXDTemplate xdTemplate);
}

