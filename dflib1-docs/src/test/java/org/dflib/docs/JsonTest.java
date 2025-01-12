package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.json.Json;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.dflib.Exp.$col;

public class JsonTest extends BaseTest {

    @Test
    void list() {

// tag::list[]
        String json = """
                [
                    5,
                    { "a":1, "b":"S1" },
                    { "a":2, "b":"S2", "c":"S3" },
                    { "a":3, "c":"S4", "d":false }
                ]""";

        DataFrame df = Json
                .loader()
                .load(json);
// end::list[]

        print("list", df);
    }

    @Test
    void object() {

// tag::object[]
        String json = """
                {
                    "p1" : 5,
                    "p2" : { "a":1, "b":"S1" },
                    "p3" : { "a":2, "b":"S2", "c":"S3" },
                    "p4" : { "a":3, "c":"S4", "d":false }
                }""";

        DataFrame df = Json
                .loader()
                .load(json);
// end::object[]

        print("object", df);
    }

    @Test
    void nested() {

// tag::nested[]

        Function<Object, String> labeler = o -> switch (o) { // <1>
            case List l -> "list";
            case Map m -> "map";
            default -> "scalar";
        };

        String json = """
                [
                    { "a": [1,2,3]   },
                    { "a": {"b":4, "c":5} },
                    { "a": {"b":[6,7]} }
                ]""";

        DataFrame df = Json
                .loader()
                .load(json)
                .cols("L1").merge($col("a").mapVal(labeler))  // <2>
                .cols("L2").merge($col("a").mapVal(o -> switch (o) {  // <3>
                    case List l -> labeler.apply(l.get(0));
                    case Map m -> labeler.apply(m.values().iterator().next());
                    default -> "scalar";
                }));
// end::nested[]

        print("nested", df);
    }

    @Test
    void pathRoot() {

// tag::pathRoot[]
        String json = """
               { "a":1, "b":"S1" }""";

        DataFrame df = Json
                .loader()
                .pathExpression("$") // <1>
                .load(json);
// end::pathRoot[]

        print("pathRoot", df);
    }

    @Test
    void pathChildrenProperties() {

// tag::pathChildrenProperties[]
        String json = """
                [
                    5,
                    { "a":1, "b":"S1" },
                    { "a":2, "b":"S2", "c":"S3" },
                    { "a":3, "c":"S4", "d":false }
                ]""";

        DataFrame df = Json
                .loader()
                .pathExpression("$..['a','b']") // <1>
                .load(json);
// end::pathChildrenProperties[]

        print("pathChildrenProperties", df);
    }

    @Test
    void pathChildrenPropertiesIncludeNulls() {

// tag::pathChildrenPropertiesIncludeNulls[]
        String json = """
                [
                    5,
                    { "a":1, "b":"S1" },
                    { "a":2, "b":"S2", "c":"S3" },
                    { "a":3, "c":"S4", "d":false }
                ]""";

        DataFrame df = Json
                .loader()
                .pathExpression("$..['a','b']")
                .nullsForMissingLeafs() // <1>
                .load(json);
// end::pathChildrenPropertiesIncludeNulls[]

        print("pathChildrenProperties", df);
    }

    @Test
    void pathNestedRoot() {

// tag::pathNestedRoot[]
        String json = """
                {
                    "data" : [
                        { "a":1, "b":"S1" },
                        { "a":2, "b":"S2" },
                        { "a":3, "b":"S4" }
                    ]
                }""";

        DataFrame df = Json
                .loader()
                .pathExpression("$.data.*") // <1>
                .nullsForMissingLeafs()
                .load(json);
// end::pathNestedRoot[]

        print("pathNestedRoot", df);
    }

    @Test
    void save() {
// tag::save[]
        DataFrame df = DataFrame
                .byArrayRow("name", "age")
                .appender()
                .append("Joe", 18)
                .append("Andrus", 45)
                .append("Joan", 32)
                .toDataFrame();

        String json = Json
                .saver()
                .saveToString(df); // <1>
// end::save[]

        System.out.println(json);
    }
}
