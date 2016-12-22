package com.zzz.game.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.zzz.game.R;

/**
 * Created by dell_2 on 2016/12/19.
 */
public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void share(View view) {

        Toast.makeText(this, view.toString(), Toast.LENGTH_SHORT).show();
    }


}
