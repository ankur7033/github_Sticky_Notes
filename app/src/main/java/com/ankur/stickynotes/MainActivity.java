package com.ankur.stickynotes;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText noteEdt;
    private Button increaseSize, decreaseSize, boldBtn, underlineBtn, italicBtn, saveBtn;
    private TextView sizeTV;
    private float currentSize;
    StickyNote note = new StickyNote();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        noteEdt = findViewById(R.id.editText);
        increaseSize = findViewById(R.id.increaseSizeBtn);
        decreaseSize = findViewById(R.id.decreaseSizeBtn);
        boldBtn = findViewById(R.id.boldBtn);
        underlineBtn = findViewById(R.id.underLineBtn);
        italicBtn = findViewById(R.id.italicBtn);
        saveBtn = findViewById(R.id.saveButton);
        sizeTV = findViewById(R.id.sizeTV);

        // Load the saved note
//        String savedNote = note.getStick(this);
//        noteEdt.setText(savedNote);

        currentSize = noteEdt.getTextSize();
        sizeTV.setText(String.valueOf(currentSize));

        increaseSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSize++;
                sizeTV.setText(String.valueOf(currentSize));
                noteEdt.setTextSize(currentSize);
            }
        } );

        decreaseSize.setOnClickListener(v -> {
            currentSize--;
            sizeTV.setText(String.valueOf(currentSize));
            noteEdt.setTextSize(currentSize);
        });

        boldBtn.setOnClickListener(v -> {
            if (noteEdt.getTypeface() != null && noteEdt.getTypeface().isBold()) {
                noteEdt.setTypeface(Typeface.DEFAULT);
            } else {
                noteEdt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }
        });

        underlineBtn.setOnClickListener(v -> {
            if ((noteEdt.getPaintFlags() & Paint.UNDERLINE_TEXT_FLAG) != 0) {
                noteEdt.setPaintFlags(noteEdt.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
            } else {
                noteEdt.setPaintFlags(noteEdt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            }
        });

        italicBtn.setOnClickListener(v -> {
            if (noteEdt.getTypeface() != null && noteEdt.getTypeface().isItalic()) {
                noteEdt.setTypeface(Typeface.DEFAULT);
            } else {
                noteEdt.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
            }
        });

        saveBtn.setOnClickListener(v -> saveButton());
    }

    public void saveButton() {
        note.setStick(noteEdt.getText().toString(), this);
        updateWidget();
        Toast.makeText(this, "Note has been updated..", Toast.LENGTH_SHORT).show();
    }

    private void updateWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.widget_layout);
        ComponentName thisWidget = new ComponentName(this, AppWidget.class);
        remoteViews.setTextViewText(R.id.appwidget_text, noteEdt.getText().toString());
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }
}
