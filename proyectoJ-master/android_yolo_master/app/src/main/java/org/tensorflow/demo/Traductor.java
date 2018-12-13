package org.tensorflow.demo;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static org.tensorflow.demo.Clases.VariablesYDatos.etiquetas;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class Traductor extends AppCompatActivity {
    GifImageView gf;
    EditText txtTraducir;
    TextView txtProgreso;
    ImageButton btnTraducir,btnLimpiar;
    ImageView PlayPause;
    int mayor=0,contador=1;
    String palMayor="";
    ArrayList<Integer> lista;
    int VideoPSettear=0;
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traductor);
        lista=new ArrayList<>();
        //---------------------
        txtProgreso=findViewById(R.id.txtProgreso);
        PlayPause=findViewById(R.id.btnPausePlay);
        //----------------------
        gf=findViewById(R.id.gif);
        gf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
            /*
            if (countdownActivado) {
                countDownTimer.cancel();
                countdownActivado = false;
                PlayPause.setImageResource(R.drawable.ic_action_play);
            } else {
                countDownTimer.start();
                countdownActivado = true;
                //PlayPause.setImageResource(R.drawable.ic_action_pausa);
            }*/
                    //PlayPause.setImageResource(R.drawable.ic_action_reload_video);
                    countDownTimer.cancel();
                    countDownTimer.start();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        txtTraducir=findViewById(R.id.txtTraducir);
        btnLimpiar=findViewById(R.id.btnLimpiar);
        btnTraducir=findViewById(R.id.btnTraducir);
        btnTraducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtTraducir.getText().toString().equalsIgnoreCase("")){
                    show("Proporciona algo para traducir.");
                    return;
                }
                lista.clear();
                contador=1;

                comparar(remplazarComunes(txtTraducir.getText().toString()).split(" "));
            }
        });
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTraducir.setText("");
            }
        });
    }
    void show(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
    void comparar(String partes []){
        String palabraAC="",palabra="";
        boolean sino=false;
        for(int i = 0;i<partes.length;i++) { //repasar el input
            for (int j = 0; j < etiquetas.length; j++) {

                palabraAC = LimpiarCadenas(partes[i]);
                palabraAC=palabraAC.replace(" ","");
                palabra = etiquetas[j][0].toString();

                //  System.err.println(palabraAC);
                compararLetras(palabraAC, palabra);

            }
            if (mayor != 0) {

                int pos=0;
                // System.out.println(palabraAC + " se parece más a: " + palMayor);
                for (int k = 0; k < etiquetas.length; k++) {
                    if (palMayor.equalsIgnoreCase(etiquetas[k][0].toString())) {
                        pos=k;
                    }
                }
                VideoPSettear=((int)(etiquetas[pos][1]));
                //video((int)(etiquetas[pos][1]));
                //  SettearVideo();

                lista.add(VideoPSettear);
                // countDownTimer.start();

            }
            palMayor="";
            mayor=0;
            if(sino){
                i++;
                sino=false;
            }
        }
        gf.setImageResource(lista.get(0));
        txtProgreso.setText("1/"+lista.size());
        video();
    }
    String remplazarComunes(String frase){

        frase=LimpiarCadenas(frase);
        frase=frase.replace("buenas noches","buenasnoches");
        frase=frase.replace("buena noche","buenasnoches");
        frase=frase.replace("buenas taredes","buenastardes");
        frase=frase.replace("buena tarde","buenastardes");
        frase=frase.replace("buenos dias","buenosdias");
        frase=frase.replace("buen dia","buenosdias");
        frase=frase.replace("como estas","comoestas");
        frase=frase.replace("cual es tu nombre","tunombre");
        frase=frase.replace("como te llamas","tunombre");
        frase=frase.replace("dime tu nombre","tunombre");
        frase=frase.replace("de nada","denada");
        frase=frase.replace("gusto en conocerte","gustoenconocerte");
        frase=frase.replace("hasta luego","nosvemos");
        frase=frase.replace("nos vemos","nosvemos");
        frase=frase.replace("adios","nosvemos");
        frase=frase.replace("a dios","nosvemos");
        frase=frase.replace("medio","masomenos");
        frase=frase.replace("por que","porque");
        frase=frase.replace("para que","paraque");
        frase=frase.replace("mas o menos","masomenos");

        frase=frase.replace("en adelante","enadelante");
        frase=frase.replace("para delante","enadelante");
        frase=frase.replace("adelante","enadelante");
        frase=frase.replace("delante","enadelante");
        frase=frase.replace("hacia delante","enadelante");
        frase=frase.replace("posterior","despues");
        frase=frase.replace("posteriormente","despues");
        frase=frase.replace("medio dia","mediodia");
        frase=frase.replace("otra vez","otravez");
        frase=frase.replace("todavia no","todaviano");
        frase=frase.replace("aun no","todaviano");
        frase=frase.replace("una vez","unavez");
        frase=frase.replace("primera vez","primeravez");
        frase=frase.replace("por primera vez","primeravez");
        frase=frase.replace("mi primera vez","primeravez");

        frase=frase.replace("no poder","nopoder");
        frase=frase.replace("no puedo","nopoder");

        frase=frase.replace("mio","yo");
        frase=frase.replace("mi","yo");
        frase=frase.replace("usted","tu");
        frase=frase.replace("nuestro","nosotros");
        frase=frase.replace("tuyo","tu");
        frase=frase.replace("suyo","ellos");

        return frase;
    }
    String LimpiarCadenas(String palabraAC){
        String original = "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýÿ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "AAAAAAACEEEEIIIIDNOOOOOOUUUUYBaaaaaaaceeeeiiiionoooooouuuuyy";
        for (int k=0; k<original.length(); k++) {
            palabraAC = palabraAC.replace(original.charAt(k), ascii.charAt(k));
        }
        palabraAC=palabraAC.toLowerCase();
        palabraAC = palabraAC.replace("?", "");palabraAC = palabraAC.replace("!", "");palabraAC = palabraAC.replace("¿", "");
        palabraAC = palabraAC.replace("¡", "");palabraAC = palabraAC.replace(",", "");palabraAC = palabraAC.replace(".", "");
        //palabraAC = palabraAC.replace(" ", "");
        return palabraAC;
    }
    void compararLetras(String palabraAC,String palabra){

        int total = palabra.length();
        int totalAC = palabraAC.length();
        int aciertos=0,menor=0;
        if(total<totalAC){
            menor=total;
        }else if(total==totalAC){
            menor=total;
        }else{
            menor=totalAC;
        }
        for(int i = 0;i<menor;i++){
            if(palabra.charAt(i)==palabraAC.charAt(i)){
                aciertos++;
            }
        }
        int res = (int) (menor/2);
        //System.out.println("res: "+res+"\nAciertos: "+aciertos+"\n------------------------");
        if(aciertos>mayor && aciertos>res){
            mayor=aciertos;
            palMayor=palabra;
        }

//        return aciertos>=res;
    }
    void video (){
        countDownTimer = new CountDownTimer(3000, 1000) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                if(contador==lista.size()){
                    contador=0;
                    PlayPause.setImageResource(R.drawable.ic_action_reload_video);
                    return;
                }
                gf.setImageResource(lista.get(contador));
                txtProgreso.setText((contador+1)+"/"+lista.size());
                contador++;
                video();
            }
        }.start();
    }
}
