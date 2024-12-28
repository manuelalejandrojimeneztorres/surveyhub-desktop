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
import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.QuestionOptionDAO;
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
public class QuestionOptionEventController {

    private final OnlineSurveySystemView view;
    private final QuestionOptionController questionOptionController;

    public QuestionOptionEventController(OnlineSurveySystemView view, QuestionOptionDAO questionOptionDAO, QuestionDAO questionDAO) {
        this.view = view;
        this.questionOptionController = new QuestionOptionController(view, questionOptionDAO, questionDAO);
        initializeEventHandlers();
    }

    private void initializeEventHandlers() {
        // Acción para el botón de añadir nuevo estado
        view.getjButton18().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionOptionController.insertQuestionOptionAndUpdateTable();
            }
        });

        // Acción para el botón de actualizar estado
        view.getjButton19().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionOptionController.updateQuestionOptionAndUpdateTable();
            }
        });

        // Acción para el botón de eliminar estado
        view.getjButton20().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionOptionController.deleteQuestionOptionAndUpdateTable();
            }
        });

        // Acción para el botón de limpiar los campos
        view.getjButton21().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionOptionController.clearFields();
            }
        });

        // Acción para el botón de refrescar la tabla
        view.getjButton50().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionOptionController.fillTable();
                questionOptionController.updateCountLabel();
            }
        });

        // Acción para la selección de una fila en la tabla
        view.getjTable5().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    questionOptionController.fillFieldsFromSelectedRow();
                }
            }
        });

        // Acción para la selección de una fila en la tabla
        view.getjTable5().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                questionOptionController.fillFieldsFromSelectedRow();
            }
        });

        // Agregar listener al campo de texto "busquedaTextField"
        view.getjTextField13().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String textoBusqueda = view.getjTextField13().getText();
                int indiceColumna = view.getjComboBox8().getSelectedIndex();
                TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(view.getjTable5().getModel());
                view.getjTable5().setRowSorter(rowSorter);
                if (textoBusqueda.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda, indiceColumna));
                }
            }
        });

        // Listener para el botón
        view.getjToggleButton5().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getjList5().getSelectedIndices().length == 0) {
                    //JOptionPane.showMessageDialog(OnlineSurveySystemView.this, "Seleccione al menos una columna", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    view.getjToggleButton5().setSelected(false);
                    return;
                }

                if (view.getjToggleButton5().isSelected()) {
                    // Mostrar columnas seleccionadas
                    questionOptionController.showSelectedColumns();
                } else {
                    // Mostrar todas las columnas
                    questionOptionController.resetTable();
                }
            }
        });

        // Listener para el JList
        view.getjList5().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                view.getjToggleButton5().setSelected(false);
            }
        });

        // Agregar listener al menú para generar el informe
        view.getQuestionOptionsTableJMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionOptionController.generateReport("Question Option", "Online Survey System", DateUtils.getCurrentDateWithTimeZone());
            }
        });

        // Agregar listener al botón para generar el informe
        view.getjButton41().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionOptionController.generateReport("Question Option", "Online Survey System", DateUtils.getCurrentDateWithTimeZone());
            }
        });
    }
}
