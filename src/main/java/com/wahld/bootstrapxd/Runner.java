package com.wahld.bootstrapxd;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.cli.CommandLine;
import org.springframework.xd.rest.client.impl.SpringXDTemplate;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

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
public class Runner {

    CommandLine line;

    @Setter
    URI uri;
    List<Event> events;

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
            List<Map<String,Object>> eventMap = mapper.readValue(configFile, List.class);
            //List<Event> events = mapper.readValue(configFile, new TypeReference<List<Event>>(){});
            this.events = new ArrayList<>();

            eventMap.forEach(k -> {
                        this.events.add(new Event((String) k.get("name"), (String) k.get("definition"), (Boolean) k.get("deploy")));
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void execute(){
        events.forEach((e) ->
            dispatch(uri, e)
        );
    }

    public void dispatch(URI uri, Event event){
        SpringXDTemplate xdTemplate = new SpringXDTemplate(uri);
        xdTemplate.streamOperations().createStream(event.name, event.definition, event.deploy);
    }


    public class Event{
        @Getter
        public String name;
        @Getter
        public String definition;
        @Getter
        public boolean deploy;

        @JsonCreator
        private Event(@JsonProperty("name") String name,
                      @JsonProperty("definition") String definition,
                      @JsonProperty("deploy") boolean deploy){
            this.name = name;
            this.definition = definition;
            this.deploy =deploy;
        }



        public void print(){
            System.out.println(this.definition);
            System.out.println(this.deploy);
        }
    }
}
