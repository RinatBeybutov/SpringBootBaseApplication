package com.example.demo.Entity;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ReaderConsole implements IReader{
    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
