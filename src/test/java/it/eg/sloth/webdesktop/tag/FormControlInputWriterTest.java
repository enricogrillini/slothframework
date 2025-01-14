package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.form.ControlState;
import it.eg.sloth.form.fields.field.impl.Input;
import it.eg.sloth.form.fields.field.impl.InputTotalizer;
import it.eg.sloth.form.fields.field.impl.Text;
import it.eg.sloth.form.fields.field.impl.TextTotalizer;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
class FormControlInputWriterTest {

    private static final String INPUT_VIEW_EMPTY = ResourceUtil.normalizedResourceAsString("snippet-html/form-control/input-view-empty.html");
    private static final String INPUT_VIEW_FULL = ResourceUtil.normalizedResourceAsString("snippet-html/form-control/input-view-full.html");

    private static final String INPUT_VIEW_STATE = ResourceUtil.normalizedResourceAsString("snippet-html/form-control/input-view-state.html");
    private static final String INPUT_VIEW_STATE_LINK = ResourceUtil.normalizedResourceAsString("snippet-html/form-control/input-view-state-link.html");

    // Controlli
    private static final String CTRL_INPUT = "<input id=\"{0}\" name=\"{0}\" type=\"{1}\" value=\"{2}\"{3}{4}/>";
    private static final String CTRL_INPUT_GROUP = "<div class=\"input-group input-group-sm\"><input id=\"{0}\" name=\"{0}\" type=\"{1}\" value=\"{2}\"{3}{4}/></div><div class=\"small text-danger\">Lorem ipsum</div>";

    // Attributi
    private static final String ATTR_STEP = " step=\"1\"";
    private static final String ATTR_EDIT = " class=\"form-control form-control-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\"";
    private static final String ATTR_EDIT_DANGER = " class=\"form-control form-control-sm is-invalid\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\"";

