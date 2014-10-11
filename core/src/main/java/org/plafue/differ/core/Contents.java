package org.plafue.differ.core;

import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

public class Contents {
    public final Set<Path> dirs;
    public final Set<Path> files;
    public final Path originalDir;


    Contents(Set<Path> dirs, Set<Path> files, Path originalDir) {
        this.dirs = dirs;
        this.files = files;
        this.originalDir = originalDir;
    }

    public Set<Path> getRelativeFiles(){
        return relativize(files);
    }

    private Set<Path> relativize(Set<Path> path) {

    return path.stream()
            .map(originalDir::relativize)
            .collect(Collectors.toSet());
    }
}
