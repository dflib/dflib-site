package com.nhl.dflib.docs;

import com.nhl.dflib.DataFrame;
import com.nhl.dflib.Printers;
import com.nhl.dflib.Series;

public abstract class BaseExample {

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
