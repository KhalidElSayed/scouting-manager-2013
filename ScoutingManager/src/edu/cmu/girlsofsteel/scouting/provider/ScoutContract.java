package edu.cmu.girlsofsteel.scouting.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines a contract between the Scout content provider and its clients. A
 * contract defines the information that a client needs to access the provider
 * as one or more data tables. A contract is a public, non-extendable (final)
 * class that contains constants defining column names and URIs. A well-written
 * client depends only on the constants in the contract.
 */
public final class ScoutContract {

  //@formatter:off
  /** Column names for teams table */
  interface TeamsColumns {

    /** The team's unique number */
    String NUMBER = "number";

    /** The team's nickname */
    String NAME = "name";

    /** The uri address for the team's photo */
    String PHOTO = "team_photo";

    /** The team's rank */
    String RANK = "rank";

    /** Does the robot score on the low hoop? */
    String ROBOT_CAN_SCORE_ON_LOW = "score_on_low";
    /** Does the robot score on the middle hoop? */
    String ROBOT_CAN_SCORE_ON_MID = "score_on_mid";
    /** Does the robot score on the high hoop? */
    String ROBOT_CAN_SCORE_ON_HIGH = "score_on_high";

    /** Can the robot climb on level 1? */
    String ROBOT_CAN_CLIMB_LEVEL_ONE = "climb_level_one";
    /** Can the robot climb on level 2? */
    String ROBOT_CAN_CLIMB_LEVEL_TWO = "climb_level_two";
    /** Can the robot climb on level 3? */
    String ROBOT_CAN_CLIMB_LEVEL_THREE = "climb_level_three";
    /** Can the robot help climb? */
    String ROBOT_CAN_HELP_CLIMB = "helps_climb";

    /** Can the robot go under the tower? */
    String ROBOT_CAN_GO_UNDER_TOWER = "goes_under_tower";
    /** How many driving gears does the robot have? */
    String ROBOT_NUM_DRIVING_GEARS = "driving_gears";

    /**
     * 0 - Basic tank drive (4 wheels)
     * 1 - Basic tank drive (6 wheels)
     * 2 - Basic tank drive (8 wheels)
     * 3 - Basic tank drive (tank tread)
     * 4 - Omni (kiwi)
     * 5 - Omni (mecanum)
     * 6 - Omni (swerve/crab)
     * 7 - Other
     */
    String ROBOT_DRIVE_TRAIN = "drive_train";

    String ROBOT_DRIVE_TRAIN_OTHER = "drive_train_other";

    /**
     * 0 - KoP
     * 1 - plaction/traction
     * 2 - pneumatic
     * 3 - mecanum
     * 4 - other
     */
    String ROBOT_TYPE_OF_WHEEL = "type_of_wheel";
  }

  /** Column names for team_matches table */
  interface TeamMatchesColumns {
    /** References '_id' in Teams table */
    String TEAM_ID = "team_number";

    /** References 'number' in Matches table */
    String MATCH_NUMBER = "match_number";

    String AUTO_SHOTS_MADE_TOWER = "auto_shots_made_tower";
    String AUTO_SHOTS_MISS_TOWER = "auto_shots_miss_tower";
    String AUTO_SHOTS_MADE_LOW = "auto_shots_made_low";
    String AUTO_SHOTS_MADE_MID = "auto_shots_made_mid";
    String AUTO_SHOTS_MADE_HIGH = "auto_shots_made_high";
    String AUTO_SHOTS_MISS_LOW = "auto_shots_miss_low";
    String AUTO_SHOTS_MISS_MID = "auto_shots_miss_mid";
    String AUTO_SHOTS_MISS_HIGH = "auto_shots_miss_high";

    String TELE_SHOTS_MADE_TOWER = "tele_shots_made_tower";
    String TELE_SHOTS_MISS_TOWER = "tele_shots_miss_tower";
    String TELE_SHOTS_MADE_LOW = "tele_shots_made_low";
    String TELE_SHOTS_MADE_MID = "tele_shots_made_mid";
    String TELE_SHOTS_MADE_HIGH = "tele_shots_made_high";
    String TELE_SHOTS_MISS_LOW = "tele_shots_miss_low";
    String TELE_SHOTS_MISS_MID = "tele_shots_miss_mid";
    String TELE_SHOTS_MISS_HIGH = "tele_shots_miss_high";

    String SHOOTS_FROM_WHERE = "shoots_from_where";
    String TOWER_LEVEL_ONE = "tower_level_one";
    String TOWER_LEVEL_TWO = "tower_level_two";
    String TOWER_LEVEL_THREE = "tower_level_three";
    String TOWER_FELL_OFF = "fell_off_tower";
    String HUMAN_PLAYER_ABILITY = "human_player_ability";
    String FRISBEES_FROM_FEEDER = "frisbees_from_feeder";
    String FRISBEES_FROM_FLOOR = "frisbees_from_floor";
    String ROBOT_STRATEGY = "strategy";
    String ROBOT_SPEED = "speed";
    String ROBOT_MANEUVERABILITY = "maneuverability";
    String ROBOT_PENALTY = "penalty";
    String COMMENTS = "comments";
  }
  //@formatter:on

  public static final String AUTHORITY = "edu.cmu.girlsofsteel.scouting";
  public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
  private static final String PATH_TEAMS = "teams";
  // private static final String PATH_MATCHES = "matches";
  private static final String PATH_TEAM_MATCHES = "team_matches";

  /**
   * Teams table contract. This table stores a list of teams and information
   * about those teams.
   */
  public static final class Teams implements BaseColumns, TeamsColumns {
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.scout.team";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.scout.team";

    // Get all teams
    public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH_TEAMS).build();

    // Get a single team
    public static Uri teamIdUri(long teamId) {
      return CONTENT_URI.buildUpon().appendPath("" + teamId).build();
    }

    private Teams() {
    }
  }

  /**
   * The "team_match" table provides information on how a specific team
   * performed while competing during a given match. This class contains no
   * methods, but implements BaseColumns and TeamMatchesColumns for public
   * access to the table's column names.
   */
  public static final class TeamMatches implements BaseColumns,
      TeamMatchesColumns {
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.scout.team_match";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.scout.team_match";

    // Get all team-matches
    public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH_TEAM_MATCHES).build();

    // Get all team-matches for a team
    public static Uri teamIdUri(long teamId) {
      return CONTENT_URI.buildUpon().appendPath(PATH_TEAMS).appendPath("" + teamId).build();
    }

    // Get a single team-match
    public static Uri matchIdTeamIdUri(long matchId, long teamId) {
      return CONTENT_URI.buildUpon().appendPath("" + matchId).appendPath(PATH_TEAMS).appendPath("" + teamId).build();
    }

    private TeamMatches() {
    }
  }

  private ScoutContract() {
  }
}