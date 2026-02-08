package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.JoinType;
import org.junit.jupiter.api.Test;

public class HConcatTest extends BaseTest {

    @Test
    public void hConcat() {
// tag::hConcat[]
        DataFrame df1 = DataFrame.foldByRow("a", "b").of(
                1, 2,
                3, 4);

        DataFrame df2 = DataFrame.foldByRow("a", "b").of(
                5, 6,
                7, 8);

        DataFrame dfh = df1.hConcat(df2); // <1>
// end::hConcat[]

        print("hConcat", dfh);
    }

    @Test
    public void hConcat_LeftMismatch() {

// tag::hConcat_LeftMismatch[]
        DataFrame df1 = DataFrame.foldByRow("a", "b").of(
                1, 2,
                3, 4,
                5, 6);

        DataFrame df2 = DataFrame.foldByRow("c", "d").of(
                7, 8,
                9, 10);

        DataFrame dfv = df1.hConcat(JoinType.left, df2);
// end::hConcat_LeftMismatch[]

        print("hConcat_LeftMismatch", dfv);
    }
}
