package no.hiof.larseknu.playingwithfragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class HappySadActivity extends Activity {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);

        fragmentManager = getFragmentManager();
    }

    public void addHappyFragment(View view) {
        HappyFragment happyFragment = new HappyFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.content, happyFragment, "Happy");
        fragmentTransaction.commit();
    }

    public void addSadFragment(View view) {
        SadFragment sadFragment = new SadFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.content, sadFragment, "Sad");
        fragmentTransaction.commit();
    }

    public void removeHappyFragment(View view) {
        Fragment fragment = fragmentManager.findFragmentByTag("Happy");

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }
    }

    public void removeSadFragment(View view) {
        Fragment fragment = fragmentManager.findFragmentByTag("Sad");

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }
    }

    public void replaceWithHappyFragment(View view) {
        HappyFragment happyFragment = new HappyFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.content, happyFragment, "HappyReplaced");
        
        fragmentTransaction.commit();
    }

    public void replaceWithSadFragment(View view) {
        SadFragment sadFragment = new SadFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.content, sadFragment, "SadReplaced");

        fragmentTransaction.commit();
    }
}
