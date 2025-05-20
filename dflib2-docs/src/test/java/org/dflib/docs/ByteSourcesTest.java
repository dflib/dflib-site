package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.csv.Csv;
import org.dflib.zip.Zip;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

public class ByteSourcesTest {

    @Test
    public void zip() {
// tag::zip[]
        Zip zip = Zip.of(new File( // ...
// end::zip[]
                "src/test/resources/test.zip"
// tag::zip[]
        ));

        Map<String, DataFrame> dfs = zip.sources() // <1>
                .process((n, s) -> n.endsWith(".csv") ? Csv.load(s) : null); // <2>
// end::zip[]
    }
}
