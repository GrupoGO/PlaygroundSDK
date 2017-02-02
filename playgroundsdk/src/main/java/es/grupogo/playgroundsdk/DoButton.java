package es.grupogo.playgroundsdk;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.PopupMenu;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Carlos Olmedo on 30/1/17.
 */

public class DoButton extends Button implements View.OnClickListener, RequestHelper.RequestCallback<List<Action>>{

    private List<Action> actions;

    public DoButton(Context context) {
        super(context);
        init();
    }

    public DoButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DoButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    private void init() {
        setOnClickListener(this);
        setText("Do!");
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getContext(), "Do!", Toast.LENGTH_SHORT).show();
        RequestHelper requestHelper = RequestHelper.getInstance(view.getContext());

        requestHelper.getActionsAsync("Pobreza", this);

    }

    @Override
    public void onResponse(List<Action> actions) {

        this.actions = actions;

        if (!actions.isEmpty()) {
            PopupMenu popupMenu = new PopupMenu(getContext(), this);
            for (int i = 0; i < actions.size(); i++) {
                popupMenu.getMenu().add(0, Integer.parseInt(actions.get(i).getId()), Menu.NONE, actions.get(i).getTitle());
            }
            popupMenu.show();
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Action action = DoButton.this.actions.get(DoButton.this.actions.indexOf(new Action(String.valueOf(item.getItemId()))));
                    String url = action.getUrl();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    getContext().startActivity(i);
                    return false;
                }
            });
        } else {
            Toast.makeText(getContext(), "No se encontraron acciones", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFailure(Throwable t) {

    }
}
