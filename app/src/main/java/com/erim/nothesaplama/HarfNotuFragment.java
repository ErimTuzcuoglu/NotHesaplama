package com.erim.nothesaplama;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class HarfNotuFragment extends Fragment implements View.OnClickListener{

    EditText vizeEdt,finalEdt,hbno;
    Button   hesapla;
    TextView harfnotu,bagilort,ort, harfNotuGerekliPuanlar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_harf_notu,container,false);
        vizeEdt = (EditText) v.findViewById(R.id.VizeET);
        finalEdt = (EditText) v.findViewById(R.id.FinalET);
        hesapla = (Button) v.findViewById(R.id.HarfHesapla);
        hbno = (EditText) v.findViewById(R.id.hbnoET);
        harfnotu = (TextView) v.findViewById(R.id.letterScore);
        bagilort = (TextView) v.findViewById(R.id.relativeAverage);
        ort = (TextView) v.findViewById(R.id.vizeFinalAverage);

        harfNotuGerekliPuanlar = (TextView)v.findViewById(R.id.letterScoreNecessary);

        hesapla.setOnClickListener(this);

        //Eğer Hbno edittexindeyken klavyeden enterlandıysa, hesapla butonuna basılmış olarak kabul etmesi için.
        hbno.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_ENTER ){
                    hesapla.performClick();
                    return true;
                }

                return false;
            }
        });


        // Inflate the layout for this fragment
        return v;

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.HarfHesapla) {

            //Tamam butonuna tıklandıgında klavyeyi gizlemek için.
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);



            if (String.valueOf(vizeEdt.getText()).equals("") || String.valueOf(finalEdt.getText()).equals("") || String.valueOf(hbno.getText()).equals("")) {
                Toast.makeText(getContext(), getResources().getText(R.string.lutfenDoldur), Toast.LENGTH_LONG).show();
                return;
            }

                double num = Double.parseDouble(String.valueOf(vizeEdt.getText()));      // Vize Notu TextBox
                double num2 = Double.parseDouble(String.valueOf(finalEdt.getText()));      // Final TextBox
                double num3 = Double.parseDouble(String.valueOf(hbno.getText()));      // HBNO TextBox
                double num4 = (num * 0.4) + (num2 * 0.6);               // Vize,Final Ortalaması

                if(num2 > 100 || num > 100 || num3 > 100){
                    Toast.makeText(getContext(), R.string.lutfen, Toast.LENGTH_SHORT).show();
                }else{
                    kontrol(num2, num3, num4);
                    bagilort.setText("Vize + Final Ortalamanız :   " + String.valueOf(num4));
                    kacGerekliKontrol(num, num3);
                }
            }
    }

    private void kontrol(double num2, double num3, double num4){
        if (num2 < 35.0) {
            harfnotu.setText("Harf Notunuz: FF (Final Notunuz 35'in altında olduğu için!)");
        } else if (num3 < 30.0) {
            double num5 = num4 * 1.35;
            harfnotu.setText("Harf Notunuz: " + harfNotuBul(num5));
            ort.setText("Bağıl Ortalamanız : " + String.valueOf(num5));
        } else if ((num3 < 60.0) && (num3 >= 30.0)) {
            double num6 = num4 * (1.05 + ((60.0 - num3) / 100.0));
            harfnotu.setText("Harf Notunuz: " + harfNotuBul(num6));
            ort.setText("Bağıl Ortalamanız : " + String.valueOf(num6));
        } else if (num3 >= 60.0) {
            double num7 = num4 * 1.0;
            harfnotu.setText("Harf Notunuz: " + harfNotuBul(num7));
            ort.setText("Bağıl Ortalamanız : " + String.valueOf(num7));
        }
    }
    private void kacGerekliKontrol(double num, double num3){
        harfNotuGerekliPuanlar.setText("");
        double yeniNum4 = 0;
        double yenibagil;
        String oncekiHarfNotu = "";
        int oncekiNot = 0;

        for(int i = 35; i <= 100; i++){
            yenibagil = 0;
            yeniNum4 = (num * 0.4) + (i * 0.6);

            if(num3 < 30.0){
                yenibagil = yeniNum4 * 1.35;
            }else if(num3 < 60.0 && num3 >= 30.0){
                yenibagil = yeniNum4 * (1.05 + ((60.0 - num3) / 100.0));
            }else if(num3 >= 60){
                yenibagil = yeniNum4 * 1.0;
            }

            if(!oncekiHarfNotu.equals(harfNotuBul(yenibagil)) && harfNotuGerekliPuanlar.getText().equals("")){
                harfNotuGerekliPuanlar.setText(R.string.aralikHarfNotlari);
                oncekiHarfNotu = harfNotuBul(yenibagil);
                oncekiNot = i;
            }else if(!oncekiHarfNotu.equals(harfNotuBul(yenibagil)) && !harfNotuGerekliPuanlar.getText().equals("")){
                harfNotuGerekliPuanlar.setText(harfNotuGerekliPuanlar.getText().toString() + oncekiNot + " - " + (i-1) + " arası " + oncekiHarfNotu + "\n");
                oncekiHarfNotu = harfNotuBul(yenibagil);
                oncekiNot = i;
            }

            if(i == 100)
                harfNotuGerekliPuanlar.setText(harfNotuGerekliPuanlar.getText().toString() + oncekiNot + " - 100 arası " + oncekiHarfNotu + "\n");
        }
    }

    private String harfNotuBul(double not){
        if (not < 39.0) {
            return "FF";
        } else if ((not < 46.0) && (not >= 39.0)) {
            return "FD";
        } else if ((not < 53.0) && (not >= 46.0)) {
            return "DD";
        } else if ((not < 60.0) && (not >= 53.0)) {
            return "DC";
        } else if ((not < 67.0) && (not >= 60.0)) {
            return "CC";
        } else if ((not < 74.0) && (not >= 67.0)) {
            return "CB";
        } else if ((not < 81.0) && (not >= 74.0)) {
            return "BB";
        } else if ((not < 88.0) && (not >= 81.0)) {
            return "BA";
        } else if (/*(not <= 100.0) && */(not >= 88.0)) {
            return "AA";
        } else {
            Toast.makeText(getContext(), R.string.lutfen, Toast.LENGTH_SHORT).show();
            return "";
        }
    }
}
