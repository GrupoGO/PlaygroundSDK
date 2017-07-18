package es.grupogo.playgroundsdk.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import es.grupogo.playgroundsdk.Action;
import es.grupogo.playgroundsdk.ApiManager;
import es.grupogo.playgroundsdk.ActionDetailActivity;
import es.grupogo.playgroundsdk.DoButton;
import es.grupogo.playgroundsdk.RequestHelper;
import es.grupogo.playgroundsdk.RequestManager;
import es.grupogo.playgroundsdk2.R;

/**
 * Created by carlosolmedo on 13/7/17.
 */

public class ActionsPagerView extends ConstraintLayout{

    private String query = "";
    private int numActions = 10;
    private Double latitude;
    private Double longitude;
    private ActionsRecyclerAdapter adapter;
    private List<Action> savedActions;


    private ActionViewHolder.OnActionClickListener listener = new ActionViewHolder.OnActionClickListener() {
        @Override
        public void onActionClick(Action action) {

            /*Intent browserIntent;
            if(action.getUrl()==null || action.getUrl().equals("")){
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(action.getCmsUrl()));
            } else {
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(action.getUrl()));
            }
            getContext().startActivity(browserIntent);*/

            Intent intent = new Intent(getContext(), ActionDetailActivity.class);
            intent.putExtra(ActionDetailActivity.EXTRA_ACTION, action);
            getContext().startActivity(intent);
        }

        @Override
        public void onActionDelete(Action action) {

        }
    };

    public ActionsPagerView(Context context) {
        super(context);
        init(null);
    }

    public ActionsPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public ActionsPagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs) {

        inflate(getContext(), R.layout.widget_pager_actions, this);

        //Enable saveInstance
        setSaveEnabled(true);

        if (attrs!=null) {
            // Go through all custom attrs.
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.PlaygroundWidget,
                    0, 0);

            numActions = a.getInt(R.styleable.PlaygroundWidget_num_actions_widget, 10);
            query = a.getString(R.styleable.PlaygroundWidget_query_widget);
            if (query==null) {
                query = "";
            }
            try{
                String latitudeString = a.getString(R.styleable.PlaygroundWidget_latitude_widget);
                if(latitudeString==null){
                    latitude = null;
                } else {
                    latitude = Double.parseDouble(latitudeString);
                }
                String longitudeString = a.getString(R.styleable.PlaygroundWidget_longitude_widget);
                if(longitudeString==null){
                    longitude = null;
                } else {
                    longitude = Double.parseDouble(longitudeString);
                }

            } catch (NumberFormatException e){
                e.printStackTrace();
            }

            // Google tells us to call recycle.
            a.recycle();
        }

        setupRecycler();

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

    public void reloadActions() {
        if(latitude!=null && longitude!=null){ //WITH LOCATION
            ApiManager.getInstance(getContext()).getActionsInBounds(query, numActions, null, null, null, null, latitude, longitude, new ApiManager.ApiCallback<List<Action>>() {
                @Override
                public void onResponse(List<Action> response) {
                    adapter.setActions(response);
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            });
        } else { //WITHOUT LOCATION
            ApiManager.getInstance(getContext()).getActions(query, numActions, null, null, null, null, null, null, new ApiManager.ApiCallback<List<Action>>() {
                @Override
                public void onResponse(List<Action> response) {
                    adapter.setActions(response);
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    public List<Action> getActions(){
        return adapter.getActions();
    }

    public void setActions(List<Action> actions){
        adapter.setActions(actions);
    }


    @Override
    protected Parcelable onSaveInstanceState() {

        Parcelable superState = super.onSaveInstanceState();
        ActionsPagerView.SavedState ss = new ActionsPagerView.SavedState(superState);
        ss.actions = adapter.getActions();

        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        ActionsPagerView.SavedState ss = (ActionsPagerView.SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        savedActions = ss.actions;

    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        // As we save our own instance state, ensure our children don't save and restore their state as well.
        super.dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        super.dispatchThawSelfOnly(container);
    }

    //Like OnResume (this method is called after OnRestoreInstanceState
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if(this.savedActions==null){
            reloadActions();
        } else {
            adapter.setActions(this.savedActions);
        }

    }

    private static class SavedState extends BaseSavedState {

        List<Action> actions;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in, ClassLoader classLoader) {
            super(in);
            actions = in.readArrayList(classLoader);

        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeTypedList(actions);
        }

        public static final Parcelable.Creator<ActionsPagerView.SavedState> CREATOR
                = new ClassLoaderCreator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return createFromParcel(parcel, null);
            }

            @Override
            public ActionsPagerView.SavedState createFromParcel(Parcel source) {
                return createFromParcel(source, null);
            }

            public ActionsPagerView.SavedState[] newArray(int size) {
                return new ActionsPagerView.SavedState[size];
            }
        };
    }
}
