package edu.cmu.girlsofsteel.scout;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;

import edu.cmu.girlsofsteel.scout.util.LogUtil;

public class MainActivity extends SherlockFragmentActivity {
  private static final String TAG = LogUtil.makeLogTag(MainActivity.class);

  /**
   * Defines an enum type to determine whether the application is in
   * "team scouting mode" or "match scouting mode".
   */
  public static enum ScoutMode {
    TEAM,
    MATCH,
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getSupportMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
  }

}
