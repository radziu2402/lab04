package main;

import java.awt.*;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import javax.swing.*;

public class XYLineChart_AWT extends JPanel{
    private final XYSeries velocity = new XYSeries("velocity",false,true);


    public XYLineChart_AWT(String chartTitle, String yLabel){
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "kąt obrotu [stopnie]",
                yLabel,
                createDataset(),
                PlotOrientation.VERTICAL,
                false, true, false);

        ChartPanel chartPanel = new ChartPanel(xylineChart);
        chartPanel.setPreferredSize(new Dimension(435,370));
        final XYPlot plot = xylineChart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        plot.setRenderer(renderer);
        add(chartPanel);
    }

    private XYDataset createDataset(){
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(velocity);
        return dataset;
    }

    public void updateSeries(double y, double angle){
        velocity.add(angle,y);
    }
    public void deleteSeries(){
        velocity.clear();
    }

}
