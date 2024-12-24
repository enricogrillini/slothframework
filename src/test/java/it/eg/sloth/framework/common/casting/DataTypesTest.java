package it.eg.sloth.framework.common.casting;

import it.eg.sloth.framework.common.base.BigDecimalUtil;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
 *
 * @author Enrico Grillini
 */
class DataTypesTest {

    @Test
    void timestamp_formatText() throws FrameworkException {
        Timestamp timestamp = TimeStampUtil.parseTimestamp("01/06/2020", "dd/MM/yyyy");

        assertEquals("01/06/2020", DataTypes.DATE.formatText(timestamp, Locale.ITALY));
        assertEquals("06/01/2020", DataTypes.DATE.formatText(timestamp, Locale.US));

        assertEquals("lun, 01/06", DataTypes.DATE.formatText(timestamp, Locale.ITALY, "EE, dd/MM"));
        assertEquals("Mon, 01/06", DataTypes.DATE.formatText(timestamp, Locale.US, "EE, dd/MM"));
    }

    @Test
    void timestampParseValue_DATE() throws FrameworkException {
        assertEquals(TimeStampUtil.parseTimestamp("16/09/2023", "dd/MM/yyyy"), DataTypes.DATE.parseValue("2023-09-16", Locale.ITALY));
    }

    @Test
    void timestampParseValue_DATETIME() throws FrameworkException {
        assertEquals(TimeStampUtil.parseTimestamp("16/09/2023 08:12:00", "dd/MM/yyyy HH:mm:ss"), DataTypes.DATETIME.parseValue("2023-09-16T08:12:00", Locale.ITALY));
        assertEquals(TimeStampUtil.parseTimestamp("16/09/2023 08:12:14", "dd/MM/yyyy HH:mm:ss"), DataTypes.DATETIME.parseValue("2023-09-16T08:12:14", Locale.ITALY));

        assertEquals(TimeStampUtil.parseTimestamp("16/09/2023 08:12:14", "dd/MM/yyyy HH:mm:ss"), DataTypes.DATETIME.parseValue("2023-09-16T08:12:14", Locale.ITALY));
    }



    @Test
    void bigDecimalFormatValueTest() throws FrameworkException {
        assertEquals("1,000.00", DataTypes.DECIMAL.formatValue(BigDecimal.valueOf(1000), Locale.US));
        assertEquals("1.000,00", DataTypes.DECIMAL.formatValue(BigDecimal.valueOf(1000), Locale.ITALY));
        assertEquals("10,00", DataTypes.DECIMAL.formatValue(BigDecimal.valueOf(10), Locale.ITALY));
        assertEquals("0,00", DataTypes.DECIMAL.formatValue(BigDecimal.valueOf(0), Locale.ITALY));

        assertEquals("1000", DataTypes.INTEGER.formatValue(BigDecimal.valueOf(1000), Locale.US));
        assertEquals("1000", DataTypes.INTEGER.formatValue(BigDecimal.valueOf(1000), Locale.ITALY));
        assertEquals("10", DataTypes.INTEGER.formatValue(BigDecimal.valueOf(10), Locale.ITALY));
        assertEquals("0", DataTypes.INTEGER.formatValue(BigDecimal.valueOf(0), Locale.ITALY));

        assertEquals("1,000.00 $", DataTypes.CURRENCY.formatValue(BigDecimal.valueOf(1000), Locale.US));
        assertEquals("1.000,00 €", DataTypes.CURRENCY.formatValue(BigDecimal.valueOf(1000), Locale.ITALY));
        assertEquals("10,00 €", DataTypes.CURRENCY.formatValue(BigDecimal.valueOf(10), Locale.ITALY));
        assertEquals("0,00 €", DataTypes.CURRENCY.formatValue(BigDecimal.valueOf(0), Locale.ITALY));

        assertEquals("10,00 %", DataTypes.PERC.formatValue(BigDecimal.valueOf(10), Locale.ITALY));
        assertEquals("0,00 %", DataTypes.PERC.formatValue(BigDecimal.valueOf(0), Locale.ITALY));

        assertEquals("1,000.0000000", DataTypes.NUMBER.formatValue(BigDecimal.valueOf(1000), Locale.US));
        assertEquals("1.000,0000000", DataTypes.NUMBER.formatValue(BigDecimal.valueOf(1000), Locale.ITALY));
        assertEquals("1,000.9870000", DataTypes.NUMBER.formatValue(BigDecimal.valueOf(1000.987), Locale.US));
        assertEquals("1.000,9870000", DataTypes.NUMBER.formatValue(BigDecimal.valueOf(1000.987), Locale.ITALY));
        assertEquals("10,0000000", DataTypes.NUMBER.formatValue(BigDecimal.valueOf(10), Locale.ITALY));
        assertEquals("0,0000000", DataTypes.NUMBER.formatValue(BigDecimal.valueOf(0), Locale.ITALY));
    }


