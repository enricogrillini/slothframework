package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.db.decodemap.map.StringDecodeMap;
import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.impl.*;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;
import it.eg.sloth.webdesktop.tag.support.SampleEscaper;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;

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
class FormControlWriterTest {

    private static final String BASE_AUTOCOMPLETE_VIEW = "<div class=\"form-control form-control-sm bg-gray-200\" style=\"height: auto;\">{0}</div>";
    private static final String BASE_AUTOCOMPLETE_EDIT = "<input id=\"name\" name=\"name\" value=\"{0}\" class=\"form-control form-control-sm autoComplete\" fields=\"master\"/>";

    private static final String LINK_AUTOCOMPLETE = "<div class=\"form-control form-control-sm bg-gray-200\" style=\"height: auto;\"><a href=\"{1}\" >{0}</a></div>";

    private static final String BASE_CHECKBOX_VIS = "<div class=\"custom-control custom-checkbox\"><input id=\"name\" name=\"name\" type=\"checkbox\" value=\"S\" disabled=\"\" class=\"custom-control-input\"/><div class=\"custom-control-label\"></div></div>";
    private static final String BASE_CHECKBOX_MOD = "<div class=\"custom-control custom-checkbox\"><input id=\"name\" name=\"name\" type=\"checkbox\" value=\"S\" class=\"custom-control-input\"{0}/><label class=\"custom-control-label\" for=\"name\"></label></div>";

    private static final String LINK_DECODEDTEXT = "<div class=\"form-control form-control-sm bg-gray-200\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\" style=\"height: auto;\"><a href=\"{1}\" >{0}</a></div>";

    private static final String BASE_TEXT = "<div class=\"form-control form-control-sm bg-gray-200\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\" style=\"height: auto;\">{0}</div>";

    private static final String BASE_HIDDEN = "<input id=\"{0}\" name=\"{0}\" type=\"hidden\" value=\"{1}\"/>";

    private static final String BASE_LINK = "<a href=\"{1}\" class=\"btn btn-outline-primary btn-sm\"/>{0}</a>";

    private static final String BASE_SEMAPHORE = "<div class=\"btn-group btn-group-toggle d-flex\" data-toggle=\"buttons\"><label class=\"btn btn-outline-success btn-sm disabled\"><i class=\"far fa-circle\"></i></label><label class=\"btn btn-outline-warning btn-sm disabled\"><i class=\"far fa-circle\"></i></label><label class=\"btn btn-outline-danger btn-sm disabled\"><i class=\"far fa-circle\"></i></label></div>";
    private static final String BASE_SEMAPHORE_MOD = "<div class=\"btn-group btn-group-toggle d-flex\" data-toggle=\"buttons\"><label class=\"btn btn-outline-success btn-sm \"><input  id=\"name\" name=\"name\" type=\"radio\" value=\"G\"><i class=\"far fa-circle\"></i></label><label class=\"btn btn-outline-warning btn-sm \"><input  id=\"name\" name=\"name\" type=\"radio\" value=\"Y\"><i class=\"far fa-circle\"></i></label><label class=\"btn btn-outline-danger btn-sm \"><input  id=\"name\" name=\"name\" type=\"radio\" value=\"R\"><i class=\"far fa-circle\"></i></label></div>";

    private static final String BASE_SWITCH_VIS = "<div class=\"custom-control custom-switch\"><input id=\"name\" name=\"name\" type=\"checkbox\" value=\"S\" disabled=\"\" class=\"custom-control-input\"/><div class=\"custom-control-label\"></div></div>";
    private static final String BASE_SWITCH_MOD = "<div class=\"custom-control custom-switch\"><input id=\"name\" name=\"name\" type=\"checkbox\" value=\"S\" class=\"custom-control-input\"{0}/><label class=\"custom-control-label\" for=\"name\"></label></div>";

    private static final String BASE_RADIOGROUP_VIS = " <div class=\"custom-control custom-radio custom-control-inline form-control-sm\">\n" +
            "  <input id=\"name0\" name=\"name\" type=\"radio\" value=\"S\" disabled=\"\" checked=\"\" class=\"custom-control-input\"><div class=\"custom-control-label\">S&igrave;</div>\n" +
            " </div>\n" +
            " <div class=\"custom-control custom-radio custom-control-inline form-control-sm\">\n" +
            "  <input id=\"name1\" name=\"name\" type=\"radio\" value=\"N\" disabled=\"\" class=\"custom-control-input\"><div class=\"custom-control-label\">No</div>\n" +
            " </div>\n" +
            " <div class=\"custom-control custom-radio custom-control-inline form-control-sm\">\n" +
            "  <input id=\"name2\" name=\"name\" type=\"radio\" value=\"T\" disabled=\"\" class=\"custom-control-input\"><div class=\"custom-control-label\">Tutti</div>\n" +
            " </div>\n";

