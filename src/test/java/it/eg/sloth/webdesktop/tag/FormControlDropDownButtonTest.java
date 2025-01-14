package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.AbstractTest;
import it.eg.sloth.form.fields.field.impl.DropDownButton;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
class FormControlDropDownButtonTest extends AbstractTest {

    private DropDownButton field;

    @BeforeEach
    void init() {
        List<DropDownButton.DropDownItem> list = new ArrayList<>();
        list.add(new DropDownButton.DropDownItem("code1", "description 1", ""));
        list.add(new DropDownButton.DropDownItem("code2", "description 2", ""));

        field = DropDownButton.builder()
                .name("Name")
                .description("description")
                .href("Page.html")
                .dropDownItemList(list)
                .build();
    }

    @Test
    void dropDownButton() throws FrameworkException {
        assertEqualsStr("dropDownButton.html", FormControlWriter.writeDropDownButton(field));

        assertEqualsStr("dropDownButton.html", FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));
    }

    @Test
    void dropDownButtonEmpty() throws FrameworkException {
        field.setDropDownItemList(null);

        assertEqualsStr("dropDownButtonHidden.html", FormControlWriter.writeDropDownButton(field));
    }


    @Test
    void dropDownButtonHidden() throws FrameworkException {
        field.setHidden(true);

        assertEquals(StringUtil.EMPTY, FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));
    }

}
