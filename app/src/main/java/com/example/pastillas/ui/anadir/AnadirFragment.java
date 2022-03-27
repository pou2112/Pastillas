package com.example.pastillas.ui.anadir;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pastillas.R;
import com.example.pastillas.databinding.FragmentDashboardBinding;
import com.example.pastillas.ui.medicamentos.Mostrado;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AnadirFragment extends Fragment {

    private AnadirViewModel anadirViewModel;
    private FragmentDashboardBinding binding;
    EditText etCodigo;
    Button btnLeerCodigo;
   // public String codigob;

    public AnadirFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        anadirViewModel = new ViewModelProvider(this).get(AnadirViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        etCodigo = root.findViewById(R.id.etCodigo);
        btnLeerCodigo = root.findViewById(R.id.btnLeerCodigo);


        btnLeerCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escanear();
            }
        });

        return root;
    }


    public void escanear() {

        IntentIntegrator intent = IntentIntegrator.forSupportFragment(AnadirFragment.this);
        //IntentIntegrator intent = new IntentIntegrator(getActivity());
        intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intent.setPrompt("ESCANEAR CODIGO");
        intent.setCameraId(0);
        intent.setBeepEnabled(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode,data);

        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(getContext(),"Cancelaste el escaneo", Toast.LENGTH_SHORT).show();

            }else {
                etCodigo.setText(result.getContents().toString());
            }

        }else {
            super.onActivityResult(requestCode, resultCode, data);

           // codigob = String.valueOf(resultCode);

            Intent intent = new Intent(getContext(), Mostrado.class);
            startActivity(intent);

        }

    }
}