package it.eg.sloth.framework.common.localization;

import it.eg.sloth.db.decodemap.map.StringDecodeMap;

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
public class Localization {

    private Localization() {
        // NOP
    }

    public static final String EXCEPTION_BUNDLE = "exception";


    /**
     * Formattazione
     */
    public static final StringDecodeMap LOCALE_MAP = new StringDecodeMap("it_IT,Italy; en_US,US");

    public static final String VALUE_BUNDLE = "formatValue";
    public static final String TEXT_BUNDLE = "formatText";

    public static final String PROP_DECIMAL_SEPARATOR = "separator.decimal";
    public static final String PROP_THOUSAND_SEPARATOR = "separator.thousand";

    // TimeStamp - Format
    public static final String PROP_DATE = "format.date";
    public static final String PROP_DATETIME = "format.datetime";
    public static final String PROP_TIME = "format.time";
    public static final String PROP_HOUR = "format.hour";
    public static final String PROP_MONTH = "format.month";

    // BigDecimal - Format
    public static final String PROP_NUMBER = "format.number";
    public static final String PROP_INTEGER = "format.integer";
    public static final String PROP_DECIMAL = "format.decimal";
    public static final String PROP_CURRENCY = "format.currency";
    public static final String PROP_CURRENCY_INTEGER = "format.currencyinteger";
    public static final String PROP_PERC = "format.perc";

    // String - Format
    public static final String PROP_STRING = "format.string";
    public static final String PROP_MD = "format.md";
    public static final String PROP_MAIL = "format.mail";
    public static final String PROP_PARTITA_IVA = "format.piva";
    public static final String PROP_CODICE_FISCALE = "format.codicefiscale";
    public static final String PROP_URL = "format.url";
    public static final String PROP_PASS = "format.password";

    // TimeStamp - Error
    public static final String ERR_PROP_DATE = "error.date";
    public static final String ERR_PROP_DATETIME = "error.datetime";
    public static final String ERR_PROP_TIME = "error.time";
    public static final String ERR_PROP_HOUR = "error.hour";
    public static final String ERR_PROP_MONTH = "error.month";

    // BigDecimal - Error
    public static final String ERR_PROP_NUMBER = "error.number";
    public static final String ERR_PROP_INTEGER = "error.integer";
    public static final String ERR_PROP_DECIMAL = "error.decimal";
    public static final String ERR_PROP_CURRENCY = "error.currency";
    public static final String ERR_PROP_CURRENCY_INTEGER = "error.currencyinteger";
    public static final String ERR_PROP_PERC = "error.perc";

    // String - Error
    public static final String ERR_PROP_STRING = "error.string";
    public static final String ERR_PROP_MD = "error.md";
    public static final String ERR_PROP_MAIL = "error.mail";
    public static final String ERR_PROP_PARTITA_IVA = "error.piva";
    public static final String ERR_PROP_CODICE_FISCALE = "error.codicefiscale";
    public static final String ERR_PROP_URL = "error.url";
    public static final String ERR_PROP_PASS = "error.password";

    //
    public static final String ERR_VALUE = "error.value";
    public static final String ERR_MANDATORY = "error.mandatory";
    public static final String ERR_ADMITTED_VALUE = "error.admittedvalue";

}
