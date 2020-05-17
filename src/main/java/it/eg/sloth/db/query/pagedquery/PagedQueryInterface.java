package it.eg.sloth.db.query.pagedquery;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.query.SelectQueryInterface;

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
public interface PagedQueryInterface extends SelectQueryInterface {

    /**
     * Ritorna una tabella conetenente il risultato della query
     *
     * @param start
     * @param end
     * @return
     * @throws SQLException
     * @throws IOException
     */
    DataTable<?> select(int start, int end) throws SQLException, IOException;

    /**
     * Ritorna una tabella conetenente il risultato della query
     *
     * @param connectionName
     * @param start
     * @param end
     * @return
     * @throws SQLException
     * @throws IOException
     */
    DataTable<?> select(String connectionName, int start, int end) throws SQLException, IOException;

    /**
     * Ritorna una tabella conetenente il risultato della query
     *
     * @param connection
     * @return
     * @throws SQLException
     */
    DataTable<?> select(Connection connection, int start, int end) throws SQLException, IOException;

    /**
     * Ritorna il numero totale di righe
     *
     * @return
     * @throws SQLException
     * @throws IOException
     */
    int getCount() throws SQLException, IOException;

    /**
     * Ritorna il numero totale di righe
     *
     * @param connectionName
     * @return
     * @throws SQLException
     * @throws IOException
     */
    int getCount(String connectionName) throws SQLException, IOException;

    /**
     * Ritorna il numero totale di righe
     *
     * @param connection
     * @return
     * @throws SQLException
     * @throws IOException
     */
    int getCount(Connection connection) throws SQLException, IOException;
}
