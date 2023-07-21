package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Directory;
import model.File;
import model.ParentInterface;

public class Main {

    private List<Directory> directories;
    private File maxFile;

    public Main() {
        this.directories = new ArrayList<>();
        this.maxFile = null;
    }

    public File addFile(String parentDirName, String fileName, int fileSize) {
        File file = new File(fileName, fileSize, null);
        if (maxFile == null || fileSize > maxFile.getSize()) {
            maxFile = file;
        }

        Directory directory = findDirectory(parentDirName, directories);
        if (directory == null) {
            directory = new Directory(parentDirName);
            directories.add(directory);
        }

        directory.getFiles().add(file);
        file.setParentDirectory(directory);

        return file;
    }

    public Directory addDir(String parentDirName, String dirName) {
        Directory parentDirectory = findDirectory(parentDirName, directories);
        Directory dir = new Directory(dirName);

        if (parentDirectory != null) {
            parentDirectory.getDirectories().add(dir);
        } else {
            directories.add(dir);
        }

        return dir;
    }

    public Integer getFileSize(String fileName) {
        File file = findFile(fileName, directories);
        if (file != null) {
            return file.getSize();
        } else return -1;
    }

    public String getBiggestFile() {
        return maxFile.getName();
    }

    public String showFileSystem() {
        StringBuilder str = new StringBuilder();
        directories.stream().forEach(dir -> {
            fileSystemBuilder(dir, str, 0);
        });
        return str.toString();
    }

    public ParentInterface delete(String name) {
        Directory directory = findDirectory(name, directories);
        if (directory != null) {
            directory.getFiles().clear();
            directory.getDirectories().stream().forEach(d -> delete(d));
            directories.remove(directory);

            return directory;
        }

        File file = findFile(name, directories);
        if (file != null) {
            file.getParentDirectory().getFiles().remove(file);

            return file;
        }

        return null;
    }

    //For testing
    //----------------------------------------------------------

    public List<Directory> getDirectories() {
        return directories;
    }

    public File getMaxFile() {
        return maxFile;
    }

    //Private methods
    //----------------------------------------------------------

    private StringBuilder fileSystemBuilder(Directory directory, StringBuilder builder, int tabLevel) {
        builder.append("\n\n");
        tabs(builder, tabLevel);
        builder.append(directory);


        tabLevel++;

        for (File f : directory.getFiles()) {
            builder.append("\n");
            tabs(builder, tabLevel);
            builder.append(f);
        }

        for (Directory d : directory.getDirectories()) {
            fileSystemBuilder(d, builder, tabLevel);
        }
        return builder;

    }

    private void tabs(StringBuilder builder, int tabLevel) {
        for(int i = 0; i < tabLevel; i++){
            builder.append("\t");
        }
    }

    private void delete(Directory dir) {
        dir.getFiles().clear();
        dir.getDirectories().forEach(directory -> delete(directory));
    }

    private Directory findDirectory(String name, List<Directory> directories) {
        for (Directory d : directories) {
            if (d.getName().equals(name))
                return d;
            Directory childDir = findDirectory(name, d.getDirectories());
            if (childDir != null)
                return childDir;
        }
        
        return null;
    }

    private File findFile(String name, List<Directory> directories) {
        for (Directory d : directories) {
            Optional<File> file = d.getFiles().stream().filter(f -> f.getName().equals(name)).findFirst();
            if (file.isPresent())
                return file.get();
            File childFile = findFile(name, d.getDirectories());
            if (childFile != null)
                return childFile;
        }

        return null;
    }
}
