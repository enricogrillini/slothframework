package it.eg.sloth.framework.common.message;

import org.junit.Test;

import static org.junit.Assert.*;

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
 *
 * @author Enrico Grillini
 */
public class MessageTest {

    @Test
    public void levelTest() {
        assertFalse(Level.INFO.hasHigerSeverity(Level.SUCCESS));
        assertFalse(Level.SUCCESS.hasHigerSeverity(Level.WARN));
        assertFalse(Level.WARN.hasHigerSeverity(Level.ERROR));

        assertTrue(Level.SUCCESS.hasHigerSeverity(Level.INFO));
        assertTrue(Level.WARN.hasHigerSeverity(Level.SUCCESS));
        assertTrue(Level.ERROR.hasHigerSeverity(Level.WARN));
    }


    @Test
    public void messageListTest() {
        MessageList messageList = new MessageList();
        messageList.addBaseError("addBaseError");
        messageList.addBaseWarning("addBaseWarning");

        assertEquals("addBaseError | addBaseWarning", messageList.getMessagesDescription());
    }
}
