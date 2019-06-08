package edu.jiangxin.droiddemo.service.localbinder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class LocalBinderService extends Service {
    // Random number generator
    private final Random mGenerator = new Random();

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        LocalBinderService getService() {
            // Return this instance of LocalBinderService so clients can call public methods
            return LocalBinderService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }

    /**
     * method for clients
     */
    public int getRandomNumber() {
        return mGenerator.nextInt(Integer.MAX_VALUE);
    }
}