package es.grupogo.playgroundsdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Carlos Olmedo on 6/4/16.
 */
public class UtilsHelper {



    public static int dpToPx(Context context, int dps) {
        return Math.round(context.getResources().getDisplayMetrics().density * dps);
    }


    public static String getTimeFromDate(Date date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm", Locale.US);
        return dateFormatter.format(date.getTime());
    }

    public static String getDayFromDate(Date date) {
        Locale locale = new Locale("es", "ES");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", locale);
        return dateFormatter.format(date.getTime());
    }

    /**
     Devuelve el día a partir de una string con formato dd/MM/yyyy"
     */
    public static int getDayFromDate(String dateformat){
        String[] items = dateformat.split("/");
        return Integer.parseInt(items[0]);
    }

    /**
     Devuelve el mes a partir de una string con formato dd/MM/yyyy"
     */
    public static int getMonthFromDate(String dateformat){
        String[] items = dateformat.split("/");
        Log.i("prueba", "Mes devuelto por getMonth " + Integer.parseInt(items[1]));
        return Integer.parseInt(items[1])-1;
    }

    /**
     Devuelve el año a partir de una string con formato dd/MM/yyyy"
     */
    public static int getYearFromDate(String dateformat){
        String[] items = dateformat.split("/");
        return Integer.parseInt(items[2]);
    }


    public static Drawable tintDrawable(Drawable drawable, int color) {

        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        wrappedDrawable = wrappedDrawable.mutate();
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }

    public static void setStatusBarTranslucent(Activity activity, boolean makeTranslucent) {
        if (Build.VERSION.SDK_INT>19) {
            if (makeTranslucent) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            } else {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }


    //----------------------------------------------------------------------
    //endregion


    public static void sharePicture(Activity activity, String urlPicture) {
        if (urlPicture!=null) {
            Intent shareIntent = ShareCompat.IntentBuilder.from(activity)
                    .setType("text/plain")
                    .setText(String.format(Locale.US, "%s", urlPicture))
                    .getIntent();

            if (shareIntent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(shareIntent);
            }
        }
    }

    public static void shareYoutube(Activity activity, String urlVideo) {
        if (urlVideo!=null) {
            Intent shareIntent = ShareCompat.IntentBuilder.from(activity)
                    .setType("text/plain")
                    .setText(String.format(Locale.US, "%s", urlVideo))
                    .getIntent();

            if (shareIntent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(shareIntent);
            }
        }
    }

    public static void shareWikipedia(Activity activity, String urlWiki) {
        if (urlWiki!=null) {
            Intent shareIntent = ShareCompat.IntentBuilder.from(activity)
                    .setType("text/plain")
                    .setText(String.format(Locale.US, "%s", urlWiki))
                    .getIntent();

            if (shareIntent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(shareIntent);
            }
        }
    }


    public static float getScreenWidth(Activity context){
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        return outMetrics.widthPixels;
    }

    public static Intent getTwitterIntent(Context ctx, String shareText)
    {
        Intent shareIntent;

        if(doesPackageExist(ctx, "com.twitter.android"))
        {
            shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setClassName("com.twitter.android",
                    "com.twitter.android.PostActivity");
            shareIntent.setType("text/*");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            return shareIntent;
        }
        else
        {
            String tweetUrl = "https://twitter.com/intent/tweet?text=" + shareText;
            Uri uri = Uri.parse(tweetUrl);
            shareIntent = new Intent(Intent.ACTION_VIEW, uri);
            return shareIntent;
        }
    }

    public static boolean doesPackageExist(Context context, String targetPackage){
        List<ApplicationInfo> packages;
        PackageManager pm;

        pm = context.getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if(packageInfo.packageName.equals(targetPackage))
                return true;
        }
        return false;
    }

    /**
     * Created by jorge_cmata on 5/7/17.
     */

    public static class Constant {


    }
}
