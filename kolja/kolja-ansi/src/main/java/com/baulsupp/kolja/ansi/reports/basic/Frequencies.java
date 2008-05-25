/**
 * Copyright (c) 2002-2007 Yuri Schimke. All Rights Reserved.
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package com.baulsupp.kolja.ansi.reports.basic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.collections.ComparatorUtils;

import com.baulsupp.kolja.log.viewer.format.OutputFormat;

/**
 * Frequency Counting of Strings.
 * 
 * @author Yuri Schimke
 */
public class Frequencies<T> implements Iterable<Frequencies.Count<T>> {
  private SortedMap<T, Count<T>> counts;

  public static final Comparator<Count<?>> COUNT_COMPARATOR = new Comparator<Count<?>>() {
    public int compare(Count<?> c1, Count<?> c2) {
      if (c1.getCount() < c2.getCount()) {
        return -1;
      } else if (c1.getCount() == c2.getCount()) {
        return 0;
      } else {
        return 1;
      }
    }
  };

  @SuppressWarnings("unchecked")
  public Frequencies() {
    Comparator<T> comp = ComparatorUtils.nullLowComparator(ComparatorUtils.naturalComparator());
    counts = new TreeMap<T, Count<T>>(comp);
  }

  public Frequencies(SortedMap<T, Count<T>> map) {
    counts = map;
  }

  public void increment(T url) {
    incrementBy(url, 1);
  }

  public void incrementBy(T url, long by) {
    Count<T> count = counts.get(url);

    if (count == null) {
      counts.put(url, new Count<T>(url, by));
    } else {
      count.increment(by);
    }
  }

  public static class Count<S> implements Serializable {
    private static final long serialVersionUID = -6312937012496559725L;

    private S url;
    private long i;

    public Count(S url, long i) {
      this.url = url;
      this.i = i;
    }

    public void increment(long by) {
      this.i += by;
    }

    public void increment() {
      this.i++;
    }

    @Override
    public String toString() {
      return i + " " + url;
    }

    public String toString(OutputFormat format) {
      String count = format != null ? format.format(i) : String.valueOf(i);
      return count + " " + url;
    }

    public S getItem() {
      return url;
    }

    public long getCount() {
      return i;
    }
  }

  @SuppressWarnings("unchecked")
  public List<Count<T>> getMostFrequent(int top) {
    List<Count<T>> results = new ArrayList<Count<T>>();

    results.addAll(counts.values());

    Collections.sort(results, ComparatorUtils.reversedComparator(COUNT_COMPARATOR));

    if (results.size() > top) {
      results = results.subList(0, top);
    }

    return results;
  }

  public Iterator<Count<T>> iterator() {
    return counts.values().iterator();
  }

  public int size() {
    return counts.size();
  }

  public long get(T string) {
    Count<T> c = counts.get(string);
    return c != null ? c.getCount() : 0;
  }

  public void merge(Frequencies<T> other) {
    for (Count<T> count : other.counts.values()) {
      incrementBy(count.url, count.i);
    }
  }
}
