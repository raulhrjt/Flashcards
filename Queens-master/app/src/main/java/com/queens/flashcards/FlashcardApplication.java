package com.queens.flashcards;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;

import com.queens.flashcards.Presentation.Messages;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class FlashcardApplication extends Application {

    private static Context context;
    private static final String DB_SCHEMA_PATH = "db/schema.sql";

    @Override
    public void onCreate() {
        super.onCreate();
        FlashcardApplication.context = getApplicationContext();
        copyDatabaseToDevice();

        try {
            Databases.initDBSchema(this, DB_SCHEMA_PATH);
        } catch (final SQLException | IOException e) {
            Messages.logMessage("Failed to initialize data.\n\nMessage:\n" + e.getMessage());
        }
    }

    //region HSQLDB Methods (from sample project)

    private void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {
            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);
            Databases.setDBPathName(dataDirectory.toString() + "/" + Databases.getDBPathName());

        } catch (final IOException e) {
            Messages.logMessage("Unable to access application data: " + e.getMessage());
        }
    }

    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }

    //endregion

    //region Static Methods

    public static Context getContext() {
        return FlashcardApplication.context;
    }
    //endregion
}
