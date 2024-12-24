package it.eg.sloth.webdesktop.controller.common;

import it.eg.sloth.form.Form;
import it.eg.sloth.form.WebRequest;
import it.eg.sloth.form.chart.SimpleChart;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.framework.utility.FileType;
import it.eg.sloth.framework.utility.xlsx.ChartXlsxWriter;
import it.eg.sloth.framework.utility.xlsx.GridXlsxWriter;
import it.eg.sloth.webdesktop.controller.BasePageInterface;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletResponse;

import java.io.OutputStream;

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
 * Gestisce l'interfaccia di base per una pagina
 *
 * @author Enrico Grillini
 */
public interface FormPageInterface<F extends Form> extends BasePageInterface {

    WebRequest getWebRequest();

    void setWebRequest(WebRequest webRequest);

    F getForm();

    MessageList getMessageList();

    boolean isNewForm();

    void setNewForm(boolean newForm);

    HttpServletResponse getResponse();

    ModelAndView getModelAndView();

    void clearModelAndView();

    void setModelAndView(String fileName, FileType fileType);

    void setModelAndView(ModelAndView modelAndView);

    void setModelAndView(String modelAndView);

    default void onInit() throws Exception {
        execInit();
    }

    void execInit() throws Exception;

    default void onBeforeNavigation() throws Exception {
        // NOP
    }

    default void onAfterNavigation() throws Exception {
        // NOP
    }

    // Implementa l'esportazione in excel di una Grid
    default void onExcel(Grid<?> grid) throws Exception {
        try (GridXlsxWriter gridXlsxWriter = new GridXlsxWriter(true, grid);) {
            OutputStream outputStream = getResponse().getOutputStream();
            String fileName = BaseFunction.nvl(grid.getTitle(), grid.getName()) + FileType.XLSX.getExtension();
            fileName = StringUtil.toFileName(fileName);

            setModelAndView(fileName, FileType.XLSX);
            gridXlsxWriter.getWorkbook().write(outputStream);
        }
    }

    // Implementa l'esportazione in excel di un Chart
    default void onExcel(SimpleChart<?> chart) throws Exception {
        try (ChartXlsxWriter chartXlsxWriter = new ChartXlsxWriter(true, chart);) {
            OutputStream outputStream = getResponse().getOutputStream();
            String fileName = BaseFunction.nvl(chart.getTitle(), chart.getName()) + FileType.XLSX.getExtension();
            fileName = StringUtil.toFileName(fileName);

            setModelAndView(fileName, FileType.XLSX);
            chartXlsxWriter.getWorkbook().write(outputStream);
        }
    }

}
