package org.dflib.jjava.docs;

import org.dflib.jjava.kernel.JavaKernel;
import org.junit.jupiter.api.Test;

import static org.dflib.jjava.kernel.JavaNotebookStatics.kernel;

public class KernelTest {

    @Test
    public void doKernel() {
        JavaKernel kernel1 = JavaKernel.builder().build();

        try {
            kernel1.onStartup();

            // tag::doKernel[]
            JavaKernel kernel = kernel(); // <1>
            // end::doKernel[]

        } finally {
            kernel1.onShutdown(false);
        }
    }

    @Test
    public void doEval() {
        JavaKernel kernel1 = JavaKernel.builder().build();

        try {
            kernel1.onStartup();

            // tag::doEval[]
            kernel().eval("1 + 2");
            // end::doEval[]

        } finally {
            kernel1.onShutdown(false);
        }
    }
}
