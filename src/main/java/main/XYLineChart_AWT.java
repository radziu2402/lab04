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
    private XYSeries velocity = new XYSeries("velocity",false,false);


    public XYLineChart_AWT(String chartTitle, String xLabel){
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle,
                xLabel,
                "kąt obrotu [stopnie]",
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

    public void updateSeries(double x, double angle){
        velocity.add(x,angle);
    }
    public void deleteSeries(){
        velocity.clear();
    }

}
