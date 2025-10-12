package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.IntSeries;
import org.dflib.Printers;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.dflib.Exp.$int;


public class WindowRankTest extends BaseTest {

    static final DataFrame df = DataFrame.foldByRow("name", "salary").of(
            "Jerry Cosin", 88000,
            "Paul Austin", 93000,
            "Juliana Walewski", 85000,
            "Joan O'Hara", 93000);


    @Test
    public void windowRank() throws IOException {
// tag::windowRank[]
        IntSeries ranks = df
                .over()
                .sort("salary desc") // <1>
                .rank(); // <2>

        DataFrame df1 = df.cols("rank").merge(ranks); // <3>
// end::windowRank[]

        System.out.println();
        System.out.println("[windowRank]");
        Printers.tabular(8, 100, 100).printTo(System.out, df1);
    }

    @Test
    public void windowDenseRank() throws IOException {
// tag::windowDenseRank[]
        IntSeries ranks = df
                .over()
                .sort($int("salary").desc())
                .denseRank(); // <1>

        DataFrame df1 = df.cols("dense_rank").merge(ranks);
// end::windowDenseRank[]

        System.out.println();
        System.out.println("[windowDenseRank]");
        Printers.tabular(8, 100, 100).printTo(System.out, df1);
    }
}
