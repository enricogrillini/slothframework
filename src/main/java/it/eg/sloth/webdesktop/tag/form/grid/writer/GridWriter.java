package it.eg.sloth.webdesktop.tag.form.grid.writer;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.table.sort.SortType;
import it.eg.sloth.db.datasource.table.sort.SortingRule;
import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.form.fields.field.DecodedDataField;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.form.fields.field.base.TextField;
import it.eg.sloth.form.fields.field.impl.*;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;
import it.eg.sloth.webdesktop.tag.form.field.writer.TextControlWriter;

import java.math.BigDecimal;
import java.text.MessageFormat;

public class GridWriter extends HtmlWriter {

    public static final String GRID_CLASS = "table{0}{1}{2}";

    private static final String TABLE_OPEN = "\n" +
            "<!-- Table: {0} -->\n" +
            "{1}" +
            "<table class=\"{2}\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">\n";

    private static final String TABLE_CLOSE = "</table>\n" +
            "{0}";

    private static final String HEAD_OPEN = " <thead>\n";
    private static final String HEAD_CLOSE = " </thead>\n";

    private static final String ROW_OPEN = "  <tr{0}{1}>\n";
    private static final String ROW_CLOSE = "  </tr>\n";

    private static final String CELL_DETAIL = "   <td class=\"text-center tableDetail noselect\"><i class=\"text-info fa fa-chevron-down collapsed\" href=\"#{0}\" data-toggle=\"collapse\" aria-expanded=\"true\" aria-controls=\"collapse-collapsed\"></i></td>\n";


    public static String openTable(Grid<?> grid, boolean responsive, boolean border, boolean hover, boolean small) {
        String classHtml = MessageFormat.format(GRID_CLASS, border ? " table-bordered" : "", hover ? " table-hover" : "", small ? " table-sm" : "");

        return MessageFormat.format(TABLE_OPEN,
                Casting.getHtml(grid.getTitle()),
                responsive ? "<div class=\"table-responsive\">\n" : StringUtil.EMPTY,
                classHtml
        );
    }

    public static String closeTable(boolean responsive) {
        return MessageFormat.format(TABLE_CLOSE, responsive ? "</div>\n" : StringUtil.EMPTY);
    }

    public static String openHeader() {
        return HEAD_OPEN;
    }

    public static String closeHeader() {
        return HEAD_CLOSE;
    }

    public static String headerRow(Grid<?> grid, Fields<?> detailFields, boolean sortable) {
        StringBuilder result = new StringBuilder()
                .append("  <tr>\n");

        if (detailFields != null) {
            result.append("   <th width=\"3%\"></th>\n");
        }

        for (SimpleField field : grid) {
            String descriptionHtml = field.getHtmlDescription();

            if (field instanceof TextField && ((TextField<?>) field).isHidden() || field instanceof Hidden<?>) {
                continue;
            }

            if (field instanceof InputField) {
                InputField<?> inputField = (InputField<?>) field;
                descriptionHtml = inputField.getHtmlDescription() + (inputField.isRequired() ? " *" : "");
            }

            result.append(openCell(field, true, sortable));
            if (field instanceof Button) {
                result.append(NBSP);
            } else if (sortable && field instanceof TextField) {
                TextField<?> textField = (TextField<?>) field;

                String iconHtml = getAttribute(ATTR_CLASS, "fas fa-sort m-1");
                String buttonNameHtml = getAttribute(ATTR_NAME, NavigationConst.navStr(NavigationConst.SORT_ASC, grid.getName(), textField.getName()));

                SortingRule sortingRule = grid.getDataSource().getSortingRules().getFirstRule();
                if (sortingRule != null && sortingRule.getFieldName().equalsIgnoreCase(textField.getOrderByAlias())) {
                    if (sortingRule.getSortType() == SortType.SORT_ASC_NULLS_LAST) {
                        iconHtml = getAttribute(ATTR_CLASS, "fas fa-sort-up m-1");
                        buttonNameHtml = getAttribute(ATTR_NAME, NavigationConst.navStr(NavigationConst.SORT_DESC, grid.getName(), textField.getName()));
                    } else {
                        iconHtml = getAttribute(ATTR_CLASS, "fas fa-sort-down m-1");
                        buttonNameHtml = getAttribute(ATTR_NAME, NavigationConst.navStr(NavigationConst.SORT_ASC, grid.getName(), textField.getName()));
                    }
                }

                result.append("<button type=\"submit\"" + buttonNameHtml + " class=\"btn btn-block btn-sm d-flex justify-content-between\"><b class=\"w-100\">" + descriptionHtml + "</b><i" + iconHtml + "></i></button>");

            } else {
                result.append(descriptionHtml);
            }

            result.append(closeCell(true));

        }

        return result
                .append(ROW_CLOSE)
                .toString();

    }

