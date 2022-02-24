
package org.universidad.taludes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements Dialog.DialogListener, Dialog_Talud.DialogListener1, Dialog_Matriz.DialogListenerMatriz {
    ArrayList<Linea> lineas = new ArrayList<>();
    ArrayList<Punto> puntos = new ArrayList<>();
    ArrayList<Punto> puntosTalud = new ArrayList<>();
    ArrayList<Linea> lineasTalud = new ArrayList<>();
    ArrayList<PMatriz> circMatrix = new ArrayList<>();
    ArrayList<Punto> puntosDovela = new ArrayList<>();
    ArrayList<Punto> puntosDiametro = new ArrayList<>();
    ArrayList<Punto> puntosAngulos = new ArrayList<>();
    ArrayList<Linea> divisionesDiametro = new ArrayList<>();
    ArrayList<Dovela> dovelas = new ArrayList<>();

    Linea diametro;
    private static Circunferencia centroC;
    LinearLayout layout;
    Canvas canvas;
    Bitmap bitmap;
    PhotoView imageView;
    private static double pContrario;
    private static double Cohesion;
    private static double anguloFriccion;
    int cantidadDivisiones = 6;
    final int cantidadMatriz = 4;
    final int largo = 1024;
    final int ancho = 768;
    float nuevaMedida;
    float angulo;
    String distanciaMatriz;
    String cantidadPuntosMatriz;

    /**
     * DISEÑO
     */

    Button btnHistorial, btnHerramientas, btnResultados, btnLinea, btnTalud;
    TextView lbtHerramientas, lbtHistorial, lbtCalcular;
    RelativeLayout rlOpciones, rlHistorial;

    @SuppressLint({"RestrictedApi", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conexion(); //Aqui se conectan los elementos del diseño

        /**diseño*/

        btnHerramientas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlOpciones.setVisibility(View.VISIBLE);
                rlHistorial.setVisibility(View.INVISIBLE);
            }
        });

        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlOpciones.setVisibility(View.INVISIBLE);
                rlHistorial.setVisibility(View.VISIBLE);

                if(!circMatrix.isEmpty()){

                    String factores = "";

                    for(int i = 0; i < circMatrix.size(); i++){

                        factores += "\nFactor de seguridad " + i + ": " + circMatrix.get(i).getFactorSeguridad() + "\n";

                    }

                    lbtHistorial.setText(factores);

                }

            }
        });

        btnLinea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        btnTalud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendDialog_Talud();
            }
        });

        lbtCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < circMatrix.size(); i++) {

                    System.out.println("XXXXXXXXXXXXXXXXXXXXCIRCUNFERENCIAXXXXXXXXXXXXXXXXXXXXXXX " + i);
                    System.out.println("Cohesion " + getCohesion());
                    System.out.println("peso cont " + getpContrario());
                    System.out.println("angulo x " + getAnguloFriccion());

                    PMatriz pMatriz = circMatrix.get(i);

                    reiniciarListas();
                    centroC = pMatriz.getCircunferencia();
                    iniciarDovelas(pMatriz);
                }
            }
        });


        /**fin de diseño*/

        int j = 0;
        for (int i = 0; i < cantidadMatriz; i++) {

            if (i >= cantidadMatriz / 2) {
                Punto punto = new Punto(new PointF(300 - (50 * j), 450), Tipo.MATRIZ);
                circMatrix.add(new PMatriz(new Circunferencia(300, punto)));
                dibujarPunto(new Punto(circMatrix.get(circMatrix.size() - 1).getCircunferencia().getCentro(), Tipo.MATRIZ), pintura(Color.GREEN, 20, Paint.Style.STROKE), "", null);
                j++;
                puntosTalud.add(punto);
                continue;
            }

            Punto punto = new Punto(new PointF(300 - (50 * i), 500), Tipo.MATRIZ);
            circMatrix.add(new PMatriz(new Circunferencia(300, punto)));
            dibujarPunto(new Punto(circMatrix.get(circMatrix.size() - 1).getCircunferencia().getCentro(), Tipo.MATRIZ), pintura(Color.GREEN, 20, Paint.Style.STROKE), "", null);
            puntosTalud.add(punto);
        }

        canvas.drawLine(0, (float) (largo / (1.5)), largo, (float) (largo / 1.5), pintura(Color.GREEN, 8, Paint.Style.STROKE));
        Punto punto = new Punto(new PointF(0, (float) (largo / (1.5))), Tipo.REGULAR);
        dibujarPunto(punto, pintura(Color.RED, 2, null), "", puntos);
        puntosTalud.add(punto);
        imageView.setImageBitmap(bitmap);

        //canvas.drawPoint(250, 440, pintura(Color.GREEN, 8, Paint.Style.STROKE));
        //canvas.drawPoint(250, 40, pintura(Color.GREEN, 8, Paint.Style.STROKE));


    }

    //Método para no tener que crear un paint para cada elemento
    private Paint pintura(int color, float stroke, Paint.Style style) {

        Paint paint = new Paint();
        paint.setColor(color);
        if (style != null) {
            paint.setStrokeWidth(stroke);
            paint.setStyle(style);
        }
        paint.setTextSize(50);

        return paint;
    }

    //Dibujar un punto dados su coordenada en x y en y
    private void dibujarPunto(float x, float y, Paint paint, ArrayList<Punto> lista) {

        Punto n = new Punto(new PointF(x, y), Tipo.REGULAR);

        for (Punto punto : puntos) {

            if (punto.getCoordenadas().x == n.getCoordenadas().x && punto.getCoordenadas().y == n.getCoordenadas().y) {
                return;
            }
        }

        puntos.add(n);
        lista.add(n);
        canvas.drawPoint(n.getCoordenadas().x, n.getCoordenadas().y, paint);
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
    private void dibujarPunto(Punto punto, Paint paint, String nombre, ArrayList<Punto> lista) {

        for (Punto point : puntos) {
            if (point.getCoordenadas().x == punto.getCoordenadas().x && point.getCoordenadas().y == punto.getCoordenadas().y) {
                return;
            }
        }
        puntos.add(punto);

        if (lista != null) {
            lista.add(punto);
        }
        canvas.drawPoint(punto.getCoordenadas().x, punto.getCoordenadas().y, paint);
        imageView.setImageBitmap(bitmap);
    }

    private void dibujarPuntoMatriz(float x, float y, Paint paint, Tipo tipo) {

        PointF n = new PointF(x, y);
        //circMatrix.add(new Punto(n, tipo));
        canvas.drawPoint(n.x, n.y, paint);
        imageView.setImageBitmap(bitmap);
    }

    //dibujar una linea dado su punto inicial, ángulo y longitud
    private Linea dibujarLinea(PointF puntoIni, float angulo, float longitud, Paint paint) {

        longitud *= 10;
        float radianes = (float) ((angulo * Math.PI) / 180);

        float X = (float) Math.cos(radianes) * longitud;
        float Y = (float) Math.sin(radianes) * longitud;

        X /= 10;
        Y /= 10;

        X += puntoIni.x;
        Y -= puntoIni.y;

        PointF puntoFin = new PointF(Math.abs(X), Math.abs(Y));
        Linea l = new Linea(puntoIni, puntoFin);

        dibujarPunto(puntoFin.x, puntoFin.y, pintura(Color.RED, 2, Paint.Style.STROKE), puntos);

        if (!lineas.contains(l)) {
            lineas.add(l);
        }
        canvas.drawLine(puntoIni.x, puntoIni.y, puntoFin.x, puntoFin.y, paint);
        imageView.setImageBitmap(bitmap);
        return l;

    }

    //Dibujar una linea dado su objeto Linea
    private void dibujarLinea(Linea linea, Paint p, ArrayList list) {
        if (ValidarLinea(linea) == false) {
            canvas.drawLine(linea.getPuntoA().x, linea.getPuntoA().y, linea.getPuntoB().x, linea.getPuntoB().y, p);
            dibujarPunto(linea.getPuntoB().x, linea.getPuntoB().y, pintura(Color.RED, 2, Paint.Style.STROKE), puntos);
            list.add(linea);
            if (!lineas.contains(linea)) {
                lineas.add(linea);
            }
        }
        imageView.setImageBitmap(bitmap);
    }

    //Dibujar una circunferencia dado su centro y su radio
    private void dibujarCircunferencia(Punto centro, float radio, Paint p) {
        canvas.drawCircle(centro.getCoordenadas().x, centro.getCoordenadas().y, radio, p);
    }

    //dibujar una linea dados sus puntos inicial y final
    private void dibujarLinea(float startX, float startY, float endX, float endY, Paint paint, ArrayList list) {
        PointF A = new PointF(startX, startY);
        PointF B = new PointF(endX, endY);
        Linea linea = new Linea(A, B);
        if (ValidarLinea(linea) == false) {
            dibujarPunto(B.x, B.y, pintura(Color.RED, 2, Paint.Style.STROKE), puntos);
            list.add(linea);
            if (!lineas.contains(linea)) {
                lineas.add(linea);
            }
            canvas.drawLine(A.x, A.y, B.x, B.y, paint);
        }
        imageView.setImageBitmap(bitmap);
    }

    //Dibujar linea validado que no haya una sobre la otra
    private void dibujarLinea(PointF inicial, PointF pfinal, Paint paint, ArrayList list) {
        Linea linea = new Linea(inicial, pfinal);
        if (ValidarLinea(linea) == false) {
            dibujarPunto(pfinal.x, pfinal.y, pintura(Color.RED, 2, Paint.Style.STROKE), puntos);
            list.add(linea);
            if (!lineas.contains(linea)) {
                lineas.add(linea);
            }
            canvas.drawLine(inicial.x, inicial.y, pfinal.x, pfinal.y, paint);
        }
        imageView.setImageBitmap(bitmap);
    }

    //Trazar las dovelas y calcula y la circunferencia
    private void iniciarDovelas(PMatriz pMatriz) {


        /*
        Se crean dos lineas para hacer dos radios
        (creo que se puede hacer más resumido)
         */

        float x1 = pMatriz.getCircunferencia().getCentro().x + pMatriz.getCircunferencia().getRadio();
        float x2 = pMatriz.getCircunferencia().getCentro().x - pMatriz.getCircunferencia().getRadio();
        float y = pMatriz.getCircunferencia().getCentro().y;


        //Se crea el diametro con esas dos lineas
        diametro = new Linea(new PointF(x1, y), new PointF(x2, y));

        pMatriz.getCircunferencia().setDiametro(diametro);

        dibujarCircunferencia(new Punto(pMatriz.getCircunferencia().getCentro(), Tipo.MATRIZ), pMatriz.getCircunferencia().getRadio(), pintura(Color.RED, 5, Paint.Style.STROKE));

        dibujarLinea(diametro, pintura(Color.BLACK, 8, Paint.Style.STROKE), lineas);
        float medida = diametro.getMedida();

        float divisiones = medida / cantidadDivisiones;

        for (int i = 0; i <= cantidadDivisiones; i++) {

            if (i > 0 && i < cantidadDivisiones) {
                //Calcular las diviones del diametro y dibujar los puntos en esas divisiones
                Punto p = new Punto(new PointF(diametro.getPuntoB().x + (divisiones * i), diametro.getPuntoB().y), Tipo.REGULAR);
                puntosDiametro.add(p);
                dibujarPunto(p, pintura(Color.YELLOW, 8, Paint.Style.STROKE), "", puntos);

                //Dibujar lineas en esas divisiones para que intersecten con la circunferencia
                Linea linea = new Linea(new PointF(p.getCoordenadas().x, p.getCoordenadas().y), new PointF(p.getCoordenadas().x, p.getCoordenadas().y + 1 + pMatriz.getCircunferencia().getRadio()));

                dibujarLinea(linea, pintura(Color.BLUE, 5, Paint.Style.STROKE), divisionesDiametro);

            }

        }

        calcularIntersectos(pMatriz);

    }

    //Calcula los intersectos con la circunferencia
    private void calcularIntersectos(PMatriz pMatriz) {

        //Circunferencia con lineas
        for (int i = 0; i < lineas.size(); i++) {

            ArrayList<Punto> points = pMatriz.getCircunferencia().getIntersectionPoint(lineas.get(i));

            if (lineas.get(i).equals(diametro)) {
                continue;
            }

            if (lineas.get(i).isPointIniside(points.get(0))) {
                Punto puntito = new Punto(points.get(0).getCoordenadas(), Tipo.FINAL);
                dibujarPunto(puntito, pintura(Color.RED, 15, Paint.Style.STROKE), "", puntosDovela);
                puntosAngulos.add(puntito);
            }

            if (lineas.get(i).isPointIniside(points.get(1))) {
                Punto puntito = new Punto(points.get(1).getCoordenadas(), Tipo.INICIAL);
                dibujarPunto(puntito, pintura(Color.MAGENTA, 15, Paint.Style.STROKE), "", puntosDovela);
                puntosAngulos.add(puntito);
            }
        }

        //Intersecciones entre lineas azules y circunferencia
        /**SACAR ÁNGULO CON ESTOS PUNTOS
         */
        for (Linea linea : divisionesDiametro) {

            Punto interseccion = new Punto(new PointF(pMatriz.getCircunferencia().getIntersectionPoint2(linea).get(0).getCoordenadas().x, pMatriz.getCircunferencia().getIntersectionPoint2(linea).get(0).getCoordenadas().y), Tipo.REGULAR);
            Punto interseccion1 = new Punto(new PointF(pMatriz.getCircunferencia().getIntersectionPoint2(linea).get(1).getCoordenadas().x, pMatriz.getCircunferencia().getIntersectionPoint2(linea).get(1).getCoordenadas().y), Tipo.REGULAR);

            //Se dibujan las intersecciones de las divisiones de la dovela

            if (linea.isPointIniside(interseccion)) {
                if (interseccion.getCoordenadas().x > puntosDovela.get(0).getCoordenadas().x) {
                    dibujarPunto(pMatriz.getCircunferencia().getIntersectionPoint2(linea).get(0),
                            pintura(Color.YELLOW, 15, Paint.Style.STROKE), "", puntosDovela);

                    puntosAngulos.add(pMatriz.getCircunferencia().getIntersectionPoint2(linea).get(0));
                }
            }

            if (linea.isPointIniside(interseccion1)) {
                if (interseccion1.getCoordenadas().x > puntosDovela.get(0).getCoordenadas().x) {
                    dibujarPunto(pMatriz.getCircunferencia().getIntersectionPoint2(linea).get(1),
                            pintura(Color.YELLOW, 15, Paint.Style.STROKE), "", puntosDovela);

                    puntosAngulos.add(pMatriz.getCircunferencia().getIntersectionPoint2(linea).get(1));
                }
            }

        }

        for (Linea lGeneral : lineas) {

            if (lGeneral.getPendiente() < 0) {
                puntosDovela.add(new Punto(lGeneral.getPuntoA(), Tipo.ESQUINA));
                puntosDovela.add(new Punto(lGeneral.getPuntoB(), Tipo.ESQUINA));
            }

            for (Linea lDivision : divisionesDiametro) {

                Punto intersecto = new Punto(lDivision.getIntersectPoint(lGeneral), Tipo.REGULAR);

                if (intersecto.getCoordenadas().x > puntosDovela.get(0).getCoordenadas().x) {
                    if (lDivision.isPointIniside(intersecto) && lGeneral.isPointIniside(intersecto)) {
                        dibujarPunto(intersecto, pintura(Color.YELLOW, 15, Paint.Style.STROKE), "", puntosDovela);
                    }
                }
            }
        }

        calcularPuntos(pMatriz);

    }

    //Calcular los puntos que se hallan intersectados en la circunferencia
    private void calcularPuntos(PMatriz pMatriz) {
        for (Punto punto : puntosDovela) {
            if (punto.getTipo() == Tipo.ESQUINA || punto.getTipo() == Tipo.INICIAL || punto.getTipo() == Tipo.FINAL) {
                Punto punto1 = new Punto(new PointF(punto.getCoordenadas().x, pMatriz.getCircunferencia().getDiametro().getPuntoA().y), Tipo.REGULAR);
                dibujarPunto(punto1, pintura(Color.YELLOW, 15, Paint.Style.STROKE), "", puntos);
                dibujarLinea(punto1.getCoordenadas().x, punto1.getCoordenadas().y, punto.getCoordenadas().x, punto.getCoordenadas().y + 1, pintura(Color.YELLOW, 6, Paint.Style.STROKE), divisionesDiametro);
            }
        }
        Collections.sort(divisionesDiametro);
        for (int i = 0; i < divisionesDiametro.size(); i++) {
            System.out.println(divisionesDiametro.get(i).getPuntoA().x);
        }

        System.out.println("tamaño: " + divisionesDiametro.size());

        for (int i = 0; i < divisionesDiametro.size(); i++) {
            for (Punto punto : puntosDovela) {
                if (divisionesDiametro.get(i).isPointIniside(punto)) {
                    divisionesDiametro.get(i).getPuntos().add(punto);
                }
            }
            System.out.println(i + ". puntos: " + divisionesDiametro.get(i).getPuntos().size());
        }
        calcularPuntosDovela(pMatriz);
    }

    //genArea

    private void calcularPuntosDovela(PMatriz pMatriz) {
        ArrayList<Punto> puntosTemp = new ArrayList<>();

        //Coje los puntos correctamente, wuju!

        for (int i = 0; i < divisionesDiametro.size(); i++) {

            if (divisionesDiametro.get(i).getPuntos().size() > 0) {

                addPoints(divisionesDiametro.get(i).getPuntos(), puntosTemp);

                if (divisionesDiametro.get(i).getPuntos().size() == 1) {

                    if (divisionesDiametro.get(i).getPuntos().get(0).getTipo().equals(Tipo.FINAL))
                        dovelas.add(genPoligono(puntosTemp));

                } else if (divisionesDiametro.get(i).getPuntos().size() == 2) {
                    dovelas.add(genPoligono(puntosTemp));
                }

            }
        }

        //Verificar la cantidad de vertices de cada poligono creado
        for (int i = 0; i < dovelas.size(); i++) {

            System.out.println("poligono " + i + " vertices " + dovelas.get(i).getVertices().size());

        }

        //poligonos.get(4).ordenarPoligono();

        /*for (Punto punto : poligonos.get(4).getVertices()) {

            System.out.println("coordenadas: " + punto.getCoordenadas());
            canvas.drawPoint(punto.getCoordenadas().x, punto.getCoordenadas().y, pintura(Color.RED, 10, Paint.Style.STROKE));

        }*/

        /*int j = 2;

        for (int i = 0; i < dovelas.get(j).getVertices().size() - 1; i++) {
            canvas.drawLine(dovelas.get(j).getVertices().get(i).getCoordenadas().x, dovelas.get(j).getVertices().get(i).getCoordenadas().y,
                    dovelas.get(j).getVertices().get(i + 1).getCoordenadas().x, dovelas.get(j).getVertices().get(i + 1).getCoordenadas().y,
                    pintura(Color.MAGENTA, 8, Paint.Style.STROKE));

        }*/

        for (int i = 0; i < dovelas.size(); i++) {

            System.out.println("Area " + i + ": " + dovelas.get(i).getArea());
            System.out.println("coordenadas poligono " + i);

            for (int k = 0; k < dovelas.get(i).getVertices().size(); k++) {

                System.out.println(dovelas.get(i).getVertices().get(k).getCoordenadas());

            }

        }

        Collections.sort(puntosAngulos, Punto.puntoCompareX);

        System.out.println("angulos " + puntosAngulos.size());

        ArrayList<Float> longitudes = new ArrayList<>();
        ArrayList<Double> angulos = new ArrayList<>();

        for (int i = 1; i < puntosAngulos.size() - 1; i++) {

            Linea linea1;
            Linea linea2;
            Double angulo;


            if (puntosAngulos.get(i).getCoordenadas().x < pMatriz.getCircunferencia().getCentro().x) {
                linea1 = dibujarLineasAngulos(puntosAngulos.get(i).getCoordenadas(), puntosAngulos.get(i - 1).getCoordenadas());
                linea2 = dibujarLineaAnguloIzquierda(puntosAngulos.get(i).getCoordenadas(), puntosAngulos.get(i - 1).getCoordenadas());

                angulo = calcularAngulo(linea2.getMedida(), linea1.getMedida());

                longitudes.add(linea1.getMedida());
                angulos.add(angulo);

                System.out.println();
            } else {
                linea1 = dibujarLineasAngulos(puntosAngulos.get(i).getCoordenadas(), puntosAngulos.get(i + 1).getCoordenadas());
                linea2 = dibujarLineaAnguloDerecha(puntosAngulos.get(i).getCoordenadas(), puntosAngulos.get(i + 1).getCoordenadas());
                angulo = calcularAngulo(linea2.getMedida(), linea1.getMedida());

                longitudes.add(linea1.getMedida());
                angulos.add(angulo);
            }


            if (puntosAngulos.get(i).getCoordenadas().x == pMatriz.getCircunferencia().getCentro().x || (puntosAngulos.get(i).getCoordenadas().x < pMatriz.getCircunferencia().getCentro().x &&
                    puntosAngulos.get(i + 1).getCoordenadas().x > pMatriz.getCircunferencia().getCentro().x)) {

                linea1 = dibujarLineasAngulos(puntosAngulos.get(i).getCoordenadas(), puntosAngulos.get(i - 1).getCoordenadas());
                linea2 = dibujarLineaAnguloIzquierda(puntosAngulos.get(i).getCoordenadas(), puntosAngulos.get(i - 1).getCoordenadas());
                angulo = calcularAngulo(linea2.getMedida(), linea1.getMedida());
                longitudes.add(linea1.getMedida());
                angulos.add(angulo);

            }
        }

        for (int i = 0; i < dovelas.size(); i++) {

            dovelas.get(i).setAnguloAuxiliar(angulos.get(i));
            dovelas.get(i).setLongitud(longitudes.get(i));

        }

        calcularFactorSeguridad(pMatriz);

    }

    private double calcularAngulo(float medida1, float medida2) {

        double angulo;

        angulo = Math.toDegrees(Math.acos(medida1 / medida2));

        return angulo;
    }

    private Linea dibujarLineasAngulos(PointF p1, PointF p2) {

        Linea linea = new Linea(p1, p2);
        canvas.drawLine(p1.x, p1.y, p2.x, p2.y, pintura(Color.RED, 8, Paint.Style.STROKE));

        return linea;

    }

    private Linea dibujarLineaAnguloIzquierda(PointF p1, PointF p2) {
        Linea linea = new Linea(p1, new PointF(p2.x, p1.y));
        canvas.drawLine(linea.getPuntoA().x, linea.getPuntoA().y, linea.getPuntoB().x, linea.getPuntoB().y, pintura(Color.RED, 8, Paint.Style.STROKE));
        return linea;
    }

    private Linea dibujarLineaAnguloDerecha(PointF p1, PointF p2) {
        Linea linea = new Linea(p1, new PointF(p2.x, p1.y));
        canvas.drawLine(linea.getPuntoA().x, linea.getPuntoA().y, linea.getPuntoB().x, linea.getPuntoB().y, pintura(Color.RED, 8, Paint.Style.STROKE));
        return linea;
    }


    private ArrayList<Punto> addPoints(ArrayList<Punto> base, ArrayList<Punto> objetivo) {

        for (Punto punto : base) {
            objetivo.add(punto);
        }

        return objetivo;
    }

    private Dovela genPoligono(ArrayList<Punto> list) {

        Dovela dovela = new Dovela();

        Punto aux1 = new Punto(list.get(list.size() - 1).getCoordenadas(), Tipo.REGULAR);
        Punto aux2 = new Punto(list.get(list.size() - 2).getCoordenadas(), Tipo.REGULAR);

        for (Punto punto : list) {
            dovela.getVertices().add(punto);
        }

        dovela.ordenarPoligono();
        dovela.calculateArea();

        list.clear();

        list.add(aux1);
        list.add(aux2);

        return dovela;
    }

    private boolean ValidarLinea(Linea linea) {
        for (Linea linea1 : lineas) {
            if (linea1.getPuntoA().equals(linea.getPuntoA())) {
                if (linea1.getPuntoB().equals(linea.getPuntoB())) {
                    return true;
                } else if (linea1.isPointIniside(new Punto(linea.getPuntoB(), Tipo.REGULAR))) {
                    return true;
                }
            }
        }
        return false;
    }

    private double calcularFactorSeguridad(PMatriz pMatriz) {

        double factorSeguridad, acumNum = 0, acumDen = 0;

        for (int i = 0; i < dovelas.size(); i++) {

            acumNum += ((getCohesion() * dovelas.get(i).getLongitud()) +
                    (dovelas.get(i).getPeso() * Math.cos(dovelas.get(i).getAnguloAuxiliar() *
                            Math.tan(getAnguloFriccion()))));

            acumDen += dovelas.get(i).getPeso() * Math.sin(dovelas.get(i).getAnguloAuxiliar());

        }

        factorSeguridad = acumNum / acumDen;
        pMatriz.setFactorSeguridad(factorSeguridad);

        //20, 10, 25
        Toast.makeText(this, "factor de seguridad " + factorSeguridad, Toast.LENGTH_SHORT).show();
        System.out.println("factor de seguridad " + factorSeguridad);

        return factorSeguridad;

    }

    private void conexion() {


        imageView = findViewById(R.id.tablero);
        // medida = findViewById(R.id.txtMedida);
        //angulo = findViewById(R.id.txtAngulo);
        bitmap = Bitmap.createBitmap(largo, ancho, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        layout = findViewById(R.id.layout);


        /** diseño*/
        btnHistorial = findViewById(R.id.btnHistorial);
        btnHerramientas = findViewById(R.id.btnHerramientas);
        btnResultados = findViewById(R.id.btnResultados);
        lbtHerramientas = findViewById(R.id.lbtHerramientas);
        btnLinea = findViewById(R.id.btnLinea);
        btnTalud = findViewById(R.id.btnTalud);
        rlOpciones = findViewById(R.id.rlOpciones);
        lbtHistorial = findViewById(R.id.lbtHistorial);
        rlHistorial = findViewById(R.id.rlHistorial);
        lbtCalcular = findViewById(R.id.lbtCalcular);

    }

    public static Circunferencia getCentroC() {
        return centroC;
    }

    public static double getpContrario() {
        return pContrario;
    }

    public static double getCohesion() {
        return Cohesion;
    }

    public static double getAnguloFriccion() {
        return anguloFriccion;
    }

    /**
     * metodo diseño
     */

    public void openDialog() {
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(), "Dialogo");

    }

    @Override
    public void ApplyTexts(String medida, String angulo) {

        nuevaMedida = Float.parseFloat(medida);
        this.angulo = Float.parseFloat(angulo);
        nuevaMedida = nuevaMedida * 10;
        lineasTalud.add(dibujarLinea(puntos.get(puntos.size() - 1).getCoordenadas(), this.angulo, nuevaMedida, pintura(Color.BLACK, 8, Paint.Style.STROKE)));
        puntosTalud.add(new Punto(lineasTalud.get(lineasTalud.size() - 1).getPuntoB(), Tipo.REGULAR));

    }

    public void opendDialog_Talud() {

        Dialog_Talud dialog_talud = new Dialog_Talud();
        dialog_talud.show(getSupportFragmentManager(), "Dialogo");

    }

    @Override
    public void ApplyTextsTalud(String peso, String cohesion, String angulo) {

        pContrario = Double.valueOf(Double.parseDouble(peso));
        Cohesion = Double.valueOf(Double.parseDouble(cohesion));
        anguloFriccion = Double.valueOf(Double.parseDouble(angulo));

    }

    public void opendDialog_Matriz() {

        Dialog_Matriz dialog_matriz = new Dialog_Matriz();
        dialog_matriz.show(getSupportFragmentManager(), "Dialogo");

    }

    @Override
    public void ApplyTextsMatrix(String cantidad, String distancia) {

        distanciaMatriz = distancia;
        cantidadPuntosMatriz = cantidad;

    }

    private void reiniciarListas() {

        puntosDovela.clear();
        puntosDiametro.clear();
        puntosAngulos.clear();
        divisionesDiametro.clear();
        dovelas.clear();

        lineas.clear();
        for(Linea linea : lineasTalud){
            lineas.add(linea);
        }

        puntos.clear();
        for(Punto punto : puntosTalud){
            puntos.add(punto);
        }

    }

}
