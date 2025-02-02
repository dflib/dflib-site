package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.IntSeries;
import org.dflib.print.TabularPrinter;
import org.junit.jupiter.api.Test;

import static org.dflib.Exp.$int;


public class WindowRankTest extends BaseTest {

    static final DataFrame df = DataFrame.foldByRow("name", "salary").of(
            "Jerry Cosin", 88000,
            "Paul Austin", 93000,
            "Juliana Walewski", 85000,
            "Joan O'Hara", 93000);

    static {
        System.out.println(new TabularPrinter(8, 100).toString(df));
    }

    @Test
    public void windowRank() {
// tag::windowRank[]
        IntSeries ranks = df
                .over()
                .sorted($int("salary").desc()) // <1>
                .rank(); // <2>

        DataFrame df1 = df.cols("rank").merge(ranks); // <3>
// end::windowRank[]

        System.out.println();
        System.out.println("[windowRank]");
        System.out.println(new TabularPrinter(8, 100).toString(df1));
    }

    @Test
    public void windowDenseRank() {
// tag::windowDenseRank[]
        IntSeries ranks = df
                .over()
                .sorted($int("salary").desc())
                .denseRank(); // <1>

        DataFrame df1 = df.cols("dense_rank").merge(ranks);
// end::windowDenseRank[]

        System.out.println();
        System.out.println("[windowDenseRank]");
        System.out.println(new TabularPrinter(8, 100).toString(df1));
    }
}
