package com.example.artem.custombar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CustomBar bar = (CustomBar)findViewById(R.id.cbBar);
        bar.setValue(10);
        bar.setOnClickListener(new View.OnClickListener(){
            int delta = 5;
            @Override
            public void onClick(View view) {
                int old_value = bar.getValue();
                if(old_value + delta >= 100 || old_value + delta <= 0 ){
                    delta = -delta;
                }
                bar.setValue(bar.getValue() + delta);
            }
        });
    }


}
