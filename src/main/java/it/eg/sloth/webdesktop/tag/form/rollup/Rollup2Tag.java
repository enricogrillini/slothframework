package it.eg.sloth.webdesktop.tag.form.rollup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.eg.sloth.db.datasource.DataNode;
import it.eg.sloth.form.dwh.Attribute;
import it.eg.sloth.form.dwh.Level;
import it.eg.sloth.form.dwh.Measure;
import it.eg.sloth.form.dwh.rollup.Rollup;
import it.eg.sloth.framework.common.exception.BusinessException;
import it.eg.sloth.webdesktop.tag.form.base.BaseElementTag;
import it.eg.sloth.webdesktop.tag.form.rollup.writer.RollupWriter;

public class Rollup2Tag extends BaseElementTag<Rollup> {

  private static final long serialVersionUID = 1L;

  public static final String TABLE_CLASS_NAME = "frRollup";
  public static final String GROUP_CLASS_NAME = "frGroup";
  public static final String SUM_CLASS_NAME = "frSum";
  public static final String TEXT_CLASS_NAME = "frText";

  private List<String> bufferList;
  private int rowindex;

  private void writeBufferln(String text) {
    if (rowindex > bufferList.size()) {
      bufferList.add(text + "\n");
    } else {
      bufferList.set(rowindex - 1, bufferList.get(rowindex - 1) + text + "\n");
    }
  }

  private void writeHeaders(DataNode node, int levelNumber) throws CloneNotSupportedException, BusinessException, IOException {
    Rollup rollup = getElement();

    if (node == null)
      return;

    int levelSize = rollup.getLevels().size();
    int textSize = rollup.getAttributes().size() + rollup.getMeasures().size();
    int colspan = levelSize + textSize - levelNumber - 2;

    writeln(" <tr class=\"" + GROUP_CLASS_NAME + "0\">");
    writeln("  <td class=\"span\" colspan=\"" + colspan + "\">&nbsp;</td>");

    for (DataNode child : node.getChilds()) {
      Level<?> levelClone = (Level<?>) (rollup.getLevels().get(levelNumber)).clone();
      levelClone.copyFromDataSource(child);
      writeln("  <td>" + RollupWriter.writeLevel(levelClone) + "</td>");
    }

    writeln(" </tr>");

  }

  private void writeChilds(DataNode node, int levelNumber, boolean first) throws CloneNotSupportedException, BusinessException {
    Rollup rollup = getElement();

    if (node == null)
      return;

    int levelSize = rollup.getLevels().size();
    int textSize = rollup.getAttributes().size() + rollup.getMeasures().size();

    for (DataNode child : node.getChilds()) {
      rowindex++;

      if (levelNumber < rollup.getLevels().size()) {
        if (first) {
          writeBufferln(" <tr class=\"" + GROUP_CLASS_NAME + levelNumber + "\">");

          for (int i = 1; i < levelNumber; i++) {
            writeBufferln("  <td class=\"span\">&nbsp;</td>");
          }

          int colspan = levelSize + textSize - levelNumber - 1;
          Level<?> levelClone = (Level<?>) (rollup.getLevels().get(levelNumber)).clone();
          levelClone.copyFromDataSource(child);

          writeBufferln("  <td colspan=\"" + colspan + "\">" + RollupWriter.writeLevel(levelClone) + "</td>");
          writeBufferln("  <td>&nbsp;</td>");
        } else {
          writeBufferln("  <td>&nbsp;</td>");
        }

        writeChilds(child, levelNumber + 1, first);

        writeSum(child, levelNumber + 1, first);

      } else {
        if (first) {
          writeBufferln(" <tr class=\"" + TEXT_CLASS_NAME + "\">");
          for (int i = 1; i < levelNumber; i++) {
            writeBufferln("  <td class=\"span\">&nbsp;</td>");
          }

          // Attribute
          for (Attribute<?> attribute : rollup.getAttributes()) {
            Attribute<?> attributeClone = (Attribute<?>) attribute.clone();
            attributeClone.copyFromDataSource(child);

            // ControlTag.Factory.writeAttribute(attributeClone) + "</td>");
            writeBufferln("  <td>" + RollupWriter.writeAttribute(attributeClone) + "</td>");
          }
        }

        // Measure
        for (Measure<?> measure : rollup.getMeasures()) {
          Measure<?> measureClone = (Measure<?>) measure.clone();
          measureClone.copyFromDataSource(child);

          // ControlTag.Factory.writeMeasure(measureClone) + "</td>");
          writeBufferln("  <td style=\"text-align:right\">" + RollupWriter.writeMeasure(measureClone) + "</td>");
        }

      }

    }
  }

  private void writeSum(DataNode node, int levelNumber, boolean first) throws CloneNotSupportedException, BusinessException {
    Rollup rollup = getElement();

    if (node == null)
      return;

    if (rollup.getMeasures().size() > 0) {
      rowindex++;

      if (first) {
        writeBufferln(" <tr class=\"" + SUM_CLASS_NAME + levelNumber + "\">");

        // Level
        if (rollup.getLevels().size() > 0) {
          writeBufferln("  <td colspan=\"" + rollup.getLevels().size() + "\">&nbsp;</td>");
        }

        // Text
        for (int i = 1; i < rollup.getAttributes().size(); i++) {
          writeBufferln("  <td>&nbsp;</td>");
        }
      }

      // Measure
      for (Measure<?> measure : rollup.getMeasures()) {
        Measure<?> measureClone = (Measure<?>) measure.clone();
        measureClone.copyFromDataSource(node);
        writeBufferln("  <td style=\"text-align:right\">" + RollupWriter.writeMeasure(measureClone) + "</td>");
      }
    }

  }

  protected int startTag() throws Throwable {
    writeln("<table class=\"frRollup\" cellspacing=\"0\">");

    writeHeaders(getElement().getDataNode(), 0);

    int i = 0;
    bufferList = new ArrayList<String>();
    for (DataNode node : getElement().getDataNode()) {
      rowindex = 0;
      writeChilds(node, 1, i == 0);
      writeSum(node, 1, i == 0);

      i++;
    }

    for (String riga : bufferList) {
      writeln(riga + "</tr>");
    }

    writeln("</table>");
    return SKIP_BODY;
  }

  protected void endTag() throws Throwable {
  }
}
