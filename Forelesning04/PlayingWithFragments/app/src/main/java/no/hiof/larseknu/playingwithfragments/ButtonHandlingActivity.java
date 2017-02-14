package no.hiof.larseknu.playingwithfragments;

import android.app.Activity;
import android.os.Bundle;

public class ButtonHandlingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_handling);
    }

//	public void onClick(View view) {
//		FragmentManager fragmentManager = getFragmentManager();
//		ButtonHandlingFragment fragment = (ButtonHandlingFragment) fragmentManager.findFragmentById(R.id.buttonHandlingFragment);
//
//		fragment.myButtonClickHandler(view);
//	}
}
