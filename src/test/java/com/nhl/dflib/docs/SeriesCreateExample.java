package com.nhl.dflib.docs;

import com.nhl.dflib.IntSeries;
import com.nhl.dflib.Series;
import com.nhl.dflib.accumulator.Accumulator;
import com.nhl.dflib.accumulator.ObjectAccumulator;
import org.junit.Test;

import java.io.InputStream;
import java.util.Scanner;

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

    @Test
    public void createIncrementally() {

        InputStream inputStream = getClass().getResourceAsStream("lines.txt");

// tag::createIncrementally[]
        // InputStream inputStream = ...
        Accumulator<String> accum = new ObjectAccumulator<>();
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            accum.add(scanner.next());
        }

        Series<String> s = accum.toSeries();
// end::createIncrementally[]

        print("createIncrementally", s);
    }
}
