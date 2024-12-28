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
import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.QuestionDAO;
import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.ResponseDAO;
import es.ieselrincon.dam.ppp.surveyhubdesktop.models.Answer;
import es.ieselrincon.dam.ppp.surveyhubdesktop.models.Question;
import es.ieselrincon.dam.ppp.surveyhubdesktop.models.Response;
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
public class AnswerController {

    private final OnlineSurveySystemView view;
    private final AnswerDAO answerDAO;
    private final ResponseDAO responseDAO;
    private final QuestionDAO questionDAO;
    private DefaultTableModel originalTableModel;

    public AnswerController(OnlineSurveySystemView view, AnswerDAO answerDAO, ResponseDAO responseDAO, QuestionDAO questionDAO) {
        this.view = view;
        this.answerDAO = answerDAO;
        this.responseDAO = responseDAO;
        this.questionDAO = questionDAO;
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
        DefaultTableModel model = (DefaultTableModel) view.getjTable8().getModel();
        model.setRowCount(0); // Limpiar la tabla antes de rellenarla
        List<Answer> answerList = answerDAO.findAll();
        for (Answer answer : answerList) {
            model.addRow(new Object[]{
                answer.getAnswerId(),
                answer.getResponseId(),
                answer.getQuestionId(),
                answer.getAnswer()
            });
        }
    }

    // Método para comprobar si se ha seleccionado una fila y rellenar los campos
    public void fillFieldsFromSelectedRow() {
        int selectedRow = view.getjTable8().getSelectedRow();
        if (selectedRow != -1) {
            Integer responseId = (Integer) view.getjTable8().getValueAt(selectedRow, 1);
            Integer questionId = (Integer) view.getjTable8().getValueAt(selectedRow, 2);
            String answer = (String) view.getjTable8().getValueAt(selectedRow, 3);

            view.getjSpinner15().setValue(responseId);
            view.getjSpinner16().setValue(questionId);
            view.getjTextField9().setText(answer);
        }
    }

    // Método para insertar una encuesta y actualizar la tabla
    public void insertAnswerAndUpdateTable() {
        try {
            Integer responseId = (Integer) view.getjSpinner15().getValue();
            Integer questionId = (Integer) view.getjSpinner16().getValue();
            String answerText = view.getjTextField9().getText();

            // Buscar Response y Question correspondientes
            Response response = responseDAO.findById(responseId);
            if (response == null) {
                throw new IllegalArgumentException("Invalid Response ID");
            }

            Question question = questionDAO.findById(questionId);
            if (question == null) {
                throw new IllegalArgumentException("Invalid Question ID");
            }

            Answer answer = new Answer();
            answer.setResponseByResponseId(response);
            answer.setQuestionByQuestionId(question);
            answer.setAnswer(answerText);

            answerDAO.save(answer);

            fillTable();
            updateCountLabel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar una encuesta existente en la base de datos y actualizar la tabla
    public void updateAnswerAndUpdateTable() {
        int selectedRow = view.getjTable8().getSelectedRow();
        if (selectedRow != -1) {
            try {
                int answerId = (int) view.getjTable8().getValueAt(selectedRow, 0);
                Answer answerToUpdate = answerDAO.findById(answerId);
                if (answerToUpdate != null) {
                    Integer updatedResponseId = (Integer) view.getjSpinner15().getValue();
                    Integer updatedQuestionId = (Integer) view.getjSpinner16().getValue();
                    String updatedAnswerText = view.getjTextField9().getText();

                    // Buscar Response y Question correspondientes
                    Response response = responseDAO.findById(updatedResponseId);
                    if (response == null) {
                        throw new IllegalArgumentException("Invalid Response ID");
                    }

                    Question question = questionDAO.findById(updatedQuestionId);
                    if (question == null) {
                        throw new IllegalArgumentException("Invalid Question ID");
                    }

                    answerToUpdate.setResponseByResponseId(response);
                    answerToUpdate.setQuestionByQuestionId(question);
                    answerToUpdate.setAnswer(updatedAnswerText);

                    answerDAO.update(answerToUpdate);

                    fillTable();
                    updateCountLabel();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Método para eliminar una encuesta de la base de datos y actualizar la tabla
    public void deleteAnswerAndUpdateTable() {
        int selectedRow = view.getjTable8().getSelectedRow();
        if (selectedRow != -1) {
            int answerId = (int) view.getjTable8().getValueAt(selectedRow, 0);

            Answer answer = answerDAO.findById(answerId);
            if (answer != null) {

                answerDAO.delete(answer);

                fillTable();
                updateCountLabel();
            }
        }
    }

    // Método para vaciar los campos de la vista
    public void clearFields() {
        view.getjSpinner15().setValue(1);
        view.getjSpinner16().setValue(1);
        view.getjTextField9().setText("");
    }

    public void updateCountLabel() {
        long count = answerDAO.count();
        JLabel countLabel = view.getjLabel84();
        countLabel.setText("Count: " + count);
    }

    public void saveOriginalTableModel() {
        originalTableModel = (DefaultTableModel) view.getjTable8().getModel();
    }

    // Método para mostrar solo las columnas seleccionadas
    public void showSelectedColumns() {
        String[] selectedColumns = view.getjList8().getSelectedValuesList().toArray(new String[0]);
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
        view.getjTable8().setModel(newTableModel);
        view.getjTable8().revalidate();
        view.getjTable8().repaint();
    }

    // Método para restablecer la tabla mostrando todas las columnas
    public void resetTable() {
        // Restaurar el modelo de tabla original
        view.getjTable8().setModel(originalTableModel);
        view.getjTable8().revalidate();
        view.getjTable8().repaint();
    }

    // Método para generar un informe
    public void generateReport(String tableName, String appName, String reportDate) {
        try {
            // Cargar el archivo jrxml desde el classpath
            InputStream reportStream = getClass().getResourceAsStream("/templates/answer_report_template.jrxml");
            if (reportStream == null) {
                throw new FileNotFoundException("answer_report_template.jrxml file not found on classpath");
            }

            // Compilar el reporte
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Parámetros para el informe
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tableName", tableName);
            parameters.put("appName", appName);
            parameters.put("reportDate", reportDate);

            // Crear el origen de datos a partir de la tabla
            JRTableModelDataSource dataSource = new JRTableModelDataSource(view.getjTable8().getModel());

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
