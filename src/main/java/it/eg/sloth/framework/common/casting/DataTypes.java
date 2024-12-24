package it.eg.sloth.framework.common.casting;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.BigDecimalUtil;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.ExceptionCode;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.localization.Localization;
import it.eg.sloth.framework.utility.html.HtmlInput;
import it.eg.sloth.webdesktop.tag.form.chart.pojo.NumerFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;

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
 * Definizione dei formati standard
 *
 * @author Enrico Grillini
 */
public enum DataTypes {
    // Timestamp
    DATE(HtmlInput.TYPE_DATE, Localization.PROP_DATE, Localization.ERR_PROP_DATE),
    DATETIME(HtmlInput.TYPE_DATETIME, Localization.PROP_DATETIME, Localization.ERR_PROP_DATETIME),
    TIME(HtmlInput.TYPE_TIME, Localization.PROP_TIME, Localization.ERR_PROP_TIME),
    HOUR(HtmlInput.TYPE_HOUR, Localization.PROP_HOUR, Localization.ERR_PROP_HOUR),
    MONTH(HtmlInput.TYPE_MONTH, Localization.PROP_MONTH, Localization.ERR_PROP_MONTH),

    // Bigdecimal
    DECIMAL(HtmlInput.TYPE_DECIMAL, Localization.PROP_DECIMAL, Localization.ERR_PROP_DECIMAL),
    INTEGER(HtmlInput.TYPE_INTEGER, Localization.PROP_INTEGER, Localization.ERR_PROP_INTEGER),
    CURRENCY(HtmlInput.TYPE_CURRENCY, Localization.PROP_CURRENCY, Localization.ERR_PROP_CURRENCY),
    CURRENCY_INTEGER(HtmlInput.TYPE_CURRENCY_INTEGER, Localization.PROP_CURRENCY_INTEGER, Localization.ERR_PROP_CURRENCY_INTEGER),
    PERC(HtmlInput.TYPE_PERC, Localization.PROP_PERC, Localization.ERR_PROP_PERC),
    NUMBER(HtmlInput.TYPE_NUMBER, Localization.PROP_NUMBER, Localization.ERR_PROP_NUMBER),

    // String
    STRING(HtmlInput.TYPE_STRING, Localization.PROP_STRING, Localization.ERR_PROP_STRING),
    MD(HtmlInput.TYPE_MD, Localization.PROP_MD, Localization.ERR_PROP_MD),
    MAIL(HtmlInput.TYPE_MAIL, Localization.PROP_MAIL, Localization.ERR_PROP_MAIL),
    PARTITA_IVA(HtmlInput.TYPE_PARTITA_IVA, Localization.PROP_PARTITA_IVA, Localization.ERR_PROP_PARTITA_IVA),
    CODICE_FISCALE(HtmlInput.TYPE_CODICE_FISCALE, Localization.PROP_CODICE_FISCALE, Localization.ERR_PROP_CODICE_FISCALE),
    URL(HtmlInput.TYPE_URL, Localization.PROP_URL, Localization.ERR_PROP_URL),
    PASSWORD(HtmlInput.TYPE_PASSWORD, Localization.PROP_PASS, Localization.ERR_PROP_PASS);

    private String htmlType;
    private String formatProperties;
    private String errorProperties;

    public String getHtmlType() {
        return htmlType;
    }

    public String getErrorProperties() {
        return errorProperties;
    }

    @SuppressWarnings("unused")
    DataTypes(String htmlType, String formatProperties, String errorProperties) {
        this.htmlType = htmlType;
        this.formatProperties = formatProperties;
        this.errorProperties = errorProperties;
    }

    /**
     * Converta la stringa passata nel suo valore nativo
     *
     * @param value
     * @param locale
     * @return
     * @throws FrameworkException
     */
    public Object parseValue(String value, Locale locale) throws FrameworkException {
        ResourceBundle bundle = ResourceBundle.getBundle(Localization.VALUE_BUNDLE, locale);

        return parseValue(value, locale, bundle, null);
    }


