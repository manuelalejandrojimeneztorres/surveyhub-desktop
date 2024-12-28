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

import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.QuestionTypeDAO;
import es.ieselrincon.dam.ppp.surveyhubdesktop.models.QuestionType;
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
public class QuestionTypeController {

    private final OnlineSurveySystemView view;
    private final QuestionTypeDAO questionTypeDAO;
    private DefaultTableModel originalTableModel;

    public QuestionTypeController(OnlineSurveySystemView view, QuestionTypeDAO questionTypeDAO) {
        this.view = view;
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
        DefaultTableModel model = (DefaultTableModel) view.getjTable3().getModel();
        model.setRowCount(0); // Limpiar la tabla antes de rellenarla
        List<QuestionType> questionTypeList = questionTypeDAO.findAll();
        for (QuestionType questionType : questionTypeList) {
            model.addRow(new Object[]{
                questionType.getQuestionTypeId(),
                questionType.getQuestionType()
            });
        }
    }

    // Método para comprobar si se ha seleccionado una fila y rellenar el JComboBox editable
    public void fillComboBoxFromSelectedRow() {
        int selectedRow = view.getjTable3().getSelectedRow();
        if (selectedRow != -1) {
            String questionType = (String) view.getjTable3().getValueAt(selectedRow, 1);
            view.getjComboBox3().setSelectedItem(questionType);
        }
    }

    // Método para insertar un nuevo estado en la base de datos y actualizar la tabla
    public void insertQuestionTypeAndUpdateTable() {
        String newQuestionType = (String) view.getjComboBox3().getSelectedItem();

        QuestionType questionType = new QuestionType();
        questionType.setQuestionType(newQuestionType);

        questionTypeDAO.save(questionType);

        fillTable();
        updateCountLabel();
    }

    // Método para actualizar un estado existente en la base de datos y actualizar la tabla
    public void updateQuestionTypeAndUpdateTable() {
        int selectedRow = view.getjTable3().getSelectedRow();
        if (selectedRow != -1) {
            int questionTypeId = (int) view.getjTable3().getValueAt(selectedRow, 0);

            QuestionType questionType = questionTypeDAO.findById(questionTypeId);
            if (questionType != null) {
                String updatedQuestionType = (String) view.getjComboBox3().getSelectedItem();

                questionType.setQuestionType(updatedQuestionType);

                questionTypeDAO.update(questionType);

                fillTable();
                updateCountLabel();
            }
        }
    }

    // Método para eliminar un estado de la base de datos y actualizar la tabla
    public void deleteQuestionTypeAndUpdateTable() {
        int selectedRow = view.getjTable3().getSelectedRow();
        if (selectedRow != -1) {
            int questionTypeId = (int) view.getjTable3().getValueAt(selectedRow, 0);

            QuestionType questionType = questionTypeDAO.findById(questionTypeId);
            if (questionType != null) {

                questionTypeDAO.delete(questionType);

                fillTable();
                updateCountLabel();
            }
        }
    }

    // Método para vaciar el JComboBox editable
    public void clearComboBox() {
        view.getjComboBox3().getEditor().setItem(null);
    }

    public void updateCountLabel() {
        long count = questionTypeDAO.count();
        JLabel countLabel = view.getjLabel79();
        countLabel.setText("Count: " + count);
    }

    public void saveOriginalTableModel() {
        originalTableModel = (DefaultTableModel) view.getjTable3().getModel();
    }

    // Método para mostrar solo las columnas seleccionadas
    public void showSelectedColumns() {
        String[] selectedColumns = view.getjList3().getSelectedValuesList().toArray(new String[0]);
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
        view.getjTable3().setModel(newTableModel);
        view.getjTable3().revalidate();
        view.getjTable3().repaint();
    }

    // Método para restablecer la tabla mostrando todas las columnas
    public void resetTable() {
        // Restaurar el modelo de tabla original
        view.getjTable3().setModel(originalTableModel);
        view.getjTable3().revalidate();
        view.getjTable3().repaint();
    }

    // Método para generar un informe
    public void generateReport(String tableName, String appName, String reportDate) {
        try {
            // Cargar el archivo jrxml desde el classpath
            InputStream reportStream = getClass().getResourceAsStream("/templates/question_type_report_template.jrxml");
            if (reportStream == null) {
                throw new FileNotFoundException("question_type_report_template.jrxml file not found on classpath");
            }

            // Compilar el reporte
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Parámetros para el informe
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tableName", tableName);
            parameters.put("appName", appName);
            parameters.put("reportDate", reportDate);

            // Crear el origen de datos a partir de la tabla
            JRTableModelDataSource dataSource = new JRTableModelDataSource(view.getjTable3().getModel());

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
