package com.wahld.bootstrapxd;

import org.apache.commons.cli.*;
import org.apache.commons.cli.ParseException;
/**
 * Created by dwahl on 5/22/15.
 */
public class BootstrapXD {

    private static Options buildOptions(){
        Options options = new Options();

        Option configPath =
                Option.builder("configPath").argName("configFile").hasArg()
                .desc("use given config yaml")
                .build();

        Option xdHost =
                Option.builder("xdHost").argName("xdHost")
                .hasArg()
                .desc("host address host:ip")
                .build();

        Option help =
                Option.builder("help").argName("help")
                        .hasArg()
                        .desc("print this help")
                        .build();

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
        catch( Exception exp ) {
            // oops, something went wrong
            System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
        }


    }
}
