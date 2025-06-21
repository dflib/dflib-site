package org.dflib.docs;

import org.dflib.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

// tag::expImport[]
import static org.dflib.Exp.*;
// end::expImport[]

public class ExpTest extends BaseTest {

    @Test
    public void columnExps() {

// tag::columnExps[]
        $col("col"); // <1>

        // <2>
        $decimal("col");
        $double("col");
        $int("col");
        $long("col");

        // <3>
        $date("col");
        $dateTime("col");
        $time("col");

        // <4>
        $bool("col");
        $str("col");
// end::columnExps[]
    }

    @Test
    public void columnExp() {

// tag::columnExp[]
        StrExp lastExp = $str("last"); // <1>
        DecimalExp salaryExp = $decimal(2); // <2>
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
    public void castAs() {
// tag::castAs[]
        $str("salary").castAsDecimal();
// end::castAs[]
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
    public void valInCondition() {

        DataFrame df = DataFrame.foldByRow("first", "last", "salary").of(
                "Jerry", "Cosin", new BigDecimal("120000"),
                "Juliana", "Walewski", new BigDecimal("80000"),
                "Joan", "O'Hara", new BigDecimal("95000"));

// tag::valInCondition[]
        Series<String> fn = ifExp(
                $decimal("salary").ge(100000),
                $strVal("high"), // <1>
                $strVal("low") // <2>
        ).eval(df);
// end::valInCondition[]

        print("valInCondition", fn);
    }

    @Test
    public void mapVal() {

// tag::mapVal[]
        Exp<byte[]> bytes = $decimal("col")
                .mapVal(d -> d.toBigInteger().toByteArray()); // <1>
// end::mapVal[]
    }

    @Test
    public void map() {

// tag::map[]
        Exp<Integer> exp = $int("col")
                .map(s -> Series.ofVal(s.get(0), s.size())); // <1>
// end::map[]
    }

    @Test
    public void agg() {

// tag::agg[]
        Exp<Integer> exp = $col("col")
                .agg(s -> s.unique().size()); // <1>
// end::agg[]
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
