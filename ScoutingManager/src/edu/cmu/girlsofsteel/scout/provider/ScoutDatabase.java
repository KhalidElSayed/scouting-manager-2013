package edu.cmu.girlsofsteel.scout.provider;

// TODO: IMPLEMENT THE onUpgrade() METHOD BEFORE MARKET RELEASE!!
// TODO: UNDERSTAND THE onUpgrade() METHOD!!
// TODO: MAKE SURE TO HANDLE CONFLICTS WITH UNIQUE TABLE COLUMNS!!
// TODO: TEST ON RANDOMLY CHOSEN AUTO-INCREMENT IDS TO REDUCE ANY BIAS IN TESTING STAGES!!

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import edu.cmu.girlsofsteel.scout.provider.ScoutContract.Matches;
import edu.cmu.girlsofsteel.scout.provider.ScoutContract.TeamMatches;
import edu.cmu.girlsofsteel.scout.provider.ScoutContract.Teams;

/**
 * This class is responsible for the creation of the database. The ScoutProvider
 * class allows access to the data stored in the database.
 */
public class ScoutDatabase extends SQLiteOpenHelper {

	private static final String TAG = "ScoutDatabase";

	private static final String DATABASE_NAME = "scouting_manager.db";
	private static final int DATABASE_VERSION = 1;

	/** SQLite table names. */
	interface Tables {
		String TEAMS = "teams";
		String TEAM_MATCHES = "team_matches";
		String MATCHES = "matches";
	}

	/** {@code REFERENCES} clauses. */
	private interface References {
		String TEAM_ID = "REFERENCES " + Tables.TEAMS + "(" + Teams._ID + ")";
		String MATCH_ID = "REFERENCES " + Tables.MATCHES + "(" + Matches._ID
				+ ")";
	}

	public ScoutDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + Tables.TEAMS + " (" + Teams._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + Teams.NUMBER
				+ " INTEGER NOT NULL," + Teams.NAME + " TEXT," + Teams.PHOTO
				+ " TEXT," + "UNIQUE (" + Teams.NUMBER
				+ ") ON CONFLICT REPLACE);");

		db.execSQL("CREATE TABLE " + Tables.MATCHES + " (" + Matches._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + Matches.NUMBER
				+ " INTEGER NOT NULL," + "UNIQUE (" + Matches.NUMBER
				+ ") ON CONFLICT REPLACE);");

		db.execSQL("CREATE TABLE " + Tables.TEAM_MATCHES + " ("
				+ TeamMatches._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ TeamMatches.MATCH_ID + " INTEGER NOT NULL "
				+ References.MATCH_ID + "," + TeamMatches.TEAM_ID
				+ " INTEGER NOT NULL " + References.TEAM_ID + ");");
	}

	/**
	 * The database currently upgrades the database by destroying the existing
	 * data. The real application MUST upgrade the database in place.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ".");
	}
}
