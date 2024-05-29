package com.example.demo.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Helper {
    public static String readFile(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }
}
