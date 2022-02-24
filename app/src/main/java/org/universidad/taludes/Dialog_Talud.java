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

public class Dialog_Talud extends AppCompatDialogFragment {

    EditText txtPeso, txtCohesion, txtAngulo;
    private Dialog_Talud.DialogListener1 listener;

    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.talud, null);



        builder.setView(view).setTitle("Talud").setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                String peso = txtPeso.getText().toString();
                String cohesion = txtCohesion.getText().toString();
                String angulo= txtAngulo.getText().toString();
                listener.ApplyTextsTalud(peso, cohesion, angulo);

            }
        });
        txtPeso = view.findViewById(R.id.txtPeso);
        txtCohesion = view.findViewById(R.id.txtCohesion);
        txtAngulo = view.findViewById(R.id.txtAngulo0);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (Dialog_Talud.DialogListener1) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ "Error");
        }
    }

    public interface DialogListener1{
        void ApplyTextsTalud(String peso, String cohesion, String angulo);
    }
}
