package it.eg.sloth.webdesktop.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.eg.sloth.TestFactory;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.form.chart.SimpleChart;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.utility.html.HtmlColor;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.jaxb.form.ChartType;
import it.eg.sloth.webdesktop.tag.form.chart.writer.ChartWriter;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
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
class ChartWriterTest {

    static final String CHART_CANVAS = ResourceUtil.normalizedResourceAsString("snippet-html/chart/chart-canvas.html");
    static final String CHART_CANVAS_CUSTOM = ResourceUtil.normalizedResourceAsString("snippet-html/chart/chart-canvas-custom.html");
    static final String CHART_SCRIPT_LINE = ResourceUtil.normalizedResourceAsString("snippet-html/chart/chart-script-line.html");
    static final String CHART_SCRIPT_PIE = ResourceUtil.normalizedResourceAsString("snippet-html/chart/chart-script-pie.html");
    static final String CHART_SCRIPT_POLAR_AREA = ResourceUtil.normalizedResourceAsString("snippet-html/chart/chart-script-polarArea.html");
    static final String CHART_SCRIPT_WATERFALL = ResourceUtil.normalizedResourceAsString("snippet-html/chart/chart-script-waterfall.html");

    static final String CHART_SCRIPT_PIE_CUSTOM_PALETTE = ResourceUtil.normalizedResourceAsString("snippet-html/chart/chart-script-pie-custom-palette.html");

    @Test
    void writeCanvasTest() throws FrameworkException {
        SimpleChart<DataTable<?>> simpleChart = TestFactory.getSimpleChart(ChartType.LINE);
        assertEquals(CHART_CANVAS, ChartWriter.writeCanvas(simpleChart, null));

        assertEquals(CHART_CANVAS_CUSTOM, ChartWriter.writeCanvas(simpleChart, "custom"));
    }

    @Test
    void writeScriptLineTest() throws FrameworkException, JsonProcessingException {
        SimpleChart<DataTable<?>> simpleChart = TestFactory.getSimpleChart(ChartType.LINE);
        assertEquals(CHART_SCRIPT_LINE, ChartWriter.openScript(simpleChart));
    }

    @Test
    void writeScriptPieTest() throws FrameworkException, JsonProcessingException {
        SimpleChart<DataTable<?>> simpleChart = TestFactory.getSimpleChart(ChartType.PIE);
        assertEquals(CHART_SCRIPT_PIE, ChartWriter.openScript(simpleChart));
    }

    @Test
    void writeScriptPieCustomPaletteTest() throws FrameworkException, JsonProcessingException {
        SimpleChart<DataTable<?>> simpleChart = TestFactory.getSimpleChart(ChartType.PIE);
        simpleChart.setPalette(HtmlColor.DANGER, HtmlColor.WARNING, HtmlColor.PRIMARY);

        assertEquals(CHART_SCRIPT_PIE_CUSTOM_PALETTE, ChartWriter.openScript(simpleChart));
    }

    @Test
    void writeScriptWaterfallTest() throws FrameworkException, JsonProcessingException {
        SimpleChart<DataTable<?>> simpleChart = TestFactory.getDoubleChart(ChartType.WATERFALL);
        assertEquals(CHART_SCRIPT_WATERFALL, ChartWriter.openScript(simpleChart));
    }

    @Test
    void writeScriptPolarAreaTest() throws FrameworkException, JsonProcessingException {
        SimpleChart<DataTable<?>> simpleChart = TestFactory.getSimpleChart(ChartType.POLAR_AREA);
        assertEquals(CHART_SCRIPT_POLAR_AREA, ChartWriter.openScript(simpleChart));
    }

}
