package com.example.demo;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JFreeChartTest {


    @Disabled
    @Test
    public void chartTest() {

        //折线图数据
        DefaultCategoryDataset lineDataset = new DefaultCategoryDataset();
        //添加数据
        lineDataset.addValue(6, "", "语文");
        lineDataset.addValue(7, "", "数学");
        lineDataset.addValue(6, "", "英语");
        lineDataset.addValue(5, "", "物理");
        lineDataset.addValue(4, "", "化学");
        lineDataset.addValue(4, "", "化学1");
        lineDataset.addValue(3, "", "生物");
        lineDataset.addValue(2, "", "生物1");
        lineDataset.addValue(3, "", "生物2");
        lineDataset.addValue(4, "", "生物3");
        lineDataset.addValue(5, "", "生物4");

        //柱状图数据
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        //添加数据
        barDataset.addValue(4, "", "语文");
        barDataset.addValue(7, "", "数学");
        barDataset.addValue(6, "", "英语");
        barDataset.addValue(5, "", "物理");
        barDataset.addValue(7, "", "化学");
        barDataset.addValue(6, "", "化学1");
        barDataset.addValue(9, "", "生物");
        barDataset.addValue(8, "", "生物1");
        barDataset.addValue(6, "", "生物2");
        barDataset.addValue(8, "", "生物3");
        barDataset.addValue(7, "", "生物4");

        //生成的柱状图
        JFreeChart chart = ChartFactory.createBarChart(
            "科目成绩",
            "科目",//X轴的标签
            "分数",//Y轴的标签
            barDataset, //图标显示的数据集合
            PlotOrientation.VERTICAL, //图像的显示形式（水平或者垂直）
            false,//是否显示子标题
            false,//是否生成提示的标签
            false); //是否生成URL链接

        /*
         * 处理图形上的乱码
         */

        //处理主标题的乱码
        chart.getTitle().setFont(new Font("黑体", Font.BOLD, 18));

        //获取图表区域对象
        CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();

        //获取X轴的对象
        CategoryAxis categoryAxis = (CategoryAxis) categoryPlot.getDomainAxis();

        //获取Y轴的对象
        NumberAxis numberAxis = (NumberAxis) categoryPlot.getRangeAxis();

        //处理X轴上的乱码
        categoryAxis.setTickLabelFont(new Font("黑体", Font.BOLD, 10));

        //处理X轴外的乱码
        categoryAxis.setLabelFont(new Font("黑体", Font.BOLD, 10));

        //处理Y轴上的乱码
        numberAxis.setTickLabelFont(new Font("黑体", Font.BOLD, 10));

        //处理Y轴外的乱码
        numberAxis.setLabelFont(new Font("黑体", Font.BOLD, 10));

        //自定义Y轴上显示的刻度，以10作为1格
        numberAxis.setAutoTickUnitSelection(false);
        NumberTickUnit unit = new NumberTickUnit(1);
        numberAxis.setTickUnit(unit);

        //获取绘图区域对象
        BarRenderer barRenderer = (BarRenderer) categoryPlot.getRenderer();

        //设置柱形图的宽度
        barRenderer.setMaximumBarWidth(0.07);

        //在图形上显示数字
        barRenderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        barRenderer.setDefaultItemLabelsVisible(true);
        barRenderer.setDefaultItemLabelFont(new Font("宋体", Font.BOLD, 10));

        /*
         * 放折线图数据
         */
        categoryPlot.setDataset(1, lineDataset);
        //设置折线
        XYSplineRenderer splinerenderer = new XYSplineRenderer();
        splinerenderer.setPrecision(5);

        BasicStroke brokenLine = new BasicStroke(2f,//线条粗细
            BasicStroke.CAP_SQUARE,           //端点风格
            BasicStroke.JOIN_ROUND,           //折点风格
            8.f,                              //折点处理办法 ,如果要实线把该参数设置为NULL
            new float[]{8.0f},               //虚线数组
            0.0f);
        LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
        lineandshaperenderer.setDefaultToolTipGenerator(new StandardCategoryToolTipGenerator());
        lineandshaperenderer.setDefaultStroke(brokenLine);
        categoryPlot.setRenderer(1, lineandshaperenderer);
        // 柱状图和纵轴紧靠
        categoryAxis.setLowerMargin(0.0);

        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
        //折线在柱面前面显示
        categoryPlot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

        /*
         * 在E盘目录下生成图片
         */
        File file = new File("/data/chart.png");
        try {
            ChartUtils.saveChartAsJPEG(file, chart, 800, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

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

    /**
     * 波峰、波谷
     */
    @Disabled
    @Test
    public void waveTest() {
        double[] wave = new double[]{37.69, 37.67, 39.11, 39.69, 39.08, 37.76, 36.36, 35.28, 34.67, 34.42, 34.31, 34.11, 33.67, 32.97, 32.13, 31.34, 30.84, 30.82, 31.34, 32.33, 33.56, 34.69, 35.42, 35.52, 35.02, 34.22, 33.64, 33.68, 33.98, 32.19};
        int direction = wave[1] - wave[0] > 0 ? -1 : 1;
        for (int i = 1; i < wave.length - 1; i++) {
            if ((wave[i + 1] - wave[i]) * direction > 0) {
                direction *= -1;
                if (direction == 1) {
                    System.out.println("(" + i + "," + wave[i] + ")" + "波峰");
                } else {
                    System.out.println("(" + i + "," + wave[i] + ")" + "波谷");
                }
            }
        }
    }

}
