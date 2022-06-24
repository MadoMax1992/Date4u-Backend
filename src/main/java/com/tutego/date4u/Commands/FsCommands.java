package com.tutego.date4u.Commands;

import com.tutego.date4u.FileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.*;
import org.springframework.util.unit.DataSize;

@ShellComponent
public class FsCommands {
    private final FileSystem fs;


    @Autowired
    public FsCommands( FileSystem fs ) { this.fs = fs; }

    @ShellMethod( "Display required free disk space" )
    public long minimumFreeDiskSpace() {
        return 1_000_000;
    }

    @ShellMethod( "Lowercase string" )
    public String toLowercase( String input ) { return input.toLowerCase(); }

    @ShellMethod("Display free disk sapce")
    public long freeDiskSpace() {
        return DataSize.ofBytes(fs.getFreeDiskSpace()).toGigabytes();
    }
}
