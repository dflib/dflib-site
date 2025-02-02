package org.dflib.docs;

import org.dflib.DataFrame;
import org.dflib.Printers;
import org.dflib.Series;

public abstract class BaseTest {

    protected void print(String label, DataFrame df) {
        System.out.println();
        System.out.println("[" + label + "]");
        System.out.println(Printers.tabular.toString(df));
    }

    protected void print(String label, Series<?> series) {
        System.out.println();
        System.out.println("[" + label + "]");
        System.out.println(Printers.tabular.toString(series));
    }
}
