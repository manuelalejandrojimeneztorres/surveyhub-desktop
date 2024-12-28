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

import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.QuestionDAO;
import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.QuestionTypeDAO;
import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.SurveyDAO;
import es.ieselrincon.dam.ppp.surveyhubdesktop.models.Question;
import es.ieselrincon.dam.ppp.surveyhubdesktop.models.QuestionType;
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
public class QuestionController {

    private final OnlineSurveySystemView view;
    private final QuestionDAO questionDAO;
    private final SurveyDAO surveyDAO;
    private final QuestionTypeDAO questionTypeDAO;
    private DefaultTableModel originalTableModel;

    public QuestionController(OnlineSurveySystemView view, QuestionDAO questionDAO, SurveyDAO surveyDAO, QuestionTypeDAO questionTypeDAO) {
        this.view = view;
        this.questionDAO = questionDAO;
        this.surveyDAO = surveyDAO;
        this.questionTypeDAO = questionTypeDAO;
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
        DefaultTableModel model = (DefaultTableModel) view.getjTable4().getModel();
        model.setRowCount(0); // Limpiar la tabla antes de rellenarla
        List<Question> questionList = questionDAO.findAll();
        for (Question question : questionList) {
            model.addRow(new Object[]{
                question.getQuestionId(),
                question.getSurveyBySurveyId().getSurveyId(),
                question.getQuestionOrder(),
                question.getQuestionTypeByQuestionTypeId().getQuestionTypeId(),
                question.getQuestionText(),
                question.getIsMandatory()
            });
        }
    }

    // Método para comprobar si se ha seleccionado una fila y rellenar los campos
    public void fillFieldsFromSelectedRow() {
        int selectedRow = view.getjTable4().getSelectedRow();
        if (selectedRow != -1) {
            Integer surveyId = (Integer) view.getjTable4().getValueAt(selectedRow, 1);
            Integer questionOrder = (Integer) view.getjTable4().getValueAt(selectedRow, 2);
            Integer questionTypeId = (Integer) view.getjTable4().getValueAt(selectedRow, 3);
            String questionText = (String) view.getjTable4().getValueAt(selectedRow, 4);
            String isMandatory = (String) view.getjTable4().getValueAt(selectedRow, 5);

            view.getjSpinner1().setValue(surveyId);
            view.getjSpinner7().setValue(questionOrder);
            view.getjSpinner8().setValue(questionTypeId);
            view.getjTextField4().setText(questionText);
            view.getjComboBox4().setSelectedItem(isMandatory);
        }
    }

