package it.eg.sloth.webdesktop.tag.form.field.writer;

import it.eg.sloth.db.decodemap.DecodeMap;
import it.eg.sloth.db.decodemap.DecodeValue;
import it.eg.sloth.form.ControlState;
import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.base.Elements;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.impl.*;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.ExceptionCode;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.BootStrapClass;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;

import java.text.MessageFormat;

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
public class FormControlWriter extends HtmlWriter {

    public static final String DROPDOWN_BUTTON = " <button class=\"{1}\" type=\"button\" data-toggle=\"dropdown\" aria-expanded=\"false\">{0}</button>\n";
    public static final String DROPDOWN_BUTTON_EMPTY = " <button class=\"{1}\" type=\"button\">{0}</button>\n";
    public static final String DROPDOWN_ITEM = "  <a class=\"dropdown-item\" href=\"{2}?{3}={0}\">{1}</a>\n";

    public static final String INPUT = "<input id=\"{0}\" name=\"{0}\" type=\"{1}\" value=\"{2}\"{3}/>";

    public static final String INPUT_VIEW = "<div{1} style=\"height: auto;\">{0}</div>";

    public static final String TEXT_AREA_VIEW = "<div{1}{2}>{0}</div>";

    public static final String FILE_GENERIC_VIEW = "<button{0} type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Download file\"><i class=\"fas fa-download\"></i> Download</button>";
    public static final String FILE_GENERIC_VIEW_NOT_EXISTS = "<span class=\"btn btn-link btn-sm text-secondary\"><i class=\"fas fa-download\"></i> Non presente</span>";

    public static final String FILE_GENERIC_EDIT = "<div class=\"custom-file small\"><input{0}{1}{2}{3} class=\"custom-file-input\"><label class=\"custom-file-label\"{4}>Choose file</label></div>";
    public static final String MULTIPLE_FILE_GENERIC_EDIT = "<div class=\"custom-file small\"><input{0}{1}{2}{3} class=\"custom-file-input\" multiple=\"multiple\"><label class=\"custom-file-label\"{4}>Choose file</label></div>";

    public static final String INPUT_GROUP = "<div class=\"input-group input-group-sm\">{0}{1}</div>{2}";
    public static final String INPUT_GROUP_STATE_MESSAGE = "<div class=\"small {0}\">{1}</div>";

    // Scrive un Controllo
    public static String writeControl(SimpleField element, Elements<?> parentElement, ViewModality pageViewModality, boolean overflow) throws FrameworkException {
        switch (element.getFieldType()) {
            case AUTO_COMPLETE:
                return writeAutoComplete((AutoComplete) element, parentElement, pageViewModality);
            case BUTTON:
                return writeButton((Button) element);
            case CHECK_BOX:
                return writeCheckBox((CheckBox) element, pageViewModality);
            case CHECK_BUTTONS:
                return writeCheckButtons((CheckButtons<?, Object>) element, pageViewModality);
            case CHECK_GROUP:
                return writeCheckGroups((CheckGroup<?, Object>) element, pageViewModality);
            case COMBO_BOX:
                return writeComboBox((ComboBox) element, parentElement, pageViewModality);
            case DECODED_TEXT:
                return writeDecodedText((DecodedText) element, parentElement);
            case DROPDOWN_BUTTON:
                return writeDropDownButton((DropDownButton) element);
            case FILE:
                return writeFile((File) element, pageViewModality);
            case HIDDEN:
                return writeHidden((Hidden) element);
            case INPUT:
                return writeInput((Input) element, parentElement, pageViewModality);
            case INPUT_TOTALIZER:
                return writeInputTotalizer((InputTotalizer) element, parentElement, pageViewModality);
            case MULTIPLE_AUTO_COMPLETE:
                return writeMultipleAutoComplete((MultipleAutoComplete) element, parentElement, pageViewModality);
            case MULTIPLE_FILE:
                return writeMultipleFile((MultipleFile) element, pageViewModality);
            case LINK:
                return writeLink((Link) element);
            case RADIO_BUTTONS:
                return writeRadioButtons((RadioButtons) element, pageViewModality);
            case RADIO_GROUP:
                return writeRadioGroup((RadioGroup) element, pageViewModality);
            case SEMAPHORE:
                return writeSemaphore((Semaphore) element, pageViewModality);
            case SWITCH:
                return writeSwitch((Switch) element, pageViewModality);
            case TEXT:
                return writeText((Text) element, parentElement);
            case TEXT_AREA:
                return writeTextArea((TextArea) element, pageViewModality, overflow);
            case TEXT_TOTALIZER:
                return writeTextTotalizer((TextTotalizer) element, parentElement);
            default:
                throw new FrameworkException(ExceptionCode.INVALID_CONTROL);
        }

    }

