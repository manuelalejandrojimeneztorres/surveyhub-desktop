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
import es.ieselrincon.dam.ppp.surveyhubdesktop.models.Survey;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Manuel Alejandro Jiménez Torres
 */
public class SurveyController {

    private final OnlineSurveySystemView view;
    private final SurveyDAO surveyDAO;
    private DefaultTableModel originalTableModel;
    private static final Logger logger = LoggerFactory.getLogger(SurveyController.class);

    public SurveyController(OnlineSurveySystemView view, SurveyDAO surveyDAO) {
        this.view = view;
        this.surveyDAO = surveyDAO;
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
        DefaultTableModel model = (DefaultTableModel) view.getjTable2().getModel();
        model.setRowCount(0); // Limpiar la tabla antes de rellenarla
        List<Survey> surveyList = surveyDAO.findAll();
        for (Survey survey : surveyList) {
            model.addRow(new Object[]{
                survey.getSurveyId(),
                survey.getSurveyName(),
                survey.getSurveyDescription(),
                survey.getStartDate(),
                survey.getEndDate(),
                survey.getMinResponses(),
                survey.getMaxResponses(),
                survey.getSurveyStatusBySurveyStatusId().getSurveyStatusId()
            });
        }
    }

    // Método para comprobar si se ha seleccionado una fila y rellenar los campos
    public void fillFieldsFromSelectedRow() {
        int selectedRow = view.getjTable2().getSelectedRow();
        if (selectedRow != -1) {
            String surveyName = (String) view.getjTable2().getValueAt(selectedRow, 1);
            String surveyDescription = (String) view.getjTable2().getValueAt(selectedRow, 2);
            String startDate = (String) view.getjTable2().getValueAt(selectedRow, 3);
            String endDate = (String) view.getjTable2().getValueAt(selectedRow, 4);
            Integer minResponses = (Integer) view.getjTable2().getValueAt(selectedRow, 5);
            Integer maxResponses = (Integer) view.getjTable2().getValueAt(selectedRow, 6);
            int surveyStatusId = (int) view.getjTable2().getValueAt(selectedRow, 7);

            view.getjTextField2().setText(surveyName);
            view.getjTextField3().setText(surveyDescription);
            view.getjTextField19().setText(startDate);
            view.getjTextField18().setText(endDate);
            view.getjSpinner4().setValue(minResponses);
            view.getjSpinner5().setValue(maxResponses);
            view.getjSpinner6().setValue(surveyStatusId);
        }
    }

    public void insertSurveyAndUpdateTable() {
        try {
            String surveyName = view.getjTextField2().getText();
            String surveyDescription = view.getjTextField3().getText();
            String startDate = view.getjTextField19().getText();
            String endDate = view.getjTextField18().getText();
            Integer minResponses = (Integer) view.getjSpinner4().getValue();
            Integer maxResponses = (Integer) view.getjSpinner5().getValue();
            int surveyStatusId = (int) view.getjSpinner6().getValue();

            // Buscar el SurveyStatus correspondiente
            SurveyStatus surveyStatus = surveyDAO.findSurveyStatusById(surveyStatusId);
            if (surveyStatus == null) {
                throw new IllegalArgumentException("Invalid Survey Status ID");
            }

            Survey survey = new Survey();
            survey.setSurveyName(surveyName);
            survey.setSurveyDescription(surveyDescription);
            survey.setStartDate(startDate);
            survey.setEndDate(endDate);
            survey.setMinResponses(minResponses);
            survey.setMaxResponses(maxResponses);
            survey.setSurveyStatusBySurveyStatusId(surveyStatus);

            surveyDAO.save(survey);

            fillTable();
            updateCountLabel();
        } catch (Exception e) {
            logger.error("Survey insertion into the database was unsuccessful.", e);
        }
    }