    // Método para insertar una encuesta y actualizar la tabla
    public void insertQuestionAndUpdateTable() {
        try {
            Integer surveyId = (Integer) view.getjSpinner1().getValue();
            Integer questionOrder = (Integer) view.getjSpinner7().getValue();
            Integer questionTypeId = (Integer) view.getjSpinner8().getValue();
            String questionText = view.getjTextField4().getText();
            String isMandatory = (String) view.getjComboBox4().getSelectedItem();

            // Buscar Survey y QuestionType correspondientes
            Survey survey = surveyDAO.findById(surveyId);
            if (survey == null) {
                throw new IllegalArgumentException("Invalid Survey ID");
            }

            QuestionType questionType = questionTypeDAO.findById(questionTypeId);
            if (questionType == null) {
                throw new IllegalArgumentException("Invalid Question Type ID");
            }

            Question question = new Question();
            question.setQuestionOrder(questionOrder);
            question.setQuestionText(questionText);
            question.setIsMandatory(isMandatory);
            question.setSurveyBySurveyId(survey);
            question.setQuestionTypeByQuestionTypeId(questionType);

            questionDAO.save(question);

            fillTable();
            updateCountLabel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar una encuesta existente en la base de datos y actualizar la tabla
    public void updateQuestionAndUpdateTable() {
        int selectedRow = view.getjTable4().getSelectedRow();
        if (selectedRow != -1) {
            try {
                int questionId = (int) view.getjTable4().getValueAt(selectedRow, 0);

                Question questionToUpdate = questionDAO.findById(questionId);
                if (questionToUpdate != null) {
                    int updatedSurveyId = (int) view.getjSpinner1().getValue();
                    int updatedQuestionOrder = (int) view.getjSpinner7().getValue();
                    int updatedQuestionTypeId = (int) view.getjSpinner8().getValue();
                    String updatedQuestionText = view.getjTextField4().getText();
                    String updatedIsMandatory = (String) view.getjComboBox4().getSelectedItem();

                    // Buscar Survey y QuestionType correspondientes
                    Survey survey = surveyDAO.findById(updatedSurveyId);
                    if (survey == null) {
                        throw new IllegalArgumentException("Invalid Survey ID");
                    }

                    QuestionType questionType = questionTypeDAO.findById(updatedQuestionTypeId);
                    if (questionType == null) {
                        throw new IllegalArgumentException("Invalid Question Type ID");
                    }

                    questionToUpdate.setQuestionOrder(updatedQuestionOrder);
                    questionToUpdate.setQuestionText(updatedQuestionText);
                    questionToUpdate.setIsMandatory(updatedIsMandatory);
                    questionToUpdate.setSurveyBySurveyId(survey);
                    questionToUpdate.setQuestionTypeByQuestionTypeId(questionType);

                    questionDAO.update(questionToUpdate);

                    fillTable();
                    updateCountLabel();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Método para eliminar una encuesta de la base de datos y actualizar la tabla
    public void deleteQuestionAndUpdateTable() {
        int selectedRow = view.getjTable4().getSelectedRow();
        if (selectedRow != -1) {
            int questionId = (int) view.getjTable4().getValueAt(selectedRow, 0);

            Question question = questionDAO.findById(questionId);
            if (question != null) {

                questionDAO.delete(question);

                fillTable();
                updateCountLabel();
            }
        }
    }

    // Método para vaciar los campos de la vista
    public void clearFields() {
        view.getjSpinner1().setValue(1);
        view.getjSpinner7().setValue(1);
        view.getjSpinner8().setValue(1);
        view.getjTextField4().setText("");
        view.getjComboBox4().setSelectedIndex(0);
    }

    public void updateCountLabel() {
        long count = questionDAO.count();
        JLabel countLabel = view.getjLabel80();
        countLabel.setText("Count: " + count);
    }

    public void saveOriginalTableModel() {
        originalTableModel = (DefaultTableModel) view.getjTable4().getModel();
    }

    // Método para mostrar solo las columnas seleccionadas
    public void showSelectedColumns() {
        String[] selectedColumns = view.getjList4().getSelectedValuesList().toArray(new String[0]);
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
        view.getjTable4().setModel(newTableModel);
        view.getjTable4().revalidate();
        view.getjTable4().repaint();
    }

    // Método para restablecer la tabla mostrando todas las columnas
    public void resetTable() {
        // Restaurar el modelo de tabla original
        view.getjTable4().setModel(originalTableModel);
        view.getjTable4().revalidate();
        view.getjTable4().repaint();
    }

    // Método para generar un informe
    public void generateReport(String tableName, String appName, String reportDate) {
        try {
            // Cargar el archivo jrxml desde el classpath
            InputStream reportStream = getClass().getResourceAsStream("/templates/question_report_template.jrxml");
            if (reportStream == null) {
                throw new FileNotFoundException("question_report_template.jrxml file not found on classpath");
            }

            // Compilar el reporte
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Parámetros para el informe
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tableName", tableName);
            parameters.put("appName", appName);
            parameters.put("reportDate", reportDate);

            // Crear el origen de datos a partir de la tabla
            JRTableModelDataSource dataSource = new JRTableModelDataSource(view.getjTable4().getModel());

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
