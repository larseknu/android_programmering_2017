package no.hiof.larseknu.playingwithmaterialdesign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class ColorActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        toolbar.setTitle("Colors");

        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.animals:
                Intent intent = new Intent(this, AnimalActivity.class);
                startActivity(intent);
                break;
        }

        return true;
    }
}
