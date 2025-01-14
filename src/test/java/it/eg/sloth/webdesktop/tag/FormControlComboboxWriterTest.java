package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.db.decodemap.map.StringDecodeMap;
import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.impl.ComboBox;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
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
class FormControlComboboxWriterTest {

    private static final String VIEW_TAG = ResourceUtil.normalizedResourceAsString("snippet-html/form-control/combobox-view.html");
    private static final String LINK_TAG = ResourceUtil.normalizedResourceAsString("snippet-html/form-control/combobox-link.html");
    private static final String EDIT_TAG = ResourceUtil.normalizedResourceAsString("snippet-html/form-control/combobox-edit.html");

    Fields fields;
    ComboBox<String> field;

    @BeforeEach
    public void init() throws FrameworkException {
        fields = new Fields("Prova");

        StringDecodeMap stringDecodeMap = new StringDecodeMap("A,Scelta A; B, Scelta B");
        stringDecodeMap.get("B").setValid(false);

        field = new ComboBox<String>("name", "description", DataTypes.STRING);
        field.setDecodeMap(stringDecodeMap);
        field.setValue("A");
    }

    @Test
    void comboBoxTest() throws FrameworkException {
        assertEquals(VIEW_TAG, FormControlWriter.writeComboBox(field, fields, ViewModality.VIEW));

        field.setBaseLink("ProvaPage.html?id=");
        assertEquals(LINK_TAG, FormControlWriter.writeComboBox(field, fields, ViewModality.VIEW));

        field.setBaseLink(null);
        assertEquals(EDIT_TAG, FormControlWriter.writeComboBox(field, fields, ViewModality.EDIT));

        // Controllo generico
        assertEquals(EDIT_TAG, FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));

        // Empty
        field.setHidden(true);
        assertEquals(StringUtil.EMPTY, FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));
    }


}
