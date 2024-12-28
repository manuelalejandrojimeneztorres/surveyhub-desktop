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

import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.SurveyStatusDAO;
import es.ieselrincon.dam.ppp.surveyhubdesktop.utils.DateUtils;
import es.ieselrincon.dam.ppp.surveyhubdesktop.views.OnlineSurveySystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Manuel Alejandro Jiménez Torres
 */
public class SurveyStatusEventController {

    private final OnlineSurveySystemView view;
    private final SurveyStatusController surveyStatusController;

    public SurveyStatusEventController(OnlineSurveySystemView view, SurveyStatusDAO surveyStatusDAO) {
        this.view = view;
        this.surveyStatusController = new SurveyStatusController(view, surveyStatusDAO);
        initializeEventHandlers();
    }

    private void initializeEventHandlers() {
        // Acción para el botón de añadir nuevo estado
        view.getjButton2().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                surveyStatusController.insertSurveyStatusAndUpdateTable();
            }
        });

        // Acción para el botón de actualizar estado
        view.getjButton3().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                surveyStatusController.updateSurveyStatusAndUpdateTable();
            }
        });

        // Acción para el botón de eliminar estado
        view.getjButton4().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                surveyStatusController.deleteSurveyStatusAndUpdateTable();
            }
        });

        // Acción para el botón de limpiar el combobox
        view.getjButton9().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                surveyStatusController.clearComboBox();
            }
        });

        // Acción para el botón de refrescar la tabla
        view.getjButton46().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                surveyStatusController.fillTable();
                surveyStatusController.updateCountLabel();
            }
        });

        // Acción para la selección de una fila en la tabla
        view.getjTable1().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    surveyStatusController.fillComboBoxFromSelectedRow();
                }
            }
        });

        // Acción para la selección de una fila en la tabla
        view.getjTable1().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                surveyStatusController.fillComboBoxFromSelectedRow();
            }
        });

        // Agregar listener al campo de texto "busquedaTextField"
        view.getjTextField1().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String textoBusqueda = view.getjTextField1().getText();
                int indiceColumna = view.getjComboBox2().getSelectedIndex();
                TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(view.getjTable1().getModel());
                view.getjTable1().setRowSorter(rowSorter);
                if (textoBusqueda.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda, indiceColumna));
                }
            }
        });

        // Listener para el botón
        view.getjToggleButton1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getjList1().getSelectedIndices().length == 0) {
                    //JOptionPane.showMessageDialog(OnlineSurveySystemView.this, "Seleccione al menos una columna", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    view.getjToggleButton1().setSelected(false);
                    return;
                }

                if (view.getjToggleButton1().isSelected()) {
                    // Mostrar columnas seleccionadas
                    surveyStatusController.showSelectedColumns();
                } else {
                    // Mostrar todas las columnas
                    surveyStatusController.resetTable();
                }
            }
        });

        // Listener para el JList
        view.getjList1().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                view.getjToggleButton1().setSelected(false);
            }
        });

        // Agregar listener al menú para generar el informe
        view.getSurveyStatusesTableJMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                surveyStatusController.generateReport("Survey Status", "Online Survey System", DateUtils.getCurrentDateWithTimeZone());
            }
        });

        // Agregar listener al botón para generar el informe
        view.getjButton1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                surveyStatusController.generateReport("Survey Status", "Online Survey System", DateUtils.getCurrentDateWithTimeZone());
            }
        });
    }
}