    // Scrive un campo: AutoComplete
    public static String writeAutoComplete(AutoComplete<?> autocomplete, Elements<?> parentElement, ViewModality pageViewModality) throws FrameworkException {
        if (autocomplete.isHidden()) {
            return StringUtil.EMPTY;
        }

        ViewModality viewModality = autocomplete.getViewModality() == ViewModality.AUTO ? pageViewModality : autocomplete.getViewModality();

        String innerHtml;
        if (viewModality == ViewModality.VIEW) {
            innerHtml = MessageFormat.format(
                    INPUT_VIEW,
                    TextControlWriter.writeControlSpace(autocomplete, parentElement),
                    getAttribute(ATTR_CLASS, BootStrapClass.getViewControlClass(autocomplete)) + getTooltipAttributes(autocomplete.getTooltip()));
        } else {
            innerHtml = new StringBuilder()
                    .append(BEGIN_INPUT)
                    .append(getAttribute(ATTR_ID, autocomplete.getName()))
                    .append(getAttribute(ATTR_NAME, autocomplete.getName()))
                    .append(getAttribute(ATTR_VALUE, Casting.getHtml(autocomplete.getDecodedText(), true, true)))
                    .append(getAttribute(ATTR_CLASS, BootStrapClass.getControlClass(autocomplete)))
                    .append(getAttribute(ATTR_READONLY, autocomplete.isReadOnly(), ""))
                    .append(getAttribute(ATTR_PLACEHOLDER, !BaseFunction.isBlank(autocomplete.getPlaceHolder()), autocomplete.getPlaceHolder()))
                    .append(getAttribute("fields", parentElement.getName()))
                    .append("/>")
                    .toString();
        }

        // Gestione stato
        return toInputGroup(innerHtml, autocomplete.getState(), Casting.getHtml(autocomplete.getStateMessage()));
    }

    // Scrive un campo: Button
    public static String writeButton(Button button) {
        if (button.isHidden()) {
            return StringUtil.EMPTY;
        }

        StringBuilder result = new StringBuilder()
                .append("<button")
                .append(getAttribute(ATTR_ID, button.getHtlmName()))
                .append(getAttribute(ATTR_NAME, button.getHtlmName()))
                .append(getAttribute(ATTR_CLASS, MessageFormat.format(BootStrapClass.BUTTON_CLASS, button.getButtonType().value())))
                .append(getAttribute(ATTR_DISABLED, button.isDisabled(), ""))
                .append(getAttribute(ON_CLICK, Boolean.TRUE.equals(button.isLoading()) || !BaseFunction.isBlank(button.getConfirmMessage()), "buttonConfirm(this);"))
                .append(getAttribute(ATTR_DATA_TITLE, !BaseFunction.isBlank(button.getConfirmMessage()), "Conferma"))
                .append(getAttribute(ATTR_DATA_DESCRIPTION, !BaseFunction.isBlank(button.getConfirmMessage()), Casting.getHtml(button.getConfirmMessage())))
                .append(getAttribute(ATTR_DATA_LOADING, Boolean.TRUE.equals(button.getLoading()), "true"))
                .append(getTooltipAttributes(button.getTooltip()))
                .append(">")
                .append(BaseFunction.nvl(button.getImgHtml(), ""))
                .append(BaseFunction.isBlank(button.getImgHtml()) || BaseFunction.isBlank(button.getDescription()) ? "" : "&nbsp;")
                .append(Casting.getHtml(button.getDescription()))
                .append("</button>");

        return result.toString();
    }

    // Scrive un campo: CheckBox
    public static String writeCheckBox(CheckBox<?> checkBox, ViewModality pageViewModality) {
        if (checkBox.isHidden()) {
            return StringUtil.EMPTY;
        }

        ViewModality viewModality = checkBox.getViewModality() == ViewModality.AUTO ? pageViewModality : checkBox.getViewModality();
        StringBuilder result = new StringBuilder()
                .append("<div class=\"custom-control custom-checkbox\"><input")
                .append(getAttribute(ATTR_ID, checkBox.getName()))
                .append(getAttribute(ATTR_NAME, checkBox.getName()))
                .append(getAttribute(ATTR_TYPE, VAL_ATTR_TYPE_CHECKBOX))
                .append(getAttribute(ATTR_VALUE, checkBox.getValChecked().toString()))
                .append(getAttribute(ATTR_DISABLED, viewModality == ViewModality.VIEW || checkBox.isReadOnly(), ""))
                .append(getAttribute(ATTR_CLASS, BootStrapClass.CHECK_CLASS))
                .append(getAttribute(ATTR_CHECKED, checkBox.isChecked(), ""))
                .append("/>");

        if (viewModality == ViewModality.VIEW) {
            result.append("<div")
                    .append(getAttribute(ATTR_CLASS, "custom-control-label"))
                    .append("></div>");
        } else {
            result
                    .append("<label")
                    .append(getAttribute(ATTR_CLASS, "custom-control-label"))
                    .append(getAttribute(ATTR_FOR, checkBox.getName()))
                    .append("></label>");
        }

        return result
                .append("</div>")
                .toString();
    }

