package edu.cmu.girlsofsteel.scout.util;

import static edu.cmu.girlsofsteel.scout.util.LogUtil.LOGE;
import static edu.cmu.girlsofsteel.scout.util.LogUtil.makeLogTag;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVWriter;
import edu.cmu.girlsofsteel.scout.R;
import edu.cmu.girlsofsteel.scout.provider.ScoutContract.TeamMatches;
import edu.cmu.girlsofsteel.scout.provider.ScoutContract.Teams;

/**
 * Exports the teams and matches to a CSV file on the disk.
 *
 * @author Alex Lockwood
 */
public class ExportDatabaseTask extends AsyncTask<Void, Void, String> {
  private static final String TAG = makeLogTag(ExportDatabaseTask.class);
  private Context mCtx;

  public ExportDatabaseTask(Context ctx) {
    mCtx = ctx.getApplicationContext();
  }

  @Override
  protected String doInBackground(Void... args) {
    Resources res = mCtx.getResources();
    if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      return res.getString(R.string.export_not_mounted);
    }

    String exportDirName = res.getString(R.string.export_dir_name);
    File exportDir = new File(Environment.getExternalStorageDirectory(), exportDirName);
    exportDir.mkdir();

    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
    File teamsFile = new File(exportDir, res.getString(R.string.export_teams_file_name) + "_" + timeStamp + ".csv");
    File matchesFile = new File(exportDir, res.getString(R.string.export_matches_file_name) + "_" + timeStamp + ".csv");

    Uri[] uris = new Uri[] { Teams.CONTENT_URI, TeamMatches.CONTENT_URI };
    File[] files = new File[] { teamsFile, matchesFile };

    try {
      for (int i = 0; i < uris.length; i++) {
        files[i].createNewFile();
        CSVWriter writer = new CSVWriter(new FileWriter(files[i]));
        Cursor cur = mCtx.getContentResolver().query(uris[i], null, null, null, null);
        String[] colNames = cur.getColumnNames();
        writer.writeNext(colNames);
        if (cur.moveToFirst()) {
          do {
            String[] row = new String[colNames.length];
            for (int j = 0; j < colNames.length; j++) {
              // This will throw an exception if the field value is a BLOB!
              row[j] = cur.getString(j);
            }
            writer.writeNext(row);
          } while (cur.moveToNext());
        }
        writer.close();
        cur.close();
      }
      return res.getString(R.string.export_success);
    } catch (SQLException ex) {
      LOGE(TAG, ex.getMessage(), ex);
    } catch (IOException ex) {
      LOGE(TAG, ex.getMessage(), ex);
    }
    return res.getString(R.string.export_failed);
  }

  @Override
  protected void onPostExecute(String toastMsg) {
    Toast.makeText(mCtx, toastMsg, Toast.LENGTH_SHORT).show();
  }
}