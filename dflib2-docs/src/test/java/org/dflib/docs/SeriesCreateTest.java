package org.dflib.docs;

import org.dflib.Extractor;
import org.dflib.IntSeries;
import org.dflib.Series;
import org.dflib.builder.SeriesAppender;
import org.dflib.junit5.SeriesAsserts;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class SeriesCreateTest extends BaseTest {

    @Test
    public void create() {
// tag::create[]
        Series<String> s = Series.of("a", "bcd", "ef", "g");
// end::create[]

        print("create", s);
    }

    @Test
    public void createInt() {
// tag::createInt[]
        IntSeries is = Series.ofInt(0, 1, -300, Integer.MAX_VALUE);
// end::createInt[]

        print("createInt", is);
    }

    @Test
    public void createIncrementally() {

        InputStream inputStream = new ByteArrayInputStream("line1\nline2\nline3".getBytes());

// tag::createIncrementally[]
        // InputStream inputStream = ...
        SeriesAppender<String, String> appender = Series
                .byElement(Extractor.<String>$col()) // <1>
                .appender();

        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            appender.append(scanner.next()); // <2>
        }

        Series<String> s = appender.toSeries();
// end::createIncrementally[]

        new SeriesAsserts(s).expectData("line1", "line2", "line3");
        print("createIncrementally", s);
    }
}