    // Scrive un campo: CheckButtons
    public static String writeCheckButtons(CheckButtons<?, Object> checkButtons, ViewModality pageViewModality) throws FrameworkException {
        if (checkButtons.isHidden()) {
            return StringUtil.EMPTY;
        }

        String[] values = checkButtons.getSplittedValues();
        String[] descriptions = checkButtons.getSplittedDescriptions();

        ViewModality viewModality = checkButtons.getViewModality() == ViewModality.AUTO ? pageViewModality : checkButtons.getViewModality();
        StringBuilder result = new StringBuilder()
                .append("<div class=\"btn-group btn-group-toggle d-flex\" data-toggle=\"buttons\">");

        int i = 0;
        for (String value : values) {
            String htmlValue = Casting.getHtml(value);
            String description = descriptions.length > i ? descriptions[i] : StringUtil.EMPTY;
            boolean active = checkButtons.isChecked(checkButtons.getDataType().parseValue(value, checkButtons.getLocale(), checkButtons.getFormat()));

            if (viewModality == ViewModality.VIEW) {
                result.append(MessageFormat.format("<label{0}>", getAttribute(ATTR_CLASS, active, "btn btn-primary btn-sm disabled", "btn btn-outline-primary btn-sm disabled")))
                        .append(Casting.getHtml(description));
            } else {
                result.append(MessageFormat.format("<label{0}>", getAttribute(ATTR_CLASS, active, "btn btn-outline-primary btn-sm active", "btn btn-outline-primary btn-sm ")))
                        .append("<input ")
                        .append(getAttribute(ATTR_ID, checkButtons.getName()))
                        .append(getAttribute(ATTR_NAME, checkButtons.getName()))
                        .append(getAttribute(ATTR_TYPE, VAL_ATTR_TYPE_CHECKBOX))
                        .append(getAttribute(ATTR_VALUE, htmlValue))
                        .append(getAttribute(ATTR_CHECKED, active, ""))
                        .append(">" + Casting.getHtml(description));
            }

            result.append("</label>");

            i++;
        }

        return result
                .append("</div>")
                .toString();
    }

    // Scrive un campo: CheckGroups
    public static String writeCheckGroups(CheckGroup<?, Object> checkGroup, ViewModality pageViewModality) throws FrameworkException {
        if (checkGroup.isHidden()) {
            return StringUtil.EMPTY;
        }

        String[] values = checkGroup.getSplittedValues();
        String[] descriptions = checkGroup.getSplittedDescriptions();

        ViewModality viewModality = checkGroup.getViewModality() == ViewModality.AUTO ? pageViewModality : checkGroup.getViewModality();
        StringBuilder result = new StringBuilder();

        int i = 0;
        for (String value : values) {
            String htmlValue = Casting.getHtml(value);
            String description = descriptions.length > i ? descriptions[i] : StringUtil.EMPTY;
            boolean active = checkGroup.isChecked(checkGroup.getDataType().parseValue(value, checkGroup.getLocale(), checkGroup.getFormat()));

            result
                    .append(" <div class=\"custom-control custom-checkbox custom-control-inline form-control-sm\">\n")
                    .append("  <input")
                    .append(getAttribute(ATTR_ID, checkGroup.getName() + i))
                    .append(getAttribute(ATTR_NAME, checkGroup.getName()))
                    .append(getAttribute(ATTR_TYPE, VAL_ATTR_TYPE_CHECKBOX))
                    .append(getAttribute(ATTR_VALUE, htmlValue))
                    .append(getAttribute(ATTR_READONLY, checkGroup.isReadOnly(), ""))
                    .append(getAttribute(ATTR_DISABLED, viewModality == ViewModality.VIEW, ""))
                    .append(getAttribute(ATTR_CHECKED, active, ""))
                    .append(getAttribute(ATTR_CLASS, "custom-control-input"))
                    .append(">");

            if (viewModality == ViewModality.VIEW) {
                result.append("<div class=\"custom-control-label\">")
                        .append(Casting.getHtml(description))
                        .append("</div>\n");
            } else {
                result.append("<label class=\"custom-control-label\"")
                        .append(getAttribute(ATTR_FOR, checkGroup.getName() + i))
                        .append(">")
                        .append(Casting.getHtml(description))
                        .append("</label>\n");
            }

            result.append(" </div>\n");

            i++;
        }

        return result.toString();
    }

