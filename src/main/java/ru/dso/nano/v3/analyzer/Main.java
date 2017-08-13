package ru.dso.nano.v3.analyzer;

import javax.swing.*;
import java.io.File;

public class Main {
    private static AppMain app;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> {
            if(args.length > 0 && new File(args[0]).exists()) {
                app = new AppMain(new File(args[0]));
            } else {
                app = new AppMain();
            }
        });
    }
}
