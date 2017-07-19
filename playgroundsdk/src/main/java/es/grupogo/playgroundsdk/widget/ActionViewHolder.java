package es.grupogo.playgroundsdk.widget;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Locale;

import co.lujun.androidtagview.TagContainerLayout;
import es.grupogo.playgroundsdk.Action;
import es.grupogo.playgroundsdk.UtilsHelper;
import es.grupogo.playgroundsdk2.R;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by Carlos Olmedo on 19/4/17.
 */

public class ActionViewHolder extends RecyclerView.ViewHolder {

    public interface OnActionClickListener {
        void onActionClick(Action action);

        void onActionDelete(Action action);
    }


    TextView textViewSite;

    TextView textViewTitle;

    TextView textViewDescription;

    ImageView imageViewAction;

    TextView textViewPledges;

    TextView buttonPledge;

    MaterialRatingBar ratingBar;

    ImageView logoWebsiteImage;

    TagContainerLayout mTagContainerLayout;


    private static final int MAX_NUMBER_TAGS = 3;

    private Action action;
    private OnActionClickListener callback;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            callback.onActionClick(action);
        }
    };

    public ActionViewHolder(OnActionClickListener callback, View itemView) {
        super(itemView);

        this.callback = callback;

        textViewSite = (TextView)itemView.findViewById(R.id.textViewSite);

        textViewTitle = (TextView)itemView.findViewById(R.id.textViewTitle);

        textViewDescription = (TextView)itemView.findViewById(R.id.textViewDescription);

        imageViewAction = (ImageView)itemView.findViewById(R.id.imageViewAction);

        textViewPledges = (TextView)itemView.findViewById(R.id.textViewPledgeNumber);

        buttonPledge = (TextView)itemView.findViewById(R.id.textViewPledge);

        ratingBar = (MaterialRatingBar)itemView.findViewById(R.id.ratingBar);

        logoWebsiteImage = (ImageView)itemView.findViewById(R.id.image_logo_website);

        mTagContainerLayout = (TagContainerLayout)itemView.findViewById(R.id.tagContainerLayout);


        itemView.setOnClickListener(listener);

        /*if (buttonPledge != null) {

            buttonPledge.setOnClickListener(listener);
        } else {
            itemView.setOnClickListener(listener);
        }*/
    }

    public void bind(final Action action, boolean deleteable) {

        this.action = action;

        if (textViewPledges != null) {
            textViewPledges.setText(String.format(Locale.US, "%d", action.getPledges()));
        }

        //Action

        if (mTagContainerLayout != null) {
            mTagContainerLayout.removeAllTags();
            if (action.getCategories().size() == 0) {
                mTagContainerLayout.setVisibility(View.INVISIBLE);
            } else {
                mTagContainerLayout.setVisibility(View.VISIBLE);
            }
            int numberTags = 0;
            for (String s : action.getCategories()) {
                if (!s.equals("")) {
                    mTagContainerLayout.addTag(s);
                }
                numberTags++;
                if (numberTags >= MAX_NUMBER_TAGS) {
                    break;
                }
            }
        }

        if (textViewSite != null) {
            textViewSite.setText(action.getWebsite());

           /* int resID = itemView.getResources().getIdentifier("ic_source_" + action.getWebsite() , "drawable", itemView.getContext().getPackageName());
            if (logoWebsiteImage != null) {
                if(resID==0){ //Logo not found in resources
                    logoWebsiteImage.setVisibility(View.GONE);
                } else {
                    logoWebsiteImage.setVisibility(View.VISIBLE);
                    logoWebsiteImage.setImageResource(resID);
                }

            }*/

        }

        if (textViewTitle != null) {
            textViewTitle.setText(action.getTitle());
        }
        if (textViewDescription != null) {
            textViewDescription.setText(action.getDescription());
        }

        if (imageViewAction != null) {
            bindImage(imageViewAction, itemView);
        }

        if (ratingBar != null) {
            ratingBar.setRating(action.getReview());
        }

        if (buttonPledge != null) {

            int colorBg = ContextCompat.getColor(itemView.getContext(), action.getShortTypeColor());
            int color = Color.WHITE;

            buttonPledge.setTextColor(color);

            Drawable backgroundDrawable = buttonPledge.getBackground();
            if (backgroundDrawable != null) {
                UtilsHelper.tintDrawable(backgroundDrawable, colorBg);
            }

            buttonPledge.setText(itemView.getContext().getString(action.getShortType()));
            //buttonPledge.setCompoundDrawablesWithIntrinsicBounds(UtilsHelper.tintDrawable(doIcon, color), null, null, null);
        }

    }

    private void bindImage(ImageView imageView, View layout) {
        try {
            Glide.with(itemView.getContext())
                    .load(action.getImage())
                    .into(imageView);


        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            //Glide.clear(layout);
        }

    }
}


