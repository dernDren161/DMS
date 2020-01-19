package gui;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class PersonFileFilter extends FileFilter {
    @Override
    public boolean accept(File file) {
        String name = file.getName();
        String extension = Utils.getFileExtension(name); // getting the extension after a file "."

        if(extension == null){
            return false;
        }

        if(extension.equals("odt")){
            return true;
        }

        return false;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
