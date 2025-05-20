package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.Printers;
import org.dflib.Series;

import java.util.Map;

public abstract class BaseTest {

    protected void print(String label, Map<String, DataFrame> dfs) {
        System.out.println();
        System.out.println("[" + label + "]");

        dfs.forEach((k, v) -> System.out.println(k + Printers.tabular.print(v)));
    }

    protected void print(String label, DataFrame df) {
        System.out.println();
        System.out.println("[" + label + "]");
        System.out.println(Printers.tabular.print(df));
    }

    protected void print(String label, Series<?> series) {
        System.out.println();
        System.out.println("[" + label + "]");
        System.out.println(Printers.tabular.print(series));
    }
}
