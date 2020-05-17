package it.eg.sloth.db.query.filteredquery.filter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.eg.sloth.framework.FrameComponent;
import lombok.Getter;
import lombok.Setter;

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
@Setter
@Getter
public class OnlyValFilter extends FrameComponent implements Filter {

    int sqlTypes;
    Object value;

    public OnlyValFilter(int sqlTypes, Object value) {
        this.sqlTypes = sqlTypes;
        this.value = value;
    }

    public String getWhereCondition() {
        return "";
    }

    public int addValues(PreparedStatement statement, int i) throws SQLException {
        statement.setObject(i++, getValue(), getSqlTypes());

        return i;
    }

}
