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
import es.ieselrincon.dam.ppp.surveyhubdesktop.models.SurveyStatus;
import es.ieselrincon.dam.ppp.surveyhubdesktop.views.OnlineSurveySystemView;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Manuel Alejandro Jiménez Torres
 */
public class SurveyStatusController {

    private final OnlineSurveySystemView view;
    private final SurveyStatusDAO surveyStatusDAO;
    private DefaultTableModel originalTableModel;

    public SurveyStatusController(OnlineSurveySystemView view, SurveyStatusDAO surveyStatusDAO) {
        this.view = view;
        this.surveyStatusDAO = surveyStatusDAO;
        initialize();
    }

    // Método para inicializar el controlador
    private void initialize() {
        fillTable();
        updateCountLabel();
        saveOriginalTableModel();
    }

    // Método para rellenar la tabla
    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) view.getjTable1().getModel();
        model.setRowCount(0); // Limpiar la tabla antes de rellenarla
        List<SurveyStatus> surveyStatusList = surveyStatusDAO.findAll();
        for (SurveyStatus surveyStatus : surveyStatusList) {
            model.addRow(new Object[]{
                surveyStatus.getSurveyStatusId(),
                surveyStatus.getSurveyStatus()
            });
        }
    }

    // Método para comprobar si se ha seleccionado una fila y rellenar el JComboBox editable
    public void fillComboBoxFromSelectedRow() {
        int selectedRow = view.getjTable1().getSelectedRow();
        if (selectedRow != -1) {
            String surveyStatus = (String) view.getjTable1().getValueAt(selectedRow, 1);
            view.getjComboBox1().setSelectedItem(surveyStatus);
        }
    }

    // Método para insertar un nuevo estado en la base de datos y actualizar la tabla
    public void insertSurveyStatusAndUpdateTable() {
        String newStatus = (String) view.getjComboBox1().getSelectedItem();

        SurveyStatus surveyStatus = new SurveyStatus();
        surveyStatus.setSurveyStatus(newStatus);

        surveyStatusDAO.save(surveyStatus);

        fillTable();
        updateCountLabel();
    }

    // Método para actualizar un estado existente en la base de datos y actualizar la tabla
    public void updateSurveyStatusAndUpdateTable() {
        int selectedRow = view.getjTable1().getSelectedRow();
        if (selectedRow != -1) {
            int surveyStatusId = (int) view.getjTable1().getValueAt(selectedRow, 0);

            SurveyStatus surveyStatus = surveyStatusDAO.findById(surveyStatusId);
            if (surveyStatus != null) {
                String updatedStatus = (String) view.getjComboBox1().getSelectedItem();

                surveyStatus.setSurveyStatus(updatedStatus);

                surveyStatusDAO.update(surveyStatus);

                fillTable();
                updateCountLabel();
            }
        }
    }

    // Método para eliminar un estado de la base de datos y actualizar la tabla
    public void deleteSurveyStatusAndUpdateTable() {
        int selectedRow = view.getjTable1().getSelectedRow();
        if (selectedRow != -1) {
            int surveyStatusId = (int) view.getjTable1().getValueAt(selectedRow, 0);

            SurveyStatus surveyStatus = surveyStatusDAO.findById(surveyStatusId);
            if (surveyStatus != null) {

                surveyStatusDAO.delete(surveyStatus);

                fillTable();
                updateCountLabel();
            }
        }
    }

    // Método para vaciar el JComboBox editable
    public void clearComboBox() {
        view.getjComboBox1().getEditor().setItem(null);
    }

    public void updateCountLabel() {
        long count = surveyStatusDAO.count();
        JLabel countLabel = view.getjLabel77();
        countLabel.setText("Count: " + count);
    }

    public void saveOriginalTableModel() {
        originalTableModel = (DefaultTableModel) view.getjTable1().getModel();
    }

    // Método para mostrar solo las columnas seleccionadas
    public void showSelectedColumns() {
        String[] selectedColumns = view.getjList1().getSelectedValuesList().toArray(new String[0]);
        DefaultTableModel newTableModel = new DefaultTableModel();

        // Agregar solo las columnas seleccionadas al nuevo modelo de tabla
        for (String columnName : selectedColumns) {
            int columnIndex = originalTableModel.findColumn(columnName);
            if (columnIndex != -1) {
                Vector<Object> columnData = new Vector<>();
                for (int row = 0; row < originalTableModel.getRowCount(); row++) {
                    columnData.add(originalTableModel.getValueAt(row, columnIndex));
                }
                newTableModel.addColumn(columnName, columnData);
            }
        }

        // Establecer el nuevo modelo en la tabla
        view.getjTable1().setModel(newTableModel);
        view.getjTable1().revalidate();
        view.getjTable1().repaint();
    }

    // Método para restablecer la tabla mostrando todas las columnas
    public void resetTable() {
        // Restaurar el modelo de tabla original
        view.getjTable1().setModel(originalTableModel);
        view.getjTable1().revalidate();
        view.getjTable1().repaint();
    }

    // Método para generar un informe
    public void generateReport(String tableName, String appName, String reportDate) {
        try {
            // Cargar el archivo jrxml desde el classpath
            InputStream reportStream = getClass().getResourceAsStream("/templates/survey_status_report_template.jrxml");
            if (reportStream == null) {
                throw new FileNotFoundException("survey_status_report_template.jrxml file not found on classpath");
            }

            // Compilar el reporte
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Parámetros para el informe
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tableName", tableName);
            parameters.put("appName", appName);
            parameters.put("reportDate", reportDate);

            // Crear el origen de datos a partir de la tabla
            JRTableModelDataSource dataSource = new JRTableModelDataSource(view.getjTable1().getModel());

            // Compilar y visualizar el informe
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
