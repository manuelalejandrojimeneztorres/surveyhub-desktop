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
import es.ieselrincon.dam.ppp.surveyhubdesktop.models.Respondent;
import es.ieselrincon.dam.ppp.surveyhubdesktop.models.Response;
import es.ieselrincon.dam.ppp.surveyhubdesktop.models.Survey;
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
public class ResponseController {

    private final OnlineSurveySystemView view;
    private final ResponseDAO responseDAO;
    private final SurveyDAO surveyDAO;
    private final RespondentDAO respondentDAO;
    private DefaultTableModel originalTableModel;

    public ResponseController(OnlineSurveySystemView view, ResponseDAO responseDAO, SurveyDAO surveyDAO, RespondentDAO respondentDAO) {
        this.view = view;
        this.responseDAO = responseDAO;
        this.surveyDAO = surveyDAO;
        this.respondentDAO = respondentDAO;
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
        DefaultTableModel model = (DefaultTableModel) view.getjTable7().getModel();
        model.setRowCount(0); // Limpiar la tabla antes de rellenarla
        List<Response> responseList = responseDAO.findAll();
        for (Response response : responseList) {
            model.addRow(new Object[]{
                response.getResponseId(),
                response.getSurveyId(),
                response.getRespondentId(),
                response.getBeginDate(),
                response.getEndDate()
            });
        }
    }

    // Método para comprobar si se ha seleccionado una fila y rellenar los campos
    public void fillFieldsFromSelectedRow() {
        int selectedRow = view.getjTable7().getSelectedRow();
        if (selectedRow != -1) {
            Integer surveyId = (Integer) view.getjTable7().getValueAt(selectedRow, 1);
            Integer respondentId = (Integer) view.getjTable7().getValueAt(selectedRow, 2);
            String beginDate = (String) view.getjTable7().getValueAt(selectedRow, 3);
            String endDate = (String) view.getjTable7().getValueAt(selectedRow, 4);

            view.getjSpinner11().setValue(surveyId);
            view.getjSpinner12().setValue(respondentId);
            view.getjTextField21().setText(beginDate);
            view.getjTextField20().setText(endDate);
        }
    }

    // Método para insertar una encuesta y actualizar la tabla
    public void insertResponseAndUpdateTable() {
        try {
            Integer surveyId = (Integer) view.getjSpinner11().getValue();
            Integer respondentId = (Integer) view.getjSpinner12().getValue();
            String beginDate = view.getjTextField21().getText();
            String endDate = view.getjTextField20().getText();

            // Buscar Survey y Respondent correspondientes
            Survey survey = responseDAO.findSurveyById(surveyId);
            if (survey == null) {
                throw new IllegalArgumentException("Invalid Survey ID");
            }

            Respondent respondent = responseDAO.findRespondentById(respondentId);
            if (respondent == null) {
                throw new IllegalArgumentException("Invalid Respondent ID");
            }

            Response response = new Response();
            response.setSurveyBySurveyId(survey);
            response.setRespondentByRespondentId(respondent);
            response.setBeginDate(beginDate);
            response.setEndDate(endDate);

            responseDAO.save(response);

            fillTable();
            updateCountLabel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar una encuesta existente en la base de datos y actualizar la tabla
    public void updateResponseAndUpdateTable() {
        int selectedRow = view.getjTable7().getSelectedRow();
        if (selectedRow != -1) {
            try {
                int responseId = (int) view.getjTable7().getValueAt(selectedRow, 0);

                Response responseToUpdate = responseDAO.findById(responseId);
                if (responseToUpdate != null) {
                    Integer updatedSurveyId = (Integer) view.getjSpinner11().getValue();
                    Integer updatedRespondentId = (Integer) view.getjSpinner12().getValue();
                    String updatedBeginDate = view.getjTextField21().getText();
                    String updatedEndDate = view.getjTextField20().getText();

                    // Buscar Survey y Respondent correspondientes
                    Survey survey = responseDAO.findSurveyById(updatedSurveyId);
                    if (survey == null) {
                        throw new IllegalArgumentException("Invalid Survey ID");
                    }

                    Respondent respondent = responseDAO.findRespondentById(updatedRespondentId);
                    if (respondent == null) {
                        throw new IllegalArgumentException("Invalid Respondent ID");
                    }

                    responseToUpdate.setSurveyBySurveyId(survey);
                    responseToUpdate.setRespondentByRespondentId(respondent);
                    responseToUpdate.setBeginDate(updatedBeginDate);
                    responseToUpdate.setEndDate(updatedEndDate);

                    responseDAO.update(responseToUpdate);

                    fillTable();
                    updateCountLabel();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Método para eliminar una encuesta de la base de datos y actualizar la tabla
    public void deleteResponseAndUpdateTable() {
        int selectedRow = view.getjTable7().getSelectedRow();
        if (selectedRow != -1) {
            int responseId = (int) view.getjTable7().getValueAt(selectedRow, 0);

            Response response = responseDAO.findById(responseId);
            if (response != null) {

                responseDAO.delete(response);

                fillTable();
                updateCountLabel();
            }
        }
    }

    // Método para vaciar los campos de la vista
    public void clearFields() {
        view.getjSpinner11().setValue(1);
        view.getjSpinner12().setValue(1);
        view.getjTextField21().setText("");
        view.getjTextField20().setText("");
    }

    public void updateCountLabel() {
        long count = responseDAO.count();
        JLabel countLabel = view.getjLabel83();
        countLabel.setText("Count: " + count);
    }

    public void saveOriginalTableModel() {
        originalTableModel = (DefaultTableModel) view.getjTable7().getModel();
    }

    // Método para mostrar solo las columnas seleccionadas
    public void showSelectedColumns() {
        String[] selectedColumns = view.getjList7().getSelectedValuesList().toArray(new String[0]);
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
        view.getjTable7().setModel(newTableModel);
        view.getjTable7().revalidate();
        view.getjTable7().repaint();
    }

    // Método para restablecer la tabla mostrando todas las columnas
    public void resetTable() {
        // Restaurar el modelo de tabla original
        view.getjTable7().setModel(originalTableModel);
        view.getjTable7().revalidate();
        view.getjTable7().repaint();
    }

    // Método para generar un informe
    public void generateReport(String tableName, String appName, String reportDate) {
        try {
            // Cargar el archivo jrxml desde el classpath
            InputStream reportStream = getClass().getResourceAsStream("/templates/response_report_template.jrxml");
            if (reportStream == null) {
                throw new FileNotFoundException("response_report_template.jrxml file not found on classpath");
            }

            // Compilar el reporte
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Parámetros para el informe
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tableName", tableName);
            parameters.put("appName", appName);
            parameters.put("reportDate", reportDate);

            // Crear el origen de datos a partir de la tabla
            JRTableModelDataSource dataSource = new JRTableModelDataSource(view.getjTable7().getModel());

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
