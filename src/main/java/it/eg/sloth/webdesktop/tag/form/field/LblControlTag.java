package it.eg.sloth.webdesktop.tag.form.field;

import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.webdesktop.tag.form.base.BaseControlTag;
import it.eg.sloth.webdesktop.tag.form.field.writer.LabelControlWriter;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

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
@Getter
@Setter
public class LblControlTag extends BaseControlTag {

    private static final long serialVersionUID = 1L;

    boolean overflow;

    String labelWidth;
    String controlWidth;

    String mobileLabelWidth;
    String mobileControlWidth;

    protected void writeField() throws IOException, FrameworkException {
        write(LabelControlWriter.writeLblControl(getElement(), getParentElement(), getViewModality(), isOverflow(), getLabelWidth(), getControlWidth(), getMobileLabelWidth(), getMobileControlWidth()));
    }
}