    /**
     * Convertae la stringa passata nel suo valore nativo
     *
     * @param value
     * @param locale
     * @return
     * @throws FrameworkException
     */
    public Object parseValue(String value, Locale locale, String format) throws FrameworkException {
        ResourceBundle bundle = ResourceBundle.getBundle(Localization.VALUE_BUNDLE, locale);
        return parseValue(value, locale, bundle, format);
    }

    public boolean isValid(String value, Locale locale) throws FrameworkException {
        return isValid(value, locale, null);
    }

    public boolean isValid(String value, Locale locale, String format) throws FrameworkException {
        ResourceBundle bundle = ResourceBundle.getBundle(Localization.VALUE_BUNDLE, locale);

        try {
            parseValue(value, locale, bundle, format);
            return true;
        } catch (FrameworkException e) {
            return false;
        }
    }

    /**
     * Converta la stringa passata nel suo valore nativo
     *
     * @param value
     * @param valueBundle
     * @return
     * @throws FrameworkException
     */
    private Object parseValue(String value, Locale locale, ResourceBundle valueBundle, String format) throws FrameworkException {
        switch (this) {
            case DATE:
            case DATETIME:
            case TIME:
            case HOUR:
            case MONTH:
                if (BaseFunction.isBlank(format)) {
                    format = valueBundle.getString(formatProperties);
                }
                return TimeStampUtil.parseTimestamp(value, locale, format);

            case DECIMAL:
            case INTEGER:
            case CURRENCY:
            case CURRENCY_INTEGER:
            case PERC:
            case NUMBER:
                DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
                decimalFormatSymbols.setDecimalSeparator(valueBundle.getString(Localization.PROP_DECIMAL_SEPARATOR).charAt(0));
                decimalFormatSymbols.setGroupingSeparator(valueBundle.getString(Localization.PROP_THOUSAND_SEPARATOR).charAt(0));
                return BigDecimalUtil.parseBigDecimal(value, valueBundle.getString(NUMBER.formatProperties), decimalFormatSymbols);

            case MAIL:
                return StringUtil.parseMail(value);

            case CODICE_FISCALE:
                return StringUtil.parseCodiceFiscale(value);

            case PARTITA_IVA:
                return StringUtil.parsePartitaIva(value);

            default:
                return value;
        }
    }

    /**
     * Ritorna il value passato formattato per essere passato al value dell'input
     *
     * @param value
     * @param locale
     * @return
     * @throws FrameworkException
     */
    public String formatValue(Object value, Locale locale) throws FrameworkException {
        ResourceBundle bundle = ResourceBundle.getBundle(Localization.VALUE_BUNDLE, locale);

        return format(value, locale, bundle, null);
    }

    /**
     * Ritorna il value passato formattato per essere passato al value dell'input
     *
     * @param value
     * @param locale
     * @return
     * @throws FrameworkException
     */
    public String formatValue(Object value, Locale locale, String format) throws FrameworkException {
        ResourceBundle bundle = ResourceBundle.getBundle(Localization.VALUE_BUNDLE, locale);

        return format(value, locale, bundle, format);
    }

    /**
     * Ritorna il value passato formattato per essere passato al value dell'input
     *
     * @param value
     * @param locale
     * @return
     * @throws FrameworkException
     */
    public String formatText(Object value, Locale locale) throws FrameworkException {
        ResourceBundle bundle = ResourceBundle.getBundle(Localization.TEXT_BUNDLE, locale);

        return format(value, locale, bundle, null);
    }

    /**
     * Ritorna il value passato formattato per essere passato al value dell'input
     *
     * @param value
     * @param locale
     * @return
     * @throws FrameworkException
     */
    public String formatText(Object value, Locale locale, String format) throws FrameworkException {
        ResourceBundle bundle = ResourceBundle.getBundle(Localization.TEXT_BUNDLE, locale);

        return format(value, locale, bundle, format);
    }

