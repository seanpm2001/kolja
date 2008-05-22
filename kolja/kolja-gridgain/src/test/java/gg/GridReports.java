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
package gg;

import org.springframework.util.StopWatch;

import com.baulsupp.kolja.ansi.reports.ReportRunnerMain;

/**
 * @author Yuri Schimke
 * 
 */
public class GridReports {
  public static void main(String[] args) throws Exception {
    StopWatch sw = new StopWatch("Frequency - o1000k.ap");
    sw.start();

    ReportRunnerMain.main("-x", "../kolja-widefinder/src/main/config/wf.xml", "-g",
        "com.baulsupp.kolja.gridgain.GridGainReportEngineFactory", "-r", "freq?q=url&count=10",
        "../kolja-widefinder/src/test/logs/o1000k.ap");

    sw.stop();

    System.out.println(sw.shortSummary());
  }
}
