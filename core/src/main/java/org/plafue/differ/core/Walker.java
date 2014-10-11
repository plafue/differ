package org.plafue.differ.core;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

public class Walker {

    public Contents walk(final Path dir) throws IOException {

        final Set<Path> dirs = new HashSet<>();
        final Set<Path> files = new HashSet<>();

        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                files.add(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                dirs.add(dir);
                return super.preVisitDirectory(dir, attrs);
            }
        });
        return new Contents(dirs,files, dir);

    }

}
