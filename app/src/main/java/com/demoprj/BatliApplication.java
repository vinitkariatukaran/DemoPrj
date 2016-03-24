package com.demoprj;

/**
 * Created by mounil.shah on 12/10/2015.
 */

import android.app.Application;
import android.util.Log;

import com.demoprj.ParseModel.DeliveryBoy;
import com.demoprj.ParseModel.Expense;
import com.demoprj.ParseModel.GasBooking;
import com.demoprj.ParseModel.GasSize;
import com.demoprj.ParseModel.GasType;
import com.demoprj.ParseModel.User;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.SaveCallback;

public class BatliApplication extends Application {

    // App level variable to retain selected spinner value
    public static final String APPLICATION_ID = "yzc4Qw6C7iHTvIDBsOwgffeQpU8CF3p4HtBxseLd";
    public static final String CLIENT_KEY = "VcYb5DLOZib79I7M5YQ6TdwKBbuJjubQ7CBjErdV";


    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(DeliveryBoy.class);
        ParseObject.registerSubclass(GasSize.class);
        ParseObject.registerSubclass(GasType.class);
        ParseObject.registerSubclass(GasBooking.class);
        ParseObject.registerSubclass(Expense.class);
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParsePush.subscribeInBackground("Batliwala", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.d("ParsePush", "Successfully subscribe to parsepush channel");
            }
        });
    }
}