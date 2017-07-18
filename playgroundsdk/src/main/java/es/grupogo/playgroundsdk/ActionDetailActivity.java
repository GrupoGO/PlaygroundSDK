package es.grupogo.playgroundsdk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import es.grupogo.playgroundsdk2.R;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;



public class ActionDetailActivity extends AppCompatActivity  {

    public static final String EXTRA_ACTION = "action";


    private TextView textViewPlatform;
    private TextView textViewType;
    private TextView textViewTitle;
    private TextView textViewDescription;
    private TextView textViewPledges;
    private TextView textViewPeople;
    private ImageView imageViewImage;
    private MaterialRatingBar ratingBar;
    private TextView textViewWebLink;
    private TextView textViewPledge;

    private Action action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_detail);

        bind();

        action = getIntent().getParcelableExtra(EXTRA_ACTION);

        if (action!=null) {
            bindAction(action);
        }

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        textViewPledge.setText(getString(action.getShortType()));
        textViewPledge.setTextColor(ContextCompat.getColor(this, R.color.md_black_1000));

        textViewWebLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActionDetailActivity.this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.EXTRA_URL, action.getUrl());
                intent.putExtra(WebViewActivity.EXTRA_TITLE, "");
                startActivity(intent);
            }
        });

    }

    private void bind() {
        textViewPlatform = (TextView) findViewById(R.id.textViewPlatform);
        textViewType = (TextView) findViewById(R.id.textViewSite);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        textViewPledges = (TextView) findViewById(R.id.textViewNumPledges);
        textViewPeople = (TextView) findViewById(R.id.textViewPeople);
        imageViewImage = (ImageView) findViewById(R.id.imageView);
        ratingBar = (MaterialRatingBar) findViewById(R.id.ratingBar);
        textViewWebLink = (TextView) findViewById(R.id.textViewWebLink);
        textViewPledge = (TextView) findViewById(R.id.textViewPledge);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_detail, menu);

        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId()==android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId()==R.id.action_share){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            String msg = String.format("%s\n%s", action.getTitle(), action.getUrl());
            sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void bindAction(Action action) {

        textViewPlatform.setText(action.getWebsite());
        textViewType.setText(action.getType());
        textViewTitle.setText(action.getTitle());
        textViewDescription.setText(action.getDescription());
        textViewPledges.setText("");
        //textViewPledges.setText(getString(R.string.number_pledges, action.getPledges()));
        textViewPeople.setText(String.format("%d %s", action.getMetricQuantity(), action.getMetric()));
        ratingBar.setRating(action.getReview());
        Glide.with(this).load(action.getImage()).into(imageViewImage);

    }

    @Override
    protected void onDestroy() {
        Glide.get(this).clearMemory();
        super.onDestroy();
    }
}
