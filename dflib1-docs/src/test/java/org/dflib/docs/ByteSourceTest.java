package org.dflib.docs;

import org.apache.hadoop.thirdparty.com.google.common.io.Files;
import org.dflib.ByteSource;
import org.dflib.DataFrame;
import org.dflib.csv.Csv;
import org.dflib.http.Http;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class ByteSourceTest extends BaseTest {

    @Test
    public void byteArray() throws IOException {
// tag::byteArray[]
        byte[] bytes = // ...
// end::byteArray[]
                Files.toByteArray(new File("src/test/resources/f1.csv"));

// tag::byteArray[]
        ByteSource src = ByteSource.of(bytes);  // <1>

        DataFrame df = Csv
                .loader()
                .load(src); // <2>
// end::byteArray[]

        print("byteArray", df);
    }

    @Test
    public void url() {

// tag::url[]
        ByteSource src = ByteSource.ofUrl("https://example.org/my.csv");
// end::url[]
    }

    @Test
    public void resourceUrl() {

// tag::resourceUrl[]
        ByteSource src = ByteSource.ofUrl(getClass().getResource("f1.csv"));
// end::resourceUrl[]
    }

    @Test
    public void http() {

// tag::http[]
        ByteSource src = Http.of("https://example.org/")
                .path("dir/my.csv")
                .queryParam("ts", System.currentTimeMillis())
                .header("Authorization", "Bearer abcdefghijklmnop")
                .source();
// end::http[]
    }
}