    // Scrive un campo: ComboBox
    public static String writeComboBox(ComboBox<?> comboBox, Elements<?> parentElement, ViewModality pageViewModality) throws FrameworkException {
        if (comboBox.isHidden()) {
            return StringUtil.EMPTY;
        }

        ViewModality viewModality = comboBox.getViewModality() == ViewModality.AUTO ? pageViewModality : comboBox.getViewModality();
        String innerHtml;
        if (viewModality == ViewModality.VIEW) {

            innerHtml = MessageFormat.format(
                    INPUT_VIEW,
                    TextControlWriter.writeControlSpace(comboBox, parentElement),
                    getAttribute(ATTR_CLASS, BootStrapClass.getViewControlClass(comboBox)) + getTooltipAttributes(comboBox.getTooltip()));

        } else {
            StringBuilder result = new StringBuilder()
                    .append("<select")
                    .append(getAttribute(ATTR_ID, comboBox.getName()))
                    .append(getAttribute(ATTR_NAME, comboBox.getName()))
                    .append(getAttribute(ATTR_VALUE, comboBox.escapeHtmlValue()))
                    .append(getAttribute(ATTR_CLASS, BootStrapClass.getControlClass(comboBox)))
                    .append(getAttribute(ATTR_DISABLED, comboBox.isReadOnly(), ""));

            result.append(">");

            if (!comboBox.isRequired()) {
                result.append("<option value=\"\"></option>");
            }

            DecodeMap<?, ?> values = comboBox.getDecodeMap();
            if (values != null) {
                for (DecodeValue<?> value : values) {
                    if (BaseFunction.isNull(value.getCode())) {
                        continue;
                    }

                    String valueHtml = Casting.getHtml(comboBox.getDataType().formatValue(value.getCode(), comboBox.getLocale(), comboBox.getFormat()), false, false);
                    String descriptionHtml = Casting.getHtml(value.getDescription());

                    result.append("<option")
                            .append(getAttribute(ATTR_VALUE, valueHtml))
                            .append(getAttribute("selected", value.getCode().equals(comboBox.getValue()), "selected"))
                            .append(getAttribute(ATTR_CLASS, !value.isValid(), "notValid"))
                            .append(">" + descriptionHtml + "</option>");
                }
            }

            innerHtml = result.append("</select>").toString();
        }

        // Gestione stato
        return toInputGroup(innerHtml, comboBox.getState(), Casting.getHtml(comboBox.getStateMessage()));
    }

    // Scrive un campo: DecodedText
    public static String writeDecodedText(DecodedText<?> decodedText, Elements<?> parentElement) throws FrameworkException {
        if (decodedText.isHidden()) {
            return StringUtil.EMPTY;
        }

        String innerHtml = MessageFormat.format(
                INPUT_VIEW,
                TextControlWriter.writeControlSpace(decodedText, parentElement),
                getAttribute(ATTR_CLASS, BootStrapClass.getViewControlClass(decodedText)) + getTooltipAttributes(decodedText.getTooltip()));

        // Gestione stato
        return toInputGroup(innerHtml, decodedText.getState(), Casting.getHtml(decodedText.getStateMessage()));
    }

    // Scrive un campo: DropDownButton
    public static String writeDropDownButton(DropDownButton dropDownButton) {
        if (dropDownButton.isHidden()) {
            return StringUtil.EMPTY;
        }


        if (dropDownButton.getDropDownItemList() != null && !dropDownButton.getDropDownItemList().isEmpty()) {
            String buttonClass = MessageFormat.format(BootStrapClass.DROP_DOWNBUTTON, dropDownButton.getButtonType().value());

            StringBuilder result = new StringBuilder()
                    .append("<div class=\"dropdown\">\n")
                    .append(MessageFormat.format(DROPDOWN_BUTTON, Casting.getHtml(dropDownButton.getDescription()), buttonClass));

            result.append(" <div class=\"dropdown-menu\">\n");
            for (DropDownButton.DropDownItem item : dropDownButton.getDropDownItemList()) {
                result.append(MessageFormat.format(DROPDOWN_ITEM, item.getCode(), item.getDescription(), dropDownButton.getHref(), NavigationConst.navStr(NavigationConst.BUTTON, dropDownButton.getName())));
            }

            return result.append(" </div>\n")
                    .append("</div>")
                    .toString();
        } else {
            String buttonClass = MessageFormat.format(BootStrapClass.DROP_DOWNBUTTON_EMPTY, dropDownButton.getButtonType().value());

            return new StringBuilder()
                    .append("<div class=\"dropdown\">\n")
                    .append(MessageFormat.format(DROPDOWN_BUTTON_EMPTY, Casting.getHtml(dropDownButton.getDescription()), buttonClass))
                    .append("</div>")
                    .toString();
        }


    }

