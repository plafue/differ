package org.plafue.differ.core;

import com.google.common.io.Resources;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;


public class DifferTest {

    @Test
    public void testDiffingFileStructure() throws URISyntaxException, IOException {
        Path original = Paths.get(Resources.getResource("doc-v0.1").getPath());
        Path revised = Paths.get(Resources.getResource("doc-v1.0").getPath());

        Path expectedNew1 = Paths.get("gherkin/newFunctionality.feature");
        Path expectedNew2 = Paths.get("newdir/newfile");
        Path expectedNew3 = Paths.get("gherkin/newSubdir/new");

        Path expectedDeleted   = Paths.get("gherkin/functionalityThatWIllBeRemoved.feature");

        Path expectedModified1 = Paths.get("arcdoc/12_glossary.adoc");
        Path expectedModified2 = Paths.get("arcdoc/05_building_block_view.adoc");
        Path expectedModified3 = Paths.get("gherkin/functionalityA.feature");

        Path newDirectory1 = Paths.get("gherkin/newSubdir/new");
        Path newDirectory2 = Paths.get("newdir/newfile");
        Path newDirectory3 = Paths.get("gherkin/newFunctionality.feature");

        Differ differ = new Differ(original, revised, new Walker());
        DifferResult result = differ.execute();

        assertThat(result.newFiles)
                .containsOnly(expectedNew1,expectedNew2,  expectedNew3);
        assertThat(result.deletedFiles).containsOnly(expectedDeleted);
        assertThat(result.modifiedFiles).containsOnly(expectedModified1, expectedModified2, expectedModified3);
        assertThat(result.newDirectories).containsOnly(newDirectory1, newDirectory2, newDirectory3);
    }
}