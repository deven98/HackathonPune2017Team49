package devapp.com.hackathonpune2017team49;

import android.app.Application;
import android.util.Log;

import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by root on 23/9/17.
 */

public class StarterApplication extends Application {

    private static final String TAG = "Test";

    @Override
    public void onCreate() {
        super.onCreate();


        RealmConfiguration configuration = new RealmConfiguration.Builder(StarterApplication.this).deleteRealmIfMigrationNeeded().schemaVersion(4).build();
        Realm.setDefaultConfiguration(configuration);
        Log.d(TAG , "Realm set");

        RealmInspectorModulesProvider.builder(this)
                .withFolder(getCacheDir())
                .withMetaTables()
                .withDescendingOrder()
                .withLimit(1000)
                .databaseNamePattern(Pattern.compile(".+\\.realm"))
                .build();

    }
}
