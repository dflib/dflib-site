package org.dflib.docs;

import org.dflib.Condition;
import org.dflib.DataFrame;
import org.dflib.Exp;
import org.dflib.Udf1;
import org.dflib.UdfN;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.dflib.Exp.and;

public class UdfTest extends BaseTest {

    @Test
        // tag::udf1[]
    void formatNames() {
        DataFrame df = DataFrame.foldByRow("first", "last").of(
                "JERRY", "COSIN",
                "juliana", "waLEwsKi");

        Udf1<String, String> formatName = e ->
                e.castAsStr().mapVal(this::formatName); // <1>

        DataFrame clean = df.cols("first", "last").merge(
                formatName.call("first"),
                formatName.call("last")); // <2>
        // end::udf1[]
        print("udf1", clean);
        // tag::udf1[]
    }

    String formatName(String raw) {
        return Character.toUpperCase(raw.charAt(0))
                + raw.substring(1).toLowerCase();
    }
    // end::udf1[]

    @Test
    void udfN() {

        // tag::udfN[]
        DataFrame df = DataFrame.foldByRow("first", "last").of(
                "Jerry", "Cosin",
                null, "Walewski",
                "Joan", null);

        UdfN<Boolean> noNulls = exps -> and( // <1>
                Stream.of(exps)
                        .map(Exp::isNotNull)
                        .toArray(Condition[]::new)
        );

        DataFrame clean = df
                .rows(noNulls.call("first", "last").castAsBool()) // <2>
                .select();
        // end::udfN[]

        print("udfN", clean);
    }
}
