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

import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.AnswerDAO;
import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.AnswerOptionDAO;
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
public class AnswerOptionEventController {

    private final OnlineSurveySystemView view;
    private final AnswerOptionController answerOptionController;

    public AnswerOptionEventController(OnlineSurveySystemView view, AnswerOptionDAO answerOptionDAO, AnswerDAO answerDAO, QuestionOptionDAO questionOptionDAO) {
        this.view = view;
        this.answerOptionController = new AnswerOptionController(view, answerOptionDAO, answerDAO, questionOptionDAO);
        initializeEventHandlers();
    }

    private void initializeEventHandlers() {
        // Acción para el botón de añadir nuevo estado
        view.getjButton34().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerOptionController.insertAnswerOptionAndUpdateTable();
            }
        });

        // Acción para el botón de actualizar estado
        view.getjButton35().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerOptionController.updateAnswerOptionAndUpdateTable();
            }
        });

        // Acción para el botón de eliminar estado
        view.getjButton36().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerOptionController.deleteAnswerOptionAndUpdateTable();
            }
        });

        // Acción para el botón de limpiar los campos
        view.getjButton37().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerOptionController.clearFields();
            }
        });

        // Acción para el botón de refrescar la tabla
        view.getjButton54().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerOptionController.fillTable();
                answerOptionController.updateCountLabel();
            }
        });

        // Acción para la selección de una fila en la tabla
        view.getjTable9().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    answerOptionController.fillFieldsFromSelectedRow();
                }
            }
        });

        // Acción para la selección de una fila en la tabla
        view.getjTable9().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                answerOptionController.fillFieldsFromSelectedRow();
            }
        });

        // Agregar listener al campo de texto "busquedaTextField"
        view.getjTextField17().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String textoBusqueda = view.getjTextField17().getText();
                int indiceColumna = view.getjComboBox12().getSelectedIndex();
                TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(view.getjTable9().getModel());
                view.getjTable9().setRowSorter(rowSorter);
                if (textoBusqueda.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda, indiceColumna));
                }
            }
        });

        // Listener para el botón
        view.getjToggleButton9().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getjList9().getSelectedIndices().length == 0) {
                    //JOptionPane.showMessageDialog(OnlineSurveySystemView.this, "Seleccione al menos una columna", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    view.getjToggleButton9().setSelected(false);
                    return;
                }

                if (view.getjToggleButton9().isSelected()) {
                    // Mostrar columnas seleccionadas
                    answerOptionController.showSelectedColumns();
                } else {
                    // Mostrar todas las columnas
                    answerOptionController.resetTable();
                }
            }
        });

        // Listener para el JList
        view.getjList9().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                view.getjToggleButton9().setSelected(false);
            }
        });

        // Agregar listener al menú para generar el informe
        view.getAnswerOptionsTableJMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerOptionController.generateReport("Answer Option", "Online Survey System", DateUtils.getCurrentDateWithTimeZone());
            }
        });

        // Agregar listener al botón para generar el informe
        view.getjButton45().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerOptionController.generateReport("Answer Option", "Online Survey System", DateUtils.getCurrentDateWithTimeZone());
            }
        });
    }
}
