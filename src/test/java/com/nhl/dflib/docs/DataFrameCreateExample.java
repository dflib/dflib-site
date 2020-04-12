package com.nhl.dflib.docs;

import com.nhl.dflib.DataFrame;
import com.nhl.dflib.IntSeries;
import com.nhl.dflib.Series;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class DataFrameCreateExample extends BaseExample {

    @Test
    public void createRowAtATime() {
// tag::createRowAtATime[]
        DataFrame df = DataFrame
                .newFrame("name", "age") // <1>
                .addRow("Joe", 18)   // <2>
                .addRow("Andrus", 45)
                .addRow("Joan", 32)
                .create();
// end::createRowAtATime[]

        print("createRowAtATime", df);
    }

    @Test
    public void createFoldByRow() {
// tag::createFoldByRow[]
        DataFrame df = DataFrame
                .newFrame("name", "age") // <1>
                .foldByRow("Joe", 18, "Andrus", 45, "Joan", 32); // <2>
// end::createFoldByRow[]

        print("createFoldByRow", df);
    }

    @Test
    public void createFoldByColumn() {
// tag::createFoldByColumn[]
        DataFrame df = DataFrame
                .newFrame("name", "age")
                .foldByColumn("Joe", "Andrus", "Joan", 18, 45, 32);
// end::createFoldByColumn[]

        print("createFoldByColumn", df);
    }

    @Test
    public void createFromIntStream() {
// tag::createFromIntStream[]
        DataFrame df = DataFrame
                .newFrame("col1", "col2")
                .foldIntStreamByColumn(IntStream.range(0, 10000));
// end::createFromIntStream[]

        print("createFromIntStream", df);
    }

    @Test
    public void createFromSeries() {
// tag::createFromSeries[]
        DataFrame df = DataFrame
                .newFrame("name", "age")
                .columns(
                        Series.forData("Joe", "Andrus", "Joan"),
                        IntSeries.forInts(18, 45, 32)
                );
// end::createFromSeries[]

        print("createFromSeries", df);
    }
}
