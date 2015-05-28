package com.wahld.bootstrapxd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Setter;
import org.apache.commons.cli.CommandLine;
import org.springframework.xd.rest.client.impl.SpringXDTemplate;
import org.springframework.xd.rest.domain.ModuleDefinitionResource;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dwahl on 5/22/15.
 */
public class Runner{

    CommandLine line;

    @Setter public URI uri;

    List<ResourceDefinition> resourceDefinitions = new ArrayList<ResourceDefinition>();


    public Runner(){}

    public Runner(CommandLine line) {
        this.line = line;
        try {
            this.uri = new URI(line.getOptionValue("xdHost"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        File configFile = new File(line.getOptionValue("configPath"));
        readConfig(configFile);

    }

    public void readConfig(File configFile){

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            Map<String,Map<String, Map<String, Object>>> definitions = mapper.readValue(configFile, Map.class);
            definitions.forEach((k, v) -> {
                        ResourceFactory factory = new ResourceFactory(k);
                        v.forEach((g,s) -> resourceDefinitions.add(factory.getDefinition(g, s)) );
                    }
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void execute(){
        SpringXDTemplate xdTemplate = new SpringXDTemplate(uri);
        xdTemplate.streamOperations().destroyAll();
        xdTemplate.jobOperations().destroyAll();
        resourceDefinitions.forEach((k) -> k.dispatch(xdTemplate));
    }


    private class ResourceFactory{

        String type;

        public ResourceFactory(String type) {

            if (type.contentEquals("streams") || type.contentEquals("jobs")) {
                this.type = type;
            } else {
                throw new Error("UnkownType");
            }
        }

        public ResourceDefinition getDefinition (String name, Map<String,Object>configMap){

            String definition = (String) configMap.get("definition");
            boolean deploy = (boolean) configMap.get("deploy");

            if(type.equals("jobs")){
                return new JobDefinition(name, definition, deploy);
            }
            if(type.equals("streams")){
                return new StreamDefinition(name, definition, deploy);
            }
            if(type.equals("modules")){
                return new ModuleDefinition(name, definition, deploy);
            }
            return null;
        }
    }
}
