package com.sosa.juegopelotita;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private  PelotitaView pelotita;
    private SensorManager sensorManager;
    private LeeSensor leeSensor;
    private PelotitaViewModel pViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pViewModel =  new ViewModelProvider(this).get(PelotitaViewModel.class);
        leeSensor = new LeeSensor();
        //Obtener la lista de sensores disponibles
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> listaSensores = sensorManager.getSensorList(Sensor.TYPE_GAME_ROTATION_VECTOR);
        if(listaSensores.size()>0) {
            sensorManager.registerListener(leeSensor, listaSensores.get(0), SensorManager.SENSOR_DELAY_GAME);
        }
        pelotita =new PelotitaView(this);
        pViewModel.getCordenada().observe(this, new Observer<Coordenada>() {
            @Override
            public void onChanged(Coordenada coordenada) {

        pelotita.setX(coordenada.getCordenadaX());
            pelotita.setY(coordenada.getCordenadaY());
                setContentView(pelotita);
            }
        });




}
    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(leeSensor);
    }
    private class LeeSensor implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            pViewModel.cordenadas(sensorEvent.values[0],sensorEvent.values[1]);

            Log.d("sensorx",(sensorEvent.values[0]*100)+"");
            Log.d("sensory",(sensorEvent.values[1]*100)+"");

            StringBuilder muestra =new StringBuilder();

            muestra.append("x "+sensorEvent.values[0]+"\n"+"y "+sensorEvent.values[1]+"\n z "+sensorEvent.values[2]);


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }
    public class PelotitaView extends View {
        float x ;
        float y ;


        @Override
        public void setX(float x) {
            this.x = x;
        }

        @Override
        public void setY(float y) {
            this.y = y;
        }

        public PelotitaView (Context context) {
            super(context);


        }
        @Override
        protected void onDraw(Canvas canvas) {
            Paint paint =  new Paint();
            Log.d("sensorxon",((canvas.getWidth()/2)+""));
            Log.d("sensorxon",(x*100)+"");

            int radius;
            radius = 100;
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);
            paint.setColor(Color.parseColor("#000000"));
            canvas.drawCircle((canvas.getWidth()/2+y*1000) , canvas.getHeight()/2+x*1500 , radius, paint);

        }


    }




}