    private static final String BASE_RADIOGROUP_MOD = " <div class=\"custom-control custom-radio custom-control-inline form-control-sm\">\n" +
            "  <input id=\"name0\" name=\"name\" type=\"radio\" value=\"S\" checked=\"\" class=\"custom-control-input\"><label class=\"custom-control-label\" for=\"name0\">S&igrave;</label>\n" +
            " </div>\n" +
            " <div class=\"custom-control custom-radio custom-control-inline form-control-sm\">\n" +
            "  <input id=\"name1\" name=\"name\" type=\"radio\" value=\"N\" class=\"custom-control-input\"><label class=\"custom-control-label\" for=\"name1\">No</label>\n" +
            " </div>\n" +
            " <div class=\"custom-control custom-radio custom-control-inline form-control-sm\">\n" +
            "  <input id=\"name2\" name=\"name\" type=\"radio\" value=\"T\" class=\"custom-control-input\"><label class=\"custom-control-label\" for=\"name2\">Tutti</label>\n" +
            " </div>\n";

    @Test
    void autoCompleteTest() throws FrameworkException {
        Fields fields = new Fields("Master");
        AutoComplete<String> autocomplete = new AutoComplete<String>("name", "description", DataTypes.STRING);
        autocomplete.setDecodeMap(new StringDecodeMap("A,Scelta A; B, Scelta B"));
        autocomplete.setValue(null);

        verifyAutoComplete(autocomplete, "");
    }

    @Test
    void autoCompleteescaperTest() throws FrameworkException {
        AutoComplete<String> autocomplete = new AutoComplete<String>("name", "description", DataTypes.STRING);
        autocomplete.setDecodeMap(new StringDecodeMap("A,Scelta A; B, Scelta B"));
        autocomplete.setHtmlEscaper(new SampleEscaper());

        verifyAutoComplete(autocomplete, "Escaped - ");
    }


    private void verifyAutoComplete(AutoComplete<String> autocomplete, String escapedPrefix) throws FrameworkException {
        Fields fields = new Fields("Master");

        assertEquals(MessageFormat.format(BASE_AUTOCOMPLETE_VIEW, BaseFunction.nvl(escapedPrefix, "&nbsp;")), FormControlWriter.writeAutoComplete(autocomplete, fields, ViewModality.VIEW));

        autocomplete.setValue("A");
        assertEquals(MessageFormat.format(BASE_AUTOCOMPLETE_VIEW, escapedPrefix + "Scelta A"), FormControlWriter.writeAutoComplete(autocomplete, fields, ViewModality.VIEW));
        assertEquals(MessageFormat.format(BASE_AUTOCOMPLETE_EDIT, "Scelta A"), FormControlWriter.writeAutoComplete(autocomplete, fields, ViewModality.EDIT));

        // Controllo generico
        assertEquals(MessageFormat.format(BASE_AUTOCOMPLETE_EDIT, "Scelta A"), FormControlWriter.writeControl(autocomplete, fields, ViewModality.EDIT, false));

        // Link
        autocomplete.setBaseLink("destPage.html?name=");
        assertEquals(MessageFormat.format(LINK_AUTOCOMPLETE, escapedPrefix + "Scelta A", "destPage.html?name=A"), FormControlWriter.writeAutoComplete(autocomplete, fields, ViewModality.VIEW));

        // Empty
        autocomplete.setHidden(true);
        assertEquals(StringUtil.EMPTY, FormControlWriter.writeControl(autocomplete, null, ViewModality.EDIT, false));
    }

    @Test
    void checkBoxTest() throws FrameworkException {
        CheckBox checkBox = new CheckBox("name", "description", DataTypes.STRING);
        assertEquals(BASE_CHECKBOX_VIS, FormControlWriter.writeCheckBox(checkBox, ViewModality.VIEW));

        checkBox.setChecked();
        assertEquals(MessageFormat.format(BASE_CHECKBOX_MOD, " checked=\"\""), FormControlWriter.writeCheckBox(checkBox, ViewModality.EDIT));

        checkBox.setUnChecked();
        assertEquals(MessageFormat.format(BASE_CHECKBOX_MOD, ""), FormControlWriter.writeCheckBox(checkBox, ViewModality.EDIT));

        // Controllo generico
        assertEquals(MessageFormat.format(BASE_CHECKBOX_MOD, ""), FormControlWriter.writeControl(checkBox, null, ViewModality.EDIT, false));

        // Empty
        checkBox.setHidden(true);
        assertEquals(StringUtil.EMPTY, FormControlWriter.writeControl(checkBox, null, ViewModality.EDIT, false));
    }

