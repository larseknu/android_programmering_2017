package no.hiof.larseknu.playingwithservices;

import android.util.Log;

import java.util.Locale;

public class LogHelper {
    public static void ProcessAndThreadId(String label){
        String logMessage = String.format(Locale.getDefault(), "%s, Process ID:%d, Thread ID:%d", label, android.os.Process.myPid(), android.os.Process.myTid());
        Log.i("no.hiof.larseknu", logMessage);
    }
}