    // Input - String
    @Test
    void inputTest() throws FrameworkException {
        Input<String> field = new Input<String>("name", "description", DataTypes.STRING);
        field.setTooltip("tooltip");

        assertEquals(INPUT_VIEW_EMPTY, FormControlWriter.writeInput(field, null, ViewModality.VIEW));

        field.setValue("testo");
        assertEquals(MessageFormat.format(INPUT_VIEW_FULL, "testo"), FormControlWriter.writeInput(field, null, ViewModality.VIEW));
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "text", "testo", "", ATTR_EDIT), FormControlWriter.writeInput(field, null, ViewModality.EDIT));

        // Controllo generico
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "text", "testo", "", ATTR_EDIT), FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));

        // State
        field.setState(ControlState.DANGER);
        field.setStateMessage("Lorem ipsum");
        assertEquals(MessageFormat.format(CTRL_INPUT_GROUP, "name", "text", "testo", "", ATTR_EDIT_DANGER), FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));

        // Link
        field.setBaseLink("/api/");
        assertEquals(MessageFormat.format(CTRL_INPUT_GROUP, "name", "text", "testo", "", ATTR_EDIT_DANGER), FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));
        assertEquals(MessageFormat.format(INPUT_VIEW_STATE_LINK, "testo", "Lorem ipsum"), FormControlWriter.writeControl(field, null, ViewModality.VIEW, false));

        // Empty
        field.setHidden(true);
        assertEquals(StringUtil.EMPTY, FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));
    }

    // Input - Date
    @Test
    void inputDateTest() throws FrameworkException {
        Input<Timestamp> field = new Input<Timestamp>("name", "description", DataTypes.DATE);
        field.setLocale(Locale.ITALY);
        field.setTooltip("tooltip");

        assertEquals(INPUT_VIEW_EMPTY, FormControlWriter.writeInput(field, null, ViewModality.VIEW));

        field.setValue(TimeStampUtil.parseTimestamp("01/01/2020", "dd/MM/yyyy"));
        assertEquals(MessageFormat.format(INPUT_VIEW_FULL, "01/01/2020"), FormControlWriter.writeInput(field, null, ViewModality.VIEW));
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "date", "2020-01-01", "", ATTR_EDIT), FormControlWriter.writeInput(field, null, ViewModality.EDIT));

        // Controllo generico
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "date", "2020-01-01", "", ATTR_EDIT), FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));
    }

    // Input - DateTime
    @Test
    void inputDatetimeTest() throws FrameworkException {
        Input<Timestamp> field = new Input<Timestamp>("name", "description", DataTypes.DATETIME);
        field.setLocale(Locale.ITALY);
        field.setTooltip("tooltip");

        assertEquals(INPUT_VIEW_EMPTY, FormControlWriter.writeInput(field, null, ViewModality.VIEW));

        field.setValue(TimeStampUtil.parseTimestamp("01/01/2020 10:11:12", "dd/MM/yyyy HH:mm:ss"));
        assertEquals(MessageFormat.format(INPUT_VIEW_FULL, "01/01/2020 10:11:12"), FormControlWriter.writeInput(field, null, ViewModality.VIEW));
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "datetime-local", "2020-01-01T10:11:12", ATTR_STEP, ATTR_EDIT), FormControlWriter.writeInput(field, null, ViewModality.EDIT));

        // Controllo generico
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "datetime-local", "2020-01-01T10:11:12", ATTR_STEP, ATTR_EDIT), FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));
    }

    // InputTotalizer - Integer
    @Test
    void inputTotalizerTest() throws FrameworkException {
        InputTotalizer field = new InputTotalizer("name", "description", DataTypes.INTEGER);
        field.setTooltip("tooltip");

        assertEquals(INPUT_VIEW_EMPTY, FormControlWriter.writeInput(field, null, ViewModality.VIEW));

        field.setValue(BigDecimal.valueOf(10));
        assertEquals(MessageFormat.format(INPUT_VIEW_FULL, "10"), FormControlWriter.writeInput(field, null, ViewModality.VIEW));
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "number", "10", "", ATTR_EDIT), FormControlWriter.writeInput(field, null, ViewModality.EDIT));

        // Controllo generico
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "number", "10", "", ATTR_EDIT), FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));
    }

    @Test
    void textTest() throws FrameworkException {
        Text<String> field = Text.<String>builder()
                .name("name")
                .description("description")
                .dataType(DataTypes.STRING)
                .tooltip("tooltip")
                .build();

        assertEquals(INPUT_VIEW_EMPTY, FormControlWriter.writeText(field, null));

        field.setValue("testo");
        assertEquals(MessageFormat.format(INPUT_VIEW_FULL, "testo"), FormControlWriter.writeText(field, null));

        // Controllo generico
        assertEquals(MessageFormat.format(INPUT_VIEW_FULL, "testo"), FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));

        // State
        field.setState(ControlState.DANGER);
        field.setStateMessage("Lorem ipsum");
        assertEquals(MessageFormat.format(INPUT_VIEW_STATE, "testo", "Lorem ipsum"), FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));

        // Link
        field.setBaseLink("/api/");
        assertEquals(MessageFormat.format(INPUT_VIEW_STATE_LINK, "testo", "Lorem ipsum"), FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));
        assertEquals(MessageFormat.format(INPUT_VIEW_STATE_LINK, "testo", "Lorem ipsum"), FormControlWriter.writeControl(field, null, ViewModality.VIEW, false));
    }

    @Test
    void textTotalizerTest() throws FrameworkException {
        TextTotalizer field = TextTotalizer.builder()
                .name("name")
                .description("description")
                .dataType(DataTypes.INTEGER)
                .tooltip("tooltip")
                .build();

        // TextTotalizer field = new TextTotalizer("name", "description",  DataTypes.INTEGER);
        assertEquals(INPUT_VIEW_EMPTY, FormControlWriter.writeTextTotalizer(field, null));

        field.setValue(BigDecimal.valueOf(10));
        assertEquals(MessageFormat.format(INPUT_VIEW_FULL, 10), FormControlWriter.writeTextTotalizer(field, null));

        // Generico controllo
        assertEquals(MessageFormat.format(INPUT_VIEW_FULL, 10), FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));
    }

}
