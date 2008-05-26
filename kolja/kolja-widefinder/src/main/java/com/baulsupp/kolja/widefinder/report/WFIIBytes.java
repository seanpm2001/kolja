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
package com.baulsupp.kolja.widefinder.report;

import com.baulsupp.kolja.widefinder.format.HttpStatus;
import com.baulsupp.kolja.widefinder.format.MegaBytesFormat;
import com.baulsupp.kolja.widefinder.format.WideFinderLine;

/**
 * @author Yuri Schimke
 * 
 */
public class WFIIBytes extends WFIIBase<WFIIBytes> {
  private static final long serialVersionUID = 5637074836971432761L;

  public static final MegaBytesFormat MB = new MegaBytesFormat();

  public String describe() {
    return "Top URIs by bytes";
  }

  @Override
  public void processLine(WideFinderLine line) {
    Long bytes = line.getSize();

    if (bytes != null && line.getStatus().equals(HttpStatus.SUCCESS_OK)) {
      counts.incrementBy(line.getUrl(), bytes);
    }
  }

  @Override
  public void completed() {
    printSection(MB, ITEM);
  }
}