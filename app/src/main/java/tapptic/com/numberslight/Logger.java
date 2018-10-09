package tapptic.com.numberslight;

import android.util.Log;

public class Logger {
    private static String LOGTAG = "myTag";

    public static void debug(String p_param) {
        Log.wtf(LOGTAG, p_param);
    }
}
