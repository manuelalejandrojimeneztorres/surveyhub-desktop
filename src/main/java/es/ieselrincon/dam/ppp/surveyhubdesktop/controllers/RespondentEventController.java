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
public class RespondentEventController {

    private final OnlineSurveySystemView view;
    private final RespondentController respondentController;

    public RespondentEventController(OnlineSurveySystemView view, RespondentDAO respondentDAO) {
        this.view = view;
        this.respondentController = new RespondentController(view, respondentDAO);
        initializeEventHandlers();
    }

    private void initializeEventHandlers() {
        // Acción para el botón de añadir nuevo estado
        view.getjButton22().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                respondentController.insertRespondentAndUpdateTable();
            }
        });

        // Acción para el botón de actualizar estado
        view.getjButton23().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                respondentController.updateRespondentAndUpdateTable();
            }
        });

        // Acción para el botón de eliminar estado
        view.getjButton24().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                respondentController.deleteRespondentAndUpdateTable();
            }
        });

        // Acción para el botón de limpiar los campos
        view.getjButton25().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                respondentController.clearFields();
            }
        });

        // Acción para el botón de refrescar la tabla
        view.getjButton51().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                respondentController.fillTable();
                respondentController.updateCountLabel();
            }
        });

        // Acción para la selección de una fila en la tabla
        view.getjTable6().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    respondentController.fillFieldsFromSelectedRow();
                }
            }
        });

        // Acción para la selección de una fila en la tabla
        view.getjTable6().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                respondentController.fillFieldsFromSelectedRow();
            }
        });

        // Agregar listener al campo de texto "busquedaTextField"
        view.getjTextField14().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String textoBusqueda = view.getjTextField14().getText();
                int indiceColumna = view.getjComboBox9().getSelectedIndex();
                TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(view.getjTable6().getModel());
                view.getjTable6().setRowSorter(rowSorter);
                if (textoBusqueda.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda, indiceColumna));
                }
            }
        });

        // Listener para el botón
        view.getjToggleButton6().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getjList6().getSelectedIndices().length == 0) {
                    //JOptionPane.showMessageDialog(OnlineSurveySystemView.this, "Seleccione al menos una columna", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    view.getjToggleButton6().setSelected(false);
                    return;
                }

                if (view.getjToggleButton6().isSelected()) {
                    // Mostrar columnas seleccionadas
                    respondentController.showSelectedColumns();
                } else {
                    // Mostrar todas las columnas
                    respondentController.resetTable();
                }
            }
        });

        // Listener para el JList
        view.getjList6().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                view.getjToggleButton6().setSelected(false);
            }
        });

        // Agregar listener al menú para generar el informe
        view.getRespondentsTableJMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                respondentController.generateReport("Respondent", "Online Survey System", DateUtils.getCurrentDateWithTimeZone());
            }
        });

        // Agregar listener al botón para generar el informe
        view.getjButton42().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                respondentController.generateReport("Respondent", "Online Survey System", DateUtils.getCurrentDateWithTimeZone());
            }
        });
    }
}
