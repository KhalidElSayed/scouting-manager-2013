package edu.cmu.girlsofsteel.scout;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import edu.cmu.girlsofsteel.scout.provider.ScoutContract.Teams;
import edu.cmu.girlsofsteel.scout.util.ExportDatabaseTask;
import edu.cmu.girlsofsteel.scout.util.LogUtil;

public class MainActivity extends SherlockFragmentActivity {

  @SuppressWarnings("unused")
  private static final String TAG = LogUtil.makeLogTag(MainActivity.class);

  // Used to pass team ids to the next activity
  public static final String TEAM_ID_EXTRA = "team_id_extra";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
    getSupportMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_export:
        new ExportDatabaseTask(this).execute(Teams.CONTENT_URI);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * Defines an enum type to determine whether the application is in
   * "team scouting mode" or "match scouting mode".
   */
  public static enum ScoutMode {
    TEAM,
    MATCH,
  }
}
