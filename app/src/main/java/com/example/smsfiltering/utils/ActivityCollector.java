package com.example.smsfiltering.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by si on 2018-3-13.
 */

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);

    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finsihAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
