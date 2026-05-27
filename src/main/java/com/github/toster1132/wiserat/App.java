package com.github.toster1132.wiserat;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        Reader reader = new Reader(args, System.in);
        Printer.print(reader.read());
    }
}
