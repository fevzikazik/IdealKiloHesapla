package com.example.pcx.idealkilohesapla;

public class Olcum {

    int id;
    int boy;
    int kilo;
    String cinsiyet;
    int yagsizAgirlik;
    int kiloIdeal;
    String vucutYuzeyAlani;
    String vucutKitleIndexi;
    String DURUM;
    String olcumTarihi;


    public Olcum() {

    }

    public Olcum(int boy, int kilo, String cinsiyet, int yagsizAgirlik, int kiloIdeal, String vucutYuzeyAlani, String vucutKitleIndexi, String DURUM, String olcumTarihi) {
        this.boy = boy;
        this.kilo = kilo;
        this.cinsiyet = cinsiyet;
        this.yagsizAgirlik = yagsizAgirlik;
        this.kiloIdeal = kiloIdeal;
        this.vucutYuzeyAlani = vucutYuzeyAlani;
        this.vucutKitleIndexi = vucutKitleIndexi;
        this.DURUM = DURUM;
        this.olcumTarihi = olcumTarihi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBoy() {
        return boy;
    }

    public void setBoy(int boy) {
        this.boy = boy;
    }

    public int getKilo() {
        return kilo;
    }

    public void setKilo(int kilo) {
        this.kilo = kilo;
    }

    public String getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = cinsiyet;
    }

    public int getYagsizAgirlik() {
        return yagsizAgirlik;
    }

    public void setYagsizAgirlik(int yagsizAgirlik) {
        this.yagsizAgirlik = yagsizAgirlik;
    }

    public int getKiloIdeal() {
        return kiloIdeal;
    }

    public void setKiloIdeal(int kiloIdeal) {
        this.kiloIdeal = kiloIdeal;
    }

    public String getVucutYuzeyAlani() {
        return vucutYuzeyAlani;
    }

    public void setVucutYuzeyAlani(String vucutYuzeyAlani) {
        this.vucutYuzeyAlani = vucutYuzeyAlani;
    }

    public String getVucutKitleIndexi() {
        return vucutKitleIndexi;
    }

    public void setVucutKitleIndexi(String vucutKitleIndexi) {
        this.vucutKitleIndexi = vucutKitleIndexi;
    }

    public String getDURUM() {
        return DURUM;
    }

    public void setDURUM(String DURUM) {
        this.DURUM = DURUM;
    }

    public String getOlcumTarihi() {
        return olcumTarihi;
    }

    public void setOlcumTarihi(String olcumTarihi) {
        this.olcumTarihi = olcumTarihi;
    }
}