    public static String header(Grid<?> grid, Fields<?> detailFields, boolean sortable) {
        return new StringBuilder()
                .append(openHeader())
                .append(headerRow(grid, detailFields, sortable))
                .append(closeHeader())
                .toString();
    }

    public static String openCell(SimpleField field, boolean header, boolean sortable) {
        // Stile cella
        String htmlClass = "text-left";
        if (sortable) {
            htmlClass = "text-center p-1";
        } else if (FieldType.SEMAPHORE == field.getFieldType() || FieldType.CHECK_BOX == field.getFieldType() || FieldType.BUTTON == field.getFieldType()) {
            htmlClass = "text-center";
        } else if (((TextField<?>) field).getDataType().isNumber() && !(field instanceof DecodedDataField) && !(field instanceof MultipleAutoComplete)) {
            htmlClass = "text-right";
        }

        if (field instanceof Button) {
            htmlClass += " p-1";
        }

        if (header) {
            return MessageFormat.format("   <th{0}{1}>", getTooltipAttributes(field.getTooltip()), getAttribute(ATTR_CLASS, htmlClass + " text-nowrap"));
        } else {
            return MessageFormat.format("   <td{0}{1}>", getTooltipAttributes(field.getTooltip()), getAttribute(ATTR_CLASS, htmlClass));
        }
    }

    public static String closeCell(boolean headder) {
        if (headder) {
            return "</th>\n";
        } else {
            return "</td>\n";
        }
    }

    public static String cell(Grid<?> grid, SimpleField field, ViewModality viewModality) throws FrameworkException {
        StringBuilder result = new StringBuilder()
                .append(openCell(field, false, false));

        if (ViewModality.VIEW == viewModality) {
            result.append(TextControlWriter.writeControl(field, grid));
        } else {
            result.append(FormControlWriter.writeControl(field, grid, viewModality, false));
        }

        return result
                .append(closeCell(false))
                .toString();
    }

    public static String rows(Grid<?> grid, Fields<?> detailFields, ViewModality viewModality, boolean manageCurrentRow) throws FrameworkException {
        int rowNumber = 0;

        StringBuilder result = new StringBuilder();

        result.append(" <tbody>\n");

        DataTable<?> dataTable = grid.getDataSource();
        for (DataRow dataRow : dataTable) {
            if (dataTable.getPageSize() <= 0 || (dataTable.getPageStart() <= rowNumber && dataTable.getPageEnd() >= rowNumber)) {
                String classHtml = getAttribute("class", manageCurrentRow && rowNumber == dataTable.getCurrentRow(), "table-primary");

                if (rowNumber == dataTable.getCurrentRow() && ViewModality.EDIT == viewModality && !grid.isCurrentRowLocked()) {
                    // Riga corrente in edit mode
                    result.append(writeCurrentRowEditMode(grid, detailFields, rowNumber, classHtml));
                } else {
                    // Riga corrente in view mode e altre righe
                    result.append(writeRow(grid, detailFields, dataRow, rowNumber, classHtml, manageCurrentRow));
                }

                // Riga di dettaglio (se presente)
                result.append(writeRowDetail(grid, detailFields, dataRow, rowNumber));
            }
            rowNumber++;
        }
        result.append(" </tbody>\n");

        return result.toString();
    }

