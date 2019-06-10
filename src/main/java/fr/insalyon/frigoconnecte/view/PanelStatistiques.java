package fr.insalyon.frigoconnecte.view;

import fr.insalyon.frigoconnecte.Main;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.Marker;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;

public class PanelStatistiques extends JPanel {


    LinkedList<Double> temp1 = new LinkedList<>();
    LinkedList<Date> temp1date = new LinkedList<>();
    LinkedList<Double> temp2 = new LinkedList<>();
    LinkedList<Date> temp2date = new LinkedList<>();
    LinkedList<Double> temp3 = new LinkedList<>();
    LinkedList<Date> temp3date = new LinkedList<>();
    LinkedList<Double> hum1 = new LinkedList<>();
    LinkedList<Date> hum1date = new LinkedList<>();
    LinkedList<Double> hum2 = new LinkedList<>();
    LinkedList<Date> hum2date = new LinkedList<>();

    double meanTempInside = 0;
    double meanTempCongelo = 0;
    double meanHumInside = 0 ;

    final XYChart tempChart;
    JPanel tempChartPanel;
    final XYChart humChart;
    JPanel humChartPanel;

    JLabel infoLabel;


    public PanelStatistiques(Events eventsHandler, int idFrigo) {
        super();
        //this.setLayout(null);
        this.setBackground(Color.WHITE);
        
        this.refreshStats(idFrigo);

        tempChart = new XYChartBuilder().width(800).height(350).title("Températures dans mon réfrigérateur").xAxisTitle("Date").yAxisTitle("Température (°C)").build();
        tempChart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        tempChart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        tempChart.getStyler().setChartBackgroundColor(Color.white);
        Marker[] markersTemp = {null,null,null};
        tempChart.getStyler().setSeriesMarkers(markersTemp);
        if (temp1.size() > 0)
        tempChart.addSeries("Haut du réfrigérateur", temp1date, temp1);
        if (temp2.size() > 0)
        tempChart.addSeries("Bas du réfrigérateur", temp2date, temp2);
        if (temp3.size() > 0)
        tempChart.addSeries("Congélateur", temp3date, temp3);
        tempChartPanel= new XChartPanel<XYChart>(tempChart);


        humChart = new XYChartBuilder().width(800).height(350).title("Humidité dans mon réfrigérateur").xAxisTitle("Date").yAxisTitle("Taux d'humidité (%)").build();
        humChart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        humChart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        humChart.getStyler().setChartBackgroundColor(Color.white);
        Marker[] markersHum = {null,null};
        humChart.getStyler().setSeriesMarkers(markersTemp);
        if (hum1.size() > 0)
        humChart.addSeries("Haut du réfrigérateur", hum1date, hum1);
        if (hum2.size() > 0)
        humChart.addSeries("Bas du réfrigérateur", hum2date, hum2);
        humChartPanel= new XChartPanel<XYChart>(humChart);

        for (Double t : temp1) meanTempInside+=t;
        for (Double t : temp2) meanTempInside+=t;
        meanTempInside=meanTempInside/((double) (temp1.size()+temp2.size()));

        for (Double t : temp3) meanTempCongelo+=t;
        meanTempCongelo=meanTempCongelo/((double) temp3.size());

        for (Double t : hum1) meanHumInside+=t;
        for (Double t : hum2) meanHumInside+=t;
        meanHumInside=meanHumInside/((double) (hum1.size()+hum2.size()));



        this.add(new JLabel("Température moyenne sur 48h : " + (float)meanTempInside + "°C dans le réfrigérateur. "+ StandardPanel.newLine +" Taux" +
                " d'humidité de "+ (float)meanHumInside + "%. La moyenne est de " + (float)meanTempCongelo + "°C dans le congélateur."));
        this.add(tempChartPanel);
        this.add(humChartPanel);



        this.validate();
        this.setVisible(true);

    }




    public void refreshStats(int idFrigo) { //HARDCODÉ !!!!!
        ResultSet rs =  Main.maBD.getMesureFrom(idFrigo, 0);
        while (true) {
            try {
                if (!rs.next()) break;
                hum1.add(rs.getDouble("valeur")/10.0);
                hum1date.add(rs.getTimestamp("dateMesure"));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
         rs =  Main.maBD.getMesureFrom(idFrigo, 1);
        while (true) {
            try {
                if (!rs.next()) break;
                    temp1.add(rs.getDouble("valeur")/10.0);
                    temp1date.add(rs.getTimestamp("dateMesure"));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        rs =  Main.maBD.getMesureFrom(idFrigo, 2);
        while (true) {
            try {
                if (!rs.next()) break;
                hum2.add(rs.getDouble("valeur")/10.0+(Math.random()-0.5));
                hum2date.add(rs.getTimestamp("dateMesure"));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        rs =  Main.maBD.getMesureFrom(idFrigo, 3);
        while (true) {
            try {
                if (!rs.next()) break;
                temp2.add(rs.getDouble("valeur")/10.0 + (Math.random()-0.5));
                temp2date.add(rs.getTimestamp("dateMesure"));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        rs =  Main.maBD.getMesureFrom(idFrigo, 4);
        while (true) {
            try {
                if (!rs.next()) break;
                temp3.add(rs.getDouble("valeur"));
                temp3date.add(rs.getTimestamp("dateMesure"));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
