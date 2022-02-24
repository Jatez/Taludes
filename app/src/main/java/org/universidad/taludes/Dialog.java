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

public class Dialog extends AppCompatDialogFragment {

    EditText txtMedida, txtAngulo;
    private DialogListener listener;

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.linea, null);
        builder.setView(view).setTitle("Linea").setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                String medida = txtMedida.getText().toString();
                String angulo = txtAngulo.getText().toString();
                listener.ApplyTexts(medida,angulo);
            }
        });
        txtMedida = view.findViewById(R.id.txtMedida);
        txtAngulo = view.findViewById(R.id.txtAngulo);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (DialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ "Error");
        }
    }

    public interface DialogListener{
        void ApplyTexts(String medida, String angulo);

    }

}
