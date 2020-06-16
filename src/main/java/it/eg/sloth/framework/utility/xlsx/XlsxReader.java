package it.eg.sloth.framework.utility.xlsx;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2020 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Enrico Grillini
 */
public class XlsxReader {
    private XlsxReader() {
        // NOP
    }

    private static String[] getNames(Row headerRow, int minCell, int maxCell) {
        String[] result = new String[maxCell - minCell];

        for (int i = 0; i < result.length; i++) {
            int j = (short) (minCell + i);

            if (headerRow.getCell(j) == null)
                result[i] = null;

            if (headerRow.getCell(j).getCellType() != CellType.STRING)
                result[i] = null;

            String nome = headerRow.getCell(j).getRichStringCellValue().getString();
            result[i] = "";
            for (int k = 0; k < nome.length(); k++) {
                char c = nome.charAt(k);
                if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')) {
                    result[i] += c;
                }
            }
        }

        return result;
    }

    public static final DataTable getDataTable(Sheet sheet, int rowHeader, int minRow, int maxRow, int minCell, int maxCell) {
        DataTable<?> table = new Table();

        String[] names = getNames(sheet.getRow(rowHeader), minCell, maxCell);
        for (int i = minRow; i <= maxRow; i++) {

            Row row = sheet.getRow(i);
            if (row == null)
                continue;

            DataRow dataRow = table.add();
            for (int j = (short) minCell; j < (short) maxCell; j++) {
                if (names[j - minCell] == null)
                    continue;

                String nome = names[j - minCell];

                Cell cell = row.getCell(j);
                if (cell == null) {
                    dataRow.setString(nome, null);
                } else if (cell.getCellType() == CellType.STRING) {
                    dataRow.setString(nome, StringUtil.parseString(cell.getRichStringCellValue().getString()));
                } else if ("@" .equals(cell.getCellStyle().getDataFormatString())) {
                    double value = cell.getNumericCellValue();
                    if (value == 0) {
                        dataRow.setString(nome, "");
                    } else if (Math.floor(value) == value) {
                        dataRow.setString(nome, "" + (int) value);
                    } else {
                        dataRow.setString(nome, "" + value);
                    }
                } else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        dataRow.setTimestamp(nome, TimeStampUtil.toTimestamp(row.getCell(j).getDateCellValue()));
                    } else {
                        dataRow.setBigDecimal(nome, BigDecimal.valueOf(row.getCell(j).getNumericCellValue()));
                    }

                } else {
                    dataRow.setObject(nome, null);
                }
            }
        }

        return table;
    }

    public static final DataTable getDataTable(Sheet sheet) {
        return getDataTable(sheet, 0, 1, sheet.getLastRowNum(), 0, sheet.getRow(0).getLastCellNum());
    }

    public static final DataTable getDataTable(InputStream inputStream) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            return getDataTable(workbook.getSheetAt(0));
        }
    }
}