    private static String writeCurrentRowEditMode(Grid<?> grid, Fields<?> detailFields, int rowNumber, String classHtml) throws FrameworkException {
        StringBuilder result = new StringBuilder();

        result.append(MessageFormat.format(ROW_OPEN, StringUtil.EMPTY, classHtml));
        if (detailFields != null) {
            result.append("   <td>&nbsp;</td>\n");
        }

        for (SimpleField field : grid.getElements()) {
            if (field instanceof TextField && ((TextField<?>) field).isHidden() || field instanceof Hidden<?>) {
                continue;
            }

            if (field instanceof Button) {
                Button button = (Button) field;
                button.setIndex(rowNumber);
            }

            result.append(GridWriter.cell(grid, field, ViewModality.EDIT));
        }

        result.append(ROW_CLOSE);

        return result.toString();
    }

    private static String writeRow(Grid<?> grid, Fields<?> detailFields, DataRow dataRow, int rowNumber, String classHtml, boolean manageCurrentRow) throws FrameworkException {

        String idRow = NavigationConst.navStr(NavigationConst.ROW, grid.getName(), "" + rowNumber);
        String idRowDetail = NavigationConst.navStr(NavigationConst.ROW, grid.getName(), "" + rowNumber, "detail");

        Grid<?> appGrid = grid.newInstance();
        appGrid.copyFromDataSource(dataRow);

        StringBuilder result = new StringBuilder();
        result.append(MessageFormat.format(ROW_OPEN, getAttribute("id", manageCurrentRow, idRow), classHtml));
        if (detailFields != null) {
            result.append(MessageFormat.format(CELL_DETAIL, idRowDetail));
        }
        for (SimpleField field : appGrid.getElements()) {
            if (field instanceof TextField && ((TextField<?>) field).isHidden() || field instanceof Hidden<?>) {
                continue;
            }

            if (field instanceof Button) {
                Button button = (Button) field;
                button.setIndex(rowNumber);
            }

            result.append(GridWriter.cell(appGrid, field, ViewModality.VIEW));
        }
        result.append(ROW_CLOSE);

        return result.toString();
    }

    private static String writeRowDetail(Grid<?> grid, Fields<?> detailFields, DataRow dataRow, int rowNumber) throws FrameworkException {
        StringBuilder result = new StringBuilder();
        if (detailFields != null) {
            String idRowDetail = NavigationConst.navStr(NavigationConst.ROW, grid.getName(), "" + rowNumber, "detail");
            result
                    .append(MessageFormat.format("  <tr {0} class=\"frDetail collapse\">\n", getAttribute("id", idRowDetail)))
                    .append("   <td>&nbsp;</td>\n");

            for (SimpleField field : detailFields) {
                SimpleField fieldClone = field.newInstance();

                if (fieldClone instanceof Button) {
                    Button button = (Button) fieldClone;
                    button.setIndex(rowNumber);
                } else if (fieldClone instanceof DataField) {
                    DataField<?> dataField = (DataField<?>) fieldClone;
                    dataField.copyFromDataSource(dataRow);
                }

                result.append("   <td class=\"tableDetail\" colspan=\"" + grid.getElements().size() + "\">\n");
                result.append("    <b style=\"color:#660000\">" + fieldClone.getHtmlDescription() + ": </b><span>" + TextControlWriter.writeControl(fieldClone, grid) + "</span>\n");
                result.append("   </td>\n");
            }
            result.append(ROW_CLOSE);
        }

        return result.toString();
    }

    public static String total(Grid<?> grid, boolean hasDetail) throws FrameworkException {
        StringBuilder result = new StringBuilder();
        result.append(" <tr>\n");

        if (hasDetail) {
            result.append("  <td>&nbsp;</td>\n");
        }

        for (SimpleField field : grid.getElements()) {
            if (field instanceof TextField && ((TextField<?>) field).isHidden() || field instanceof Hidden<?>)
                continue;

            if (field instanceof TextTotalizer || field instanceof InputTotalizer) {
                TextField<BigDecimal> textField = (TextField<BigDecimal>) field.newInstance();

                BigDecimal totale = grid.getDataSource().sum(((TextField<?>) field).getAlias());
                textField.setValue(totale);

                result.append("  <td class=\"sum\">" + textField.escapeHtmlText() + "</td>\n");
            } else {
                result.append("  <td class=\"sum\">&nbsp;</td>\n");
            }
        }

        return result
                .append(" </tr>\n")
                .toString();

    }

}
