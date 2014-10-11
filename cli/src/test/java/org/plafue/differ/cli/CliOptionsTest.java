package org.plafue.differ.cli;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;


public class CliOptionsTest {

    @Test
    public void testOutputDirProvidedIsTakenIntoAccount() throws Exception {
        Path expectedOrigin = Paths.get("/some/origin");
        Path expectedRevision = Paths.get("/some/revision");
        Path expectedOutput = Paths.get("/some/outputdir");
        CliOptions options = new CliOptions();
        options.parseCommandLine(new String[]{"-d", "/some/origin", "-r", "/some/revision", "-o", "/some/outputdir" });
        assertThat((Iterable<Path>) options.getOriginalsDir()).isEqualTo(expectedOrigin);
        assertThat((Iterable<Path>) options.getRevisedDir()).isEqualTo(expectedRevision);
        assertThat((Iterable<Path>) options.getOutputDir()).isEqualTo(expectedOutput);
    }
}