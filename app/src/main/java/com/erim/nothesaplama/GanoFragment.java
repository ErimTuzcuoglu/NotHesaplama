package com.erim.nothesaplama;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class GanoFragment extends Fragment {
    private EditText kredi,ortalama;
    private Spinner  dersSayisi;
    private TextView dersAdiTextView, dersKredisiTextView, harfNotuTextView;
    private LinearLayout bilgiLinearLayout;
    private Button tamamButton,temizleButton;
    private int spinnerdanGelenDersAdedi;
    private float buDonemToplamKredi = 0;

    public GanoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_gano, container, false);

        kredi = (EditText)v.findViewById(R.id.mevcutToplamKrediET);
        ortalama =(EditText)v.findViewById(R.id.mevcutOrtalamaET);
        dersSayisi = (Spinner)v.findViewById(R.id.donemDersSayisiSP);

        bilgiLinearLayout = (LinearLayout)v.findViewById(R.id.bilgilerLinearLayout);

        dersAdiTextView = (TextView)v.findViewById(R.id.dersAdi);
        dersKredisiTextView = (TextView)v.findViewById(R.id.dersKredisi);
        harfNotuTextView = (TextView)v.findViewById(R.id.dersHarfNotu);

        tamamButton  = (Button)v.findViewById(R.id.bilgilerTamamButton);
        temizleButton = (Button)v.findViewById(R.id.sifirla);

        final List<LinearLayout> satirLinearLayoutListesi = new ArrayList<LinearLayout>();
        final List<Spinner> krediSpinnerListesi = new ArrayList<Spinner>();
        final List<Spinner> harfNotuSpinnerListesi = new ArrayList<Spinner>();
        final List<EditText> dersAdiEdittextListesi = new ArrayList<EditText>();


        //Ders Adedi Spinner Adaptörü.
        final String[] spinnerİcerigi = new String[]{"", "1","2","3","4","5","6","7","8","9",
                "10","11","12","13","14","15","16","17","18","19","20"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item,spinnerİcerigi);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dersSayisi.setAdapter(adapter);

        //Ders Sayısı Seçildiğinde Aşağıdaki Spinnerlar (kredi ve harf notu) ve Edittexti (ders adı) oluşturan kısım.
        dersSayisi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString() != ""){

                    spinnerdanGelenDersAdedi = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                    dersAdiTextView.setVisibility(View.VISIBLE);
                    dersKredisiTextView.setVisibility(View.VISIBLE);
                    harfNotuTextView.setVisibility(View.VISIBLE);

                    icerigiTemizle(satirLinearLayoutListesi, dersAdiEdittextListesi, krediSpinnerListesi, harfNotuSpinnerListesi);

                    //Marginleri ayarlamak için.
                    LinearLayout.LayoutParams marginler = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                    marginler.setMargins((int) getResources().getDimension(R.dimen.margin), marginler.topMargin, marginler.rightMargin, marginler.bottomMargin);

                    //Java ile component oluşturma.
                    for (int j = 1; j <= spinnerdanGelenDersAdedi;j++){
                        //Her satırda kullanılacak,içinde sadece 3 bileşeni barındıracak linear layout.
                        LinearLayout satirLinearLayout = new LinearLayout(getActivity());
                        satirLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                        satirLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        satirLinearLayout.setWeightSum(4f);


                        //EditText.
                        EditText editText = new EditText(getActivity());
                        editText.setWidth((int) getResources().getDimension(R.dimen.edittxt_widthi));
                        editText.setHeight((int) getResources().getDimension(R.dimen.yukseklik_kucuk));
                        editText.setId(j);
                        editText.setPadding(editText.getPaddingLeft(),editText.getPaddingTop(),editText.getPaddingRight(), (int) getResources().getDimension(R.dimen.text_paddingi_kucuk));
                        editText.setTextSize(getResources().getDimension(R.dimen.text_boyutu_kucuk));
                        editText.setSingleLine();
                        editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 2f));

                        //Spinner (kredi).
                        Spinner krediSpinner = new Spinner(getActivity());
                        krediSpinner.setMinimumHeight((int) getResources().getDimension(R.dimen.yukseklik_kucuk));
                        krediSpinner.setId(j);
                        krediSpinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.kredi)));
                        //Spinnerin sol margini.
                        krediSpinner.setLayoutParams(marginler);

                        //Spinner (Harf Notu).
                        Spinner harfSpinner = new Spinner(getActivity());
                        harfSpinner.setMinimumHeight((int) getResources().getDimension(R.dimen.yukseklik_kucuk));
                        harfSpinner.setId(j);
                        harfSpinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.harfNotlar)));
                        //Yukarıda tanımlanan marginin uygulanması.
                        harfSpinner.setLayoutParams(marginler);

                        dersAdiEdittextListesi.add(editText);
                        krediSpinnerListesi.add(krediSpinner);
                        harfNotuSpinnerListesi.add(harfSpinner);
                        satirLinearLayoutListesi.add(satirLinearLayout);

                        satirLinearLayout.addView(editText);
                        satirLinearLayout.addView(krediSpinner, marginler);
                        satirLinearLayout.addView(harfSpinner, marginler);
                        bilgiLinearLayout.addView(satirLinearLayout);
                    }
                    tamamButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        tamamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double oncekigno = 0, gno = 0;
                //Eğer ortalama ve kredi girilmediyse 0 yap.
                if(ortalama.getText().toString().equals("") || kredi.getText().toString().equals("")) {
                    ortalama.setText("0");
                    kredi.setText("0");
                }
                oncekigno += Double.parseDouble(ortalama.getText().toString()) * Double.parseDouble(kredi.getText().toString());
                double donemlikGno = gnoHesapla(krediSpinnerListesi, harfNotuSpinnerListesi);

                gno = ((donemlikGno * buDonemToplamKredi) + oncekigno) / (Double.parseDouble(kredi.getText().toString()) + buDonemToplamKredi);

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Ortalamalar");
                alert.setMessage("Dönemlik Ortalama: " + String.format("%.2f", donemlikGno) + "\nGenel Ortalama: " +String.format("%.2f", gno));
                alert.show();
            }
        });

        temizleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kredi.getText().clear();
                ortalama.getText().clear();
                dersSayisi.setSelection(0);

                icerigiTemizle(satirLinearLayoutListesi, dersAdiEdittextListesi, krediSpinnerListesi, harfNotuSpinnerListesi);

                dersAdiTextView.setVisibility(View.INVISIBLE);
                dersKredisiTextView.setVisibility(View.INVISIBLE);
                harfNotuTextView.setVisibility(View.INVISIBLE);
                tamamButton.setVisibility(View.INVISIBLE);
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    private double gnoHesapla(List<Spinner> krediSP, List<Spinner> harfNotSP){
        buDonemToplamKredi = 0;
        double gno = 0;
        for(int i = 0; i <spinnerdanGelenDersAdedi; i++){

            float kredi = Float.parseFloat(krediSP.get(i).getSelectedItem()+"");

            switch (harfNotSP.get(i).getSelectedItem()+""){
                case "AA":
                    gno += 4.0 * kredi;
                    break;
                case "BA":
                    gno += 3.5 * kredi;
                    break;
                case "BB":
                    gno += 3.0 * kredi;
                    break;
                case "CB":
                    gno += 2.5 * kredi;
                    break;
                case "CC":
                    gno += 2.0 * kredi;
                    break;
                case "DC":
                    gno += 1.5 * kredi;
                    break;
                case "DD":
                    gno += 1.0 * kredi;
                    break;
                case "FD":
                    gno += 0.5 * kredi;
                    break;
                case "FF":
                    gno += 0 * kredi;
                    break;
            }
            buDonemToplamKredi += kredi;
        }
        gno = gno / buDonemToplamKredi;
        return gno;
    }


    private void icerigiTemizle(List<LinearLayout> satirLL, List<EditText>dersAdiET, List<Spinner> krediSP, List<Spinner> harfNotuSP){
        if(satirLL.size() != 0){
            for(int j = 0; j < satirLL.size(); j++){
                bilgiLinearLayout.removeView(satirLL.get(j));}
            dersAdiET.clear();
            krediSP.clear();
            harfNotuSP.clear();
            satirLL.clear();
        }
    }
}
