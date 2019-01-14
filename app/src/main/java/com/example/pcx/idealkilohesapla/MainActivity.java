package com.example.pcx.idealkilohesapla;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import android.widget.FrameLayout;
import android.Manifest;
import android.os.Environment;
import android.graphics.Bitmap;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private AppCompatActivity activity = MainActivity.this;
    private Bitmap bitmap;
    private FrameLayout parentView;

    Context context = this;
    Fragment fragment = null;
    Class fragmentClass = null;
    SharedPreferences preferences,ayarlar;


    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = preferences.edit();
        //editor.putInt("count_anahtari",count)
        editor.commit();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        ayarlar = PreferenceManager.getDefaultSharedPreferences(context);


        parentView = (FrameLayout)findViewById(R.id.flContent);
        ayarlariYukle();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //------------------------------------------------------------------------------------------
        fragmentClass = frVkiHesap.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        //------------------------------------------------------------------------------------------

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Bizi Tercih Ettiğiniz İçin Teşekkür Ederiz...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

                    if (fragment!=null && fragment.getClass()==frVkiHesap.class) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInputFromWindow(drawer.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                    }

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
    }

    private void ayarlariYukle() {
        String position = ayarlar.getString("arkaplan","0");


            switch (Integer.valueOf(position)) {

                case 0:
                    parentView.setBackgroundResource(R.mipmap.arkaplan);
                    break;
                case 1:
                    parentView.setBackgroundResource(R.mipmap.thematrixer);
                    break;
                case 2:
                    parentView.setBackgroundResource(R.mipmap.darkphone);
                    break;
                case 3:
                    parentView.setBackgroundResource(R.mipmap.gokyuzu);
                    break;
                case 4:
                    parentView.setBackgroundResource(R.mipmap.deniz);
                    break;
            }
            ayarlar.registerOnSharedPreferenceChangeListener(MainActivity.this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sagmenu_cikis) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            finish();
            System.exit(0);
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_vkiHesap) {
            fragmentClass = frVkiHesap.class;
        } else if (id == R.id.nav_tumOlcumler) {
            fragmentClass = frTumOlcumler.class;
        } else if (id == R.id.nav_grafikler) {
            fragmentClass = frGrafik.class;
        } else if (id == R.id.nav_ayarlar) {
            Intent intent = new Intent(context,Ayarlar.class);
            startActivity(intent);

            return true;

        } else if (id == R.id.nav_kaydet) {
            //
            if (fragment.getClass()==frVkiHesap.class){
                parentView = findViewById(R.id.flContent);
                bitmap = ScreenshotUtil.getInstance().takeScreenshotForView(parentView); // seçilen viewden SS al
                yetkiIsteveIslemYap("kaydet");
            }
            else{
                Toast.makeText(context, getString(R.string.oncehesapla), Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.nav_paylas) {
            //
            if (fragment.getClass()==frVkiHesap.class){
                parentView = findViewById(R.id.flContent);
                bitmap = ScreenshotUtil.getInstance().takeScreenshotForView(parentView); // seçilen viewden SS al
                yetkiIsteveIslemYap("paylas");
            }
            else{
                Toast.makeText(context, getString(R.string.oncehesapla), Toast.LENGTH_SHORT).show();
            }
        }

        try {
            if (fragment!=null && fragmentClass!=fragment.getClass())
                fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void yetkiIsteveIslemYap(final String islemTipi) {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        if (bitmap != null) {
                            if (islemTipi.equalsIgnoreCase("kaydet")){
                                String yol = Environment.getExternalStorageDirectory().toString() + "/test.png";
                                FileUtil.getInstance().resimKaydet(bitmap, yol);
                                Toast.makeText(activity, "Son ölçüm başarıyla şuraya kaydedildi: \n" + " " + yol, Toast.LENGTH_LONG).show();
                            }
                            else if(islemTipi.equalsIgnoreCase("paylas")) {
                                //--
                                try {

                                    File cachePath = new File(context.getCacheDir(), "images");
                                    cachePath.mkdirs(); // uretilen yolu unutmamak için
                                    FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                    stream.close();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                                File imagePath = new File(context.getCacheDir(), "images");
                                File newFile = new File(imagePath, "image.png");
                                Uri contentUri = FileProvider.getUriForFile(context, "com.example.myapp.fileprovider", newFile);

                                if (contentUri != null) {
                                    Intent shareIntent = new Intent();
                                    shareIntent.setAction(Intent.ACTION_SEND);
                                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
                                    shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                                    startActivity(Intent.createChooser(shareIntent, "Uygulama Seçin"));
                                }
                                //--
                                Toast.makeText(activity, "Paylaşılıyor...", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(activity, "Ekran görüntüsü alınırken hata oluştu!", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            Toast.makeText(activity, "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        ayarlariYukle();
    }
}