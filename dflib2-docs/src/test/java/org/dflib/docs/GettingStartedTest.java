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

        DataFrame df2 = df1.rows(r -> r.getInt(0) % 2 == 0).select();

        System.out.println(Printers.tabular.toString(df2));
// end::gettingStarted[]
    }
}
