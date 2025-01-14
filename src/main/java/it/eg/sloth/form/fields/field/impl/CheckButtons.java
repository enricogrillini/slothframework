package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.db.decodemap.DecodeMap;
import it.eg.sloth.db.decodemap.map.BaseDecodeMap;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.framework.common.base.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


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
 * <p>
 *
 * @author Enrico Grillini
 */
@Slf4j
@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class CheckButtons<L extends List<T>, T> extends CheckGroup<L, T> {

    @Override
    public FieldType getFieldType() {
        return FieldType.CHECK_BUTTONS;
    }

    public void init(BaseDecodeMap<String> decodeMap) {
        init(decodeMap, false);
    }

    public void init(BaseDecodeMap<String> decodeMap, boolean select) {
        setValues(StringUtil.join(decodeMap.codeCollection().toArray(new String[0]), CheckGroup.SEPARATOR));
        setDescriptions(StringUtil.join(decodeMap.descriptionCollection().toArray(new String[0]), CheckGroup.SEPARATOR));

        if (select) {
            setData(getValues());
        }
    }
}
