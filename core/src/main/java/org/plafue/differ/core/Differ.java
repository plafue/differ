package org.plafue.differ.core;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Differ {

    private final Walker walker;
    private final Path original;
    private final Path revised;

    public Differ(Path original, Path revised, Walker walker) {

        this.walker = walker;
        this.original = original;
        this.revised = revised;
    }

    public DifferResult execute() throws IOException {

        Contents originalContent = walker.walk(original);
        Contents revisedContent = walker.walk(revised);

        Set<Path> newDirectories = Differ.evaluateNewDirs(originalContent, revisedContent);
        Set<Path> newFiles       = Differ.evaluateNewFiles(originalContent, revisedContent);
        Set<Path> deletedFiles   = Differ.evaluateDeletedFiles(originalContent, revisedContent);
        Set<Path> availableFiles = Differ.evaluateAvailableFiles(revisedContent, deletedFiles);
        Set<Path> modifiedFiles = availableFiles.stream()
                .filter(f -> Differ.isModified(f, original, revised))
                .collect(Collectors.toSet());

        return new DifferResult(newFiles,deletedFiles,availableFiles,modifiedFiles,newDirectories);

    }

    public static boolean isModified(Path file, Path originalParent, Path revisionParent) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return !Arrays.equals(digest(originalParent.resolve(file), md),
                    digest(revisionParent.resolve(file), md));
        } catch (NoSuchAlgorithmException | IOException e) {
            return false;
        }
    }

    private static byte[] digest(Path path, MessageDigest md) throws IOException {

        try (InputStream is = Files.newInputStream(path)) {
            new DigestInputStream(is, md);
            byte[] dataBytes = new byte[1024];
            int nread;
            while ((nread = is.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }
        }
        return md.digest();
    }

    static Set<Path> evaluateAvailableFiles(Contents revisedContent, Set<Path> deletedFiles) {
        return removeBFromA(revisedContent.getRelativeFiles(), deletedFiles);
    }

    static Set<Path> evaluateDeletedFiles(Contents originalContent, Contents revisedContent) {
        return removeBFromA(originalContent.getRelativeFiles(), revisedContent.getRelativeFiles());
    }

    static Set<Path> evaluateNewDirs(Contents originalContent, Contents revisedContent) {
        return removeBFromA(revisedContent.getRelativeFiles(), originalContent.getRelativeFiles());
    }

    static Set<Path> evaluateNewFiles(Contents originalContent, Contents revisedContent) {
        return removeBFromA(revisedContent.getRelativeFiles(), originalContent.getRelativeFiles());
    }

    private static Set<Path> removeBFromA(Set<Path> relativeFiles, Set<Path> relativeFiles1) {
        Set<Path> deletedFiles = new HashSet<>();
        deletedFiles.addAll(relativeFiles);
        deletedFiles.removeAll(relativeFiles1);
        return deletedFiles;
    }
}
