package es.grupogo.playgroundsdk.widget;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import es.grupogo.playgroundsdk.Action;

/**
 * Created by Carlos Olmedo on 19/4/17.
 */

public class ActionsRecyclerAdapter extends RecyclerView.Adapter<ActionViewHolder> {

    private List<Action> actions = new ArrayList<>();

    private LayoutInflater inflater;
    private ActionViewHolder.OnActionClickListener callback;
    private int layout;
    private boolean deleteable = true;

    public ActionsRecyclerAdapter(Context context, @LayoutRes int layout, ActionViewHolder.OnActionClickListener callback) {

        this.callback = callback;
        inflater = LayoutInflater.from(context);
        this.layout = layout;
    }

    @Override
    public ActionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(layout, parent, false);
        return new ActionViewHolder(callback, view);
    }

    @Override
    public void onBindViewHolder(ActionViewHolder holder, int position) {

        holder.bind(actions.get(position), deleteable);
    }

    @Override
    public int getItemCount() {
        return actions.size();
    }


    public void setActions(List<Action> actions) {
        this.actions.clear();
        this.actions.addAll(actions);
        notifyDataSetChanged();
    }
    public void setActions(List<Action> actions, boolean deleteable) {
        this.deleteable = deleteable;
        this.actions.clear();
        this.actions.addAll(actions);
        notifyDataSetChanged();
    }

    public void removeAction(Action action) {
        int index = actions.indexOf(action);
        actions.remove(action);
        notifyItemRemoved(index);
    }
}
