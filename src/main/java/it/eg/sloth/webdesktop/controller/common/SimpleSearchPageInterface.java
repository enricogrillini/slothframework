package it.eg.sloth.webdesktop.controller.common;

import it.eg.sloth.form.Form;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.framework.utility.FileType;
import it.eg.sloth.framework.utility.xlsx.GridXlsxWriter;

import java.io.OutputStream;

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
 * Gestisce l'interfaccia di base per una pagina
 *
 * @author Enrico Grillini
 */
public interface SimpleSearchPageInterface<F extends Form> extends FormPageInterface<F> {

    void execLoad() throws Exception;

    void execReset() throws Exception;

    /**
     * Implementa l'evento "load" in risposta all'applicazione di filtri
     *
     * @throws Exception
     */
    default void onLoad() throws Exception {
        execLoad();
        getForm().getPageInfo().setViewModality(ViewModality.VIEW_VISUALIZZAZIONE);
    }

    /**
     * Implementa l'evento "reset" per la pulizia dei filtri
     *
     * @throws Exception
     */
    default void onReset() throws Exception {
        execReset();
    }

    /**
     * Implementa l'evendo di ordinamento su una grid
     *
     * @param grid
     * @param fieldName
     * @param sortType
     * @throws Exception
     */
    default void onSort(Grid<?> grid, String fieldName, int sortType) throws Exception {
        grid.orderBy(fieldName, sortType);
    }

    /**
     * Implementa l'esprorazione in execl di una grid
     *
     * @param grid
     * @throws Exception
     */
    default void onExcel(Grid<?> grid) throws Exception {
        try (GridXlsxWriter gridXlsxWriter = new GridXlsxWriter(true, grid);) {
            OutputStream outputStream = getResponse().getOutputStream();
            String fileName = BaseFunction.nvl(grid.getTitle(), grid.getName()) + FileType.XLSX.getExtension();
            fileName = StringUtil.toFileName(fileName);

            setModelAndView(fileName, FileType.XLSX);
            gridXlsxWriter.getWorkbook().write(outputStream);
        }
    }
}
