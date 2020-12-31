package it.eg.sloth.db.datasource.table.sort;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@Getter
@Setter
@AllArgsConstructor
@ToString
public class SortingRule {

    public static final int SORT_ASC_NULLS_LAST = 1;
    public static final int SORT_DESC_NULLS_LAST = -1;

    public static final int SORT_ASC_NULLS_FIRST = 2;
    public static final int SORT_DESC_NULLS_FIRST = -2;

    private String fieldName;
    private int sortType;

    public SortingRule(String fieldName) {
        this(fieldName, SORT_ASC_NULLS_LAST);
    }

}
