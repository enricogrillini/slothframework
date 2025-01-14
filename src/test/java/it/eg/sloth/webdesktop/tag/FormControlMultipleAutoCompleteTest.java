package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.TestFactory;
import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.impl.MultipleAutoComplete;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
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
class FormControlMultipleAutoCompleteTest {

    private static final String VIEW_TAG = ResourceUtil.normalizedResourceAsString("snippet-html/form-control/multiple-autocomplete-view.html");
    private static final String EDIT_TAG = ResourceUtil.normalizedResourceAsString("snippet-html/form-control/multiple-autocomplete-edit.html");

    Fields fields;
    MultipleAutoComplete<BigDecimal> multipleAutoComplete;

    @BeforeEach
    public void init() {
        fields = new Fields("Prova");

        multipleAutoComplete = new MultipleAutoComplete<>("Numero", "Numero", DataTypes.INTEGER);
        multipleAutoComplete.setDecodeMap(TestFactory.getBaseDecodeMap());
    }

    @Test
    void autoCompleteTest() throws FrameworkException {
        List<BigDecimal> values = new ArrayList<>();
        values.add(BigDecimal.valueOf(1));
        values.add(BigDecimal.valueOf(2));

        multipleAutoComplete.setValue(values);
        assertEquals(VIEW_TAG, FormControlWriter.writeMultipleAutoComplete(multipleAutoComplete, fields, ViewModality.VIEW));
        assertEquals(EDIT_TAG, FormControlWriter.writeMultipleAutoComplete(multipleAutoComplete, fields, ViewModality.EDIT));

        assertEquals(VIEW_TAG, FormControlWriter.writeControl(multipleAutoComplete, fields, ViewModality.VIEW, false));
        assertEquals(EDIT_TAG, FormControlWriter.writeControl(multipleAutoComplete, fields, ViewModality.EDIT, false));
    }

}
