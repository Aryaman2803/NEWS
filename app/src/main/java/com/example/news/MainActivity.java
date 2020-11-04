package com.example.news;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    public Toolbar toolbar;
    TabLayout tabLayout;
    EditText editText;
    Button button;
    ImageButton imageButton;
    private ViewPager viewPager;
    private HeadlineFragment headlineFragment;
    private BusinessFragment businessFragment;
    private HealthFragment healthFragment;
    private SportsFragment sportsFragment;
    private EntertainmentFragment entertainmentFragment;
    private ScienceFragment scienceFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setSupportActionBar(toolbar);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        imageButton = findViewById(R.id.cross);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        /**(2) Fragments created now create fragment Objects**/
        headlineFragment = new HeadlineFragment();
        businessFragment = new BusinessFragment();
        healthFragment = new HealthFragment();
        sportsFragment = new SportsFragment();
        entertainmentFragment = new EntertainmentFragment();
        scienceFragment = new ScienceFragment();

        /**(3) Now we will set up our tab layout with the view pager.
         * We also need an Adapter for our ViewPager **/
        tabLayout.setupWithViewPager(viewPager);

        /**(4)Our ViewPager Adapter work has started.
         * It will be a separate class which will be inside our MainActivity**/
        viewPagerAdapter viewPagerAdapter = new viewPagerAdapter(getSupportFragmentManager(), 0);

        /**(9) Now we pass object of our Business and Headline fragment**/
        viewPagerAdapter.addFragment(headlineFragment, "Headline");
        viewPagerAdapter.addFragment(businessFragment, "Business");
        viewPagerAdapter.addFragment(healthFragment, "health");
        viewPagerAdapter.addFragment(sportsFragment, "Sports");
        viewPagerAdapter.addFragment(entertainmentFragment, "Entertainment");
        viewPagerAdapter.addFragment(scienceFragment, "Science");

        /**(10) Our ViewPagerAdapter is ready, now we set the Adapter**/
        viewPager.setAdapter(viewPagerAdapter);

        /**(11) We can pass Fragment icons dynamically too**/
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_newspaper);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_briefcase);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_hospital);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_runer_silhouette_running_fast);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_tv);
        tabLayout.getTabAt(5).setIcon(R.drawable.ic_science);

        String query = editText.getText().toString();


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(null);
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                imageButton.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                imageButton.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editText.getText().toString().isEmpty()) {
                    imageButton.setVisibility(View.GONE);
                }
            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

//        || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_GO
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if ((event != null) && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                    button.performClick();
                }
                return false;
            }
        });

        /**Integrating onClick Button features**/
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Field Empty", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, Search.class);
                    intent.putExtra("query", editText.getText().toString());
                    Log.v("Edit Query ", query);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view != null && view instanceof EditText) {
                Rect r = new Rect();
                view.getGlobalVisibleRect(r);
                int rawX = (int) ev.getRawX();
                int rawY = (int) ev.getRawY();
                if (!r.contains(rawX, rawY)) {
                    view.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        editText.setText(null);
    }

}