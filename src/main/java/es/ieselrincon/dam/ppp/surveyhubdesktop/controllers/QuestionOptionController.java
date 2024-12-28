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
import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.QuestionOptionDAO;
import es.ieselrincon.dam.ppp.surveyhubdesktop.models.Question;
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
public class QuestionOptionController {

    private final OnlineSurveySystemView view;
    private final QuestionOptionDAO questionOptionDAO;
    private final QuestionDAO questionDAO;
    private DefaultTableModel originalTableModel;

    public QuestionOptionController(OnlineSurveySystemView view, QuestionOptionDAO questionOptionDAO, QuestionDAO questionDAO) {
        this.view = view;
        this.questionOptionDAO = questionOptionDAO;
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
        DefaultTableModel model = (DefaultTableModel) view.getjTable5().getModel();
        model.setRowCount(0); // Limpiar la tabla antes de rellenarla
        List<QuestionOption> questionOptionList = questionOptionDAO.findAll();
        for (QuestionOption questionOption : questionOptionList) {
            model.addRow(new Object[]{
                questionOption.getQuestionOptionId(),
                questionOption.getQuestionByQuestionId().getQuestionId(),
                questionOption.getQuestionOptionOrder(),
                questionOption.getQuestionOptionValue()
            });
        }
    }

    // Método para comprobar si se ha seleccionado una fila y rellenar los campos
    public void fillFieldsFromSelectedRow() {
        int selectedRow = view.getjTable5().getSelectedRow();
        if (selectedRow != -1) {
            Integer questionId = (Integer) view.getjTable5().getValueAt(selectedRow, 1);
            Integer questionOptionOrder = (Integer) view.getjTable5().getValueAt(selectedRow, 2);
            String questionOptionValue = (String) view.getjTable5().getValueAt(selectedRow, 3);

            view.getjSpinner9().setValue(questionId);
            view.getjSpinner10().setValue(questionOptionOrder);
            view.getjTextField5().setText(questionOptionValue);
        }
    }

    // Método para insertar una encuesta y actualizar la tabla
    public void insertQuestionOptionAndUpdateTable() {
        Integer questionId = (Integer) view.getjSpinner9().getValue();
        Integer questionOptionOrder = (Integer) view.getjSpinner10().getValue();
        String questionOptionValue = view.getjTextField5().getText();

        // Buscar la Question correspondiente
        Question question = questionDAO.findById(questionId);
        if (question == null) {
            throw new IllegalArgumentException("Invalid Question ID");
        }

        QuestionOption questionOption = new QuestionOption();
        questionOption.setQuestionOptionOrder(questionOptionOrder);
        questionOption.setQuestionOptionValue(questionOptionValue);
        questionOption.setQuestionByQuestionId(question);

        questionOptionDAO.save(questionOption);

        fillTable();
        updateCountLabel();
    }

    // Método para actualizar una encuesta existente en la base de datos y actualizar la tabla
    public void updateQuestionOptionAndUpdateTable() {
        int selectedRow = view.getjTable5().getSelectedRow();
        if (selectedRow != -1) {
            int questionOptionId = (int) view.getjTable5().getValueAt(selectedRow, 0);

            QuestionOption questionOptionToUpdate = questionOptionDAO.findById(questionOptionId);
            if (questionOptionToUpdate != null) {
                int updatedQuestionId = (int) view.getjSpinner9().getValue();
                int updatedQuestionOptionOrder = (int) view.getjSpinner10().getValue();
                String updatedQuestionOptionValue = view.getjTextField5().getText();

                // Buscar la Question correspondiente
                Question updatedQuestion = questionDAO.findById(updatedQuestionId);
                if (updatedQuestion == null) {
                    throw new IllegalArgumentException("Invalid Question ID");
                }

                questionOptionToUpdate.setQuestionOptionOrder(updatedQuestionOptionOrder);
                questionOptionToUpdate.setQuestionOptionValue(updatedQuestionOptionValue);
                questionOptionToUpdate.setQuestionByQuestionId(updatedQuestion);

                questionOptionDAO.update(questionOptionToUpdate);

                fillTable();
                updateCountLabel();
            }
        }
    }

    // Método para eliminar una encuesta de la base de datos y actualizar la tabla
    public void deleteQuestionOptionAndUpdateTable() {
        int selectedRow = view.getjTable5().getSelectedRow();
        if (selectedRow != -1) {
            int questionOptionId = (int) view.getjTable5().getValueAt(selectedRow, 0);

            QuestionOption questionOption = questionOptionDAO.findById(questionOptionId);
            if (questionOption != null) {

                questionOptionDAO.delete(questionOption);

                fillTable();
                updateCountLabel();
            }
        }
    }

    // Método para vaciar los campos de la vista
    public void clearFields() {
        view.getjSpinner9().setValue(1);
        view.getjSpinner10().setValue(1);
        view.getjTextField5().setText("");
    }

    public void updateCountLabel() {
        long count = questionOptionDAO.count();
        JLabel countLabel = view.getjLabel81();
        countLabel.setText("Count: " + count);
    }

    public void saveOriginalTableModel() {
        originalTableModel = (DefaultTableModel) view.getjTable5().getModel();
    }

    // Método para mostrar solo las columnas seleccionadas
    public void showSelectedColumns() {
        String[] selectedColumns = view.getjList5().getSelectedValuesList().toArray(new String[0]);
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
        view.getjTable5().setModel(newTableModel);
        view.getjTable5().revalidate();
        view.getjTable5().repaint();
    }

    // Método para restablecer la tabla mostrando todas las columnas
    public void resetTable() {
        // Restaurar el modelo de tabla original
        view.getjTable5().setModel(originalTableModel);
        view.getjTable5().revalidate();
        view.getjTable5().repaint();
    }

    // Método para generar un informe
    public void generateReport(String tableName, String appName, String reportDate) {
        try {
            // Cargar el archivo jrxml desde el classpath
            InputStream reportStream = getClass().getResourceAsStream("/templates/question_option_report_template.jrxml");
            if (reportStream == null) {
                throw new FileNotFoundException("question_option_report_template.jrxml file not found on classpath");
            }

            // Compilar el reporte
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Parámetros para el informe
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tableName", tableName);
            parameters.put("appName", appName);
            parameters.put("reportDate", reportDate);

            // Crear el origen de datos a partir de la tabla
            JRTableModelDataSource dataSource = new JRTableModelDataSource(view.getjTable5().getModel());

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
