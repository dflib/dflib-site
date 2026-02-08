package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.JoinType;
import org.dflib.Series;
import org.junit.jupiter.api.Test;

public class UnionTest extends BaseTest {

    @Test
    public void unionSeries() {

// tag::unionSeries[]
        Series<String> sConcat = Series.union(
                Series.of("x", "y", "z"),
                Series.of("a"),
                Series.of("m", "n"));
// end::unionSeries[]

        print("unionSeries", sConcat);
    }

    @Test
    public void union() {

// tag::union[]
        DataFrame df1 = DataFrame.foldByRow("a", "b").of(
                1, 2,
                3, 4);

        DataFrame df2 = DataFrame.foldByRow("a", "b").of(
                5, 6,
                7, 8);

        DataFrame dfu = DataFrame.union(df1, df2); // <1>
// end::union[]

        print("union", dfu);
    }

    @Test
    public void unionInnerMismatch() {

// tag::union_InnerMismatch[]
        DataFrame df1 = DataFrame.foldByRow("b", "a").of(
                1, 2,
                3, 4);

        DataFrame df2 = DataFrame.foldByRow("a", "c").of( // <1>
                5, 6,
                7, 8);

        DataFrame dfv = DataFrame.union(JoinType.inner, df1, df2); // <2>
// end::union_InnerMismatch[]

        print("unionInnerMismatch", dfv);
    }

    @Test
    public void unionLeftMismatch() {

        DataFrame df1 = DataFrame.foldByRow("b", "a").of(
                1, 2,
                3, 4);

        DataFrame df2 = DataFrame.foldByRow("a", "c").of(
                5, 6,
                7, 8);

// tag::union_LeftMismatch[]
        DataFrame dfv = DataFrame.union(JoinType.left, df1, df2);
// end::union_LeftMismatch[]

        print("unionLeftMismatch", dfv);
    }
}
