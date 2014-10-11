package org.plafue.differ.core;

import java.nio.file.Path;
import java.util.Set;

public class DifferResult {
    public final Set<Path> newFiles;
    public final Set<Path> deletedFiles;
    public final Set<Path> availableFiles;
    public final Set<Path> modifiedFiles;
    public final Set<Path> newDirectories;

    public DifferResult(Set<Path> newFiles, Set<Path> deletedFiles, Set<Path> availableFiles, Set<Path> modifiedFiles, Set<Path> newDirectories) {
        this.newFiles = newFiles;
        this.deletedFiles = deletedFiles;
        this.availableFiles = availableFiles;
        this.modifiedFiles = modifiedFiles;
        this.newDirectories = newDirectories;
    }
}
