package org.universidad.taludes;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog_Matriz extends AppCompatDialogFragment {

    EditText txtCantidad, txtDistancia;
    private DialogListenerMatriz listener;

    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.matriz, null);


        builder.setView(view).setTitle("Talud").setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                String cantidad = txtCantidad.getText().toString();
                String distancia = txtDistancia.getText().toString();
                listener.ApplyTextsMatrix(cantidad, distancia);

            }
        });
        txtCantidad = view.findViewById(R.id.txtCantidad);
        txtDistancia = view.findViewById(R.id.txtDistancia);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (Dialog_Matriz.DialogListenerMatriz) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ "Error");
        }
    }

    public interface DialogListenerMatriz{
        void ApplyTextsMatrix(String peso, String cohesion);
    }

}
