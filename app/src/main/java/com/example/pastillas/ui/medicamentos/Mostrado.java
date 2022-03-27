package com.example.pastillas.ui.medicamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.Glide;
import com.example.pastillas.R;
import com.example.pastillas.databinding.ActivityMostradoBinding;
import com.example.pastillas.ui.anadir.AnadirFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Mostrado extends AppCompatActivity {

    ActivityMostradoBinding binding;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://pastillapp-36e2a-default-rtdb.firebaseio.com/");
    ImageView fotoMed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMostradoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.readdataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = binding.codigo.getText().toString();
                if (!username.isEmpty()) {

                    readData(username);
                } else {

                    Toast.makeText(Mostrado.this, "Introduce un código de barras", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private void readData(String username) {


        //reference = FirebaseDatabase.getInstance().getReference("Users");
        reference = FirebaseDatabase.getInstance().getReference("Medicamentos");
        reference.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful()) {

                    if (task.getResult().exists()) {

                        Toast.makeText(Mostrado.this, "Éxito", Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();

                        String codigobarras = String.valueOf(dataSnapshot.child("codigobarras").getValue());

                        String nombreMed = String.valueOf(dataSnapshot.child("nombre").getValue());

                        String foto = String.valueOf(dataSnapshot.child("imagen").getValue());

                        String prospecto = String.valueOf(dataSnapshot.child("prospecto").getValue());
                        String tipo = String.valueOf(dataSnapshot.child("tipo").getValue());
                        String cantidad = String.valueOf(dataSnapshot.child("cantidad").getValue());
                        String uso = String.valueOf(dataSnapshot.child("uso").getValue());
                        String empresa = String.valueOf(dataSnapshot.child("empresa").getValue());
                        String quees = String.valueOf(dataSnapshot.child("quees").getValue());
                        String formato = String.valueOf(dataSnapshot.child("formato").getValue());



                        String imgTipo;

                        Log.e("prospecto", prospecto);


                        binding.quees.setText(quees);
                        binding.empresa.setText(empresa);
                        binding.uso.setText(uso);
                        binding.cantidad.setText(cantidad);
                        binding.codigobarras.setText(codigobarras);
                        binding.nombreMed.setText(nombreMed);
                        Glide.with(binding.getRoot()).load(formato).into(binding.imagenFormato);
                        Glide.with(binding.getRoot()).load(foto).into(binding.fotoMed);


                        binding.botonProspecto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(prospecto));
                                startActivity(i);
                            }
                        });



                        switch (formato) {
                            case "sobres":
                                Glide.with(binding.getRoot()).load(R.drawable.sobre).into(binding.imagenFormato);
                        break;
                            case "pastilla":
                                Glide.with(binding.getRoot()).load(R.drawable.pildora_color).into(binding.imagenFormato);

                        }


                        switch (tipo) {
                            case "cabeza":
                                Glide.with(binding.getRoot()).load(R.drawable.dolorcabeza).into(binding.imagenTipo);
                                String nombrecito = String.valueOf(binding.imagenTipo.getTag());
                                binding.imagenTipo.setTag(nombrecito);

                                break;
                            case "mocos":
                                Glide.with(binding.getRoot()).load(R.drawable.mocos_color).into(binding.imagenTipo);
                                break;

                        }


                        // Picasso.get().load(foto).into(binding.fotoMed);


                        Log.e("foto", foto);


                    } else {

                        Toast.makeText(Mostrado.this, "No existe", Toast.LENGTH_SHORT).show();

                    }


                } else {

                    Toast.makeText(Mostrado.this, "Error en la lectura", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}