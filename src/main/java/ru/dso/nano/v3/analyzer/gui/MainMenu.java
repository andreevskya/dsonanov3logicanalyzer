package ru.dso.nano.v3.analyzer.gui;

import ru.dso.nano.v3.analyzer.AppMain;
import ru.dso.nano.v3.analyzer.resources.Strings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu implements ActionListener {
    private JMenuBar menubar;
    private JMenuItem menuFileOpen;
    private JMenuItem menuFileExit;
    private JMenuItem menuEditSelectAll;
    private JMenuItem menuEditDelete;

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
        JMenu menuEdit = new JMenu(Strings.MENU_EDIT.getString());
        menuEditDelete = createMenuItem(Strings.MENU_EDIT_DELETE.getString());
        menuEdit.add(menuEditDelete);
        menuEditSelectAll = createMenuItem(Strings.MENU_EDIT_SELECT_ALL.getString());
        menuEdit.addSeparator();
        menuEdit.add(menuEditSelectAll);

        menubar.add(menuFile);
        menubar.add(menuEdit);
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
        if(e.getSource() == menuEditDelete) {
            app.deleteSelected();
        }
        if(e.getSource() == menuEditSelectAll) {
            app.selectAll();
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
