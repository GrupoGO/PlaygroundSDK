package es.grupogo.playgroundsdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import es.grupogo.playgroundsdk.widget.ActionsPagerView;

public class MainActivity extends AppCompatActivity {

    ActionsPagerView pagerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pagerView = (ActionsPagerView) findViewById(R.id.pager);
        pagerView.setNumActions(3);
        //pagerView.setQuery("animals");
        pagerView.setPosition(40.489353842, -3.6827461);

    }

}