    // Scrive un campo: File
    public static String writeFile(File file, ViewModality pageViewModality) {
        if (file.isHidden()) {
            return StringUtil.EMPTY;
        }

        ViewModality viewModality = file.getViewModality() == ViewModality.AUTO ? pageViewModality : file.getViewModality();
        if (viewModality == ViewModality.VIEW) {
            if (file.exists()) {
                return MessageFormat.format(FILE_GENERIC_VIEW, getAttribute(ATTR_NAME, NavigationConst.navStr(NavigationConst.BUTTON, file.getName())));
            } else {
                return FILE_GENERIC_VIEW_NOT_EXISTS;
            }
        } else {
            return MessageFormat.format(FILE_GENERIC_EDIT,
                    getAttribute(ATTR_ID, file.getName()),
                    getAttribute(ATTR_NAME, file.getName()),
                    getAttribute(ATTR_TYPE, "file"),
                    getTooltipAttributes(file.getTooltip()),
                    getAttribute(ATTR_FOR, file.getName()));
        }
    }

    // Scrive un campo: Hidden
    public static String writeHidden(Hidden<?> input) {
        return new StringBuilder()
                .append(BEGIN_INPUT)
                .append(getAttribute(ATTR_ID, input.getName()))
                .append(getAttribute(ATTR_NAME, input.getName()))
                .append(getAttribute(ATTR_TYPE, "hidden"))
                .append(getAttribute(ATTR_VALUE, input.escapeHtmlText()))
                .append("/>")
                .toString();
    }


    // Scrive un campo: Input
    public static String writeInput(Input<?> input, Elements<?> parentElement, ViewModality pageViewModality) throws FrameworkException {
        if (input.isHidden()) {
            return StringUtil.EMPTY;
        }

        ViewModality viewModality = input.getViewModality() == ViewModality.AUTO ? pageViewModality : input.getViewModality();

        String innerHtml;
        if (viewModality == ViewModality.VIEW) {
            innerHtml = MessageFormat.format(
                    INPUT_VIEW,
                    TextControlWriter.writeControlSpace(input, parentElement),
                    getAttribute(ATTR_CLASS, BootStrapClass.getViewControlClass(input)) + getTooltipAttributes(input.getTooltip()));

        } else {
            innerHtml = MessageFormat.format(INPUT,
                    input.getName(),
                    input.getDataType().getHtmlType(),
                    input.escapeHtmlValue(),
                    getAttribute("step", DataTypes.DATETIME == input.getDataType() || DataTypes.TIME == input.getDataType(), "1") +
                            getAttribute("step", DataTypes.HOUR == input.getDataType(), "60") +
                            getAttribute(ATTR_CLASS, BootStrapClass.getControlClass(input)) +
                            getAttribute("placeholder", DataTypes.MONTH == input.getDataType(), "yyyy-mm") +
                            getAttribute(ATTR_READONLY, input.isReadOnly(), "") +
                            getAttribute("maxlength", input.getMaxLength() > 0, "" + input.getMaxLength()) +
                            getTooltipAttributes(input.getTooltip()));
        }

        // Gestione Stato
        return toInputGroup(innerHtml, input.getState(), Casting.getHtml(input.getStateMessage()));
    }

    // Scrive un campo: InputTotalizer
    public static String writeInputTotalizer(InputTotalizer inputTotalizer, Elements<?> parentElement, ViewModality pageViewModality) throws FrameworkException {
        return writeInput(inputTotalizer, parentElement, pageViewModality);
    }

    // Scrive un campo: Link
    public static String writeLink(Link link) {
        if (link.isHidden()) {
            return StringUtil.EMPTY;
        }

        StringBuilder result = new StringBuilder()
                .append("<a")
                .append(getAttribute(ATTR_HREF, !BaseFunction.isBlank(link.getHref()), link.getHref()))
                .append(getAttribute(ATTR_TARGET, !BaseFunction.isBlank(link.getTarget()), link.getTarget()))
                .append(getAttribute(ATTR_CLASS, MessageFormat.format(BootStrapClass.BUTTON_CLASS, link.getButtonType().value())))
                .append(getAttribute(ATTR_DISABLED, link.isDisabled(), ""))
                .append(getTooltipAttributes(link.getTooltip()))
                .append("/>")
                .append(BaseFunction.nvl(link.getImgHtml(), ""))
                .append(BaseFunction.isBlank(link.getImgHtml()) || BaseFunction.isBlank(link.getDescription()) ? "" : "&nbsp;")
                .append(Casting.getHtml(link.getDescription()))
                .append("</a>");

        return result.toString();
    }

