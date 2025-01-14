package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.AbstractTest;
import it.eg.sloth.form.ControlState;
import it.eg.sloth.form.fields.field.impl.*;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;
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
class FormControlTextAreaWriterTest extends AbstractTest {

    private TextArea<String> field;

    @BeforeEach
    void init() {
        field = new TextArea<String>("name", "description", DataTypes.STRING);
    }

    @Test
    void textArea_Empty() throws FrameworkException {
        assertEqualsStr("textArea_Empty.html", FormControlWriter.writeTextArea(field, ViewModality.VIEW, false));
    }


    @Test
    void textArea_Hidden() throws FrameworkException {
        field.setHidden(true);
        assertEquals(StringUtil.EMPTY, FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));
    }

    @Test
    void textArea_View() throws FrameworkException {
        field.setValue("testo");

        assertEqualsStr("textArea_View.html", FormControlWriter.writeTextArea(field, ViewModality.VIEW, false));
        assertEqualsStr("textArea_View.html", FormControlWriter.writeControl(field, null, ViewModality.VIEW, false));
    }

    @Test
    void textArea_View_Overflow() throws FrameworkException {
        field.setValue("testo");

        assertEqualsStr("textArea_View_Overflow.html", FormControlWriter.writeTextArea(field, ViewModality.VIEW, true));
        assertEqualsStr("textArea_View_Overflow.html", FormControlWriter.writeControl(field, null, ViewModality.VIEW, true));
    }


    @Test
    void textArea_Edit() throws FrameworkException {
        field.setValue("testo");

        assertEqualsStr("textArea_Edit.html", FormControlWriter.writeTextArea(field, ViewModality.EDIT, false));
        assertEqualsStr("textArea_Edit.html", FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));
    }

}
