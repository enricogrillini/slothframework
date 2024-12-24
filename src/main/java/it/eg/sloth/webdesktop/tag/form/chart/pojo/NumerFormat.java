package it.eg.sloth.webdesktop.tag.form.chart.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

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
@Data
@AllArgsConstructor
public class NumerFormat {

    public static final NumerFormat CURRENCY_FORMAT = new NumerFormat(2, ",", ".", " €");
    public static final NumerFormat CURRENCY_INTEGER_FORMAT = new NumerFormat(0, ",", ".", " €");
    public static final NumerFormat DECIMAL_FORMAT = new NumerFormat(2, ",", ".", "");
    public static final NumerFormat INTEGER_FORMAT = new NumerFormat(0, ",", ".", "");
    public static final NumerFormat PERC_FORMAT = new NumerFormat(2, ",", ".", " %");

    int decimals;
    String decimalSeparator;
    String thousandsSeparator;
    String suffix;

}