    // Scrive un campo: MultipleAutoComplete
    public static <T> String writeMultipleAutoComplete(MultipleAutoComplete<T> multipleAutoComplete, Elements<?> parentElement, ViewModality pageViewModality) throws FrameworkException {
        if (multipleAutoComplete.isHidden()) {
            return StringUtil.EMPTY;
        }

        ViewModality viewModality = multipleAutoComplete.getViewModality() == ViewModality.AUTO ? pageViewModality : multipleAutoComplete.getViewModality();

        String innerHtml;
        if (viewModality == ViewModality.VIEW || multipleAutoComplete.isReadOnly()) {
            innerHtml = MessageFormat.format(
                    INPUT_VIEW,
                    TextControlWriter.writeControlSpace(multipleAutoComplete, parentElement),
                    getAttribute(ATTR_CLASS, BootStrapClass.getViewControlClass(multipleAutoComplete)) + getTooltipAttributes(multipleAutoComplete.getTooltip()));


        } else {
            innerHtml = new StringBuilder()
                    .append(BEGIN_INPUT)
                    .append(getAttribute(ATTR_ID, multipleAutoComplete.getName()))
                    .append(getAttribute(ATTR_NAME, multipleAutoComplete.getName()))
                    .append(getAttribute(ATTR_VALUE, Casting.getHtml(multipleAutoComplete.getDecodedText(), true, true)))
                    .append(getAttribute(ATTR_CLASS, BootStrapClass.getControlClass(multipleAutoComplete)))
                    .append(getAttribute(ATTR_READONLY, multipleAutoComplete.isReadOnly(), ""))
                    .append(getAttribute(ATTR_PLACEHOLDER, !BaseFunction.isBlank(multipleAutoComplete.getPlaceHolder()), multipleAutoComplete.getPlaceHolder()))
                    .append(getAttribute("invalid", multipleAutoComplete.getInvalidDecodedText()))
                    .append(getAttribute("fields", parentElement.getName()))
                    .append("/>")
                    .toString();
        }

        // Gestione link/stato
        return innerHtml;
    }

    // Scrive un campo: MultipleFile
    public static String writeMultipleFile(MultipleFile file, ViewModality pageViewModality) {
        if (file.isHidden()) {
            return StringUtil.EMPTY;
        }

        ViewModality viewModality = file.getViewModality() == ViewModality.AUTO ? pageViewModality : file.getViewModality();
        if (viewModality == ViewModality.VIEW) {
            return MessageFormat.format(FILE_GENERIC_VIEW, getAttribute(ATTR_NAME, NavigationConst.navStr(NavigationConst.BUTTON, file.getName())));
        } else {
            return MessageFormat.format(MULTIPLE_FILE_GENERIC_EDIT,
                    getAttribute(ATTR_ID, file.getName()),
                    getAttribute(ATTR_NAME, file.getName()),
                    getAttribute(ATTR_TYPE, "file"),
                    getTooltipAttributes(file.getTooltip()),
                    getAttribute(ATTR_FOR, file.getName()));
        }
    }


    // Scrive un campo: RadioButtons
    public static String writeRadioButtons(RadioButtons<?> radioButtons, ViewModality pageViewModality) throws FrameworkException {
        if (radioButtons.isHidden()) {
            return StringUtil.EMPTY;
        }

        ViewModality viewModality = radioButtons.getViewModality() == ViewModality.AUTO ? pageViewModality : radioButtons.getViewModality();
        StringBuilder result = new StringBuilder()
                .append("<div class=\"btn-group btn-group-toggle d-flex\" data-toggle=\"buttons\">");

        // Opzioni
        DecodeMap<?, ?> decodeMap = radioButtons.getDecodeMap();
        if (decodeMap != null) {
            for (DecodeValue<?> value : decodeMap) {
                String htmlValue = Casting.getHtml(radioButtons.getDataType().formatValue(value.getCode(), radioButtons.getLocale(), radioButtons.getFormat()));
                boolean active = value.getCode() != null && value.getCode().equals(radioButtons.getValue());

                if (viewModality == ViewModality.VIEW) {
                    result.append(MessageFormat.format("<label{0}>", getAttribute(ATTR_CLASS, active, "btn btn-primary btn-sm disabled", "btn btn-outline-primary btn-sm disabled")))
                            .append(Casting.getHtml(value.getDescription()));
                } else {
                    result.append(MessageFormat.format("<label{0}>", getAttribute(ATTR_CLASS, active, "btn btn-outline-primary btn-sm active", "btn btn-outline-primary btn-sm ")))
                            .append("<input ")
                            .append(getAttribute(ATTR_ID, radioButtons.getName()))
                            .append(getAttribute(ATTR_NAME, radioButtons.getName()))
                            .append(getAttribute(ATTR_TYPE, VAL_ATTR_TYPE_RADIO))
                            .append(getAttribute(ATTR_VALUE, htmlValue))
                            .append(getAttribute(ATTR_CHECKED, active, ""))
                            .append(">" + Casting.getHtml(value.getDescription()));
                }

                result.append("</label>");
            }
        }

        return result
                .append("</div>")
                .toString();
    }