    @Test
    void bigDecimalParseValueTest_DECIMAL() throws FrameworkException {
        // Interi
        assertEquals(BigDecimal.valueOf(1000), DataTypes.DECIMAL.parseValue("1000.00", Locale.US));
        assertEquals(BigDecimal.valueOf(1000), DataTypes.DECIMAL.parseValue("1000,00", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(10), DataTypes.DECIMAL.parseValue("10,00", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(0), DataTypes.DECIMAL.parseValue("0,00", Locale.ITALY));

        // Decimali con unità di misura
        assertEquals(BigDecimal.valueOf(12.67), DataTypes.DECIMAL.parseValue("12,67 mm/s", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(499), DataTypes.DECIMAL.parseValue("499 rpm", Locale.ITALY));
    }

    @Test
    void bigDecimalParseValueTest_INTEGER() throws FrameworkException {
        assertEquals(BigDecimal.valueOf(1000), DataTypes.INTEGER.parseValue("1000", Locale.US));
        assertEquals(BigDecimal.valueOf(1000), DataTypes.INTEGER.parseValue("1000", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(10), DataTypes.INTEGER.parseValue("10", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(0), DataTypes.INTEGER.parseValue("0", Locale.ITALY));
    }

    @Test
    void bigDecimalParseValueTest_CURRENCY() throws FrameworkException {
        assertEquals(BigDecimal.valueOf(1000), DataTypes.CURRENCY.parseValue("1,000.00 $", Locale.US));
        assertEquals(BigDecimal.valueOf(1000), DataTypes.CURRENCY.parseValue("1.000,00 €", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(1000), DataTypes.CURRENCY.parseValue("1.000", Locale.ITALY));
        assertEquals(BigDecimalUtil.toBigDecimal(1000.987), DataTypes.CURRENCY.parseValue("1,000.987 $", Locale.US));
        assertEquals(BigDecimalUtil.toBigDecimal(1000.987), DataTypes.CURRENCY.parseValue("1.000,987 €", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(10), DataTypes.CURRENCY.parseValue("10,00 €", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(0), DataTypes.CURRENCY.parseValue("0,00 €", Locale.ITALY));

        assertEquals(BigDecimal.valueOf(1000), DataTypes.CURRENCY.parseValue("1000.00", Locale.US));
        assertEquals(BigDecimal.valueOf(1000), DataTypes.CURRENCY.parseValue("1000,00", Locale.ITALY));
        assertEquals(BigDecimalUtil.toBigDecimal(1000.987), DataTypes.CURRENCY.parseValue("1000.987", Locale.US));
        assertEquals(BigDecimalUtil.toBigDecimal(1000.987), DataTypes.CURRENCY.parseValue("1000,987", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(1234), DataTypes.CURRENCY.parseValue("12.34", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(12.34), DataTypes.CURRENCY.parseValue("12,34", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(0), DataTypes.CURRENCY.parseValue("0.00", Locale.ITALY));
    }

    @Test
    void bigDecimalParseValueTest_CURRENCY_INTEGER() throws FrameworkException {
        assertEquals(BigDecimal.valueOf(1000), DataTypes.CURRENCY_INTEGER.parseValue("1,000.00 $", Locale.US));
        assertEquals(BigDecimal.valueOf(1000), DataTypes.CURRENCY_INTEGER.parseValue("1.000,00 €", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(1000), DataTypes.CURRENCY_INTEGER.parseValue("1.000", Locale.ITALY));
        assertEquals(BigDecimalUtil.toBigDecimal(1000.987), DataTypes.CURRENCY_INTEGER.parseValue("1,000.987 $", Locale.US));
        assertEquals(BigDecimalUtil.toBigDecimal(1000.987), DataTypes.CURRENCY_INTEGER.parseValue("1.000,987 €", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(10), DataTypes.CURRENCY_INTEGER.parseValue("10,00 €", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(0), DataTypes.CURRENCY_INTEGER.parseValue("0,00 €", Locale.ITALY));

        assertEquals(BigDecimal.valueOf(1000), DataTypes.CURRENCY_INTEGER.parseValue("1000.00", Locale.US));
        assertEquals(BigDecimal.valueOf(1000), DataTypes.CURRENCY_INTEGER.parseValue("1000,00", Locale.ITALY));
        assertEquals(BigDecimalUtil.toBigDecimal(1000.987), DataTypes.CURRENCY_INTEGER.parseValue("1000.987", Locale.US));
        assertEquals(BigDecimalUtil.toBigDecimal(1000.987), DataTypes.CURRENCY_INTEGER.parseValue("1000,987", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(1234), DataTypes.CURRENCY_INTEGER.parseValue("12.34", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(12.34), DataTypes.CURRENCY_INTEGER.parseValue("12,34", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(0), DataTypes.CURRENCY_INTEGER.parseValue("0.00", Locale.ITALY));
    }

    @Test
    void bigDecimalParseValue_PERC() throws FrameworkException {
        assertEquals(BigDecimal.valueOf(1234), DataTypes.PERC.parseValue("12.34", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(12.34), DataTypes.PERC.parseValue("12,34", Locale.ITALY));
    }

    @Test
    void bigDecimalParseValue_NUMBER() throws FrameworkException {
        assertEquals(BigDecimal.valueOf(1000), DataTypes.NUMBER.parseValue("1000", Locale.US));
        assertEquals(BigDecimal.valueOf(1000), DataTypes.NUMBER.parseValue("1000", Locale.ITALY));
        assertEquals(BigDecimalUtil.toBigDecimal(1000.987), DataTypes.NUMBER.parseValue("1000.987", Locale.US));
        assertEquals(BigDecimalUtil.toBigDecimal(1000.987), DataTypes.NUMBER.parseValue("1000,987", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(10), DataTypes.NUMBER.parseValue("10,00", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(0), DataTypes.NUMBER.parseValue("0,00", Locale.ITALY));
    }

    @Test
    void bigDecimalFormatTextTest() throws FrameworkException {
        assertEquals("1,000.00", DataTypes.DECIMAL.formatText(BigDecimal.valueOf(1000), Locale.US));
        assertEquals("1.000,00", DataTypes.DECIMAL.formatText(BigDecimal.valueOf(1000), Locale.ITALY));
        assertEquals("10,00", DataTypes.DECIMAL.formatText(BigDecimal.valueOf(10), Locale.ITALY));
        assertEquals("0,00", DataTypes.DECIMAL.formatText(BigDecimal.valueOf(0), Locale.ITALY));

        assertEquals("1000", DataTypes.INTEGER.formatText(BigDecimal.valueOf(1000), Locale.US));
        assertEquals("1000", DataTypes.INTEGER.formatText(BigDecimal.valueOf(1000), Locale.ITALY));
        assertEquals("10", DataTypes.INTEGER.formatText(BigDecimal.valueOf(10), Locale.ITALY));
        assertEquals("0", DataTypes.INTEGER.formatText(BigDecimal.valueOf(0), Locale.ITALY));

        assertEquals("10,00 %", DataTypes.PERC.formatText(BigDecimal.valueOf(10), Locale.ITALY));
        assertEquals("0,00 %", DataTypes.PERC.formatText(BigDecimal.valueOf(0), Locale.ITALY));

        assertEquals("1,000.0000000", DataTypes.NUMBER.formatText(BigDecimal.valueOf(1000), Locale.US));
        assertEquals("1.000,0000000", DataTypes.NUMBER.formatText(BigDecimal.valueOf(1000), Locale.ITALY));
        assertEquals("1,000.9870000", DataTypes.NUMBER.formatText(BigDecimal.valueOf(1000.987), Locale.US));
        assertEquals("1.000,9870000", DataTypes.NUMBER.formatText(BigDecimal.valueOf(1000.987), Locale.ITALY));
        assertEquals("10,0000000", DataTypes.NUMBER.formatText(BigDecimal.valueOf(10), Locale.ITALY));
        assertEquals("0,0000000", DataTypes.NUMBER.formatText(BigDecimal.valueOf(0), Locale.ITALY));
    }


    void currencyFormatTextTest() throws FrameworkException {
        assertEquals("1,000.00 $", DataTypes.CURRENCY.formatText(BigDecimal.valueOf(1000), Locale.US));
        assertEquals("1.000,00 €", DataTypes.CURRENCY.formatText(BigDecimal.valueOf(1000), Locale.ITALY));
        assertEquals("10,00 €", DataTypes.CURRENCY.formatText(BigDecimal.valueOf(10), Locale.ITALY));
        assertEquals("0,00 €", DataTypes.CURRENCY.formatText(BigDecimal.valueOf(0), Locale.ITALY));
    }

    void currencyIntegerFormatTextTest() throws FrameworkException {
        assertEquals("1,000 $", DataTypes.CURRENCY_INTEGER.formatText(BigDecimal.valueOf(1000), Locale.US));
        assertEquals("1.000 €", DataTypes.CURRENCY_INTEGER.formatText(BigDecimal.valueOf(1000), Locale.ITALY));
        assertEquals("10 €", DataTypes.CURRENCY_INTEGER.formatText(BigDecimal.valueOf(10), Locale.ITALY));
        assertEquals("0 €", DataTypes.CURRENCY_INTEGER.formatText(BigDecimal.valueOf(0), Locale.ITALY));
    }

    @Test
    void stringParseTest() throws FrameworkException {
        FrameworkException frameworkException;

        // Mail non valida
        frameworkException = assertThrows(FrameworkException.class, () -> {
            DataTypes.MAIL.parseValue("@@", Locale.ITALY);
        });
        assertEquals("Impossibile validare il valore passato - Indirizzo email errato", frameworkException.getMessage());

        // Mail valida
        assertEquals("alice@gmail.com", DataTypes.MAIL.parseValue("alice@gmail.com", Locale.ITALY));


        // Codice fiscale non valido
        frameworkException = assertThrows(FrameworkException.class, () -> {
            DataTypes.CODICE_FISCALE.parseValue("AAAAA", Locale.ITALY);
        });
        assertEquals("Impossibile validare il valore passato - Lunghezza codice fiscale errata", frameworkException.getMessage());

        // Codice fiscale valido
        assertEquals("RSSMRA80A01A944I", DataTypes.CODICE_FISCALE.parseValue("RSSMRA80A01A944I", Locale.ITALY));

        // Partita IVA non valida
        frameworkException = assertThrows(FrameworkException.class, () -> {
            DataTypes.PARTITA_IVA.parseValue("AAAAA", Locale.ITALY);
        });
        assertEquals("Impossibile validare il valore passato - Lunghezza partita iva errata", frameworkException.getMessage());
        DataTypes.PARTITA_IVA.parseValue("00000000000", Locale.ITALY);

        // Partita IVA valida
        assertEquals("00000000000", DataTypes.PARTITA_IVA.parseValue("00000000000", Locale.ITALY));
    }

}
