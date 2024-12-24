package it.eg.sloth.framework.utility.html;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
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
public class HtmlInput {
    private HtmlInput() {
        // NOP
    }

    // TimeStamp
    public static final String TYPE_DATE = "date";
    public static final String TYPE_DATETIME = "datetime-local";
    public static final String TYPE_TIME = "time";
    public static final String TYPE_HOUR = "time";
    public static final String TYPE_MONTH = "month";

    // BigDecimal
    public static final String TYPE_NUMBER = "text";
    public static final String TYPE_INTEGER = "number";
    public static final String TYPE_DECIMAL = "text";
    public static final String TYPE_CURRENCY = "text";

    public static final String TYPE_CURRENCY_INTEGER = "text";
    public static final String TYPE_PERC = "text";

    // String
    public static final String TYPE_STRING = "text";
    public static final String TYPE_MD = "text";
    public static final String TYPE_MAIL = "text";
    public static final String TYPE_PARTITA_IVA = "text";
    public static final String TYPE_CODICE_FISCALE = "text";
    public static final String TYPE_URL = "text";
    public static final String TYPE_PASSWORD = "password";

}
