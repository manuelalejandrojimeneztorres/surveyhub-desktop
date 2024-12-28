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
import es.ieselrincon.dam.ppp.surveyhubdesktop.models.Respondent;
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
public class RespondentController {

    private final OnlineSurveySystemView view;
    private final RespondentDAO respondentDAO;
    private DefaultTableModel originalTableModel;

    public RespondentController(OnlineSurveySystemView view, RespondentDAO respondentDAO) {
        this.view = view;
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
        DefaultTableModel model = (DefaultTableModel) view.getjTable6().getModel();
        model.setRowCount(0); // Limpiar la tabla antes de rellenarla
        List<Respondent> respondentList = respondentDAO.findAll();
        for (Respondent respondent : respondentList) {
            model.addRow(new Object[]{
                respondent.getRespondentId(),
                respondent.getLoginName(),
                respondent.getRespondentFullName(),
                respondent.getEmailAddress()
            });
        }
    }

    // Método para comprobar si se ha seleccionado una fila y rellenar los campos
    public void fillFieldsFromSelectedRow() {
        int selectedRow = view.getjTable6().getSelectedRow();
        if (selectedRow != -1) {
            String loginName = (String) view.getjTable6().getValueAt(selectedRow, 1);
            String respondentFullName = (String) view.getjTable6().getValueAt(selectedRow, 2);
            String emailAddress = (String) view.getjTable6().getValueAt(selectedRow, 3);

            view.getjTextField6().setText(loginName);
            view.getjTextField7().setText(respondentFullName);
            view.getjTextField8().setText(emailAddress);
        }
    }

    // Método para insertar una encuesta y actualizar la tabla
    public void insertRespondentAndUpdateTable() {
        String loginName = view.getjTextField6().getText();
        String respondentFullName = view.getjTextField7().getText();
        String emailAddress = view.getjTextField8().getText();

        Respondent respondent = new Respondent();
        respondent.setLoginName(loginName);
        respondent.setRespondentFullName(respondentFullName);
        respondent.setEmailAddress(emailAddress);

        respondentDAO.save(respondent);

        fillTable();
        updateCountLabel();
    }

    // Método para actualizar una encuesta existente en la base de datos y actualizar la tabla
    public void updateRespondentAndUpdateTable() {
        int selectedRow = view.getjTable6().getSelectedRow();
        if (selectedRow != -1) {
            int respondentId = (int) view.getjTable6().getValueAt(selectedRow, 0);

            Respondent respondentToUpdate = respondentDAO.findById(respondentId);
            if (respondentToUpdate != null) {
                String updatedLoginName = view.getjTextField6().getText();
                String updatedRespondentFullName = view.getjTextField7().getText();
                String updatedEmailAddress = view.getjTextField8().getText();

                respondentToUpdate.setLoginName(updatedLoginName);
                respondentToUpdate.setRespondentFullName(updatedRespondentFullName);
                respondentToUpdate.setEmailAddress(updatedEmailAddress);

                respondentDAO.update(respondentToUpdate);

                fillTable();
                updateCountLabel();
            }
        }
    }

    // Método para eliminar una encuesta de la base de datos y actualizar la tabla
    public void deleteRespondentAndUpdateTable() {
        int selectedRow = view.getjTable6().getSelectedRow();
        if (selectedRow != -1) {
            int respondentId = (int) view.getjTable6().getValueAt(selectedRow, 0);

            Respondent respondent = respondentDAO.findById(respondentId);
            if (respondent != null) {

                respondentDAO.delete(respondent);

                fillTable();
                updateCountLabel();
            }
        }
    }

    // Método para vaciar los campos de la vista
    public void clearFields() {
        view.getjTextField6().setText("");
        view.getjTextField7().setText("");
        view.getjTextField8().setText("");
    }

    public void updateCountLabel() {
        long count = respondentDAO.count();
        JLabel countLabel = view.getjLabel82();
        countLabel.setText("Count: " + count);
    }

    public void saveOriginalTableModel() {
        originalTableModel = (DefaultTableModel) view.getjTable6().getModel();
    }

    // Método para mostrar solo las columnas seleccionadas
    public void showSelectedColumns() {
        String[] selectedColumns = view.getjList6().getSelectedValuesList().toArray(new String[0]);
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
        view.getjTable6().setModel(newTableModel);
        view.getjTable6().revalidate();
        view.getjTable6().repaint();
    }

    // Método para restablecer la tabla mostrando todas las columnas
    public void resetTable() {
        // Restaurar el modelo de tabla original
        view.getjTable6().setModel(originalTableModel);
        view.getjTable6().revalidate();
        view.getjTable6().repaint();
    }

    // Método para generar un informe
    public void generateReport(String tableName, String appName, String reportDate) {
        try {
            // Cargar el archivo jrxml desde el classpath
            InputStream reportStream = getClass().getResourceAsStream("/templates/respondent_report_template.jrxml");
            if (reportStream == null) {
                throw new FileNotFoundException("respondent_report_template.jrxml file not found on classpath");
            }

            // Compilar el reporte
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Parámetros para el informe
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tableName", tableName);
            parameters.put("appName", appName);
            parameters.put("reportDate", reportDate);

            // Crear el origen de datos a partir de la tabla
            JRTableModelDataSource dataSource = new JRTableModelDataSource(view.getjTable6().getModel());

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
