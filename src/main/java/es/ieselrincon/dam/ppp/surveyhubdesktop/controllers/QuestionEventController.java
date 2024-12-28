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

import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.QuestionDAO;
import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.QuestionTypeDAO;
import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.SurveyDAO;
import es.ieselrincon.dam.ppp.surveyhubdesktop.utils.DateUtils;
import es.ieselrincon.dam.ppp.surveyhubdesktop.views.OnlineSurveySystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Manuel Alejandro Jiménez Torres
 */
public class QuestionEventController {

    private final OnlineSurveySystemView view;
    private final QuestionController questionController;

    public QuestionEventController(OnlineSurveySystemView view, QuestionDAO questionDAO, SurveyDAO surveyDAO, QuestionTypeDAO questionTypeDAO) {
        this.view = view;
        this.questionController = new QuestionController(view, questionDAO, surveyDAO, questionTypeDAO);
        initializeEventHandlers();
    }

    private void initializeEventHandlers() {
        // Acción para el botón de añadir nuevo estado
        view.getjButton14().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionController.insertQuestionAndUpdateTable();
            }
        });

        // Acción para el botón de actualizar estado
        view.getjButton15().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionController.updateQuestionAndUpdateTable();
            }
        });

        // Acción para el botón de eliminar estado
        view.getjButton16().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionController.deleteQuestionAndUpdateTable();
            }
        });

        // Acción para el botón de limpiar los campos
        view.getjButton17().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionController.clearFields();
            }
        });

        // Acción para el botón de refrescar la tabla
        view.getjButton49().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionController.fillTable();
                questionController.updateCountLabel();
            }
        });

        // Acción para la selección de una fila en la tabla
        view.getjTable4().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    questionController.fillFieldsFromSelectedRow();
                }
            }
        });

        // Acción para la selección de una fila en la tabla
        view.getjTable4().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                questionController.fillFieldsFromSelectedRow();
            }
        });

        // Agregar listener al campo de texto "busquedaTextField"
        view.getjTextField12().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String textoBusqueda = view.getjTextField12().getText();
                int indiceColumna = view.getjComboBox7().getSelectedIndex();
                TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(view.getjTable4().getModel());
                view.getjTable4().setRowSorter(rowSorter);
                if (textoBusqueda.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda, indiceColumna));
                }
            }
        });

        // Listener para el botón
        view.getjToggleButton4().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getjList4().getSelectedIndices().length == 0) {
                    //JOptionPane.showMessageDialog(OnlineSurveySystemView.this, "Seleccione al menos una columna", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    view.getjToggleButton4().setSelected(false);
                    return;
                }

                if (view.getjToggleButton4().isSelected()) {
                    // Mostrar columnas seleccionadas
                    questionController.showSelectedColumns();
                } else {
                    // Mostrar todas las columnas
                    questionController.resetTable();
                }
            }
        });

        // Listener para el JList
        view.getjList4().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                view.getjToggleButton4().setSelected(false);
            }
        });

        // Agregar listener al menú para generar el informe
        view.getQuestionsTableJMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionController.generateReport("Question", "Online Survey System", DateUtils.getCurrentDateWithTimeZone());
            }
        });

        // Agregar listener al botón para generar el informe
        view.getjButton40().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionController.generateReport("Question", "Online Survey System", DateUtils.getCurrentDateWithTimeZone());
            }
        });
    }
}
