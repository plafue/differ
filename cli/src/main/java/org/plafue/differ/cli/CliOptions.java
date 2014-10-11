package org.plafue.differ.cli;

import org.apache.commons.cli.*;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CliOptions {

    private static final String WORKING_DIR = System.getProperty("user.dir");
    private static final String ORIGINAL_DIR_SHORT = "d";
    private static final String ORIGINAL_DIR_LONG = "original_dir";
    private static final String OUTPUT_DIR_SHORT = "o";
    private static final String OUTPUT_DIR_LONG = "output-dir";
    private static final String REVISED_DIR_LONG = "revised-dir";
    private static final String REVISED_DIR_SHORT = "r";

    private Path outputDir;
    private Path originalsDir;
    private Path revisedDir;

    private final Options options;

    public CliOptions() throws ParseException {
        options = new Options();
        options.addOption(ORIGINAL_DIR_SHORT, ORIGINAL_DIR_LONG, true, "Location of the original set of files")
                .addOption(OUTPUT_DIR_SHORT, OUTPUT_DIR_LONG, true, "Path to save xhtml files to. Default is working directory")
                .addOption(REVISED_DIR_SHORT, REVISED_DIR_LONG, true, "Location of the revised set of files");
    }

    public CommandLine parseCommandLine(String[] args) throws ParseException {

        try {
            CommandLineParser commandLineParser = new BasicParser();
            CommandLine cmd = commandLineParser.parse(options, args, true);

            this.outputDir    = parseOutPutDirectory(cmd);
            this.originalsDir = parseOriginalsDirectory(cmd);
            this.revisedDir   = parseRevisedDirectory(cmd);

            return cmd;

        } catch (ParseException e) {
            printHelp(options);
            System.exit(1);
            return null;
        }
    }

    private void printHelp(Options options) {
        new HelpFormatter().printHelp("-d ORIGINAL_DIR -r REVISED_DIR [-o OUTPUT_DIR]", options);
    }

    private Path parseOutPutDirectory(CommandLine cmd) {
        if (!cmd.hasOption("o")) {
            return Paths.get(WORKING_DIR);
        }

        return Paths.get(cmd.getOptionValue("o"));
    }

    private Path parseOriginalsDirectory(CommandLine cmd) throws ParseException {
        if (!cmd.hasOption("d")) {
            throw new ParseException("Original revision directory is mandatory");
        }
        return Paths.get(cmd.getOptionValue("d"));
    }

    private Path parseRevisedDirectory(CommandLine cmd) throws ParseException {
        if (!cmd.hasOption("r")) {
            throw new ParseException("New revision directory is mandatory");
        }
        return Paths.get(cmd.getOptionValue("r"));
    }

    public Path getRevisedDir() {
        return revisedDir;
    }

    public Path getOriginalsDir() {
        return originalsDir;
    }

    public Path getOutputDir() {
        return outputDir;
    }
}
