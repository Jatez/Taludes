package org.universidad.taludes;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.Collections;

public class Dovela {

    private ArrayList<Punto> vertices = new ArrayList<>();
    private double area;
    private double anguloAuxiliar;
    private float longitud;
    private double peso;

    public double calculateArea() {

        int n = this.getVertices().size();

        if (n > 2) {

            area = 0.0;

            int j = n - 1;

            for (int i = 0; i < n; i++) {
                area += (getVertices().get(j).getCoordenadas().x + getVertices().get(i).getCoordenadas().x) * (getVertices().get(j).getCoordenadas().y - getVertices().get(i).getCoordenadas().y);

                // j is previous vertex to i
                j = i;
            }
            area = Math.abs(area / 2.0);
            peso = MainActivity.getpContrario() * area;
            return area;
        } else {
            return 0;
        }
    }

    public ArrayList<Punto> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Punto> vertices) {
        this.vertices = vertices;
    }

    public double getArea() {
        return area;
    }

    public void ordenarPoligono() {

        if (vertices.size() > 3) {

            boolean mayor = false;
            Collections.sort(vertices, Punto.puntoCompareY);

           for (Punto punto : vertices) {
                if (punto.getCoordenadas().x > MainActivity.getCentroC().getCentro().x) {
                    mayor = true;
                }
            }

            if (mayor) {
                Punto aux = vertices.get(0);
                vertices.set(0, vertices.get(1));
                vertices.set(1, aux);
            }
        }
    }

    private boolean pointExists(ArrayList<Punto> list, PointF punto) {
        for (Punto p : list) {
            if (p.getCoordenadas().equals(punto)) {
                return true;
            }
        }
        return false;
    }

    private int getPointPos(ArrayList<Punto> list, PointF punto) {

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCoordenadas().equals(punto)) {
                return i;
            }
        }

        return 0;

    }

    private Linea minLinea(ArrayList<Linea> list) {
        Linea lineaMenor;
        if (list.isEmpty()) {
            lineaMenor = null;
        } else {

            if (list.size() == 1) {
                return list.get(0);
            }

            lineaMenor = new Linea(new PointF(0, 0), new PointF(0, 0));

            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    lineaMenor = list.get(i);
                } else {
                    if (list.get(i).getMedida() < lineaMenor.getMedida()) {
                        lineaMenor = list.get(i);
                    }
                }
            }
        }
        return lineaMenor;
    }

    private ArrayList<Linea> buscarLineas(ArrayList<Linea> list, int tipo) {

        ArrayList<Linea> listaFiltrada = new ArrayList<>();

        for (Linea l : list) {

            if (tipo == 1) {
                if (l.isInfinite() || l.getPendiente() < 0 || l.getPendiente() == 0) {

                    listaFiltrada.add(l);

                }

            } else {
                if (l.isInfinite() || l.getPendiente() > 0 || l.getPendiente() == 0) {

                    listaFiltrada.add(l);

                }
            }
        }
        return listaFiltrada;
    }

    public double getAnguloAuxiliar() {
        return anguloAuxiliar;
    }

    public void setAnguloAuxiliar(double anguloAuxiliar) {
        this.anguloAuxiliar = anguloAuxiliar;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public double getPeso() {



        return peso;
    }

}
