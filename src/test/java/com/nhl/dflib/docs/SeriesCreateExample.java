package com.nhl.dflib.docs;

import com.nhl.dflib.IntSeries;
import com.nhl.dflib.Series;
import org.junit.Test;

public class SeriesCreateExample extends BaseExample {

    @Test
    public void create() {
// tag::create[]
        Series<String> s = Series.forData("a", "bcd", "ef", "g");
// end::create[]

        print("create", s);
    }

    @Test
    public void createInt() {
// tag::createInt[]
        IntSeries is = IntSeries.forInts(0, 1, -300, Integer.MAX_VALUE);
// end::createInt[]

        print("createInt", is);
    }
}
