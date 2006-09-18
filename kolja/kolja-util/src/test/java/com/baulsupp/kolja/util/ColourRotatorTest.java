package com.baulsupp.kolja.util;

import com.baulsupp.kolja.util.colours.ColourPair;
import com.baulsupp.kolja.util.colours.ColourRotator;

import junit.framework.TestCase;

public class ColourRotatorTest extends TestCase {
  public void testNext() {
    ColourRotator r = new ColourRotator(ColourPair.BLUE_ON_BLACK, ColourPair.GREEN_ON_BLACK);
    
    assertEquals(ColourPair.BLUE_ON_BLACK, r.next());
    assertEquals(ColourPair.GREEN_ON_BLACK, r.next());
    assertEquals(ColourPair.BLUE_ON_BLACK, r.next());
  }

}