    // Scrive un campo: RadioGroup
    public static String writeRadioGroup(RadioGroup<?> radioGroup, ViewModality pageViewModality) throws FrameworkException {
        if (radioGroup.isHidden()) {
            return StringUtil.EMPTY;
        }

        ViewModality viewModality = radioGroup.getViewModality() == ViewModality.AUTO ? pageViewModality : radioGroup.getViewModality();
        StringBuilder result = new StringBuilder();

        // Opzioni
        int i = 0;
        DecodeMap<?, ?> decodeMap = radioGroup.getDecodeMap();
        if (decodeMap != null) {
            for (DecodeValue<?> value : decodeMap) {
                String htmlValue = Casting.getHtml(radioGroup.getDataType().formatValue(value.getCode(), radioGroup.getLocale(), radioGroup.getFormat()));

                result
                        .append(" <div class=\"custom-control custom-radio custom-control-inline form-control-sm\">\n")
                        .append("  <input")
                        .append(getAttribute(ATTR_ID, radioGroup.getName() + i))
                        .append(getAttribute(ATTR_NAME, radioGroup.getName()))
                        .append(getAttribute(ATTR_TYPE, VAL_ATTR_TYPE_RADIO))
                        .append(getAttribute(ATTR_VALUE, htmlValue))
                        .append(getAttribute(ATTR_READONLY, radioGroup.isReadOnly(), ""))
                        .append(getAttribute(ATTR_DISABLED, viewModality == ViewModality.VIEW, ""))
                        .append(getAttribute(ATTR_CHECKED, value.getCode() != null && value.getCode().equals(radioGroup.getValue()), ""))
                        .append(getAttribute(ATTR_CLASS, "custom-control-input"))
                        .append(">");

                if (viewModality == ViewModality.VIEW) {
                    result.append("<div class=\"custom-control-label\">")
                            .append(Casting.getHtml(value.getDescription()))
                            .append("</div>\n");
                } else {
                    result.append("<label class=\"custom-control-label\"")
                            .append(getAttribute(ATTR_FOR, radioGroup.getName() + i))
                            .append(">")
                            .append(Casting.getHtml(value.getDescription()))
                            .append("</label>\n");
                }

                result.append(" </div>\n");

                i++;
            }
        }

        return result.toString();
    }

    // Scrive un campo: Semaforo
    public static String writeSemaphore(Semaphore semaphore, ViewModality pageViewModality) throws FrameworkException {
        if (semaphore.isHidden()) {
            return StringUtil.EMPTY;
        }

        ViewModality viewModality = semaphore.getViewModality() == ViewModality.AUTO ? pageViewModality : semaphore.getViewModality();
        StringBuilder result = new StringBuilder()
                .append("<div class=\"btn-group btn-group-toggle d-flex\" data-toggle=\"buttons\">");

        // Opzioni
        DecodeMap<String, ?> decodeMap = semaphore.getDecodeMap();
        if (decodeMap != null) {
            for (DecodeValue<String> value : decodeMap) {
                String htmlValue = Casting.getHtml(semaphore.getDataType().formatValue(value.getCode(), semaphore.getLocale(), semaphore.getFormat()));
                boolean active = value.getCode() != null && value.getCode().equals(semaphore.getValue());

                String btnOutline;
                String btn;
                switch (value.getCode()) {
                    case Semaphore.GREEN:
                        btnOutline = "btn-outline-success";
                        btn = "btn-success";
                        break;
                    case Semaphore.YELLOW:
                        btnOutline = "btn-outline-warning";
                        btn = "btn-warning";
                        break;
                    case Semaphore.RED:
                        btnOutline = "btn-outline-danger";
                        btn = "btn-danger";
                        break;
                    case Semaphore.BLACK:
                        btnOutline = "btn-outline-dark";
                        btn = "btn-dark";
                        break;
                    default:
                        btnOutline = "btn-outline-secondary";
                        btn = "btn-secondary";
                }


                if (viewModality == ViewModality.VIEW) {
                    result.append(MessageFormat.format("<label{0}>", getAttribute(ATTR_CLASS, active, "btn " + btn + " btn-sm disabled", "btn " + btnOutline + " btn-sm disabled")))
                            .append("<i class=\"far fa-circle\"></i>");
                } else {
                    result.append(MessageFormat.format("<label{0}>", getAttribute(ATTR_CLASS, active, "btn " + btnOutline + " btn-sm active", "btn " + btnOutline + " btn-sm ")))
                            .append("<input ")
                            .append(getAttribute(ATTR_ID, semaphore.getName()))
                            .append(getAttribute(ATTR_NAME, semaphore.getName()))
                            .append(getAttribute(ATTR_TYPE, VAL_ATTR_TYPE_RADIO))
                            .append(getAttribute(ATTR_VALUE, htmlValue))
                            .append(getAttribute(ATTR_CHECKED, active, ""))
                            .append(">" + "<i class=\"far fa-circle\"></i>");
                }

                result.append("</label>");
            }
        }

        return result
                .append("</div>")
                .toString();
    }

