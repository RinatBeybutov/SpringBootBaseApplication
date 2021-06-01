package com.example.demo.Entity;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class ReaderFile implements IReader{
    @Override
    public String read() {
        String messageFile = "src/main/resources/messageFile.txt";
        try {
            return new String(Files.readAllBytes(Paths.get(messageFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
