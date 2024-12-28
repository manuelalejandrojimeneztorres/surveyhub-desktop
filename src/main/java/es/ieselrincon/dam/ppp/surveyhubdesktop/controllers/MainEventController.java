/*
 * Copyright 2024 Manuel Alejandro Jiménez Torres.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.ieselrincon.dam.ppp.surveyhubdesktop.controllers;

import es.ieselrincon.dam.ppp.surveyhubdesktop.utils.UIUtils;
import es.ieselrincon.dam.ppp.surveyhubdesktop.views.OnlineSurveySystemView;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 *
 * @author Manuel Alejandro Jiménez Torres
 */
public class MainEventController implements ActionListener {

    private final OnlineSurveySystemView dashboardView;
    private boolean isDarkMode = false;
    private ItemListener darkModeListener;

    public MainEventController(OnlineSurveySystemView dashboardView) {
        this.dashboardView = dashboardView;
        configureEventHandlers();
        addMenuListeners();
    }

    private void configureEventHandlers() {
        // Configurar eventos
        darkModeListener = e -> handleLookAndFeelChange();
        dashboardView.getTurnOnDarkModeJCheckBox().addItemListener(darkModeListener);
        dashboardView.getTurnOnDarkModeJMenuItem().addActionListener(e -> handleLookAndFeelChange());
        dashboardView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleExitButtonClicked();
            }
        });
        dashboardView.getExitJMenuItem().addActionListener(e -> handleExitButtonClicked());
        dashboardView.getKeyboardShortcutsJMenuItem().addActionListener(e -> handleKeyboardShortcutsMenuItemClicked());
        dashboardView.getAboutJMenuItem().addActionListener(e -> handleAboutMenuItemClicked());
    }

    private void handleExitButtonClicked() {
        final String CLOSE_CONFIRMATION_MESSAGE = "Are you sure you want to exit the application? Any unsaved changes may be lost.";
        final String CLOSE_CONFIRMATION_TITLE = "Exit Confirmation Message";
        final Icon ICON = null;
        final Object[] OPTIONS = null;
        final Object INITIAL_VALUE = null;

        dashboardView.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        int userChoice = JOptionPane.showOptionDialog(dashboardView,
                CLOSE_CONFIRMATION_MESSAGE,
                CLOSE_CONFIRMATION_TITLE,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                ICON, OPTIONS, INITIAL_VALUE);
        if (userChoice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void addMenuListeners() {
        JMenuItem[] menuItems = {
            dashboardView.getSurveyStatusesTabJMenuItem(),
            dashboardView.getSurveysTabJMenuItem(),
            dashboardView.getQuestionTypesTabJMenuItem(),
            dashboardView.getQuestionsTabJMenuItem(),
            dashboardView.getQuestionOptionsTabJMenuItem(),
            dashboardView.getRespondentsTabJMenuItem(),
            dashboardView.getResponsesTabJMenuItem(),
            dashboardView.getAnswersTabJMenuItem(),
            dashboardView.getAnswerOptionsTabJMenuItem()
        };

        for (int i = 0; i < menuItems.length; i++) {
            menuItems[i].setActionCommand(String.valueOf(i));
            menuItems[i].addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int tabIndex = Integer.parseInt(e.getActionCommand());
        dashboardView.getMainViewJTabbedPane().setSelectedIndex(tabIndex);
    }

    private void handleLookAndFeelChange() {
        isDarkMode = !isDarkMode;
        UIUtils.toggleTheme(isDarkMode);
        SwingUtilities.updateComponentTreeUI(dashboardView);
        dashboardView.validate();

        // Actualizar el estado del JCheckBox y el texto del JMenuItem
        dashboardView.getTurnOnDarkModeJCheckBox().removeItemListener(darkModeListener);
        dashboardView.getTurnOnDarkModeJCheckBox().setSelected(isDarkMode);
        dashboardView.getTurnOnDarkModeJCheckBox().addItemListener(darkModeListener);
        dashboardView.getTurnOnDarkModeJMenuItem().setText(isDarkMode ? "Turn off Dark Mode" : "Turn on Dark Mode");

        // Actualizar el atajo de teclado del JMenuItem
        dashboardView.getTurnOnDarkModeJMenuItem().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, isDarkMode ? InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK : InputEvent.CTRL_DOWN_MASK));
    }

    private void handleKeyboardShortcutsMenuItemClicked() {
//        final String MESSAGE = "<html><h1>Keyboard Shortcuts</h1><h2>File</h2><ul><li><b><kbd>Ctrl+Alt+1</kbd></b>: Create Report from Survey Statuses Table</li><li><b><kbd>Ctrl+Alt+2</kbd></b>: Create Report from Surveys Table</li><li><b><kbd>Ctrl+Alt+3</kbd></b>: Create Report from Question Types Table</li><li><b><kbd>Ctrl+Alt+4</kbd></b>: Create Report from Questions Table</li><li><b><kbd>Ctrl+Alt+5</kbd></b>: Create Report from Question Options Table</li><li><b><kbd>Ctrl+Alt+6</kbd></b>: Create Report from Respondents Table</li><li><b><kbd>Ctrl+Alt+7</kbd></b>: Create Report from Responses Table</li><li><b><kbd>Ctrl+Alt+8</kbd></b>: Create Report from Answers Table</li><li><b><kbd>Ctrl+Alt+9</kbd></b>: Create Report from Answer Options Table</li><li><b><kbd>Ctrl+Q</kbd></b>: Exit the Application</li></ul><h2>Navigate</h2><ul><li><b><kbd>Ctrl+1</kbd></b>: Go to Survey Statuses Tab</li><li><b><kbd>Ctrl+2</kbd></b>: Go to Surveys Tab</li><li><b><kbd>Ctrl+3</kbd></b>: Go to Question Types Tab</li><li><b><kbd>Ctrl+4</kbd></b>: Go to Questions Tab</li><li><b><kbd>Ctrl+5</kbd></b>: Go to Question Options Tab</li><li><b><kbd>Ctrl+6</kbd></b>: Go to Respondents Tab</li><li><b><kbd>Ctrl+7</kbd></b>: Go to Responses Tab</li><li><b><kbd>Ctrl+8</kbd></b>: Go to Answers Tab</li><li><b><kbd>Ctrl+9</kbd></b>: Go to Answer Options Tab</li></ul><h2>Window</h2><ul><li><b><kbd>Ctrl+D</kbd></b>: Turn on Dark Mode</li><li><b><kbd>Ctrl+Shift+D</kbd></b>: Turn off Dark Mode</li></ul><h2>Help</h2><ul><li><b><kbd>Ctrl+K</kbd></b>: Show Keyboard Shortcuts</li><li><b><kbd>Ctrl+I</kbd></b>: Show Application Information</li></ul><p><i>This dialog can be closed with the <b><kbd>Esc</kbd></b> key.</i></p><p>Please refer to the application documentation for a complete list of keyboard shortcuts.</p></html>";
        final String MESSAGE = "<html><h1>Keyboard Shortcuts</h1><h2>File</h2><ul><li><b>Ctrl+Alt+1</b>: Create Report from Survey Statuses Table</li><li><b>Ctrl+Alt+2</b>: Create Report from Surveys Table</li><li><b>Ctrl+Alt+3</b>: Create Report from Question Types Table</li><li><b>Ctrl+Alt+4</b>: Create Report from Questions Table</li><li><b>Ctrl+Alt+5</b>: Create Report from Question Options Table</li><li><b>Ctrl+Alt+6</b>: Create Report from Respondents Table</li><li><b>Ctrl+Alt+7</b>: Create Report from Responses Table</li><li><b>Ctrl+Alt+8</b>: Create Report from Answers Table</li><li><b>Ctrl+Alt+9</b>: Create Report from Answer Options Table</li><li><b>Ctrl+Q</b>: Exit the Application</li></ul><h2>Navigate</h2><ul><li><b>Ctrl+1</b>: Go to Survey Statuses Tab</li><li><b>Ctrl+2</b>: Go to Surveys Tab</li><li><b>Ctrl+3</b>: Go to Question Types Tab</li><li><b>Ctrl+4</b>: Go to Questions Tab</li><li><b>Ctrl+5</b>: Go to Question Options Tab</li><li><b>Ctrl+6</b>: Go to Respondents Tab</li><li><b>Ctrl+7</b>: Go to Responses Tab</li><li><b>Ctrl+8</b>: Go to Answers Tab</li><li><b>Ctrl+9</b>: Go to Answer Options Tab</li></ul><h2>Window</h2><ul><li><b>Ctrl+D</b>: Turn on Dark Mode</li><li><b>Ctrl+Shift+D</b>: Turn off Dark Mode</li></ul><h2>Help</h2><ul><li><b>Ctrl+K</b>: Show Keyboard Shortcuts</li><li><b>Ctrl+I</b>: Show Application Information</li></ul><p><i>This dialog can be closed with the <b>Esc</b> key.</i></p><p>Please refer to the application documentation for a complete list of keyboard shortcuts.</p></html>";

        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText(MESSAGE);
        textPane.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setPreferredSize(new Dimension(500, 500));

        JOptionPane.showMessageDialog(dashboardView, scrollPane, "Keyboard Shortcuts", JOptionPane.PLAIN_MESSAGE);
    }

    private void handleAboutMenuItemClicked() {
        final String MESSAGE = """
                           ONLINE SURVEY SYSTEM
                           
                           COPYRIGHT NOTICE AND STATEMENT OF AUTHORSHIP
                           
                           Copyright © 2024 by Manuel Alejandro Jiménez Torres. All rights reserved.
                           
                           This notice serves as a formal declaration of the exclusive rights held by the author, Manuel Alejandro Jiménez Torres, over the software known as the "Online Survey System". This declaration affirms the author's proprietary rights, including but not limited to, rights of ownership, authorship, and protection of intellectual property associated with the aforementioned software. Any unauthorized use, reproduction, distribution, or modification of this software, in whole or in part, without the express written consent of the author, is strictly prohibited.""";
        JOptionPane.showMessageDialog(dashboardView, MESSAGE, "About", JOptionPane.INFORMATION_MESSAGE);
    }
}
