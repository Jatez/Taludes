package org.universidad.taludes;

import android.graphics.PointF;

import java.util.ArrayList;

public class Circunferencia {

    private float radio;
    private PointF centro;
    private Linea diametro;

    public Circunferencia(float radio, Punto centro) {

        this.setRadio(radio);
        this.setCentro(centro.getCoordenadas());

    }


    //@Override
    public ArrayList<Punto> getIntersectionPoint(Linea linea) {

        ArrayList<Punto> puntos = new ArrayList<>();

        float x1, x2, y, a = 1, b = -(2 * getCentro().x);
        double c = -(Math.pow(getRadio(), 2)) + (Math.pow(getCentro().x, 2)) + (Math.pow(getCentro().y, 2)) + (Math.pow(linea.getIndependienteB(), 2)) - (2 * getCentro().y * linea.getIndependienteB());

        float operacion = (float) Math.sqrt(Math.pow(b, 2) - 4 * (a * c));

        x1 = (-b + operacion) / 2 * a;
        x2 = (-b - operacion) / 2 * a;

        y = linea.getIndependienteB();

        Punto interseccion1 = new Punto(new PointF(x1, y), Tipo.REGULAR);
        Punto interseccion2 = new Punto(new PointF(x2, y), Tipo.REGULAR);

        puntos.add(interseccion1);
        puntos.add(interseccion2);

        return puntos;
    }

    public ArrayList<Punto> getIntersectionPoint2(Linea linea) {

        ArrayList<Punto> puntos = new ArrayList<>();

        float a = 1, b = -(2 * getCentro().y), c1 = (float) Math.pow((linea.getPuntoA().x - getCentro().x), 2);
        float c = c1 + (float) (Math.pow(getCentro().y, 2)) - (float) (Math.pow(getRadio(), 2));

        float operacion = (float) Math.sqrt(Math.pow(b, 2) - 4 * (a * c));

        float y1 = (-b + operacion) / 2 * a;
        float y2 = (-b - operacion) / 2 * a;

        Punto interseccion1 = new Punto(new PointF(linea.getPuntoA().x, y1), Tipo.REGULAR);
        Punto interseccion2 = new Punto(new PointF(linea.getPuntoA().x, y2), Tipo.REGULAR);

        puntos.add(interseccion1);
        puntos.add(interseccion2);

        return puntos;
    }

    public float getRadio() {
        return radio;
    }

    public void setRadio(float radio) {
        this.radio = radio;
    }

    public PointF getCentro() {
        return centro;
    }

    public void setCentro(PointF centro) {
        this.centro = centro;
    }

    public Linea getDiametro() {
        return diametro;
    }

    public void setDiametro(Linea diametro) {
        this.diametro = diametro;
    }
}
