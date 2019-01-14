package com.example.pcx.idealkilohesapla;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class frTumOlcumler extends Fragment {


    Context context;
    ListView listemiz = null;
    List<Olcum> olcumListesi;
    SQLiteHelper db;
    ArrayAdapter<String> mAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.layout_olcumler,container,false);
        context = inflater.getContext();

        db = new SQLiteHelper(context);


        listemiz = (ListView) view.findViewById(R.id.listemiz);


        olcumListesi = db.OlcumleriGetir();
        List<String> listeKayitlar = new ArrayList<>();
        for (int i=0; i< olcumListesi.size(); i++){
            listeKayitlar.add(i,olcumListesi.get(i).getDURUM() + " " + olcumListesi.get(i).getOlcumTarihi());
        }
        mAdapter = new ArrayAdapter<String>(context, R.layout.layout_olcumler_satir,R.id.listMetin, listeKayitlar);
        listemiz.setAdapter(mAdapter);

        listemiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Ölçüm Bilgisi Görüntüle");
                builder.setMessage(
                                "Boy: "+olcumListesi.get(position).getBoy() + " \n" +
                                "Kilo: "+olcumListesi.get(position).getKilo() + " \n" +
                                "Cinsiyet: "+olcumListesi.get(position).getCinsiyet() + " \n" +
                                "İdeal Kilo: "+olcumListesi.get(position).getKiloIdeal() + " \n" +
                                "Yağsız Ağırlık: "+olcumListesi.get(position).getYagsizAgirlik() + " \n" +
                                "VKI: "+olcumListesi.get(position).getVucutKitleIndexi() + " \n" +
                                "Vucut Yuzey Alanı: "+olcumListesi.get(position).getVucutYuzeyAlani() + " \n" +
                                "DURUM: "+olcumListesi.get(position).getDURUM() + " \n" +
                                "Ölçüm Kayıt Tarihi: \n"+olcumListesi.get(position).getOlcumTarihi()
                );
                builder.show();
            }
        });

        listemiz.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Ölçümü Sil");
                builder.setMessage("Bu ölçümü silmek istediğinize eminmisiniz?");
                builder.setNegativeButton("SİL", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {

                        db.OlcumSil(olcumListesi.get(position).getId());
                        //--

                        olcumListesi = db.OlcumleriGetir();
                        List<String> listeKayitlar = new ArrayList<>();
                        for (int i=0; i< olcumListesi.size(); i++){
                            listeKayitlar.add(i,olcumListesi.get(i).getDURUM() + " " + olcumListesi.get(i).getOlcumTarihi());
                        }
                        mAdapter = new ArrayAdapter<String>(context, R.layout.layout_olcumler_satir,R.id.listMetin, listeKayitlar);
                        listemiz.setAdapter(mAdapter);
                        //--

                        Fragment fragment = null;
                        Class fragmentClass = null;
                        fragmentClass = frTumOlcumler.class;
                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        FragmentTransaction tr = getFragmentManager().beginTransaction();
                        tr.replace(R.id.flContent, fragment);
                        tr.commit();

                        Toast.makeText(context, "Kayıt Başarıyla Silindi!", Toast.LENGTH_SHORT).show();

                    }
                });


                builder.setPositiveButton("VAZGEÇ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Vazgeçildi

                    }
                });
                builder.show();



                return true;
            }
        });






        return view;
    }
}
