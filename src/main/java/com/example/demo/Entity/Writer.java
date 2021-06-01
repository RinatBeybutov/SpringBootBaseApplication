package com.example.demo.Entity;

import org.springframework.stereotype.Component;

@Component
public class Writer {

    public void printString(IReader reader) {
        System.out.println(reader.read());
    }

}
