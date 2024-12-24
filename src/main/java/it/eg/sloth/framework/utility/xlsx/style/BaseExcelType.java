package it.eg.sloth.framework.utility.xlsx.style;

import it.eg.sloth.framework.common.casting.DataTypes;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.poi.ss.usermodel.DataFormat;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Enrico Grillini
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BaseExcelType {//

    public static final BaseExcelType INTEGER = new BaseExcelType("0");
    public static final BaseExcelType RICH_INTEGER = new BaseExcelType("#,##0");
    public static final BaseExcelType DECIMAL = new BaseExcelType("_-* #,##0.00_-;-* #,##0.00_-;_-* \"-\"??_-;_-@_-");
    public static final BaseExcelType PERC = new BaseExcelType(" 0%");
    public static final BaseExcelType CURRENCY = new BaseExcelType("#,##0.00 €;[Red]-#,##0.00 €");
    public static final BaseExcelType CURRENCY_INTEGER = new BaseExcelType("#,##0 €;[Red]-#,##0 €");
    public static final BaseExcelType DATE = new BaseExcelType("dd/mm/yyyy");
    public static final BaseExcelType MONTH = new BaseExcelType("mm/yyyy");
    public static final BaseExcelType DATETIME = new BaseExcelType("dd/mm/yyyy hh:mm:ss");
    public static final BaseExcelType TIME = new BaseExcelType("hh:mm:ss");
    public static final BaseExcelType HOUR = new BaseExcelType("hh:mm");

    private String format;

    public BaseExcelType(String format) {
        this.format = format;
    }

    /**
     * Ritorna il Format POI sulla base delle info disponibili
     *
     * @param dataFormat
     * @return
     */
    public short formatFromDataFormat(DataFormat dataFormat) {
        return dataFormat.getFormat(format);
    }

    public static class Factory {
        private Factory() {
            // NOP
        }

        /**
         * Ritorna il BaseExcelType corrispondente al DataType del framework
         *
         * @return
         */
        public static final BaseExcelType fromDataType(DataTypes dataTypes) {
            return switch (dataTypes) {
                case DATE -> DATE;
                case MONTH -> MONTH;
                case DATETIME -> DATETIME;
                case TIME -> TIME;
                case HOUR -> HOUR;
                case INTEGER -> INTEGER;
                case DECIMAL -> DECIMAL;
                case PERC -> PERC;
                case CURRENCY -> CURRENCY;
                case CURRENCY_INTEGER -> CURRENCY_INTEGER;
                case STRING, MD, MAIL, PARTITA_IVA, CODICE_FISCALE -> null;
                default -> null;
            };
        }

    }
}
