package ru.dso.nano.v3.analyzer.gui;


import javafx.stage.FileChooser;
import ru.dso.nano.v3.analyzer.AppMain;
import ru.dso.nano.v3.analyzer.resources.Strings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu implements ActionListener {
    private JMenuBar menubar;
    private JMenuItem menuFileOpen;
    private JMenuItem menuFileExit;

    private AppMain app;

    public MainMenu(AppMain app) {
        this.app = app;
        menubar = new JMenuBar();
        JMenu menuFile = new JMenu(Strings.MENU_FILE.getString());
        menuFileOpen = createMenuItem(Strings.MENU_FILE_OPEN.getString());
        menuFileExit = createMenuItem(Strings.MENU_FILE_EXIT.getString());
        menuFile.add(menuFileOpen);
        menuFile.addSeparator();
        menuFile.add(menuFileExit);
        menubar.add(menuFile);
    }

    private JMenuItem createMenuItem(String name) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(this);
        return item;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menuFileOpen) {
            onMenuFileOpen();
            return;
        }
        if(e.getSource() == menuFileExit) {
            app.exit();
        }
    }

    public JMenuBar getMenu() {
        return menubar;
    }

    private void onMenuFileOpen() {
        final JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(app.getPanel());
        if(result == JFileChooser.APPROVE_OPTION) {
            app.open(fc.getSelectedFile());
        }
    }
}
