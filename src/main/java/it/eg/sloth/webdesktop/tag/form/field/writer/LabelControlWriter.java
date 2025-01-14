package it.eg.sloth.webdesktop.tag.form.field.writer;

import it.eg.sloth.form.base.Elements;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.form.fields.field.impl.Button;
import it.eg.sloth.form.fields.field.impl.File;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.BootStrapClass;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;
import it.eg.sloth.webdesktop.tag.form.group.writer.GroupWriter;

public class LabelControlWriter extends HtmlWriter {

    private static boolean hasLabel(SimpleField element) {
        return !(element instanceof Button);
    }

    public static String writeLabel(SimpleField simpleField) {
        if (!hasLabel(simpleField)) {
            return StringUtil.EMPTY;
        }

        String strRequired = StringUtil.EMPTY;
        if (simpleField instanceof InputField && ((InputField<?>) simpleField).isRequired() || simpleField instanceof File && ((File) simpleField).isRequired()) {
            strRequired = "*";
        }


        return new StringBuilder()
                .append("<label")
                .append(getAttribute("for", simpleField.getName()))
                .append(getAttribute(ATTR_CLASS, BootStrapClass.LABEL_CLASS))
                .append(getTooltipAttributes(simpleField.getTooltip()))
                .append(">" + simpleField.getHtmlDescription() + strRequired + ":</label>")
                .toString();
    }

    public static String writeLblControl(SimpleField simpleField, Elements<?> parentElement, ViewModality viewModality, String labelWidth, String controlWidth) throws FrameworkException {
        return writeLblControl(simpleField, parentElement, viewModality, false, labelWidth, controlWidth, labelWidth, controlWidth);
    }

    public static String writeLblControl(SimpleField simpleField, Elements<?> parentElement, ViewModality viewModality, boolean overflow, String labelWidth, String controlWidth, String mLabelWidth, String mControlWidth) throws FrameworkException {
        StringBuilder result = new StringBuilder();

        if (hasLabel(simpleField)) {
            result
                    .append(GroupWriter.openCell(labelWidth, mLabelWidth))
                    .append(writeLabel(simpleField))
                    .append(GroupWriter.closeCell());
        }

        return result
                .append(GroupWriter.openCell(controlWidth, mControlWidth))
                .append(FormControlWriter.writeControl(simpleField, parentElement, viewModality, overflow))
                .append(GroupWriter.closeCell())
                .toString();
    }


}
