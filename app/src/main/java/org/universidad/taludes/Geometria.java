package org.universidad.taludes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.widget.ImageView;

import java.util.ArrayList;

public class Geometria {

    Canvas canvas;
    ImageView imageView;
    Bitmap bitmap;

    public Geometria(Canvas canvas, ImageView imageView, Bitmap bitmap){

        this.canvas = canvas;
        this.imageView = imageView;
        this.bitmap = bitmap;

    }

    /*
    TODO:
    Pasar los métodos que no deberían estar en la clase principal para ésta
    así ahorramos espacio en el MainActivity y no queda tan cargado
    */

    public void dibujarPunto(float x, float y, Paint paint, String nombre, ArrayList<Punto> lista) {

        Punto n = new Punto(new PointF(x, y), Tipo.REGULAR);

        lista.add(n);
        canvas.drawPoint(n.getCoordenadas().x, n.getCoordenadas().y, paint);
        canvas.drawText(nombre, n.getCoordenadas().x, n.getCoordenadas().y - 10, paint);
        imageView.setImageBitmap(bitmap);

    }

    /**
     * Dibujar un punto dado su PointF
     *
     * @param punto  punto a dibujar
     * @param paint  color y estilo del punto
     * @param nombre nombre que va a tener ese punto
     * @param lista  lista a la que se va a agregar al punto
     */
    public void dibujarPunto(Punto punto, Paint paint, String nombre, ArrayList<Punto> lista) {
        lista.add(punto);
        canvas.drawPoint(punto.getCoordenadas().x, punto.getCoordenadas().y, paint);
        canvas.drawText(nombre, punto.getCoordenadas().x, punto.getCoordenadas().y - 10, paint);
        imageView.setImageBitmap(bitmap);
    }

    //Dibujar una linea dado su objeto Linea
    public void dibujarLinea(Linea linea, Paint pinturaLinea, Paint pinturaPunto, ArrayList listLinea, ArrayList listPunto) {

        canvas.drawLine(linea.getPuntoA().x, linea.getPuntoA().y, linea.getPuntoB().x, linea.getPuntoB().y, pinturaLinea);
        dibujarPunto(linea.getPuntoB().x, linea.getPuntoB().y, pinturaPunto, "", listPunto);
        listLinea.add(linea);

    }

    public void dibujarCircunferencia(Punto centro, float radio, Paint p, ArrayList list) {
        canvas.drawCircle(centro.getCoordenadas().x, centro.getCoordenadas().y, radio, p);
        Circunferencia circunferencia = new Circunferencia(radio, centro);
        list.add(circunferencia);
    }

    public void dibujarLinea(PointF puntoIni, float angulo, float longitud, Paint paintLinea, Paint pinturaPunto, ArrayList listLinea, ArrayList listPunto) {

        //float pendiente = (float) Math.tan(angulo * 100);

        longitud *= 10;
        float radianes = (float) ((angulo * Math.PI) / 180);

        float X = (float) Math.cos(radianes) * longitud;
        float Y = (float) Math.sin(radianes) * longitud;

        X /= 10;
        Y /= 10;

        X += puntoIni.x;
        Y -= puntoIni.y;

        //float y = (float) Math.sin(angulo) * longitud;
        //float x = (float) Math.cos(angulo) * longitud;

        //y += puntoIni.y;
        //x += puntoIni.x;

        PointF puntoFin = new PointF(Math.abs(X), Math.abs(Y));
        Linea l = new Linea(puntoIni, puntoFin);
        //tan-1(m/100)

        dibujarPunto(puntoFin.x, puntoFin.y, pinturaPunto, listPunto.size() + 1 + "", listPunto);
        listLinea.add(l);
        canvas.drawLine(puntoIni.x, puntoIni.y, puntoFin.x, puntoFin.y, pinturaPunto);
        imageView.setImageBitmap(bitmap);

    }

    //dibujar una linea dados sus puntos inicial y final
    public void dibujarLinea(float startX, float startY, float endX, float endY, Paint paintLinea, Paint pinturaPunto, ArrayList listLinea, ArrayList listPunto) {
        PointF A = new PointF(startX, startY);
        PointF B = new PointF(endX, endY);
        Linea linea = new Linea(A, B);
        dibujarPunto(B.x, B.y, pinturaPunto, (listPunto.size() + 1) + "", listPunto);
        listLinea.add(linea);
        canvas.drawLine(A.x, A.y, B.x, B.y, paintLinea);
        imageView.setImageBitmap(bitmap);
    }

    public void dibujarLinea(PointF inicial, PointF pfinal, Paint paintLinea, Paint pinturaPunto, ArrayList listLinea, ArrayList listPunto) {
        Linea linea = new Linea(inicial, pfinal);
        dibujarPunto(pfinal.x, pfinal.y, pinturaPunto, (listPunto.size() + 1) + "", listPunto);
        listLinea.add(linea);
        canvas.drawLine(inicial.x, inicial.y, pfinal.x, pfinal.y, paintLinea);
        imageView.setImageBitmap(bitmap);
    }

}
