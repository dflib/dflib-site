package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.Series;
import org.junit.jupiter.api.Test;

public class CleaningTest extends BaseTest {

    @Test
    public void fillNulls_Series() {
// tag::fillNulls_Series[]
        Series<String> withNulls = Series.of("a", "b", null, null, "c");
        Series<String> filled = withNulls.fillNulls("X");
// end::fillNulls_Series[]

        print("fillNulls_Series", filled);
    }

    @Test
    public void fillNullsForward_Series() {
        Series<String> withNulls = Series.of("a", "b", null, null, "c");

// tag::fillNullsForward_Series[]
        Series<String> filled = withNulls.fillNullsForward(); // <1>
// end::fillNullsForward_Series[]

        print("fillNullsForward_Series", filled);
    }

    @Test
    public void fillNullsFromSeries_Series() {

        Series<String> withNulls = Series.of("a", "b", null, null, "c");

// tag::fillNullsFromSeries_Series[]

        Series<String> mask = Series.of("A", "B", "C", "D", "E", "F", "G");
        Series<String> filled = withNulls.fillNullsFromSeries(mask);

// end::fillNullsFromSeries_Series[]

        print("fillNullsFromSeries_Series", filled);
    }

    @Test
    public void fillNullsForward_DataFrame() {

// tag::fillNullsForward_DataFrame[]
        DataFrame withNulls = DataFrame.foldByRow("c1", "c2").of(
                "1", "1",
                null, null,
                "2", "2");

        DataFrame filled = withNulls
                .fillNullsBackwards("c1")
                .fillNullsForward("c2");
// end::fillNullsForward_DataFrame[]

        print("fillNullsForward_DataFrame", filled);
    }
}
