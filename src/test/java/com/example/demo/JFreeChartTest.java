package com.example.demo;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

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


    @Disabled
    @Test
    public void chartTest2() throws IOException {
        XYDataset xyDataset = createDataset();
        JFreeChart chart = ChartFactory.createTimeSeriesChart("数量/月份", "月份", "数量",
            xyDataset, true, true, true);
        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis dateaxis = (DateAxis) plot.getDomainAxis();
        dateaxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM"));
        dateaxis.setLabelFont(new Font("黑体", Font.BOLD, 14));         //水平底部标题
        dateaxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12));  //垂直标题
        ValueAxis rangeAxis = plot.getRangeAxis();//获取柱状
        rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
        chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
        chart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));//设置标题字体

        FileOutputStream out = null;
        try {
            out = new FileOutputStream("E:\\deleted\\2.jpg");
            ChartUtils.writeChartAsJPEG(out, 0.5f, chart, 800, 600, null);
        } finally {
            try {
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private XYDataset createDataset() {
        TimeSeries timeseries = new TimeSeries("数量");
        timeseries.add(new Month(2, 2001), 181.80);
        timeseries.add(new Month(3, 2001), 167.30);
        timeseries.add(new Month(4, 2001), 153.80);
        timeseries.add(new Month(5, 2001), 167.59);
        timeseries.add(new Month(6, 2001), 158.80);
        timeseries.add(new Month(7, 2001), 148.30);
        timeseries.add(new Month(8, 2001), 153.90);
        timeseries.add(new Month(9, 2001), 142.69);
        timeseries.add(new Month(10, 2001), 123.2);
        timeseries.add(new Month(11, 2001), 131.80);
        timeseries.add(new Month(12, 2001), 139.59);
        timeseries.add(new Month(1, 2002), 142.90);
        timeseries.add(new Month(2, 2002), 138.69);
        timeseries.add(new Month(3, 2002), 137.30);
        timeseries.add(new Month(4, 2002), 143.90);
        timeseries.add(new Month(5, 2002), 139.80);
        timeseries.add(new Month(6, 2002), 137);
        timeseries.add(new Month(7, 2002), 132.80);
        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
        timeseriescollection.addSeries(timeseries);
        return timeseriescollection;
    }

    //    https://blog.csdn.net/qq_24194029/article/details/84634082
    private XYSeriesCollection createXYDataset() {
        // 注入数据1
        XYSeries linedataset1 = new XYSeries("线1");
        linedataset1.add(1, 13.79);
        linedataset1.add(5, 6.81);
        linedataset1.add(15, 4.29);
        linedataset1.add(20, 3.36);

        // 注入数据2
        XYSeries linedataset2 = new XYSeries("线2");
        linedataset2.add(1, 3.79);
        linedataset2.add(3, 4.29);
        linedataset2.add(15, 6.81);
        linedataset2.add(27, 13.36);
        linedataset2.add(25, 3.36);

        //建立数据模型
        XYSeriesCollection localXYSeriesCollection = new XYSeriesCollection();
        localXYSeriesCollection.addSeries(linedataset1);
        localXYSeriesCollection.addSeries(linedataset2);
        return localXYSeriesCollection;
    }

    @Disabled
    @Test
    public void chartTest3() throws IOException, InterruptedException {
        //建立数据模型
        XYSeriesCollection localXYSeriesCollection = createXYDataset();

        XYSplineRenderer splinerenderer = new XYSplineRenderer();
        //设置线的笔触（粗细）
//        splinerenderer.setSeriesStroke(0, new BasicStroke(4.0F, 1, 1, 1.0F));
//        splinerenderer.setSeriesStroke(1, new BasicStroke(4.0F, 1, 1, 1.0F));

        splinerenderer.setPrecision(2);// 设置精度差（影响曲线弧度）


        //设置横纵坐标描述
        NumberAxis xAxis = new NumberAxis("描述1");
        xAxis.setAutoRangeIncludesZero(false);
//        xAxis.setAutoRange(false);
        xAxis.setNumberFormatOverride(new DecimalFormat());
        NumberAxis yAxis = new NumberAxis("描述2");
        yAxis.setAutoRangeIncludesZero(false);

        XYPlot plot = new XYPlot(localXYSeriesCollection, xAxis, yAxis, splinerenderer);
        // x轴 // 分类轴网格是否可见
        plot.setDomainGridlinesVisible(true);
        // y轴 //数据轴网格是否可见
        plot.setRangeGridlinesVisible(true);
        // 是否显示格子线
        plot.setRangeGridlinesVisible(true);
        // 设置背景透明度
        plot.setBackgroundAlpha(0.3f);
        // 数据轴（y轴）色彩
        plot.setRangeGridlinePaint(Color.black);
        // 分类轴（x轴）色彩
        plot.setDomainGridlinePaint(Color.black);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        //数据轴的数据标签（可以只显示整数标签，需要将AutoTickUnitSelection设false）
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        //是否强制在自动选择的数据范围中包含0
        rangeAxis.setAutoRangeIncludesZero(true);
        //设置坐标轴间距但必须满足一定条件
        rangeAxis.setUpperMargin(1);// Y轴间距
        rangeAxis.setLowerMargin(1);//X轴间距
        //坐标轴标题旋转角度（纵坐标可以旋转）
        rangeAxis.setLabelAngle(Math.PI / 2.0);

        JFreeChart chart = new JFreeChart("测试", // 标题
            JFreeChart.DEFAULT_TITLE_FONT, // 标题的字体，这样就可以解决中文乱码的问题
            plot, true);

        ChartFrame pieFrame = new ChartFrame("统计图", chart);
        pieFrame.pack();
        pieFrame.setVisible(true);

        Thread.sleep(40000);
    }

}
