package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Directory implements DirectoryInterface, ParentInterface {
    private String name; //max 32 chars
    private Date creation;
    private List<Directory> directories;
    private List<File> files;

    public Directory (String name) {
        this.name = name;
        this.creation = new Date();
        directories = new ArrayList<>();
        files = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<Directory> getDirectories() {
        return directories;
    }

    @Override
    public List<File> getFiles() {
        return files;
    }

    @Override
    public Date getCreation() {
        return creation;
    }

    //creation should be immutable

    @Override
    public String toString() {
        return this.getName() + ": \tCreation Date: " + this.getCreation();
    }
    
}
