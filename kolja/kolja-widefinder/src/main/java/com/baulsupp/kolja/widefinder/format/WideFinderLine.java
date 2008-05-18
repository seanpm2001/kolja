/**
 * Copyright (c) 2002-2007 Yuri Schimke. All Rights Reserved.
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package com.baulsupp.kolja.widefinder.format;

import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.baulsupp.kolja.log.line.Line;
import com.baulsupp.kolja.log.line.type.BytesType;
import com.baulsupp.kolja.widefinder.WideFinderConstants;

/**
 * @author Yuri Schimke
 */
public class WideFinderLine implements Line {
  private int offset;
  private CharSequence content;
  private String ipaddress;
  private boolean failed;
  private String dateString;
  private String method;
  private transient DateTime date;
  private String url;
  private HttpStatus status;
  private Long size;
  private UserAgent userAgent;

  private static DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd/MMM/yyyy:HH:mm:ss");

  public WideFinderLine(CharSequence c) {
    this.content = c;

    parseValues();
  }

  private void parseValues() {
    try {
      int pos = 0;

      while (content.charAt(pos) != ' ') {
        pos++;
      }

      ipaddress = content.subSequence(0, pos).toString();

      pos += 6;

      dateString = content.subSequence(pos, pos + 20).toString();

      pos += 29;

      int i;
      for (i = 0; i < 5; i++) {
        char c = content.charAt(i + pos);
        if (c < 'A' || c > 'Z') {
          break;
        }
      }

      method = content.subSequence(pos, pos + i).toString();

      pos += 1 + i;

      for (i = 0; true; i++) {
        char c = content.charAt(i + pos);
        if (c == ' ') {
          break;
        }
      }

      url = content.subSequence(pos, pos + i).toString();

      pos += 1 + i;

      for (i = 0; true; i++) {
        char c = content.charAt(i + pos);
        if (c == ' ') {
          break;
        }
      }

      pos += 1 + i;

      for (i = 0; true; i++) {
        char c = content.charAt(i + pos);
        if (c == ' ') {
          break;
        }
      }

      status = new HttpStatus(content.subSequence(pos, pos + i).toString());

      pos += 1 + i;

      for (i = 0; true; i++) {
        char c = content.charAt(i + pos);
        if (c == ' ') {
          break;
        }
      }

      size = BytesType.parseBytes(content.subSequence(pos, pos + i).toString());

      pos += 1 + i;

      for (i = 0; true; i++) {
        char c = content.charAt(i + pos);
        if (c == ' ') {
          break;
        }
      }

      pos += 2 + i;

      for (i = 0; true; i++) {
        char c = content.charAt(i + pos);
        if (c == '"') {
          break;
        }
      }

      userAgent = new UserAgent(content.subSequence(pos, pos + i).toString());

      this.failed = false;
    } catch (Exception e) {
      this.failed = true;
    }
  }

  public int getOffset() {
    return offset;
  }

  public void setOffset(int lineStart) {
    this.offset = lineStart;
  }

  public Object getValue(String name) {
    if (name.equals(WideFinderConstants.IPADDRESS)) {
      return ipaddress;
    } else if (name.equals(WideFinderConstants.DATE)) {
      return getDate();
    } else if (name.equals(WideFinderConstants.ACTION)) {
      return method;
    } else if (name.equals(WideFinderConstants.URL)) {
      return url;
    } else if (name.equals(WideFinderConstants.STATUS)) {
      return status;
    } else if (name.equals(WideFinderConstants.SIZE)) {
      return size;
    } else if (name.equals(WideFinderConstants.USER_AGENT)) {
      return userAgent;
    }

    return null;
  }

  private DateTime getDate() {
    if (date == null) {
      date = DATE_FORMAT.parseDateTime(dateString);
    }

    return date;
  }

  public Map<String, Object> getValues() {
    return null;
  }

  public boolean isFailed() {
    return failed;
  }

  public void setValue(String name, Object value) {
    throw new UnsupportedOperationException();
  }

  public char charAt(int index) {
    return content.charAt(index);
  }

  public int length() {
    return content.length();
  }

  public CharSequence subSequence(int start, int end) {
    return content.subSequence(start, end);
  }

  @Override
  public String toString() {
    return content.toString();
  }
}
