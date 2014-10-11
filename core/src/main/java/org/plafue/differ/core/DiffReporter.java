package org.plafue.differ.core;

import com.google.common.io.Resources;
import freemarker.template.*;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.time.LocalDateTime.now;

public class DiffReporter {

    final Configuration cfg = new Configuration();

    public DiffReporter() throws URISyntaxException, IOException {
        cfg.setObjectWrapper(ObjectWrapper.DEFAULT_WRAPPER);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        cfg.setDirectoryForTemplateLoading(new File(Resources.getResource("templates").toURI()));
        cfg.setIncompatibleImprovements(new Version(2, 3, 0));
    }

    public void createReport(DifferResult result, Path originalsDir, Path revisedDir, Path outputDir) throws IOException, TemplateException {

        Map<Object, Object> root = buildModel(result, originalsDir, revisedDir);
        Writer fileOut = new FileWriter(new File(outputDir.toFile(),"diff.html"));
        cfg.getTemplate("template.ftl").process(root, fileOut);
    }

    private Map<Object, Object> buildModel(DifferResult result, Path originalsDir, Path revisedDir) {
        Map<Object, Object> root = new HashMap<>();

        root.put(DataModelKeys.ORIGINAL_DIR.name(), originalsDir);
        root.put(DataModelKeys.REVISION_DIR.name(), revisedDir);
        root.put(DataModelKeys.DELETED.name(),      result.deletedFiles);
        root.put(DataModelKeys.MODIFIED.name(),     prepareModifiedFIles(result, originalsDir, revisedDir));
        root.put(DataModelKeys.NEW.name(),          result.newFiles);
        root.put(DataModelKeys.TIMESTAMP.name(),    now());

        return root;
    }

    private List<Object> prepareModifiedFIles(DifferResult result, Path originalsDir, Path revisedDir) {
        List<Object> modifiedFiles = new ArrayList<>();

        result.modifiedFiles.stream().forEach(file -> {
            Map<Object,Object> fileMetadata = new HashMap<>();
            fileMetadata.put(DataModelKeys.NAME.name(), file);
            try {
                fileMetadata.put(DataModelKeys.ORIGINAL.name(), prepareSource(originalsDir, file).toString());
                fileMetadata.put(DataModelKeys.REVISION.name(), prepareSource(revisedDir, file).toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            modifiedFiles.add(fileMetadata);
        });
        return modifiedFiles;
    }

    private StringBuilder prepareSource(Path revisedDir, Path f) throws IOException {
        StringBuilder source = new StringBuilder();

        Files.lines(revisedDir.resolve(f))
                .map(StringEscapeUtils.ESCAPE_HTML4::translate)
                .forEach(l -> quoteAndAddNewLine(source, l));
        source.append("\"\"");
        return source;
    }

    private void quoteAndAddNewLine(StringBuilder source, String l) {
        source.append("\"")
                .append(l)
                .append("\\r\\n\" +");
    }

    private enum DataModelKeys {
        DELETED, MODIFIED, NEW, ORIGINAL_DIR, REVISION_DIR, ORIGINAL, REVISION, NAME, TIMESTAMP
    }
}
