package org.universidad.taludes;

import android.graphics.PointF;

import java.util.Comparator;

public class Punto {

    private PointF coordenadas;
    private Tipo tipo;

    public Punto(PointF coordenadas, Tipo tipo){

        this.setCoordenadas(coordenadas);
        this.setTipo(tipo);

    }

    public PointF getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(PointF coordenadas) {
        this.coordenadas = coordenadas;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public static Comparator<Punto> puntoCompareX = new Comparator<Punto>() {

        public int compare(Punto s1, Punto s2) {
            float StudentName1 = s1.getCoordenadas().x;
            float StudentName2 = s2.getCoordenadas().x;

            //ascending order
            return (int) StudentName1 - (int) StudentName2;

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};

    /*Comparator for sorting the list by roll no*/
    public static Comparator<Punto> puntoCompareY = new Comparator<Punto>() {

        public int compare(Punto s1, Punto s2) {

            float rollno1 = s1.getCoordenadas().y;
            float rollno2 = s2.getCoordenadas().y;

            /*For ascending order*/
            return (int) rollno2 - (int) rollno1;

            /*For descending order*/
            //rollno2-rollno1;
        }};
}
