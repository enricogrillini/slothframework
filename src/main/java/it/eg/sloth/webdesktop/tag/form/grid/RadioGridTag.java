package it.eg.sloth.webdesktop.tag.form.grid;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.form.base.Element;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.form.grid.RadioGrid;
import it.eg.sloth.framework.common.exception.BusinessException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;

import java.io.IOException;

public class RadioGridTag extends AbstractGridTag<RadioGrid<?>> {

    private static final long serialVersionUID = 1L;

    protected void writeRow(DataRow dataRow, int rowNumber) throws CloneNotSupportedException, BusinessException, IOException {
        boolean selected = rowNumber == getElement().getRowSelected();
        boolean readOnly = (getForm().getPageInfo().getViewModality() == ViewModality.VIEW_VISUALIZZAZIONE);

        String className = (rowNumber % 2 == 0) ? "frGray" : "frWhite";

        // Prima Riga
        String riga = "  <td>\n";
        riga += "   <div class=\"" + className + "\" style=\"float:none; color:#000099; font-size:9pt\">";

        int i = 0;
        for (SimpleField field : getElement()) {
            SimpleField appField = (SimpleField) field.clone();
            if (appField instanceof DataField) {
                DataField<?> dataField = (DataField<?>) appField;
                dataField.copyFromDataSource(dataRow);

                if (i == 1) {
                    riga += " - ";
                } else if (i > 1) {
                    riga += ", ";
                }
                riga += FormControlWriter.writeControl(appField, getElement(), getWebDesktopDto().getLastController(), ViewModality.VIEW_VISUALIZZAZIONE, null, null);
                i++;
            }
        }
        riga += "  </div>\n";

        // Dettaglio
        riga += " <div class=\"" + className + "\" style=\"float:none;\">";
        i = 0;
        for (Element field : getDetail()) {
            if (field instanceof DataField) {
                DataField<?> dataField = (DataField<?>) field.clone();
                dataField.copyFromDataSource(dataRow);

                riga += i >= 1 ? ", " : "";
                riga += "<b>" + dataField.getHtmlDescription() + "</b>: " + FormControlWriter.writeControl(dataField, getElement(), getWebDesktopDto().getLastController(), ViewModality.VIEW_VISUALIZZAZIONE, null, null);
            }

            i++;
        }
        riga += "</div>";
        riga += "  </td>";

        if (readOnly) {
            if (selected) {
                writeln(riga);
            }
        } else {
            writeln(" <tr class=\"" + className + "\">");
            writeln("  <td style=\"width:25px\"><input type=\"radio\" id=\"" + getElement().getName() + rowNumber + "\" name=\"" + getElement().getName() + "\" value=\"" + rowNumber + "\" " + (selected ? "checked=\"checked\"" : "") + "></td>");
            writeln(riga);
            writeln(" </tr>");
        }
    }

    protected void writeLastRow(int rowNumber) throws CloneNotSupportedException, BusinessException, IOException {
        boolean selected = getElement().isNewLine();
        boolean readOnly = (getForm().getPageInfo().getViewModality() == ViewModality.VIEW_VISUALIZZAZIONE);

        String className = (rowNumber % 2 == 0) ? "frGray" : "frWhite";

        if (readOnly && selected) {
            writeln(" <tr class=\"" + className + "\">");
            writeln("  <td>");
            int i = 0;
            for (SimpleField field : getElement()) {
                if (field instanceof DataField) {
                    if (i == 1) {
                        write(" - ");
                    } else if (i > 1) {
                        write(", ");
                    }
                    writeln(FormControlWriter.writeControl(field, getElement(), getWebDesktopDto().getLastController(), ViewModality.VIEW_VISUALIZZAZIONE, null, null));
                    i++;
                }
            }
            writeln("  </div>");

            // Dettaglio
            writeln(" <div class=\"" + className + "\" style=\"float:none;\">");
            i = 0;
            for (SimpleField field : getDetail()) {
                if (field instanceof DataField) {
                    write(i >= 1 ? ", " : "");
                    writeln("<b>" + field.getHtmlDescription() + "</b>: " + FormControlWriter.writeControl(field, getElement(), getWebDesktopDto().getLastController(), ViewModality.VIEW_VISUALIZZAZIONE, null, null));
                }

                i++;
            }

            writeln("  </td>");
            writeln(" </tr>");

        } else if (!readOnly) {
            // Prima Radio per la selezione
            writeln(" <tr class=\"" + className + "\">");
            writeln("  <td style=\"width:25px\"><input type=\"radio\" id=\"" + getElement().getName() + rowNumber + "\" name=\"" + getElement().getName() + "\" value=\"" + RadioGrid.LAST + "\" " + (selected ? "checked=\"checked\"" : "") + "></td>\n");
            writeln("  <td>\n");
            writeln("   <div class=\"" + className + "\" style=\"color:#000099; font-size:9pt\">Altra destinazione</div>\n");
            writeln("   <div id=\"" + getElement().getName() + "Last\" style=\"" + (selected ? "" : "display:none") + "\">");

            // Grid
            for (SimpleField field : getElement()) {
                if (field instanceof DataField) {
                    String descrizione = field.getHtmlDescription();
                    if (field instanceof InputField && getViewModality() == ViewModality.VIEW_MODIFICA) {
                        InputField<?> inputField = (InputField<?>) field;
                        descrizione += inputField.isRequired() ? "* " : " ";
                    }

                    writeln("   <div class=\"" + className + "\" style=\"float:left; width:30%; height:22px; text-align:right; padding-top: 3px;\">" + descrizione + ": </div>");
                    writeln("   <div class=\"" + className + "\" style=\"float:left; width:50%; height:22px;\">" + FormControlWriter.writeControl(field, getElement(), getWebDesktopDto().getLastController(), ViewModality.VIEW_MODIFICA, null, null) + "</div>");
                }
            }

            // Dettaglio
            for (Element element : getDetail()) {
                SimpleField field = (SimpleField) element;

                writeln("   <div class=\"" + className + "\" style=\"float:left; width:30%; height:22px; text-align:right; padding-top: 3px;\">" + field.getHtmlDescription() + ": </div>");
                writeln("   <div class=\"" + className + "\" style=\"float:left; width:50%; height:22px;\">" + FormControlWriter.writeControl(field, getElement(), getWebDesktopDto().getLastController(), ViewModality.VIEW_MODIFICA, null, null) + "</div>");
            }

            writeln("  </td>");
            writeln(" </tr>");
        }

    }

    public int startTag() throws Throwable {
        if (getElement().getDataSource() != null) {
            writeln("");
            writeln("<table class=\"frElenco\">");

            int rowNumber = 0;
            DataTable<?> dataTable = getElement().getDataSource();
            for (DataRow dataRow : dataTable) {
                writeRow(dataRow, rowNumber);
                rowNumber++;
            }
            writeLastRow(rowNumber);

            writeln("</table>");
        }
        return EVAL_BODY_INCLUDE;
    }

    protected void endTag() throws Throwable {
    }

}