    @Test
    void decodedTextTest() throws FrameworkException {
        DecodedText<String> field = new DecodedText<String>("name", "description", "tooltip", DataTypes.STRING);
        field.setDecodeMap(new StringDecodeMap("A,Scelta A; B, Scelta B"));

        assertEquals(MessageFormat.format(BASE_TEXT, "&nbsp;"), FormControlWriter.writeDecodedText(field, null));

        field.setValue("A");
        assertEquals(MessageFormat.format(BASE_TEXT, "Scelta A"), FormControlWriter.writeDecodedText(field, null));

        // Controllo generico
        assertEquals(MessageFormat.format(BASE_TEXT, "Scelta A"), FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));

        // Link
        field.setBaseLink("destPage.html?name=");
        assertEquals(MessageFormat.format(LINK_DECODEDTEXT, "Scelta A", "destPage.html?name=A"), FormControlWriter.writeDecodedText(field, null));
    }

    @Test
    void hiddenTest() throws FrameworkException {
        Hidden<String> field = new Hidden<String>("name", "description", DataTypes.STRING);

        assertEquals(MessageFormat.format(BASE_HIDDEN, "name", ""), FormControlWriter.writeHidden(field));

        field.setValue("testo");
        assertEquals(MessageFormat.format(BASE_HIDDEN, "name", "testo"), FormControlWriter.writeHidden(field));

        // Controllo generico
        assertEquals(MessageFormat.format(BASE_HIDDEN, "name", "testo"), FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));
    }

    @Test
    void linkTest() throws FrameworkException {
        Link link = new Link("name", "description", "www");
        assertEquals(MessageFormat.format(BASE_LINK, "description", "www"), FormControlWriter.writeLink(link));

        // Controllo generico
        assertEquals(MessageFormat.format(BASE_LINK, "description", "www"), FormControlWriter.writeControl(link, null, ViewModality.EDIT, false));

        // Empty
        link.setHidden(true);
        assertEquals(StringUtil.EMPTY, FormControlWriter.writeControl(link, null, ViewModality.EDIT, false));
    }

    @Test
    void radioGroupTest() throws FrameworkException {
        RadioGroup<String> radioGroup = new RadioGroup<String>("name", "description", DataTypes.STRING);
        radioGroup.setDecodeMap(StringDecodeMap.Factory.DECODE_MAP_SNT);
        radioGroup.setValue("S");

        assertEquals(BASE_RADIOGROUP_VIS, FormControlWriter.writeRadioGroup(radioGroup, ViewModality.VIEW));
        assertEquals(MessageFormat.format(BASE_RADIOGROUP_MOD, " checked=\"\""), FormControlWriter.writeRadioGroup(radioGroup, ViewModality.EDIT));

        // Controllo generico
        assertEquals(MessageFormat.format(BASE_RADIOGROUP_MOD, ""), FormControlWriter.writeControl(radioGroup, null, ViewModality.EDIT, false));

        // Empty
        radioGroup.setHidden(true);
        assertEquals(StringUtil.EMPTY, FormControlWriter.writeControl(radioGroup, null, ViewModality.EDIT, false));
    }

    @Test
    void semaphoreTest() throws FrameworkException {
        Semaphore semaphore = new Semaphore("name", "description", DataTypes.STRING);
        semaphore = Semaphore.<String>builder()
                .name("name")
                .description("Semaphore")
                .dataType(DataTypes.STRING)
                .viewModality(ViewModality.AUTO)
                .build();

        assertEquals(BASE_SEMAPHORE, FormControlWriter.writeSemaphore(semaphore, ViewModality.VIEW));

        // Controllo generico
        assertEquals(MessageFormat.format(BASE_SEMAPHORE, ""), FormControlWriter.writeControl(semaphore, null, ViewModality.VIEW, false));

        // VIEW_MODIFICA ancora non gestita
        assertEquals(BASE_SEMAPHORE_MOD, FormControlWriter.writeControl(semaphore, null, ViewModality.EDIT, false));

        // Empty
        semaphore.setHidden(true);
        assertEquals(StringUtil.EMPTY, FormControlWriter.writeControl(semaphore, null, ViewModality.VIEW, false));
    }

    @Test
    void switchTest() throws FrameworkException {
        Switch field = new Switch("name", "description", DataTypes.STRING);
        assertEquals(BASE_SWITCH_VIS, FormControlWriter.writeSwitch(field, ViewModality.VIEW));

        field.setChecked();
        assertEquals(MessageFormat.format(BASE_SWITCH_MOD, " checked=\"\""), FormControlWriter.writeSwitch(field, ViewModality.EDIT));

        field.setUnChecked();
        assertEquals(MessageFormat.format(BASE_SWITCH_MOD, ""), FormControlWriter.writeSwitch(field, ViewModality.EDIT));

        // Controllo generico
        assertEquals(MessageFormat.format(BASE_SWITCH_MOD, ""), FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));

        // Empty
        field.setHidden(true);
        assertEquals(StringUtil.EMPTY, FormControlWriter.writeControl(field, null, ViewModality.EDIT, false));
    }

}
