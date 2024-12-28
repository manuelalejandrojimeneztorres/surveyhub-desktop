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
public class QuestionTypeEventController {

    private final OnlineSurveySystemView view;
    private final QuestionTypeController questionTypeController;

    public QuestionTypeEventController(OnlineSurveySystemView view, QuestionTypeDAO questionTypeDAO) {
        this.view = view;
        this.questionTypeController = new QuestionTypeController(view, questionTypeDAO);
        initializeEventHandlers();
    }

    private void initializeEventHandlers() {
        // Acción para el botón de añadir nuevo estado
        view.getjButton10().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionTypeController.insertQuestionTypeAndUpdateTable();
            }
        });

        // Acción para el botón de actualizar estado
        view.getjButton11().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionTypeController.updateQuestionTypeAndUpdateTable();
            }
        });

        // Acción para el botón de eliminar estado
        view.getjButton12().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionTypeController.deleteQuestionTypeAndUpdateTable();
            }
        });

        // Acción para el botón de limpiar los campos
        view.getjButton13().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionTypeController.clearComboBox();
            }
        });

        // Acción para el botón de refrescar la tabla
        view.getjButton48().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionTypeController.fillTable();
                questionTypeController.updateCountLabel();
            }
        });

        // Acción para la selección de una fila en la tabla
        view.getjTable3().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    questionTypeController.fillComboBoxFromSelectedRow();
                }
            }
        });

        // Acción para la selección de una fila en la tabla
        view.getjTable3().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                questionTypeController.fillComboBoxFromSelectedRow();
            }
        });

        // Agregar listener al campo de texto "busquedaTextField"
        view.getjTextField11().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String textoBusqueda = view.getjTextField11().getText();
                int indiceColumna = view.getjComboBox6().getSelectedIndex();
                TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(view.getjTable3().getModel());
                view.getjTable3().setRowSorter(rowSorter);
                if (textoBusqueda.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda, indiceColumna));
                }
            }
        });

        // Listener para el botón
        view.getjToggleButton3().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getjList3().getSelectedIndices().length == 0) {
                    //JOptionPane.showMessageDialog(OnlineSurveySystemView.this, "Seleccione al menos una columna", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    view.getjToggleButton3().setSelected(false);
                    return;
                }

                if (view.getjToggleButton3().isSelected()) {
                    // Mostrar columnas seleccionadas
                    questionTypeController.showSelectedColumns();
                } else {
                    // Mostrar todas las columnas
                    questionTypeController.resetTable();
                }
            }
        });

        // Listener para el JList
        view.getjList3().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                view.getjToggleButton3().setSelected(false);
            }
        });

        // Agregar listener al menú para generar el informe
        view.getQuestionTypesTableJMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionTypeController.generateReport("Question Type", "Online Survey System", DateUtils.getCurrentDateWithTimeZone());
            }
        });

        // Agregar listener al botón para generar el informe
        view.getjButton39().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionTypeController.generateReport("Question Type", "Online Survey System", DateUtils.getCurrentDateWithTimeZone());
            }
        });
    }
}
