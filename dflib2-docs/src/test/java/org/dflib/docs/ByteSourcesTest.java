package org.dflib.docs;

import org.dflib.ByteSources;
import org.dflib.DataFrame;
import org.dflib.csv.Csv;
import org.dflib.fs.FSFolder;
import org.dflib.zip.Zip;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

public class ByteSourcesTest extends BaseTest {

    @Test
    public void fsFolder() {

        String folderName = "src/test/resources/csvs";

// tag::fsFolder[]
        FSFolder folder = FSFolder.of(Path.of(folderName));
        ByteSources byteSources = folder.sources();
// end::fsFolder[]

// tag::fsFolderProcess[]
        Map<String, DataFrame> dfs = Csv.loadAll(byteSources);
// end::fsFolderProcess[]
        print("fsFolder", dfs);
    }

    @Test
    public void zip() {
        String zipFile = "src/test/resources/test.zip";
// tag::zip[]
        Zip zip = Zip.of(new File(zipFile));
        ByteSources byteSources = zip.sources();
// end::zip[]
        Map<String, DataFrame> dfs = byteSources
                .process((n, s) -> n.endsWith(".csv") ? Csv.load(s) : null);

        print("zip", dfs);
    }
}
