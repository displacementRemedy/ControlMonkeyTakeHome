package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.Directory;
import model.File;
import service.Main;

public class AppTest {

    Directory firstDir;
    Directory firstDirLevelDown;
    Main main;

    @Before
    public void before() {
        main = new Main();
        firstDir = new Directory("First Dir");
        firstDirLevelDown = new Directory("First Dir, Level Down");
        main.getDirectories().add(firstDir);
        main.getDirectories().get(0).getDirectories().add(firstDirLevelDown);
    }
    
    @Test
    public void dirAddTest() {
        
        Directory dir = main.addDir("Nonexistent", "Test Dir");
        assertTrue(main.getDirectories().contains(dir));

        Directory dir2 = main.addDir("Test Dir", "Test Dir, Level Down");
        assertTrue(main.getDirectories().get(1).getDirectories().contains(dir2));
    }

    @Test
    public void fileAddTest() {
        File file = main.addFile("First Dir", "First File", 12345);
        assertTrue(main.getDirectories().get(0).getFiles().contains(file));

        File file2 = main.addFile("First Dir, Level Down", "First File, Level Down", 123456);
        assertTrue(main.getDirectories().get(0).getDirectories().get(0).getFiles().contains(file2));
    }

    @Test
    public void fileSizeTest() {
        main.getDirectories().get(0).getFiles().add(new File("First File", 12345, firstDir));

        assertTrue(main.getFileSize("First File") == 12345);
    }

    @Test
    public void biggestFileTest() {
        main.addFile("First Dir", "First File", 12345);
        File file2 = main.addFile("First Dir, Level Down", "First File, Level Down", 123456);

        assertTrue(main.getBiggestFile().equals(file2.getName()));
    }

    @Test
    public void showFileSystemTest() {
        main.getDirectories().get(0).getFiles().add(new File("First File", 12345, firstDir));
        main.getDirectories().get(0).getFiles().add(new File("First File #2", 12345, firstDir));

        main.getDirectories().get(0).getDirectories().get(0).getFiles().add(new File("First File, Level Down", 123456, firstDirLevelDown));
        main.getDirectories().add(new Directory("Second Dir"));

        String assumedString = "\n" +
                "\n" +
                "First Dir: \tCreation Date: " + main.getDirectories().get(0).getCreation() + "\n" +
                "\tFirst File: \tSize: 12345 | Creation Date: " + main.getDirectories().get(0).getFiles().get(0).getCreation() + "\n" +
                "\tFirst File #2: \tSize: 12345 | Creation Date: " + main.getDirectories().get(0).getFiles().get(1).getCreation() + "\n" +
                "\n" +
                "\tFirst Dir, Level Down: \tCreation Date: " + main.getDirectories().get(0).getDirectories().get(0).getCreation() + "\n" +
                "\t\tFirst File, Level Down: \tSize: 123456 | Creation Date: " + main.getDirectories().get(0).getDirectories().get(0).getFiles().get(0).getCreation() + "\n" +
                "\n" +
                "Second Dir: \tCreation Date: " + main.getDirectories().get(1).getCreation();
        assertTrue(main.showFileSystem().equals(assumedString));
    }

    @Test
    public void deleteDirTest() {
        main.delete("First Dir");
        assertTrue(main.getDirectories().isEmpty());
    }

    @Test
    public void deleteFileTest() {
        main.getDirectories().get(0).getFiles().add(new File("First File", 12345, firstDir));
        main.getDirectories().get(0).getFiles().add(new File("First File #2", 12345, firstDir));

        assertFalse(main.getDirectories().get(0).getFiles().get(0).getName().equals("First File #2"));
        main.delete("First File");
        assertTrue(main.getDirectories().get(0).getFiles().get(0).getName().equals("First File #2"));

    }
}
