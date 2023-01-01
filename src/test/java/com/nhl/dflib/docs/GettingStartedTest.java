package com.nhl.dflib.docs;

import com.nhl.dflib.DataFrame;
import com.nhl.dflib.Printers;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class GettingStartedTest extends BaseTest {

    @Test
    public void gettingStarted() {
// tag::gettingStarted[]
        DataFrame df1 = DataFrame
                .newFrame("a", "b", "c")
                .foldIntStreamByRow(IntStream.range(1, 10000));

        DataFrame df2 = df1.selectRows(
                df1.getColumnAsInt(0).indexInt(i -> i % 2 == 0));

        System.out.println(Printers.tabular.toString(df2));
// end::gettingStarted[]
    }
}