    // Scrive un campo: Switch
    public static String writeSwitch(Switch<?> field, ViewModality pageViewModality) {
        if (field.isHidden()) {
            return StringUtil.EMPTY;
        }

        ViewModality viewModality = field.getViewModality() == ViewModality.AUTO ? pageViewModality : field.getViewModality();
        StringBuilder result = new StringBuilder()
                .append("<div class=\"custom-control custom-switch\"><input")
                .append(getAttribute(ATTR_ID, field.getName()))
                .append(getAttribute(ATTR_NAME, field.getName()))
                .append(getAttribute(ATTR_TYPE, VAL_ATTR_TYPE_CHECKBOX))
                .append(getAttribute(ATTR_VALUE, field.getValChecked().toString()))
                .append(getAttribute(ATTR_DISABLED, viewModality == ViewModality.VIEW || field.isReadOnly(), ""))
                .append(getAttribute(ATTR_CLASS, BootStrapClass.CHECK_CLASS))
                .append(getAttribute(ATTR_CHECKED, field.isChecked(), ""))
                .append("/>");

        if (viewModality == ViewModality.VIEW) {
            result.append("<div")
                    .append(getAttribute(ATTR_CLASS, "custom-control-label"))
                    .append("></div>");
        } else {
            result
                    .append("<label")
                    .append(getAttribute(ATTR_CLASS, "custom-control-label"))
                    .append(getAttribute("for", field.getName()))
                    .append("></label>");
        }

        return result
                .append("</div>")
                .toString();
    }

    // Scrive un campo: Text
    public static String writeText(Text<?> text, Elements<?> parentElement) throws FrameworkException {
        if (text.isHidden()) {
            return StringUtil.EMPTY;
        }

        String innerHtml = MessageFormat.format(
                INPUT_VIEW,
                TextControlWriter.writeControlSpace(text, parentElement),
                getAttribute(ATTR_CLASS, BootStrapClass.getViewControlClass(text)) + getTooltipAttributes(text.getTooltip()));

        // Gestione Stato
        return toInputGroup(innerHtml, text.getState(), Casting.getHtml(text.getStateMessage()));
    }

    // Scrive un campo: TextArea
    public static String writeTextArea(TextArea<?> textArea, ViewModality pageViewModality, boolean overflow) throws FrameworkException {
        if (textArea.isHidden()) {
            return StringUtil.EMPTY;
        }


        ViewModality viewModality = textArea.getViewModality() == ViewModality.AUTO ? pageViewModality : textArea.getViewModality();
        if (viewModality == ViewModality.VIEW) {
            return MessageFormat.format(
                    TEXT_AREA_VIEW,
                    TextControlWriter.writeControlSpace(textArea, null),
                    getAttribute(ATTR_CLASS, BootStrapClass.getViewControlClass(textArea)) + getTooltipAttributes(textArea.getTooltip()),
                    getAttribute(ATTR_STYLE, overflow ? "min-height: 100%; overflow-y: auto;" : "height: 100%;")
            );

        } else {
            return new StringBuilder()
                    .append("<textarea")
                    .append(getAttribute(ATTR_ID, textArea.getName()))
                    .append(getAttribute(ATTR_NAME, textArea.getName()))
                    .append(getAttribute(ATTR_CLASS, BootStrapClass.getControlClass(textArea)))
                    .append(getAttribute(ATTR_STYLE, overflow ? "min-height: 100%; overflow-y: auto;" : "min-height: 100%;"))
                    .append(getAttribute("rows", textArea.getRows().toString()))
                    .append(getAttribute(ATTR_READONLY, textArea.isReadOnly(), ""))
                    .append(getAttribute("maxlength", textArea.getMaxLength() > 0, "" + textArea.getMaxLength()))
                    .append(">" + textArea.escapeHtmlValue() + "</textarea>")
                    .toString();

        }
    }

    // Scrive un campo: TextTotalizer
    public static String writeTextTotalizer(TextTotalizer textTotalizer, Elements<?> parentElement) throws FrameworkException {
        return writeText(textTotalizer, parentElement);
    }

    private static final String toInputGroup(String innerHtml, ControlState controlState, String stateMessageHtml) {
        if (controlState != null || !BaseFunction.isBlank(stateMessageHtml)) {
            String wrappedStateMessageHtml = MessageFormat.format(INPUT_GROUP_STATE_MESSAGE, BootStrapClass.getBootstrapTextClass(controlState), stateMessageHtml);
            return MessageFormat.format(INPUT_GROUP, innerHtml, "", wrappedStateMessageHtml);
        } else {
            return innerHtml;
        }
    }

}
