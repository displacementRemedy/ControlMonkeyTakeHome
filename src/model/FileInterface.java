package model;

public interface FileInterface {

    Integer getSize();

    void setSize(Integer size);

    Directory getParentDirectory();

    void setParentDirectory(Directory parentDirectory);
}