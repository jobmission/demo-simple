package com.example.demo;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.hipparchus.stat.regression.SimpleRegression;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;

public class MathTest {
    @Disabled
    @Test
    public void mathTest() throws IOException {
        java.util.List<Double> input = Arrays.asList(37.54, 38.09, 39.18, 39.08, 38.84, 38.01, 37.24, 35.16, 34.47, 34.01, 34.31, 34.07, 33.50, 32.90, 32.62, 32.09, 31.08, 30.18, 30.44, 31.53, 34.68, 34.92, 36.46, 35.31, 33.79, 33.79, 34.53, 34.19, 33.28, 32.37);
        java.util.List<Double> result = polynomial(input, 7);
        System.out.println(result);
    }

    public List<Double> polynomial(List<Double> data, int degree) {
        final WeightedObservedPoints obs = new WeightedObservedPoints();
        for (int i = 0; i < data.size(); i++) {
            obs.add(i, data.get(i));
        }
        /**
         * 实例化一个2次多项式拟合器
         */
        final PolynomialCurveFitter fitter = PolynomialCurveFitter.create(degree);//degree 阶数，一般为 3

        /**
         * 实例化检索拟合参数(多项式函数的系数)
         */
        final double[] coeff = fitter.fit(obs.toList());
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            //多项式函数f(x) = a0 * x + a1 * pow(x, 2) + .. + an * pow(x, n).
//            double a = coeff[0] * Math.pow(i, 0);
//            double b = coeff[1] * Math.pow(i, 1);
//            double c = coeff[2] * Math.pow(i, 2);
//            double d = coeff[3] * Math.pow(i, 3);
//            double e = coeff[4] * Math.pow(i, 4);
//            double f = coeff[5] * Math.pow(i, 5);
//            double g = coeff[6] * Math.pow(i, 6);
//            double h = coeff[7] * Math.pow(i, 7);
//            double j = coeff[8] * Math.pow(i, 8);
//            double k = coeff[9] * Math.pow(i, 9);
//
//            double tmp = (a + b + c + d + e + f + g + h + j + k);

            double temp = 0;
            for (int j = 0; j <= degree; j++) {
                temp += coeff[j] * Math.pow(i, j);
            }
            result.add(temp);
        }
        return result;
    }

    public double[] minMaxNormalization(double[] data) {
        double min = Arrays.stream(data).min().orElse(Double.NaN);
        double max = Arrays.stream(data).max().orElse(Double.NaN);
        return Arrays.stream(data).map(x -> (x - min) / (max - min)).toArray();
    }

    public double[] zScoreNormalization(double[] data) {
        double mean = Arrays.stream(data).average().orElse(Double.NaN);
        double stdDev = Math.sqrt(Arrays.stream(data).map(x -> Math.pow(x - mean, 2)).sum() / (data.length - 1));
        return Arrays.stream(data).map(x -> (x - mean) / stdDev).toArray();
    }

    public double[] quantileNormalization(double[] data) {
        Arrays.sort(data);
        return DoubleStream.of(data).map(x -> Arrays.binarySearch(data, x) / (double) (data.length - 1)).toArray();
    }

    private double getMedian(double[] data) {
        double[] sortedData = Arrays.copyOf(data, data.length);
        Arrays.sort(sortedData);
        return (sortedData.length % 2 == 0) ? (sortedData[sortedData.length / 2 - 1] + sortedData[sortedData.length / 2]) / 2.0 : sortedData[sortedData.length / 2];
    }

    private double getQuantile(double[] data, double quantile) {
        int index = (int) Math.round(data.length * quantile);
        return Arrays.stream(data).sorted().toArray()[index];
    }

    public double[] quarterNormalize(double[] data) {
        double median = getMedian(data);
        double q1 = getQuantile(data, 0.25);
        double q3 = getQuantile(data, 0.75);
        double iqr = q3 - q1;
        return Arrays.stream(data).map(x -> (x - median) / iqr).toArray();
    }

    public static double[] l2Normalize(double[] vector) {
        double norm = Math.sqrt(Arrays.stream(vector).map(x -> x * x).sum());
        return Arrays.stream(vector).map(x -> x / norm).toArray();
    }


    @Disabled
    @Test
    void normalizationTest() {
        double[] data = {10, 20, 30, 40, 50};
        double[] normalizedData = minMaxNormalization(data);
        System.out.println(Arrays.toString(normalizedData));

        double[] data2 = {1, 2, 3, 4, 5};
        double[] normalizedData2 = zScoreNormalization(data2);
        System.out.println(Arrays.toString(normalizedData2));

        double[] data3 = {10, 20, 30, 40, 50};
        double[] normalizedDat3 = quantileNormalization(data3);
        System.out.println(Arrays.toString(normalizedDat3));

        double[] data4 = {10, 20, 30, 40, 50};
        double[] normalizedData4 = quarterNormalize(data4);
        System.out.println(Arrays.toString(normalizedData4));

        double[] data5 = {10, 20, 30, 40, 50};
        double[] normalizedData5 = l2Normalize(data5);
        System.out.println(Arrays.toString(normalizedData5));

        double[] temp = Arrays.stream(data).map(x -> x / 50).toArray();
        System.out.println(Arrays.toString(temp));
    }

    @Disabled
    @Test
    void slopeTest() {
        int[] timestamps = {1, 2, 3, 4, 5, 6, 7}; // 时间点（如天数）
        double[] values = {10, 12, 11, 13, 16, 14, 15};
        SimpleRegression regression = new SimpleRegression();
        for (int i = 0; i < timestamps.length; i++) {
            regression.addData(timestamps[i], values[i]);
        }
        double slope = regression.getSlope(); // 斜率为正则趋势上升
        System.out.println("slope: " + slope);

        int n = values.length;
        double S = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                S += Double.compare(values[j], values[i]);
            }
        }
        System.out.println("Slope (S): " + S);
    }
}
