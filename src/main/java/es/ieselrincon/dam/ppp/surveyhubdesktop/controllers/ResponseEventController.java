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

import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.RespondentDAO;
import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.ResponseDAO;
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
public class ResponseEventController {

    private final OnlineSurveySystemView view;
    private final ResponseController responseController;

    public ResponseEventController(OnlineSurveySystemView view, ResponseDAO responseDAO, SurveyDAO surveyDAO, RespondentDAO respondentDAO) {
        this.view = view;
        this.responseController = new ResponseController(view, responseDAO, surveyDAO, respondentDAO);
        initializeEventHandlers();
    }

    private void initializeEventHandlers() {
        // Acción para el botón de añadir nuevo estado
        view.getjButton26().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                responseController.insertResponseAndUpdateTable();
            }
        });

        // Acción para el botón de actualizar estado
        view.getjButton27().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                responseController.updateResponseAndUpdateTable();
            }
        });

        // Acción para el botón de eliminar estado
        view.getjButton28().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                responseController.deleteResponseAndUpdateTable();
            }
        });

        // Acción para el botón de limpiar los campos
        view.getjButton29().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                responseController.clearFields();
            }
        });

        // Acción para el botón de refrescar la tabla
        view.getjButton52().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                responseController.fillTable();
                responseController.updateCountLabel();
            }
        });

        // Acción para la selección de una fila en la tabla
        view.getjTable7().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    responseController.fillFieldsFromSelectedRow();
                }
            }
        });

        // Acción para la selección de una fila en la tabla
        view.getjTable7().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                responseController.fillFieldsFromSelectedRow();
            }
        });

        // Agregar listener al campo de texto "busquedaTextField"
        view.getjTextField15().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String textoBusqueda = view.getjTextField15().getText();
                int indiceColumna = view.getjComboBox10().getSelectedIndex();
                TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(view.getjTable7().getModel());
                view.getjTable7().setRowSorter(rowSorter);
                if (textoBusqueda.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda, indiceColumna));
                }
            }
        });

        // Listener para el botón
        view.getjToggleButton7().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getjList7().getSelectedIndices().length == 0) {
                    //JOptionPane.showMessageDialog(OnlineSurveySystemView.this, "Seleccione al menos una columna", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    view.getjToggleButton7().setSelected(false);
                    return;
                }

                if (view.getjToggleButton7().isSelected()) {
                    // Mostrar columnas seleccionadas
                    responseController.showSelectedColumns();
                } else {
                    // Mostrar todas las columnas
                    responseController.resetTable();
                }
            }
        });

        // Listener para el JList
        view.getjList7().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                view.getjToggleButton7().setSelected(false);
            }
        });

        // Agregar listener al menú para generar el informe
        view.getResponsesTableJMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                responseController.generateReport("Response", "Online Survey System", DateUtils.getCurrentDateWithTimeZone());
            }
        });

        // Agregar listener al botón para generar el informe
        view.getjButton43().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                responseController.generateReport("Response", "Online Survey System", DateUtils.getCurrentDateWithTimeZone());
            }
        });
    }
}
