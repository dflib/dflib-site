package org.dflib.docs;

import org.dflib.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

// tag::expImport[]
import static org.dflib.Exp.*;
// end::expImport[]

public class ExpTest extends BaseTest {

    @Test
    public void columnExp() {

// tag::columnExp[]
        StrExp lastExp = $str("last");
        DecimalExp salaryExp = $decimal(2);
// end::columnExp[]

// tag::columnExpEval[]
        DataFrame df = DataFrame.foldByRow("first", "last", "salary").of(
                "Jerry", "Cosin", new BigDecimal("120000"),
                "Juliana", "Walewski", new BigDecimal("80000"),
                "Joan", "O'Hara", new BigDecimal("95000"));

        Series<String> last = lastExp.eval(df);
        Series<BigDecimal> salary = salaryExp.eval(df);
// end::columnExpEval[]
    }

    @Test
    public void constantExp() {

        DataFrame df = DataFrame.foldByRow("first", "last", "salary").of(
                "Jerry", "Cosin", new BigDecimal("120000"),
                "Juliana", "Walewski", new BigDecimal("80000"),
                "Joan", "O'Hara", new BigDecimal("95000"));

// tag::constantExp[]
        Series<String> hi = $val("hi!").eval(df);
// end::constantExp[]

        print("hi", hi);
    }

    @Test
    public void constantConcatExp() {

        DataFrame df = DataFrame.foldByRow("first", "last", "salary").of(
                "Jerry", "Cosin", new BigDecimal("120000"),
                "Juliana", "Walewski", new BigDecimal("80000"),
                "Joan", "O'Hara", new BigDecimal("95000"));

// tag::constantConcatExp[]
        Series<String> fn = concat( // <1>
                $str("first"),
                $val(" "), // <2>
                $str("last")).eval(df);
// end::constantConcatExp[]

        print("constantConcatExp", fn);
    }

    @Test
    public void expChain() {

// tag::expChain[]
        // <1>
        Condition c = and( // <2>
                $str("last").startsWith("A"), // <3>
                $decimal("salary").add($decimal("benefits")).gt(100000.) // <4>
        );
// end::expChain[]
    }

    @Test
    public void sorter() {

// tag::sorter[]
        // sort by last name in the ascending order
        Sorter s = $str("last").asc();
// end::sorter[]
    }
}
