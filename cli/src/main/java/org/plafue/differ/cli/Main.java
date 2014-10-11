package org.plafue.differ.cli;

import freemarker.template.TemplateException;
import org.apache.commons.cli.ParseException;
import org.plafue.differ.core.Differ;
import org.plafue.differ.core.DifferResult;
import org.plafue.differ.core.DiffReporter;
import org.plafue.differ.core.Walker;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException, URISyntaxException, TemplateException {

        CliOptions options = new CliOptions();
        options.parseCommandLine(args);
        Differ differ = new Differ(options.getOriginalsDir(),options.getRevisedDir(),new Walker());
        DifferResult result = differ.execute();
        report(result);
        DiffReporter diffReporter = new DiffReporter();
        diffReporter.createReport(result, options.getOriginalsDir(), options.getRevisedDir(), options.getOutputDir());
    }

    private static void report(DifferResult result) {

        System.out.println("      New files: " + result.newFiles);
        System.out.println("  Deleted files: " + result.deletedFiles);
        System.out.println("Available files: " + result.availableFiles);
        System.out.println(" Modified files: " + result.modifiedFiles);
        System.out.println("New directories: " + result.newDirectories);
    }
}
