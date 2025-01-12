package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.NumExp;
import org.junit.jupiter.api.Test;

import static org.dflib.Exp.$int;
import static org.dflib.Exp.$long;

public class NumExpTest extends BaseTest {

    @Test
    public void arithmetics() {

        // tag::arithmetics[]
        NumExp<?> exp = $int("c1")
                .add($long("c2")) // <1>
                .div(2.); // <2>

        DataFrame df = DataFrame.foldByRow("c1", "c2").of(
                        1, 2L,
                        3, 4L)
                .cols("c3").select(exp);
        // end::arithmetics[]

        print("arithmetics", df);
    }

    @Test
    public void aggregation() {

        // tag::aggregation[]
        NumExp<?> exp = $int("c1")
                .add($long("c2"))
                .sum() // <1> aggregating here
                .div(2.);

        DataFrame df = DataFrame.foldByRow("c1", "c2").of(
                        1, 2L,
                        3, 4L)
                .cols("c3").agg(exp);
        // end::aggregation[]

        print("aggregation", df);
    }
}
