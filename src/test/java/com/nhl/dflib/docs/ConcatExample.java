package com.nhl.dflib.docs;

import com.nhl.dflib.Series;
import com.nhl.dflib.concat.SeriesConcat;
import org.junit.Test;

import java.util.Collection;

import static java.util.Arrays.asList;

public class ConcatExample extends BaseExample {

    @Test
    public void concatSeries() {

// tag::concatSeries[]
        Series<String> s1 = Series.forData("x", "y", "z");
        Series<String> s2 = Series.forData("a");
        Series<String> s3 = Series.forData("m", "n");

        Series<String> sConcat = s1.concat(s2, s3);
// end::concatSeries[]

        print("concatSeries", sConcat);
    }

    @Test
    public void concatSeries_Self() {

// tag::concatSeries_Self[]
        Series<String> s = Series.forData("x", "y");
        Series<String> sConcat = s.concat(s);
// end::concatSeries_Self[]

        print("concatSeries_Self", sConcat);
    }

    @Test
    public void concatSeries_Static() {

// tag::concatSeries_Static[]
        Collection<Series<String>> ss = asList(
                Series.forData("x", "y", "z"),
                Series.forData("a"),
                Series.forData("m", "n"));

        Series<String> sConcat = SeriesConcat.concat(ss);
// end::concatSeries_Static[]

        print("concatSeries_Static", sConcat);
    }
}
