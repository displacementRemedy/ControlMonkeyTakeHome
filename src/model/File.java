package model;

import java.util.Date;

public class File implements FileInterface, ParentInterface {
    private String name; //max 32 chars
    private Integer size; //max 2^32
    private Date creation;
    private Directory parentDirectory;

    public File (String name, Integer size, Directory parenDirectory) {
        this.name = name;
        this.size = size;
        this.parentDirectory = parenDirectory;
        this.creation = new Date();
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
    public Integer getSize() {
        return size;
    }

    @Override
    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public Directory getParentDirectory() {
        return parentDirectory;
    }

    @Override
    public void setParentDirectory(Directory parentDirectory) {
        this.parentDirectory = parentDirectory;
    }
    
    @Override
    public Date getCreation() {
        return creation;
    }

    //creation should be immutable

    @Override
    public String toString() {
        return this.getName() + ": \tSize: " + this.getSize() + " | Creation Date: " + this.getCreation();
    }

}
