package org.plafue.differ.maven;

import freemarker.template.TemplateException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.plafue.differ.core.DiffReporter;
import org.plafue.differ.core.Differ;
import org.plafue.differ.core.DifferResult;
import org.plafue.differ.core.Walker;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

@Mojo(name = "diff", defaultPhase = LifecyclePhase.PROCESS_RESOURCES)
public class DifferMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.build.directory}/differ", property = "outputDir", required = false)
    private File outputDir;

    @Parameter(property = "originalsDir", required = true)
    private File originalDir;

    @Parameter(property = "originalsDir", required = true)
    private File revisedDir;

    public void execute() throws MojoExecutionException {
        final Path original = this.originalDir.toPath();
        final Path revisedDir = this.revisedDir.toPath();
        final Path outputDir = this.outputDir.toPath();

        try {
            Differ differ = new Differ(original, revisedDir, new Walker());
            DifferResult result = differ.execute();
            report(result);
            DiffReporter diffReporter = new DiffReporter();
            createOutputDirIfNeeded();
            diffReporter.createReport(result, original, revisedDir, outputDir);
        } catch (IOException | TemplateException | URISyntaxException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void report(DifferResult result) {

        System.out.println("      New files: " + result.newFiles);
        System.out.println("  Deleted files: " + result.deletedFiles);
        System.out.println("Available files: " + result.availableFiles);
        System.out.println(" Modified files: " + result.modifiedFiles);
        System.out.println("New directories: " + result.newDirectories);
    }


    private void createOutputDirIfNeeded() {
        outputDir.mkdirs();
    }
}
