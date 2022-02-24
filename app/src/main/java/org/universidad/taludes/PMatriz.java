package org.universidad.taludes;

public class PMatriz {

    private Circunferencia circunferencia;
    private double factorSeguridad;

    public PMatriz(Circunferencia circunferencia){

        this.circunferencia = circunferencia;

    }

    public double getFactorSeguridad() {
        return factorSeguridad;
    }

    public void setFactorSeguridad(double factorSeguridad) {
        this.factorSeguridad = factorSeguridad;
    }

    public Circunferencia getCircunferencia() {
        return circunferencia;
    }
}
