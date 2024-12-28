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
public class SurveyEventController {

    private final OnlineSurveySystemView view;
    private final SurveyController surveyController;

    public SurveyEventController(OnlineSurveySystemView view, SurveyDAO surveyDAO) {
        this.view = view;
        this.surveyController = new SurveyController(view, surveyDAO);
        initializeEventHandlers();
    }

    private void initializeEventHandlers() {
        // Acción para el botón de añadir nuevo estado
        view.getjButton5().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                surveyController.insertSurveyAndUpdateTable();
            }
        });

        // Acción para el botón de actualizar estado
        view.getjButton6().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                surveyController.updateSurveyAndUpdateTable();
            }
        });

        // Acción para el botón de eliminar estado
        view.getjButton7().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                surveyController.deleteSurveyAndUpdateTable();
            }
        });

        // Acción para el botón de limpiar los campos
        view.getjButton8().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                surveyController.clearFields();
            }
        });

        // Acción para el botón de refrescar la tabla
        view.getjButton47().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                surveyController.fillTable();
                surveyController.updateCountLabel();
            }
        });

        // Acción para la selección de una fila en la tabla
        view.getjTable2().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    surveyController.fillFieldsFromSelectedRow();
                }
            }
        });

        // Acción para la selección de una fila en la tabla
        view.getjTable2().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                surveyController.fillFieldsFromSelectedRow();
            }
        });

        // Agregar listener al campo de texto "busquedaTextField"
        view.getjTextField10().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String textoBusqueda = view.getjTextField10().getText();
                int indiceColumna = view.getjComboBox5().getSelectedIndex();
                TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(view.getjTable2().getModel());
                view.getjTable2().setRowSorter(rowSorter);
                if (textoBusqueda.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda, indiceColumna));
                }
            }
        });

        // Listener para el botón
        view.getjToggleButton2().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getjList2().getSelectedIndices().length == 0) {
                    //JOptionPane.showMessageDialog(OnlineSurveySystemView.this, "Seleccione al menos una columna", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    view.getjToggleButton2().setSelected(false);
                    return;
                }

                if (view.getjToggleButton2().isSelected()) {
                    // Mostrar columnas seleccionadas
                    surveyController.showSelectedColumns();
                } else {
                    // Mostrar todas las columnas
                    surveyController.resetTable();
                }
            }
        });

        // Listener para el JList
        view.getjList2().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                view.getjToggleButton2().setSelected(false);
            }
        });

        // Agregar listener al menú para generar el informe
        view.getSurveysTableJMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                surveyController.generateReport("Survey", "Online Survey System", DateUtils.getCurrentDateWithTimeZone());
            }
        });

        // Agregar listener al botón para generar el informe
        view.getjButton38().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                surveyController.generateReport("Survey", "Online Survey System", DateUtils.getCurrentDateWithTimeZone());
            }
        });
    }
}
