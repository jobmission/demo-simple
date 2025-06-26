package com.example.demo;

import org.hipparchus.stat.regression.SimpleRegression;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class HipparchusTest {
    @Disabled
    @Test
    void regressionTest() {
        SimpleRegression regression = new SimpleRegression();
        regression.addData(1d, 2d);

        regression.addData(2d, 4d);

        regression.addData(3d, 6d);

        System.out.println(regression.getIntercept());

        System.out.println(regression.getSlope());

        System.out.println(regression.getSlopeStdErr());

        System.out.println(regression.predict(4));
    }
}
