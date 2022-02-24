package org.universidad.taludes;

import android.graphics.PointF;

import java.util.ArrayList;

public final class Linea implements Comparable {

    private boolean infinite = false;
    private PointF puntoA;
    private PointF puntoB;
    private float independienteB;
    private float pendiente;
    private ArrayList<Punto> puntos = new ArrayList<>();

    //m = 0: paralela al eje x
    //m = x: paralela al eje y

    public Linea(PointF puntoA, PointF puntoB) {

        this.setPuntoA(puntoA);
        this.setPuntoB(puntoB);

        setPendiente((puntoB.y - puntoA.y) / (puntoB.x - puntoA.x));
        setIndependienteB(puntoA.y - (getPendiente() * puntoA.x));

        if (Double.isInfinite(getPendiente())) {
            setInfinite(true);
        }
    }

    public PointF getIntersectPoint(Linea l) {

        PointF intersecto = new PointF();


        if (!isInfinite()) {
            intersecto.x = (this.getIndependienteB() - l.getIndependienteB()) / (l.getPendiente() - this.getPendiente());
            intersecto.y = getPendiente() * ((l.getIndependienteB() - this.getIndependienteB()) / (this.getPendiente() - l.getPendiente())) + getIndependienteB();
        } else {
            if (l.getPendiente() != 0) {
            /*    float x = (l.getPuntoA().x + l.puntoA.y);
                intersecto.set(this.puntoA.x, (x - this.puntoA.x));
                return intersecto;
            */
                intersecto.set(this.getPuntoA().x, (l.getPendiente() * this.getPuntoA().x) + l.getIndependienteB());
                //y = (m * x) + b

            } else {
                intersecto.set(this.getPuntoA().x, l.getPuntoA().y);
            }


        }
        return intersecto;

    }

    public float getMedida() {
        float medida = 0;
        if (getPendiente() == 0) {
            medida = Math.abs(this.getPuntoB().x - this.getPuntoA().x);
        } else {
            medida = (float) Math.abs(Math.sqrt(Math.pow((this.getPuntoB().y - this.getPuntoA().y), 2) + Math.pow((this.getPuntoB().x - this.getPuntoA().x), 2)));
        }
        return medida;
    }


    public boolean isPointIniside(Punto p) {

        PointF AB, AP;
        float kAB, kAP;

        AB = new PointF(this.getPuntoA().x - this.getPuntoB().x, this.getPuntoA().y - this.getPuntoB().y);
        AP = new PointF(this.getPuntoA().x - p.getCoordenadas().x, this.getPuntoA().y - p.getCoordenadas().y);
        //vector2 = new PointF(p.getPunto().x - this.b.x, p.getPunto().y - this.b.y);

        float operacion = (AB.x * AP.y) - (AP.x * AB.y);

        kAB = (AB.x * AB.x) + (AB.y * AB.y);
        kAP = (AB.x * AP.x) + (AB.y * AP.y);

        if (operacion == 0 || this.getPendiente() < 0) {
            if (kAP == 0 || kAP == kAB) {
                return true;
            }

            if (0 < kAP && kAP < kAB) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(Object o) {
        Linea linea = (Linea) o;
        return (int) this.getPuntoA().x - (int) linea.getPuntoA().x;
    }

    public boolean isInfinite() {
        return infinite;
    }

    public void setInfinite(boolean infinite) {
        this.infinite = infinite;
    }

    public PointF getPuntoA() {
        return puntoA;
    }

    public void setPuntoA(PointF puntoA) {
        this.puntoA = puntoA;
    }

    public PointF getPuntoB() {
        return puntoB;
    }

    public void setPuntoB(PointF puntoB) {
        this.puntoB = puntoB;
    }

    public float getIndependienteB() {
        return independienteB;
    }

    public void setIndependienteB(float independienteB) {
        this.independienteB = independienteB;
    }

    public float getPendiente() {
        return pendiente;
    }

    public void setPendiente(float pendiente) {
        this.pendiente = pendiente;
    }

    public ArrayList<Punto> getPuntos() {
        return puntos;
    }
}
