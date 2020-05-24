package it.eg.sloth.db.decodemap;

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
public interface DecodeValue<T extends Object>  {
  
  public static String DEFAULT_CODE_NAME = "codice";
  public static String DEFAULT_DESCRIPTION_NAME = "descrizione";
  public static String DEFAULT_VALID_NAME = "flagvalido";

  public T getCode();
  
  public String getDescription();
  
  public boolean isValid();

  public boolean match(String matchString);
  
}
