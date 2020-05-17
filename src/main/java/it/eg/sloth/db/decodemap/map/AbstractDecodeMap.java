package it.eg.sloth.db.decodemap.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.eg.sloth.db.decodemap.DecodeMap;
import it.eg.sloth.db.decodemap.DecodeValue;
import it.eg.sloth.db.decodemap.MapSearchType;
import it.eg.sloth.db.decodemap.value.AbstractDecodeValue;
import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2020 Enrico Grillini
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
public abstract class AbstractDecodeMap<T, V extends AbstractDecodeValue<T>> extends FrameComponent implements DecodeMap<T, V> {

    private Map<T, V> map;

    public AbstractDecodeMap() {
        map = new LinkedHashMap<>();
    }

    public void put(V decodeValue) {
        map.put(decodeValue.getCode(), decodeValue);
    }

    public V get(T code) {
        return map.get(code);
    }

    @Override
    public T encode(String description) {
        List<V> list = performSearch(description, MapSearchType.FLEXIBLE, 2);

        if (list.size() == 1) {
            return list.get(0).getCode();
        } else {
            return null;
        }
    }

    @Override
    public String decode(T code) {
        DecodeValue<T> decodeValue = get(code);

        if (decodeValue == null) {
            return null;
        } else {
            return decodeValue.getDescription();
        }
    }

    @Override
    public boolean contains(T code) {
        return map.containsKey(code);
    }

    @Override
    public Iterator<V> iterator() {
        return map.values().iterator();
    }

    @Override
    public T getFirst() {
        if (map.isEmpty()) {
            return null;
        } else {
            return map.values().iterator().next().getCode();
        }
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public List<V> valid() {
        List<V> list = new ArrayList<>();

        for (V decodeValue : this) {
            if (decodeValue.isValid()) {
                list.add(decodeValue);
            }
        }
        return list;
    }

    @Override
    public List<V> performSearch(T code) {
        List<V> list = new ArrayList<>();

        if (map.containsKey(code)) {
            list.add(map.get(code));
        }

        return list;
    }

    @Override
    public List<V> performSearch(String description, MapSearchType searchType, Integer sizeLimit) {
        List<V> list = new ArrayList<>();

        if (BaseFunction.isBlank(description)) {
            return list;
        }

        if (MapSearchType.FLEXIBLE == searchType) {
            // Ricerca flessibile
            for (V decodeMapValue : map.values()) {
                if (decodeMapValue.getDescription() != null && decodeMapValue.getDescription().trim().equalsIgnoreCase(description.trim())) {
                    list.add(decodeMapValue);
                }

                // Size Limit
                if (sizeLimit != null && list.size() >= sizeLimit) {
                    break;
                }
            }

        } else if (MapSearchType.MATCH == searchType) {
            // Match search
            String[] matchStringArray = StringUtil.tokenize(description, " ");

            for (V decodeValue : this) {
                boolean match = true;
                for (String string : matchStringArray) {
                    if (!decodeValue.match(string)) {
                        match = false;
                        break;
                    }
                }

                if (match) {
                    list.add(decodeValue);
                }

                // Size Limit
                if (sizeLimit != null && list.size() >= sizeLimit) {
                    break;
                }
            }

        } else {
            // Ricerca esatta
            for (V decodeValue : map.values()) {
                if (decodeValue.getDescription() != null && decodeValue.getDescription().equals(description)) {
                    list.add(decodeValue);
                }

                // Size Limit
                if (sizeLimit != null && list.size() >= sizeLimit) {
                    break;
                }
            }
        }

        return list;
    }

}
