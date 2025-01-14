package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.AbstractTest;
import it.eg.sloth.form.fields.field.impl.File;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
class FormControlFileTest extends AbstractTest {

    private File field;

    @BeforeEach
    void init() {
        field = File.builder()
                .name("Name")
                .description("description")
                .tooltip("tooltip")
                .build();
    }

    @Test
    void baseView() throws FrameworkException {
        assertEqualsStr("baseView.html", FormControlWriter.writeFile(field, ViewModality.VIEW));
        assertEqualsStr("baseView.html", FormControlWriter.writeControl(field, null, ViewModality.VIEW, false));
    }

    @Test
    void baseView_notExists() throws FrameworkException {
        field.setExists(false);
        assertEqualsStr("baseView_notExists.html", FormControlWriter.writeFile(field, ViewModality.VIEW));
        assertEqualsStr("baseView_notExists.html", FormControlWriter.writeControl(field, null, ViewModality.VIEW, false));
    }

    @Test
    void baseEdit() throws FrameworkException {
        assertEqualsStr("baseEdit.html", FormControlWriter.writeFile(field, ViewModality.EDIT));
        assertEqualsStr("baseEdit.html", FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));
    }
}
