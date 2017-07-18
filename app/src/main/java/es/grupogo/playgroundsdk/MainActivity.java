package es.grupogo.playgroundsdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import es.grupogo.playgroundsdk.widget.ActionsPagerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DoButton button = (DoButton) findViewById(R.id.button_do);
        button.setQuery("mobile");

        ActionsPagerView pagerView = (ActionsPagerView) findViewById(R.id.pager);
        pagerView.setNumActions(3);
        pagerView.setQuery("sex");
        pagerView.reloadActions();
    }
}
