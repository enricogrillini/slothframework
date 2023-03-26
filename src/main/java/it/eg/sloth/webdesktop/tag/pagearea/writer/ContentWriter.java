package it.eg.sloth.webdesktop.tag.pagearea.writer;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.decodemap.map.StringDecodeMap;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.form.fields.field.base.MultipleInput;
import it.eg.sloth.form.fields.field.impl.Input;
import it.eg.sloth.form.fields.field.impl.MultipleAutoComplete;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.webdesktop.alertcenter.AlertsCenterSingleton;
import it.eg.sloth.webdesktop.alertcenter.model.Alert;
import it.eg.sloth.webdesktop.parameter.model.ApplicationParameter;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;
import it.eg.sloth.webdesktop.tag.form.field.writer.TextControlWriter;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Locale;

public class ContentWriter extends HtmlWriter {

    public static final String ALERT_CENTER_OPEN = ResourceUtil.normalizedResourceAsString("snippet/pagearea/alert-center-open.html");
    public static final String ALERT_CENTER_ALERT = ResourceUtil.normalizedResourceAsString("snippet/pagearea/alert-center-alert.html");
    public static final String ALERT_CENTER_CLOSE = ResourceUtil.normalizedResourceAsString("snippet/pagearea/alert-center-close.html");

    public static final String ALERT_CARDS_OPEN = ResourceUtil.normalizedResourceAsString("snippet/pagearea/alert-cards-open.html");
    public static final String ALERT_CARDS_ALERT = ResourceUtil.normalizedResourceAsString("snippet/pagearea/alert-cards-alert.html");
    public static final String ALERT_CARDS_CLOSE = ResourceUtil.normalizedResourceAsString("snippet/pagearea/alert-cards-close.html");

    public static final String APPLICATION_PARAMETER_TABLE_OPEN = ResourceUtil.normalizedResourceAsString("snippet/pagearea/application-parameter-table-open.html");
    public static final String APPLICATION_PARAMETER_ROW = ResourceUtil.normalizedResourceAsString("snippet/pagearea/application-parameter-row.html");
    public static final String APPLICATION_PARAMETER_TABLE_CLOSE = ResourceUtil.normalizedResourceAsString("snippet/pagearea/application-parameter-table-close.html");

    public static final String openBarRight() {
        return new StringBuilder()
                .append("<!-- Topbar - Right -->\n")
                .append("<ul class=\"navbar-nav ml-auto\">")
                .toString();
    }

    public static String alertCenter(Locale locale) throws FrameworkException {
        Collection<Alert> alerts = AlertsCenterSingleton.getInstance().getList();
        if (!alerts.isEmpty()) {
            StringBuilder result = new StringBuilder();

            result.append(MessageFormat.format(ALERT_CENTER_OPEN, alerts.size()));
            for (Alert alert : alerts) {
                result.append(MessageFormat.format(
                        ALERT_CENTER_ALERT,
                        "<div class=\"icon-circle bg-" + alert.getType().name().toLowerCase() + "\">" + alert.getType().getIcon() + "</div>",
                        DataTypes.DATE.formatText(alert.getDate(), locale),
                        Casting.getHtml(alert.getText()),
                        Casting.getHtml(alert.getDetail()),
                        alert.getHref()
                ));
            }
            result.append(ALERT_CENTER_CLOSE);

            return result.toString();
        } else {
            return StringUtil.EMPTY;
        }
    }


    public static String alertCards(Locale locale) throws FrameworkException {

        Collection<Alert> alerts = AlertsCenterSingleton.getInstance().getList();
        if (!alerts.isEmpty()) {
            StringBuilder result = new StringBuilder();

            result.append(ALERT_CARDS_OPEN);
            for (Alert alert : alerts) {
                result.append(MessageFormat.format(
                        ALERT_CARDS_ALERT,
                        "<div class=\"icon-circle bg-" + alert.getType().name().toLowerCase() + "\">" + alert.getType().getIcon() + "</div>",
                        DataTypes.DATE.formatText(alert.getDate(), locale),
                        Casting.getHtml(alert.getText()),
                        Casting.getHtml(alert.getDetail()),
                        alerts.size() == 1 ? "col-12" : "col-6"));
            }
            result.append(ALERT_CARDS_CLOSE);

            return result.toString();
        } else {
            return StringUtil.EMPTY;
        }
    }

    public static String applicationParameters(Grid<?> grid, ViewModality viewModality, Locale locale) throws FrameworkException {
        StringBuilder result = new StringBuilder();
        result.append(APPLICATION_PARAMETER_TABLE_OPEN);

        int rowNumber = 0;
        DataTable<?> table = grid.getDataSource();
        for (DataRow row : table) {
            ApplicationParameter parameter = new ApplicationParameter().fromRow(row);

            DataField<?> element;
            if (parameter.isMultivalue()) {
                MultipleAutoComplete<String> multipleAutoComplete = MultipleAutoComplete.<String>builder()
                        .name("value")
                        .dataType(parameter.getDataType())
                        .decodeMap(new StringDecodeMap(StringUtil.split(parameter.getValue(), MultipleInput.DELIMITER)))
                        .locale(locale)
                        .freeInput(true)
                        .build();

                for (String value : StringUtil.split(parameter.getValue(), MultipleInput.DELIMITER)) {
                    multipleAutoComplete.addValue(value);
                }

                element = multipleAutoComplete;
            } else {
                element = Input.builder()
                        .name("value")
                        .dataType(parameter.getDataType())
                        .locale(locale)
                        .build();
            }
            element.setData(parameter.getValue());

            String tr = (rowNumber == table.getCurrentRow() ? "class=\"table-primary\"" : "id=\"navigationprefix___row___anagrafica___" + rowNumber + "\"");
            String codParameter = parameter.getCodParameter();

            String value;
            if (rowNumber == table.getCurrentRow() && viewModality == ViewModality.EDIT) {
                value = FormControlWriter.writeControl(element, grid, viewModality);
            } else {
                value = TextControlWriter.writeControl(element, grid);
            }

            result.append(MessageFormat.format(APPLICATION_PARAMETER_ROW, tr, codParameter, value));

            rowNumber++;
        }

        result.append(APPLICATION_PARAMETER_TABLE_CLOSE);
        return result.toString();
    }


}
