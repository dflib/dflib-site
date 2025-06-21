package org.dflib.docs;

import org.dflib.Exp;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.dflib.Exp.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ExpLangTest extends BaseTest {

    @Test
    public void parseExp() {

// tag::parseExp[]
        Exp<?> exp = $("a = 3");
        // same as $col("a").eq(3)
// end::parseExp[]
        assertEquals($col("a").eq(3), exp);
    }

    @Test
    public void column() {

        Exp<?> exp = $(
                // tag::column[]
                "_iAmAColumn"
                // $col("_iAmAColumn")
                // end::column[]
        );
        assertEquals($col("_iAmAColumn"), exp);
    }

    @Test
    public void columnBackticks() {

        Exp<?> exp = $(
                // tag::columnBackticks[]
                "`I am a column!`"
                // $col("I am a column!")
                // end::columnBackticks[]
        );
        assertEquals($col("I am a column!"), exp);
    }

    @Test
    public void columnBackticksEscape() {

        Exp<?> exp = $(
                // tag::columnBackticksEscape[]
                "```I am a column!```"
                // $col("`I am a column!`")
                // end::columnBackticksEscape[]
        );
        assertEquals($col("`I am a column!`"), exp);
    }

    @Test
    public void columnByIndex() {

        Exp<?> exp = $(
                // tag::columnByIndex[]
                "col(5)"
                // $col(5)
                // end::columnByIndex[]
        );
        assertEquals( $col(5), exp);
    }

    @Test
    public void strVal() {

        Exp<?> exp = $(
                // tag::strVal[]
                "'I am a String!'"
                // $strVal("I am a String!")
                // end::strVal[]
        );
        assertEquals($strVal("I am a String!"), exp);
    }

    @Test
    public void strValEscape() {

        Exp<?> exp = $(
                // tag::strValEscape[]
                "'I''m a String!'"
                // $strVal("I'm a String!")
                // end::strValEscape[]
        );
        assertEquals($strVal("I'm a String!"), exp);
    }

    @Test
    public void intVal() {

        Exp<?> exp = $(
                // tag::intVal[]
                "5000"
                // $intVal(5000)
                // end::intVal[]
        );
        assertEquals($intVal(5000), exp);
    }

    @Test
    public void intValNegative() {

        Exp<?> exp = $(
                // tag::intValNegative[]
                "-5_000_000"
                // $intVal(-5000000)
                // end::intValNegative[]
        );
        assertEquals($intVal(-5000000), exp);
    }

    @Test
    public void floatVal() {

        Exp<?> exp = $(
                // tag::floatVal[]
                "5_000.01"
                // $floatVal(5000.01)
                // end::floatVal[]
        );
        assertEquals($floatVal(5000.01f), exp);
    }

    @Test
    public void decimalVal() {

        Exp<?> exp = $(
                // tag::decimalVal[]
                "5_000.01m"
                // $decimalVal(new BigDecimal("5000.01"))
                // end::decimalVal[]
        );
        assertEquals($decimalVal(new BigDecimal("5000.01")), exp);
    }
}
