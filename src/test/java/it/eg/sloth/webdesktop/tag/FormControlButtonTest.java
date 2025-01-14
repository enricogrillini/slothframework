package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.AbstractTest;
import it.eg.sloth.form.fields.field.impl.Button;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
class FormControlButtonTest extends AbstractTest {

    private Button field;

    @BeforeEach
    void init() {
        field = Button.builder()
                .name("Name")
                .description("description")
                .build();
    }

    @Test
    void buttonBase() throws FrameworkException {
        assertEqualsStr("baseButton.html", FormControlWriter.writeButton(field));
        assertEqualsStr("baseButton.html", FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));
    }

    @Test
    void buttonHidden() throws FrameworkException {
        field.setHidden(true);
        assertEquals(StringUtil.EMPTY, FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));
    }

    @Test
    void buttonConfirmMessage() {
        field.setConfirmMessage("Prova messaggio");
        assertEqualsStr("buttonConfirmMessage.html", FormControlWriter.writeButton(field));
    }

    @Test
    void buttonLoadingMessage() {
        field.setLoading(true);
        assertEqualsStr("buttonLoadingMessage.html", FormControlWriter.writeButton(field));
    }

}
