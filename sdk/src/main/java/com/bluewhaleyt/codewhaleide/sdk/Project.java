package com.bluewhaleyt.codewhaleide.sdk;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.List;

public interface Project {

    @NonNull
    String getName();

    @NonNull
    File getDirectory();

    @NonNull
    List<File> getFiles();

}
