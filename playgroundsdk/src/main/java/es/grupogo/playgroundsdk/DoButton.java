package es.grupogo.playgroundsdk;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.PopupMenu;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import es.grupogo.awesomelibrary.BadgeView;
import es.grupogo.awesomelibrary.utils.DrawableUtils;
import es.grupogo.playgroundsdk2.R;

/**
 * Created by Carlos Olmedo on 30/1/17.
 */

public class DoButton extends AppCompatImageView implements View.OnClickListener, RequestHelper.RequestCallback<List<Action>>{

    private static class SavedState extends BaseSavedState {

        String query;
        int color;


        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            query = in.readString();
            color = in.readInt();

        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(query);
            out.writeInt(color);

        }

        public static final Parcelable.Creator<DoButton.SavedState> CREATOR
                = new Parcelable.Creator<DoButton.SavedState>() {
            public DoButton.SavedState createFromParcel(Parcel in) {
                return new DoButton.SavedState(in);
            }

            public DoButton.SavedState[] newArray(int size) {
                return new DoButton.SavedState[size];
            }
        };
    }


    private List<Action> actions;
    private String query = "";
    private int color = Color.BLACK;

    public DoButton(Context context) {
        super(context);
        init(null);
    }

    public DoButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DoButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        setOnClickListener(this);

        //Enable saveInstance
        setSaveEnabled(true);

        if (attrs!=null) {
            // Go through all custom attrs.
            TypedArray attrsArray = getContext().obtainStyledAttributes(attrs, R.styleable.DoButton);

            color = attrsArray.getColor(R.styleable.DoButton_tint, Color.BLACK);
            query = attrsArray.getString(R.styleable.DoButton_query);
            if (query==null) {
                query = "";
            }

            // Google tells us to call recycle.
            attrsArray.recycle();
        }

        setDrawableTint(color);

        if (Build.VERSION.SDK_INT >= 16){
            int[] atributes = new int[] { android.R.attr.selectableItemBackground};
            TypedArray ta = getContext().obtainStyledAttributes(atributes);
            Drawable drawableFromTheme = ta.getDrawable(0);
            ta.recycle();
            setBackground(drawableFromTheme);
        }

        setScaleType(ScaleType.CENTER);

    }

    @Override
    protected Parcelable onSaveInstanceState() {

        Parcelable superState = super.onSaveInstanceState();
        DoButton.SavedState ss = new DoButton.SavedState(superState);
        ss.query = query;
        ss.color = color;
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        DoButton.SavedState ss = (DoButton.SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        query = ss.query;
        color = ss.color;

    }

    public void setDrawableTint(int color) {
        Drawable d = ContextCompat.getDrawable(getContext(), R.drawable.do_24dp);
        DrawableUtils.tintDrawable(d, color);
        setImageDrawable(d);
        invalidate();
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getContext(), "Do!", Toast.LENGTH_SHORT).show();
        RequestHelper requestHelper = RequestHelper.getInstance(view.getContext());

        requestHelper.getActionsAsync(query, 3, null, null, null, this);

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

        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        t.printStackTrace();

    }

    public static void getSocialActions(Context context, String text, int quantity, String type,  String website,  String category, final RequestHelper.RequestCallback<List<Action>> callback) {
        RequestHelper requestHelper = RequestHelper.getInstance(context);
        requestHelper.getActionsAsync(text, quantity, type, website, category, callback);

    }
}
