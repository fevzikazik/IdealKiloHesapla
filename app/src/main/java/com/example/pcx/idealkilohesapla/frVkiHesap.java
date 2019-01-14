package com.example.pcx.idealkilohesapla;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class frVkiHesap extends Fragment {

    SQLiteHelper db;
    Context context;
    private EditText editText;
    private TextView boy_tv,durum_tv,ideal_tv,kilo_tv,yagsizagirlik_tv,vya_tv,vki_tv;
    private SeekBar seekBar;
    private RadioGroup radioGroup;
    private Button btnKaydet;
    private boolean erkekmi = true;
    private double boy = 0.0;
    private int kilo=50;

    private TextWatcher editTextOlayIsleyicisi = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                boy = Double.parseDouble(s.toString())/100.0;
            }catch (NumberFormatException e){
                boy = 0.0;

            }

            Guncelle();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private SeekBar.OnSeekBarChangeListener seekBarOlayIsleyicisi = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            kilo = 30+progress;
            Guncelle();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    private RadioGroup.OnCheckedChangeListener radioGroupOlayIsleyicisi = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId==R.id.bay){
                erkekmi = true;
            }
            else if (checkedId==R.id.bayan){
                erkekmi = false;
            }
            Guncelle();
        }
    };
    private Button.OnClickListener btnKaydetClickOlayi = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {

            // AlertDialog

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Kaydet");
            builder.setMessage("Bu ölçümü kaydetmek istediğinize eminmisiniz?");
            builder.setNegativeButton("KAYDET", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(context, "Kayıt Yapıldı!", Toast.LENGTH_SHORT).show();
                    btnKaydet.setEnabled(false);
                    //DB KAYIT
                    String cinsiyet="";
                    if(erkekmi){
                        cinsiyet="Erkek";
                    }
                    else{
                        cinsiyet="Bayan";
                    }
//--
                    Date simdikiZaman = new Date();
                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd - HH:mm:ss");

                    //db.onUpgrade(db.getWritableDatabase(),1,2);
                    db.OlcumEkle(
                            new Olcum(
                                    (int) (boy*100),
                                    kilo,
                                    cinsiyet,
                                    Integer.parseInt(yagsizagirlik_tv.getText().toString()),
                                    Integer.parseInt(ideal_tv.getText().toString()),
                                    String.valueOf(vya_tv.getText()),
                                    String.valueOf(vki_tv.getText()),
                                    String.valueOf(durum_tv.getText()),
                                    " ( " + df.format(simdikiZaman) + " )"));
                }
            });


            builder.setPositiveButton("IPTAL", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //Vazgeçildi

                }
            });


            builder.show();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_vki,container,false);
        context = inflater.getContext();

        db = new SQLiteHelper(context);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        editText = (EditText) getView().findViewById(R.id.editText2);
        boy_tv = (TextView) getView().findViewById(R.id.boy_tv);
        durum_tv = (TextView) getView().findViewById(R.id.durum_tv);
        kilo_tv = (TextView) getView().findViewById(R.id.kilo_tv);
        ideal_tv = (TextView) getView().findViewById(R.id.ideal_tv);
        btnKaydet = (Button) getView().findViewById(R.id.btnKaydet);



        yagsizagirlik_tv = (TextView) getView().findViewById(R.id.yagsizagirlik_tv);
        vya_tv = (TextView) getView().findViewById(R.id.vya_tv);
        vki_tv = (TextView) getView().findViewById(R.id.vki_tv);

        radioGroup = (RadioGroup) getView().findViewById(R.id.radioGroup);
        seekBar =  (SeekBar) getView().findViewById(R.id.seekBar);

        editText.addTextChangedListener(editTextOlayIsleyicisi);
        seekBar.setOnSeekBarChangeListener(seekBarOlayIsleyicisi);
        radioGroup.setOnCheckedChangeListener(radioGroupOlayIsleyicisi);
        btnKaydet.setOnClickListener(btnKaydetClickOlayi);
    }

    private  void  Guncelle(){

        kilo_tv.setText(String.valueOf(kilo)+" kg");
        boy_tv.setText(String.valueOf(boy));
        vki_tv.setText("");
        vya_tv.setText("");

        if (boy>0.99){

            int ideal_kiloBay = (int) (50+2.3*((boy*100*0.4)-60));
            int ideal_kiloBayan = (int) (45.5+2.3*((boy*100*0.4)-60));

            double vki = kilo/(boy*boy);
            double vya = 0.20247 * Math.pow(boy,0.725) * Math.pow(kilo,0.425);
            int yagsizAgirlik_Bay = (int) ((1.10 * kilo) - (128 * (kilo*kilo)/ Math.pow((100 * boy),2)));
            int yagsizAgirlik_Bayan = (int) ((1.07 * kilo) - (148 * (kilo*kilo)/ Math.pow((100 * boy),2)));

            if(vki < 18.5){
                durum_tv.setBackgroundResource(R.color.durum_zayif);
                durum_tv.setText(R.string.zayif);
            }else if(vki>=18.5 && vki<= 24.9){
                durum_tv.setBackgroundResource(R.color.durum_ideal);
                durum_tv.setText(R.string.ideal);
            }else if(vki>=25 && vki<= 29.9){
                durum_tv.setBackgroundResource(R.color.durum_fazlakilolu);
                durum_tv.setText(R.string.fazlakilolu);
            }else if(vki>=30 && vki<= 34.9){
                durum_tv.setBackgroundResource(R.color.durum_obez);
                durum_tv.setText(R.string.obez1);
            }else if(vki>=35 && vki<= 39.9){
                durum_tv.setBackgroundResource(R.color.durum_obez);
                durum_tv.setText(R.string.obez2);
            }else if(vki >= 40){
                durum_tv.setBackgroundResource(R.color.durum_obez);
                durum_tv.setText(R.string.obez3);
            }


            if (erkekmi){
                //erkek ise
                ideal_tv.setText(String.valueOf(ideal_kiloBay));
                yagsizagirlik_tv.setText(String.valueOf(yagsizAgirlik_Bay));
            }
            else{
                //bayan ise
                ideal_tv.setText(String.valueOf(ideal_kiloBayan));
                yagsizagirlik_tv.setText(String.valueOf(yagsizAgirlik_Bayan));
            }

            double t_vya = (int) (vya*100);
            vya = t_vya / 100;

            double t_vki = (int) (vki*10);
            vki = t_vki / 10;

            vya_tv.setText(String.valueOf(vya));
            vki_tv.setText(String.valueOf(vki));

        }else{
            ideal_tv.setText("");
            durum_tv.setText("");
            vki_tv.setText("");
            vya_tv.setText("");
            yagsizagirlik_tv.setText("");
        }

        if (durum_tv.getText()!=""){
            btnKaydet.setEnabled(true);
        }
        else{
            btnKaydet.setEnabled(false);
        }
    }


}
