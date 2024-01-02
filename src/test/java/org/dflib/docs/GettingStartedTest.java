package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.Printers;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class GettingStartedTest extends BaseTest {

    @Test
    public void gettingStarted() {
// tag::gettingStarted[]
        DataFrame df1 = DataFrame
                .foldByRow("a", "b", "c")
                .ofStream(IntStream.range(1, 10000));

        DataFrame df2 = df1.selectRows(
                df1.getColumn(0).castAsInt().indexInt(i -> i % 2 == 0));

        System.out.println(Printers.tabular.toString(df2));
// end::gettingStarted[]
    }
}
