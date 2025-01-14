package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.db.decodemap.map.StringDecodeMap;
import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.impl.*;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;
import it.eg.sloth.webdesktop.tag.form.field.writer.TextControlWriter;
import org.junit.jupiter.api.BeforeEach;
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
class TextControlWriterTest {

    private static final String BASE_CHECKBOX = "<div class=\"custom-control custom-checkbox\"><input type=\"checkbox\" class=\"custom-control-input\"{0} disabled=\"\"/><span class=\"custom-control-label\"></span></div>";

    private static final String BASE_SWITCH = "<div class=\"custom-control custom-switch\"><input type=\"checkbox\" class=\"custom-control-input\"{0} disabled=\"\"/><span class=\"custom-control-label\"></span></div>";

    Fields fields;

    @BeforeEach
    void init() {
        fields = new Fields("Master");
    }

    @Test
    void buttonTest() throws FrameworkException {
        Button field = new Button("name", "description");
        fields.addChild(field);
        assertEquals(FormControlWriter.writeButton(field), TextControlWriter.writeControl(field, fields));
    }

    @Test
    void checkBoxTest() throws FrameworkException {
        CheckBox field = new CheckBox("name", "description", DataTypes.STRING);
        assertEquals(MessageFormat.format(BASE_CHECKBOX, ""), TextControlWriter.writeControl(field, fields));

        field.setChecked();
        assertEquals(MessageFormat.format(BASE_CHECKBOX, " checked=\"\""), TextControlWriter.writeControl(field, fields));

        field.setUnChecked();
        assertEquals(MessageFormat.format(BASE_CHECKBOX, ""), TextControlWriter.writeControl(field, fields));

        // Generico controllo
        assertEquals(MessageFormat.format(BASE_CHECKBOX, ""), TextControlWriter.writeControl(field, fields));
    }

    @Test
    void comboBoxTest() throws FrameworkException {
        ComboBox<String> field = new ComboBox<String>("name", "description", DataTypes.STRING);
        fields.addChild(field);

        field.setDecodeMap(new StringDecodeMap("A,Scelta A; B, Scelta B"));

        assertEquals(StringUtil.EMPTY, TextControlWriter.writeControl(field, fields));

        field.setValue("A");
        assertEquals("Scelta A", TextControlWriter.writeControl(field, fields));
    }

    @Test
    void decodedTextTest() throws FrameworkException {
        DecodedText<String> field = new DecodedText<String>("name", "description", "tooltip", DataTypes.STRING);
        fields.addChild(field);

        field.setDecodeMap(new StringDecodeMap("A,Scelta A; B, Scelta B"));

        assertEquals(StringUtil.EMPTY, TextControlWriter.writeControl(field, fields));

        field.setValue("A");
        assertEquals("Scelta A", TextControlWriter.writeControl(field, fields));

    }

    @Test
    void hiddenTest() throws FrameworkException {
        Hidden<String> field = new Hidden<String>("name", "description", DataTypes.STRING);
        fields.addChild(field);

        assertEquals(TextControlWriter.writeControl(field, fields), FormControlWriter.writeControl(field, fields, ViewModality.EDIT, false));

        field.setValue("testo");
        assertEquals(TextControlWriter.writeControl(field, fields), FormControlWriter.writeControl(field, fields, ViewModality.EDIT, false));
    }

    @Test
    void inputTest() throws FrameworkException {
        Input<String> field = new Input<String>("name", "description", DataTypes.STRING);
        fields.addChild(field);

        assertEquals(StringUtil.EMPTY, TextControlWriter.writeControl(field, fields));

        field.setValue("testo");
        assertEquals("testo", TextControlWriter.writeControl(field, fields));
    }

    @Test
    void inputDataTest() throws FrameworkException {
        Input<Timestamp> field = new Input<Timestamp>("name", "description", DataTypes.DATE);
        fields.addChild(field);

        field.setLocale(Locale.ITALY);

        assertEquals(StringUtil.EMPTY, TextControlWriter.writeControl(field, fields));

        field.setValue(TimeStampUtil.parseTimestamp("01/01/2020", "dd/MM/yyyy"));
        assertEquals("01/01/2020", TextControlWriter.writeControl(field, fields));
    }

    @Test
    void inputTotalizerTest() throws FrameworkException {
        InputTotalizer field = new InputTotalizer("name", "description", DataTypes.INTEGER);
        fields.addChild(field);

        assertEquals(StringUtil.EMPTY, TextControlWriter.writeControl(field, fields));

        field.setValue(BigDecimal.valueOf(10));
        assertEquals("10", TextControlWriter.writeControl(field, fields));
    }

    @Test
    void switchTest() throws FrameworkException {
        Switch field = new Switch("name", "description", DataTypes.STRING);
        assertEquals(MessageFormat.format(BASE_SWITCH, ""), TextControlWriter.writeControl(field, fields));

        field.setChecked();
        assertEquals(MessageFormat.format(BASE_SWITCH, " checked=\"\""), TextControlWriter.writeControl(field, fields));

        field.setUnChecked();
        assertEquals(MessageFormat.format(BASE_SWITCH, ""), TextControlWriter.writeControl(field, fields));

        // Generico controllo
        assertEquals(MessageFormat.format(BASE_SWITCH, ""), TextControlWriter.writeControl(field, fields));
    }

    @Test
    void textTest() throws FrameworkException {
        Text<String> field = new Text<String>("name", "description", DataTypes.STRING);
        fields.addChild(field);

        assertEquals(StringUtil.EMPTY, TextControlWriter.writeControl(field, fields));

        field.setValue("testo");
        assertEquals("testo", TextControlWriter.writeControl(field, fields));
    }

    @Test
    void textAreaTest() throws FrameworkException {
        TextArea<String> field = new TextArea<String>("name", "description", DataTypes.STRING);
        fields.addChild(field);

        assertEquals(StringUtil.EMPTY, TextControlWriter.writeControl(field, fields));

        field.setValue("testo");
        assertEquals("testo", TextControlWriter.writeControl(field, fields));
    }

    @Test
    void textTotalizerTest() throws FrameworkException {
        TextTotalizer field = new TextTotalizer("name", "description", DataTypes.INTEGER);
        fields.addChild(field);

        assertEquals(StringUtil.EMPTY, TextControlWriter.writeControl(field, fields));

        field.setValue(BigDecimal.valueOf(10));
        assertEquals("10", TextControlWriter.writeControl(field, fields));
    }

}
