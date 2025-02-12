/*
 * Copyright 2025 Manuel Alejandro Jiménez Torres.
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

import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.SystemUserDAO;
import es.ieselrincon.dam.ppp.surveyhubdesktop.models.SystemUser;
import es.ieselrincon.dam.ppp.surveyhubdesktop.utils.BCryptUtils;
import es.ieselrincon.dam.ppp.surveyhubdesktop.views.OnlineSurveySystemView;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
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
public class SystemUserController {

    private final OnlineSurveySystemView view;
    private final SystemUserDAO respondentDAO;
    private DefaultTableModel originalTableModel;

    public SystemUserController(OnlineSurveySystemView view, SystemUserDAO respondentDAO) {
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
        List<SystemUser> respondentList = respondentDAO.findAll();
        for (SystemUser respondent : respondentList) {
            model.addRow(new Object[]{
                respondent.getId(),
                respondent.getLoginName(),
                respondent.getFirstName(),
                respondent.getLastName(),
                respondent.getEmailAddress(),
                respondent.getPhoneNumber(),
                respondent.getPasswordHash(),
                respondent.getStatus(),
                respondent.getTokenVersion(),
                respondent.getProfilePicture(),
                respondent.getLastLoginAt(),
                respondent.getLastPasswordChangeAt(),
                respondent.getCreatedAt(),
                respondent.getUpdatedAt()
            });
        }
    }

    // Método para comprobar si se ha seleccionado una fila y rellenar los campos
    public void fillFieldsFromSelectedRow() {
        int selectedRow = view.getjTable6().getSelectedRow();
        if (selectedRow != -1) {
            String loginName = (String) view.getjTable6().getValueAt(selectedRow, 1);
            String firstName = (String) view.getjTable6().getValueAt(selectedRow, 2);
            String lastName = (String) view.getjTable6().getValueAt(selectedRow, 3);
            String emailAddress = (String) view.getjTable6().getValueAt(selectedRow, 4);
            String phoneNumber = (String) view.getjTable6().getValueAt(selectedRow, 5);
            String passwordHash = (String) view.getjTable6().getValueAt(selectedRow, 6);
            String status = (String) view.getjTable6().getValueAt(selectedRow, 7);
            Integer tokenVersion = (Integer) view.getjTable6().getValueAt(selectedRow, 8);
            String profilePicture = (String) view.getjTable6().getValueAt(selectedRow, 9);

            view.getjTextField6().setText(loginName);
            view.getjTextField22().setText(firstName);
            view.getjTextField23().setText(lastName);
            view.getjTextField7().setText(emailAddress);
            view.getjTextField24().setText(phoneNumber);
            view.getjPasswordField1().setText(passwordHash);
            view.getjComboBox13().setSelectedItem(status);
            view.getjSpinner2().setValue(tokenVersion);
            view.getjTextField26().setText(profilePicture);
        }
    }

    // Método para insertar una encuesta y actualizar la tabla
    public void insertRespondentAndUpdateTable() {
        String loginName = view.getjTextField6().getText();
        String firstName = view.getjTextField22().getText();
        String lastName = view.getjTextField23().getText();
        String emailAddress = view.getjTextField7().getText();
        String phoneNumber = view.getjTextField24().getText();
        char[] passwordArray = view.getjPasswordField1().getPassword();
        String status = (String) view.getjComboBox13().getSelectedItem();
        Integer tokenVersion = (Integer) view.getjSpinner2().getValue();
        String profilePicture = view.getjTextField26().getText();

        SystemUser respondent = new SystemUser();
        respondent.setLoginName(loginName);
        respondent.setFirstName(firstName);
        respondent.setLastName(lastName);
        respondent.setEmailAddress(emailAddress);
        respondent.setPhoneNumber(phoneNumber);

        String passwordString = new String(passwordArray);
        String passwordHash = BCryptUtils.hashPassword(passwordString);
        respondent.setPasswordHash(passwordHash);

        respondent.setStatus(status);
        respondent.setTokenVersion(tokenVersion);
        respondent.setProfilePicture(profilePicture);

        respondentDAO.save(respondent);

        fillTable();
        updateCountLabel();
    }

    // Método para actualizar una encuesta existente en la base de datos y actualizar la tabla
    public void updateRespondentAndUpdateTable() {
        int selectedRow = view.getjTable6().getSelectedRow();
        if (selectedRow != -1) {
            int respondentId = (int) view.getjTable6().getValueAt(selectedRow, 0);

            SystemUser respondentToUpdate = respondentDAO.findById(respondentId);
            if (respondentToUpdate != null) {
                String updatedLoginName = view.getjTextField6().getText();
                String updatedFirstName = view.getjTextField22().getText();
                String updatedLastName = view.getjTextField23().getText();
                String updatedEmailAddress = view.getjTextField7().getText();
                String updatedPhoneNumber = view.getjTextField24().getText();
                char[] updatedPasswordArray = view.getjPasswordField1().getPassword();
                String updatedStatus = (String) view.getjComboBox13().getSelectedItem();
                Integer updatedTokenVersion = (Integer) view.getjSpinner2().getValue();
                String updatedProfilePicture = view.getjTextField26().getText();

                respondentToUpdate.setLoginName(updatedLoginName);
                respondentToUpdate.setFirstName(updatedFirstName);
                respondentToUpdate.setLastName(updatedLastName);
                respondentToUpdate.setEmailAddress(updatedEmailAddress);
                respondentToUpdate.setPhoneNumber(updatedPhoneNumber);

                String newPasswordString = new String(updatedPasswordArray);
                String currentPasswordHash = respondentToUpdate.getPasswordHash();
                if (!BCryptUtils.checkPassword(newPasswordString, currentPasswordHash)) {
                    String newPasswordHash = BCryptUtils.hashPassword(newPasswordString);
                    respondentToUpdate.setPasswordHash(newPasswordHash);
                    respondentToUpdate.setLastPasswordChangeAt(new Timestamp(new Date().getTime()));
                }

                respondentToUpdate.setStatus(updatedStatus);
                respondentToUpdate.setTokenVersion(updatedTokenVersion);
                respondentToUpdate.setProfilePicture(updatedProfilePicture);

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

            SystemUser respondent = respondentDAO.findById(respondentId);
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
        view.getjTextField22().setText("");
        view.getjTextField23().setText("");
        view.getjTextField7().setText("");
        view.getjTextField24().setText("");
        view.getjPasswordField1().setText("");
        view.getjComboBox13().setSelectedIndex(0);
        view.getjSpinner2().setValue(1);
        view.getjTextField26().setText("");
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
