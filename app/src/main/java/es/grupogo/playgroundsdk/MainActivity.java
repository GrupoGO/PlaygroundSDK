package es.grupogo.playgroundsdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import es.grupogo.playgroundsdk.widget.ActionsPagerView;

public class MainActivity extends AppCompatActivity {

    ArrayList<Action> actions;
    ActionsPagerView pagerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DoButton button = (DoButton) findViewById(R.id.button_do);
        button.setQuery("mobile");

        pagerView = (ActionsPagerView) findViewById(R.id.pager);
       // pagerView.setNumActions(3);
       // pagerView.setQuery("animals");
      //  pagerView.setPosition(40.489353842, -3.6827461);
      //  pagerView.reloadActions();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**  @Override
    protected void onResume() {
        super.onResume();
        if(actions==null){
            pagerView.reloadActions();
        } else {
            pagerView.setActions(actions);
        }
    }

   @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("actions", (ArrayList<Action>) pagerView.getActions());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        actions = savedInstanceState.getParcelableArrayList("actions");
    }**/
}
