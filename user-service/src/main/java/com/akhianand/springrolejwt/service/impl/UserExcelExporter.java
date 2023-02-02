package com.akhianand.springrolejwt.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.akhianand.springrolejwt.model.Role;
import com.akhianand.springrolejwt.model.User;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UserExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<User> listUsers;

    public UserExcelExporter(List<User> listUsers) {
        this.listUsers = listUsers;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "id", style);
        createCell(row, 1, "email", style);
        createCell(row, 2, "password", style);
        createCell(row, 3, "phone", style);
        createCell(row, 4, "firstName", style);
        createCell(row, 5, "lastName", style);
        createCell(row, 6, "business", style);
        createCell(row, 7, "departName", style);
        createCell(row, 8, "plant", style);
        createCell(row, 9, "plantCode", style);
        createCell(row, 10, "roles", style);
        createCell(row, 11, "status", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (User user : listUsers) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, user.getId().longValue(), style);
            createCell(row, columnCount++, user.getEmail(), style);
            createCell(row, columnCount++, user.getPassword(), style);
            createCell(row, columnCount++, user.getPhone(), style);
            createCell(row, columnCount++, user.getFirstName(), style);
            createCell(row, columnCount++, user.getLastName(), style);
            createCell(row, columnCount++, user.getBusiness(), style);
            createCell(row, columnCount++, user.getDepartName(), style);
            createCell(row, columnCount++, user.getPlant(), style);
            createCell(row, columnCount++, user.getPlantCode(), style);
            createCell(row, columnCount++, user.getRoles().parallelStream().map(Role::getName).collect(Collectors.joining(", ")), style);
            createCell(row, columnCount++, user.isStatus(), style);

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}