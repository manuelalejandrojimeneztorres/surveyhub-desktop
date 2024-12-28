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
import es.ieselrincon.dam.ppp.surveyhubdesktop.models.Answer;
import es.ieselrincon.dam.ppp.surveyhubdesktop.models.AnswerOption;
import es.ieselrincon.dam.ppp.surveyhubdesktop.models.QuestionOption;
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
public class AnswerOptionController {

    private final OnlineSurveySystemView view;
    private final AnswerOptionDAO answerOptionDAO;
    private final AnswerDAO answerDAO;
    private final QuestionOptionDAO questionOptionDAO;
    private DefaultTableModel originalTableModel;

    public AnswerOptionController(OnlineSurveySystemView view, AnswerOptionDAO answerOptionDAO, AnswerDAO answerDAO, QuestionOptionDAO questionOptionDAO) {
        this.view = view;
        this.answerOptionDAO = answerOptionDAO;
        this.answerDAO = answerDAO;
        this.questionOptionDAO = questionOptionDAO;
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
        DefaultTableModel model = (DefaultTableModel) view.getjTable9().getModel();
        model.setRowCount(0); // Limpiar la tabla antes de rellenarla
        List<AnswerOption> answerOptionList = answerOptionDAO.findAll();
        for (AnswerOption answerOption : answerOptionList) {
            model.addRow(new Object[]{
                answerOption.getAnswerOptionId(),
                answerOption.getAnswerId(),
                answerOption.getQuestionOptionId()
            });
        }
    }

    // Método para comprobar si se ha seleccionado una fila y rellenar los campos
    public void fillFieldsFromSelectedRow() {
        int selectedRow = view.getjTable9().getSelectedRow();
        if (selectedRow != -1) {
            Integer answerId = (Integer) view.getjTable9().getValueAt(selectedRow, 1);
            Integer questionOptionId = (Integer) view.getjTable9().getValueAt(selectedRow, 2);

            view.getjSpinner17().setValue(answerId);
            view.getjSpinner18().setValue(questionOptionId);
        }
    }

    // Método para insertar una encuesta y actualizar la tabla
    public void insertAnswerOptionAndUpdateTable() {
        try {
            Integer answerId = (Integer) view.getjSpinner17().getValue();
            Integer questionOptionId = (Integer) view.getjSpinner18().getValue();

            // Buscar Answer y QuestionOption correspondientes
            Answer answer = answerDAO.findById(answerId);
            if (answer == null) {
                throw new IllegalArgumentException("Invalid Answer ID");
            }

            QuestionOption questionOption = questionOptionDAO.findById(questionOptionId);
            if (questionOption == null) {
                throw new IllegalArgumentException("Invalid Question Option ID");
            }

            AnswerOption answerOption = new AnswerOption();
            answerOption.setAnswerByAnswerId(answer);
            answerOption.setQuestionOptionByQuestionOptionId(questionOption);

            answerOptionDAO.save(answerOption);

            fillTable();
            updateCountLabel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar una encuesta existente en la base de datos y actualizar la tabla
    public void updateAnswerOptionAndUpdateTable() {
        int selectedRow = view.getjTable9().getSelectedRow();
        if (selectedRow != -1) {
            try {
                int answerOptionId = (int) view.getjTable9().getValueAt(selectedRow, 0);

                AnswerOption answerOptionToUpdate = answerOptionDAO.findById(answerOptionId);
                if (answerOptionToUpdate != null) {
                    int updatedAnswerId = (int) view.getjSpinner17().getValue();
                    int updatedQuestionOptionId = (int) view.getjSpinner18().getValue();

                    // Buscar Answer y QuestionOption correspondientes
                    Answer updatedAnswer = answerDAO.findById(updatedAnswerId);
                    if (updatedAnswer == null) {
                        throw new IllegalArgumentException("Invalid Answer ID");
                    }

                    QuestionOption updatedQuestionOption = questionOptionDAO.findById(updatedQuestionOptionId);
                    if (updatedQuestionOption == null) {
                        throw new IllegalArgumentException("Invalid Question Option ID");
                    }

                    answerOptionToUpdate.setAnswerByAnswerId(updatedAnswer);
                    answerOptionToUpdate.setQuestionOptionByQuestionOptionId(updatedQuestionOption);

                    answerOptionDAO.update(answerOptionToUpdate);

                    fillTable();
                    updateCountLabel();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Método para eliminar una encuesta de la base de datos y actualizar la tabla
    public void deleteAnswerOptionAndUpdateTable() {
        int selectedRow = view.getjTable9().getSelectedRow();
        if (selectedRow != -1) {
            int answerOptionId = (int) view.getjTable9().getValueAt(selectedRow, 0);

            AnswerOption answerOption = answerOptionDAO.findById(answerOptionId);
            if (answerOption != null) {

                answerOptionDAO.delete(answerOption);

                fillTable();
                updateCountLabel();
            }
        }
    }

    // Método para vaciar los campos de la vista
    public void clearFields() {
        view.getjSpinner17().setValue(1);
        view.getjSpinner18().setValue(1);
    }

    public void updateCountLabel() {
        long count = answerOptionDAO.count();
        JLabel countLabel = view.getjLabel85();
        countLabel.setText("Count: " + count);
    }

    public void saveOriginalTableModel() {
        originalTableModel = (DefaultTableModel) view.getjTable9().getModel();
    }

    // Método para mostrar solo las columnas seleccionadas
    public void showSelectedColumns() {
        String[] selectedColumns = view.getjList9().getSelectedValuesList().toArray(new String[0]);
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
        view.getjTable9().setModel(newTableModel);
        view.getjTable9().revalidate();
        view.getjTable9().repaint();
    }

    // Método para restablecer la tabla mostrando todas las columnas
    public void resetTable() {
        // Restaurar el modelo de tabla original
        view.getjTable9().setModel(originalTableModel);
        view.getjTable9().revalidate();
        view.getjTable9().repaint();
    }

    // Método para generar un informe
    public void generateReport(String tableName, String appName, String reportDate) {
        try {
            // Cargar el archivo jrxml desde el classpath
            InputStream reportStream = getClass().getResourceAsStream("/templates/answer_option_report_template.jrxml");
            if (reportStream == null) {
                throw new FileNotFoundException("answer_option_report_template.jrxml file not found on classpath");
            }

            // Compilar el reporte
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Parámetros para el informe
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tableName", tableName);
            parameters.put("appName", appName);
            parameters.put("reportDate", reportDate);

            // Crear el origen de datos a partir de la tabla
            JRTableModelDataSource dataSource = new JRTableModelDataSource(view.getjTable9().getModel());

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
