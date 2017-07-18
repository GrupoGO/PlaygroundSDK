package es.grupogo.playgroundsdk.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.List;

import es.grupogo.playgroundsdk.Action;
import es.grupogo.playgroundsdk.RequestHelper;
import es.grupogo.playgroundsdk.RequestManager;
import es.grupogo.playgroundsdk2.R;

/**
 * Created by carlosolmedo on 13/7/17.
 */

public class ActionsPagerView extends ConstraintLayout{

    private String query = "";
    private int numActions = 10;
    private ActionsRecyclerAdapter adapter;

    private ActionViewHolder.OnActionClickListener listener = new ActionViewHolder.OnActionClickListener() {
        @Override
        public void onActionClick(Action action) {

            Intent browserIntent;
            if(action.getUrl()==null || action.getUrl().equals("")){
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(action.getCmsUrl()));
            } else {
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(action.getUrl()));
            }
            getContext().startActivity(browserIntent);
        }

        @Override
        public void onActionDelete(Action action) {

        }
    };

    public ActionsPagerView(Context context) {
        super(context);
        init();
    }

    public ActionsPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ActionsPagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        inflate(getContext(), R.layout.widget_pager_actions, this);

        setupRecycler();

        reloadActions();

    }

    private void setupRecycler() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new ActionsRecyclerAdapter(getContext(), R.layout.list_item_action, listener);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setNumActions(int numActions) {
        this.numActions = numActions;
    }

    public void reloadActions() {
        RequestManager.getInstance(getContext()).getActions(query, numActions, null, null, null, null, new RequestManager.RequestCallback<List<Action>>() {
            @Override
            public void onResponse(List<Action> response) {
                adapter.setActions(response);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


}