    /**
     * Ritorna il value passato formattato per essere passato al value dell'input
     *
     * @param value
     * @param textBundle
     * @return
     * @throws FrameworkException
     */
    private String format(Object value, Locale locale, ResourceBundle textBundle, String format) throws FrameworkException {
        if (BaseFunction.isBlank(format)) {
            format = textBundle.getString(formatProperties);
        }

        switch (this) {
            case DATE:
            case DATETIME:
            case TIME:
            case HOUR:
            case MONTH:
                if (BaseFunction.isNull(value) || value instanceof Timestamp) {
                    return TimeStampUtil.formatTimestamp((Timestamp) value, locale, format);
                } else {
                    throw new FrameworkException(ExceptionCode.FORMAT_ERROR);
                }

            case DECIMAL:
            case INTEGER:
            case CURRENCY:
            case CURRENCY_INTEGER:
            case PERC:
            case NUMBER:
                if (BaseFunction.isNull(value) || value instanceof BigDecimal) {
                    if (!BaseFunction.isBlank(format)) {
                        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
                        decimalFormatSymbols.setDecimalSeparator(textBundle.getString(Localization.PROP_DECIMAL_SEPARATOR).charAt(0));
                        decimalFormatSymbols.setGroupingSeparator(textBundle.getString(Localization.PROP_THOUSAND_SEPARATOR).charAt(0));

                        return BigDecimalUtil.formatBigDecimal((BigDecimal) value, format, decimalFormatSymbols);
                    } else if (BaseFunction.isNull(value)) {
                        return StringUtil.EMPTY;
                    } else {
                        return value.toString();
                    }

                } else {
                    throw new FrameworkException(ExceptionCode.FORMAT_ERROR);
                }

            default:
                return (String) value;
        }
    }

    /**
     * Verifica se il DataType e' una data
     *
     * @return
     */
    public boolean isDate() {
        return (this.equals(DATE) || this.equals(DATETIME) || this.equals(TIME) || this.equals(HOUR) || this.equals(MONTH));
    }

    /**
     * Verifica se il DataType e' un numero
     *
     * @return
     */
    public boolean isNumber() {
        return (this.equals(DECIMAL) || this.equals(INTEGER) || this.equals(CURRENCY) || this.equals(CURRENCY_INTEGER) || this.equals(PERC));
    }


    /**
     * Ritorna il nummber format per i grafici
     *
     * @return
     */
    public NumerFormat getNumerFormat() {
        switch (this) {
            case DECIMAL:
            case NUMBER:
                return NumerFormat.DECIMAL_FORMAT;
            case INTEGER:
                return NumerFormat.INTEGER_FORMAT;
            case CURRENCY:
                return NumerFormat.CURRENCY_FORMAT;
            case CURRENCY_INTEGER:
                return NumerFormat.CURRENCY_INTEGER_FORMAT;
            case PERC:
                return NumerFormat.PERC_FORMAT;

            default:
                return null;
        }
    }

    /**
     * Elabora il dato conservato in una forma ritorandone il testo in HTML
     *
     * @param data
     * @param locale
     * @param format
     * @return
     */
    public String escapeHtmlText(String data, Locale locale, String format) {
        try {
            Object value = parseValue(data, locale, format);
            return Casting.getHtml(formatText(value, locale, format), true, true);

        } catch (FrameworkException e) {
            return Casting.getHtml(data, true, true);
        }
    }

    /**
     * Elabora il dato conservato in una forma ritonandone il testo in HTML
     *
     * @param data
     * @param locale
     * @param format
     * @return
     */
    public String escapeJsText(String data, Locale locale, String format) {
        try {
            Object value = parseValue(data, locale, format);
            return Casting.getJs(formatText(value, locale, format));

        } catch (FrameworkException e) {
            return Casting.getJs(data);
        }
    }


}