    // Método para actualizar una encuesta existente en la base de datos y actualizar la tabla
    public void updateSurveyAndUpdateTable() {
        int selectedRow = view.getjTable2().getSelectedRow();
        if (selectedRow != -1) {
            int surveyId = (int) view.getjTable2().getValueAt(selectedRow, 0);

            Survey surveyToUpdate = surveyDAO.findById(surveyId);
            if (surveyToUpdate != null) {
                try {
                    String updatedSurveyName = view.getjTextField2().getText();
                    String updatedSurveyDescription = view.getjTextField3().getText();
                    String updatedStartDate = view.getjTextField19().getText();
                    String updatedEndDate = view.getjTextField18().getText();
                    Integer updatedMinResponses = (Integer) view.getjSpinner4().getValue();
                    Integer updatedMaxResponses = (Integer) view.getjSpinner5().getValue();
                    int updatedSurveyStatusId = (int) view.getjSpinner6().getValue();

                    // Buscar el SurveyStatus correspondiente
                    SurveyStatus surveyStatus = surveyDAO.findSurveyStatusById(updatedSurveyStatusId);
                    if (surveyStatus == null) {
                        throw new IllegalArgumentException("Invalid Survey Status ID");
                    }

                    surveyToUpdate.setSurveyName(updatedSurveyName);
                    surveyToUpdate.setSurveyDescription(updatedSurveyDescription);
                    surveyToUpdate.setStartDate(updatedStartDate);
                    surveyToUpdate.setEndDate(updatedEndDate);
                    surveyToUpdate.setMinResponses(updatedMinResponses);
                    surveyToUpdate.setMaxResponses(updatedMaxResponses);
                    surveyToUpdate.setSurveyStatusBySurveyStatusId(surveyStatus);

                    surveyDAO.update(surveyToUpdate);

                    fillTable();
                    updateCountLabel();
                } catch (Exception e) {
                    logger.error("Survey update in the database was unsuccessful.", e);
                }
            }
        }
    }

    // Método para eliminar una encuesta de la base de datos y actualizar la tabla
    public void deleteSurveyAndUpdateTable() {
        int selectedRow = view.getjTable2().getSelectedRow();
        if (selectedRow != -1) {
            int surveyId = (int) view.getjTable2().getValueAt(selectedRow, 0);

            Survey survey = surveyDAO.findById(surveyId);
            if (survey != null) {

                surveyDAO.delete(survey);

                fillTable();
                updateCountLabel();
            }
        }
    }

    // Método para vaciar los campos de la vista
    public void clearFields() {
        view.getjTextField2().setText("");
        view.getjTextField3().setText("");
        view.getjTextField19().setText("");
        view.getjTextField18().setText("");
        view.getjSpinner4().setValue(0);
        view.getjSpinner5().setValue(0);
        view.getjSpinner6().setValue(1);
    }

    public void updateCountLabel() {
        long count = surveyDAO.count();
        JLabel countLabel = view.getjLabel78();
        countLabel.setText("Count: " + count);
    }

    public void saveOriginalTableModel() {
        originalTableModel = (DefaultTableModel) view.getjTable2().getModel();
    }

    // Método para mostrar solo las columnas seleccionadas
    public void showSelectedColumns() {
        String[] selectedColumns = view.getjList2().getSelectedValuesList().toArray(new String[0]);
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
        view.getjTable2().setModel(newTableModel);
        view.getjTable2().revalidate();
        view.getjTable2().repaint();
    }

    // Método para restablecer la tabla mostrando todas las columnas
    public void resetTable() {
        // Restaurar el modelo de tabla original
        view.getjTable2().setModel(originalTableModel);
        view.getjTable2().revalidate();
        view.getjTable2().repaint();
    }

    // Método para generar un informe
    public void generateReport(String tableName, String appName, String reportDate) {
        try {
            // Cargar el archivo jrxml desde el classpath
            InputStream reportStream = getClass().getResourceAsStream("/templates/survey_report_template.jrxml");
            if (reportStream == null) {
                throw new FileNotFoundException("survey_report_template.jrxml file not found on classpath");
            }

            // Compilar el reporte
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Parámetros para el informe
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tableName", tableName);
            parameters.put("appName", appName);
            parameters.put("reportDate", reportDate);

            // Crear el origen de datos a partir de la tabla
            JRTableModelDataSource dataSource = new JRTableModelDataSource(view.getjTable2().getModel());

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
