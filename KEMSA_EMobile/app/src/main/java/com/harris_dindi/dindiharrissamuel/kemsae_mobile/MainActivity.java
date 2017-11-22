package com.harris_dindi.dindiharrissamuel.kemsae_mobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.CookieManager;


public class MainActivity extends AppCompatActivity {



    private WebView wv1;
    ProgressDialog progress;
    final Context context = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        wv1 = (WebView)findViewById(R.id.webView);
        final String url = "http://emobile.kemsa.co.ke/";
        wv1.setWebViewClient(new MyBrowser() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (view.canGoBack()) {
                    view.goBack();
                }


                if (errorCode == WebViewClient.ERROR_HOST_LOOKUP) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    // set title
                    builder.setTitle("KEMSA E-mobile");
                    // set dialog message
                    builder.setMessage("Unable to Connect to the  Internet. KEMSA E-mobile could not run becuase your device isn't connected to the Internet. Do you wish to close the application ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    moveTaskToBack(true);
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(0);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    dialog.cancel();
                                    wv1.loadUrl(url);
                                }

                            });

                    // create alert dialog
                    AlertDialog alertDialog = builder.create();

                    // show it
                    alertDialog.show();

                /*Toast error_toast = Toast.makeText(getBaseContext(),"Error: No Internet Connection",Toast.LENGTH_SHORT);
                error_toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                error_toast.show();*/
                } else if (errorCode == WebViewClient.ERROR_TIMEOUT) {

                    AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                    // set title
                    builder2.setTitle("KEMSA E-mobile");
                    // set dialog message
                    builder2.setMessage("Error Connection Timeout. Do you wish to close the application ?[Please click No to try again.]")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    moveTaskToBack(true);
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(0);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    dialog.cancel();
                                    wv1.loadUrl(url);
                                }

                            });

                    // create alert dialog
                    AlertDialog alertDialog = builder2.create();

                    // show it
                    alertDialog.show();


                }

            }
        });



        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);


        if(savedInstanceState==null){
            wv1.loadUrl(url);
        }




    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        wv1.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        wv1.restoreState(savedInstanceState);
    }

    private class MyBrowser extends WebViewClient {
        ProgressDialog pd= new ProgressDialog(MainActivity.this);
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            pd.setMessage("Loading Please Wait ...");
            pd.show();

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pd.dismiss();
        }


    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("Exit")
        .setMessage("Are you sure you want to Exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                }).setNegativeButton("No",null).show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
