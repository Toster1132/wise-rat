package com.github.toster1132.wiserat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Printer {
    private Printer() {
    }

    private static String ratArt = """
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠠⣶⣶⣦⡴⢶⣶⣶⣆⠸⠿⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⣻⣿⣷⣾⣿⣿⡿⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣴⣿⣿⣿⣿⣤⠄⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣤⣶⣾⣿⣿⣿⣿⡿⠃⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣴⣾⣿⣿⣿⣿⣿⠿⣿⣿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⢠⣾⣿⣿⣿⣿⣿⣿⣿⠀⣿⣿⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⣾⣿⣟⠛⠻⣿⣿⣿⣿⣦⡈⠉⠛⠻⡆⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣦⡈⢻⣿⣿⣿⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⢿⣿⣿⣿⣿⣧⠈⣿⣿⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⢀⣀⡀⠸⣿⣿⣿⣿⣟⣀⣉⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⣾⡿⠟⠛⠀⠉⠛⠛⠛⠛⠛⠛⠛⠛⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⢿⣧⣀⠀⠀⠀⠀⢀⣀⣠⣤⠶⠶⠶⠶⢶⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠈⠉⠛⠛⠛⠛⠉⠉⠀⠀⠀⠀⠀⣀⡼⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                     """;

    public static void print(String message) {
        String[] lines = Arrays.stream(message.split("\\r?\\n"))
                .flatMap(Printer::wrap)
                .toArray(String[]::new);
        int len = Arrays.stream(lines)
                .mapToInt(String::length)
                .max()
                .orElse(0);
        if (len == 0) {
            lines = new String[] { "Silence is gold." };
            len = lines[0].length();
        }
        String top = "\t\t\t" + "_".repeat(len + 4);
        String bot = "\t\t\t" + "-".repeat(len + 4);

        int maxLen = len;
        lines = Arrays.stream(lines)
                .map(line -> String.format("\t\t\t| %-" + maxLen + "s |", line))
                .toArray(String[]::new);

        lines[0] = top + "\n" + lines[0];
        lines[lines.length - 1] = lines[lines.length - 1] + "\n" + bot + "\n";

        System.out.println(String.join("\n", lines) + ratArt);
    }

    private static Stream<String> wrap(String line) {
        final int MAXLEN = 45;
        if (line.length() <= MAXLEN)
            return Stream.of(line);
        List<String> wrapped = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        for (String word : line.split(" ")) {
            if (current.length() + word.length() + 1 > MAXLEN) {
                wrapped.add(current.toString().trim());
                current = new StringBuilder();
            }
            current.append(word).append(" ");
        }
        if (!current.isEmpty())
            wrapped.add(current.toString().trim());
        return wrapped.stream();
    }
}
