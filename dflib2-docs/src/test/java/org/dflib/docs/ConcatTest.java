package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.JoinType;
import org.dflib.Series;
import org.dflib.concat.SeriesConcat;
import org.junit.jupiter.api.Test;

public class ConcatTest extends BaseTest {

    @Test
    public void concatSeries() {

// tag::concatSeries[]
        Series<String> s1 = Series.of("x", "y", "z");
        Series<String> s2 = Series.of("a");
        Series<String> s3 = Series.of("m", "n");

        Series<String> sConcat = s1.concat(s2, s3);
// end::concatSeries[]

        print("concatSeries", sConcat);
    }

    @Test
    public void concatSeries_Self() {

// tag::concatSeries_Self[]
        Series<String> s = Series.of("x", "y");
        Series<String> sConcat = s.concat(s);
// end::concatSeries_Self[]

        print("concatSeries_Self", sConcat);
    }

    @Test
    public void concatSeries_Static() {

// tag::concatSeries_Static[]
        Series<String> sConcat = SeriesConcat.concat(
                Series.of("x", "y", "z"),
                Series.of("a"),
                Series.of("m", "n"));
// end::concatSeries_Static[]

        print("concatSeries_Static", sConcat);
    }

    @Test
    public void vConcat() {

// tag::vConcat[]
        DataFrame df1 = DataFrame.foldByRow("a", "b").of(
                1, 2,
                3, 4);

        DataFrame df2 = DataFrame.foldByRow("a", "b").of(
                5, 6,
                7, 8);

        DataFrame dfv = df1.vConcat(df2); // <1>
// end::vConcat[]

        print("vConcat", dfv);
    }

    @Test
    public void hConcat() {

        DataFrame df1 = DataFrame.foldByRow("a", "b").of(
                1, 2,
                3, 4);

        DataFrame df2 = DataFrame.foldByRow("a", "b").of(
                5, 6,
                7, 8);

// tag::hConcat[]
        DataFrame dfh = df1.hConcat(df2); // <1>
// end::hConcat[]

        print("hConcat", dfh);
    }

    @Test
    public void vConcat_InnerMismatch() {

// tag::vConcat_InnerMismatch[]
        DataFrame df1 = DataFrame.foldByRow("b", "a").of(
                1, 2,
                3, 4);

        DataFrame df2 = DataFrame.foldByRow("a", "c").of( // <1>
                5, 6,
                7, 8);

        DataFrame dfv = df1.vConcat(JoinType.inner, df2); // <2>
// end::vConcat_InnerMismatch[]

        print("vConcat_InnerMismatch", dfv);
    }

    @Test
    public void vConcat_LeftMismatch() {

        DataFrame df1 = DataFrame.foldByRow("b", "a").of(
                1, 2,
                3, 4);

        DataFrame df2 = DataFrame.foldByRow("a", "c").of(
                5, 6,
                7, 8);

// tag::vConcat_LeftMismatch[]
        DataFrame dfv = df1.vConcat(JoinType.left, df2);
// end::vConcat_LeftMismatch[]

        print("vConcat_LeftMismatch", dfv);
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
