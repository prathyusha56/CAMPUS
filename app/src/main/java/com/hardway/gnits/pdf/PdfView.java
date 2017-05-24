package com.hardway.gnits.pdf;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.hardway.gnits.R;

import java.io.File;


public class PdfView extends AppCompatActivity {
    private static final String TAG = PdfView.class.getSimpleName();
    public static final String SAMPLE_FILE = "android_tutorial.pdf";
    WebView w;
    Integer pageNumber = 0;
    String pdfFileName;

    String dwnload_file_path;
    String dwnload_file_path1 = "http://www.collegechalo.in/pdf/";
    private ProgressBar progress;


    File file, SDCardRoot;
    String name;
    ProgressBar pb;
    Dialog dialog;
    int totalSize = 0;
    int downloadedSize = 0;
    TextView cur_val;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        Intent i = getIntent();
        name = i.getStringExtra("name");
        Log.e("Name", name);


        w = (WebView) findViewById(R.id.pdfView);
        w.setWebChromeClient(new MyWebViewClient());

        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setMax(100);

        dwnload_file_path = "http://docs.google.com/gview?embedded=true&url=" +name;

        Log.e("path", dwnload_file_path);

        w.getSettings().setJavaScriptEnabled(true);

        w.loadUrl(dwnload_file_path);

        PdfView.this.progress.setProgress(0);

    }

    private class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            PdfView.this.setValue(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }

    public void setValue(int progress) {
        this.progress.setProgress(progress);
    }
}
