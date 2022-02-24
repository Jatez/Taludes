package org.universidad.taludes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Dibujar extends Drawable {
    @Override
    public void draw(@NonNull Canvas canvas) {

        Paint paintPunto = new Paint();
        paintPunto.setColor(Color.RED);

        Paint paintLinea = new Paint();
        paintLinea.setColor(Color.BLACK);

        PointF a1 = new PointF(0, 0);
        PointF b1 = new PointF(100f, 100f);

        //Crear un elemento linea para verificar intersección
        Linea l1 = new Linea(a1, b1);

        //Dibujar los puntos de la linea 1
        canvas.drawPoint(a1.x, a1.y, paintPunto);
        canvas.drawPoint(b1.x, b1.y, paintPunto);

        PointF a2 = new PointF(100f, 300f);
        PointF b2 = new PointF(0f, 200f);

        //Crear un elemento linea para verificar intersección
        Linea l2 = new Linea(a2, b2);

        //Dibujar los puntos de la linea 2
        canvas.drawPoint(a2.x, a2.y, paintPunto);
        canvas.drawPoint(b2.x, b2.y, paintPunto);

        canvas.drawLine(a1.x, a1.y, b1.x, b1.y, paintLinea);

    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
