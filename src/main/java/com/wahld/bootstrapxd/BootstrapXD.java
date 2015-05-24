package com.wahld.bootstrapxd;

import org.apache.commons.cli.*;

/**
 * Created by dwahl on 5/22/15.
 */
public class BootstrapXD {

    private static Options buildOptions(){
        Options options = new Options();

        Option configPath =
                OptionBuilder.withArgName("configFile").hasArg()
                .withDescription("use given config yaml")
                .create("configPath");

        Option xdHost =
                OptionBuilder.withArgName("xdHost")
                .hasArg()
                .withDescription("host address host:ip")
                .create("xdHost");

        Option help =
                OptionBuilder.withArgName("help")
                        .hasArg()
                        .withDescription("print this help")
                        .create("help");

        options.addOption(help);
        options.addOption(configPath);
        options.addOption(xdHost);
        return options;
    }
    public static void printOptions(){
        HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("help", buildOptions());
    }
    public static void main(String[] args){
        CommandLineParser parser = new DefaultParser();
        try {
            // parse the command line arguments
            CommandLine line = parser.parse( buildOptions(), args );
            if((!line.hasOption("configFile") && !line.hasOption("xdHost")) || line.hasOption("help"))
            {
                printOptions();
                return;
            }

            new Runner(line).execute();
        }
        catch( ParseException exp ) {
            // oops, something went wrong
            System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
        }


    }
}
/*
throws URISyntaxException {
        SpringXDTemplate xdTemplate = new SpringXDTemplate(new URI("http://localhost:9393"));
        PagedResources<DetailedContainerResource> containers = xdTemplate.runtimeOperations().listContainers();
        for (DetailedContainerResource container : containers) {
        System.out.println(container);
        }

*/