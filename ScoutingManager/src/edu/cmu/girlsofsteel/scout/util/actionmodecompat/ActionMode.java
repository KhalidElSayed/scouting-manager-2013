package edu.cmu.girlsofsteel.scout.util.actionmodecompat;

import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import edu.cmu.girlsofsteel.scout.util.CompatUtil;

/**
 * A compatibility shim for {@link android.view.ActionMode} that shows context
 * menus on pre-Honeycomb devices.
 */
public abstract class ActionMode {
  private Object mTag;

  public static ActionMode start(FragmentActivity activity, Callback callback) {
    if (CompatUtil.hasHoneycomb()) {
      return ActionModeHoneycomb.startInternal(activity, callback);
    } else {
      return ActionModeBase.startInternal(activity, callback);
    }
  }

  public static void setMultiChoiceMode(ListView listView, FragmentActivity activity,
      MultiChoiceModeListener listener) {
    if (CompatUtil.hasHoneycomb()) {
      ActionModeHoneycomb.beginMultiChoiceMode(listView, activity, listener);
    } else {
      ActionModeBase.beginMultiChoiceMode(listView, activity, listener);
    }
  }

  /**
   * Set a tag object associated with this ActionMode.
   *
   * <p>
   * Like the tag available to views, this allows applications to associate
   * arbitrary data with an ActionMode for later reference.
   *
   * @param tag Tag to associate with this ActionMode
   *
   * @see #getTag()
   */
  public void setTag(Object tag) {
    mTag = tag;
  }

  /**
   * Retrieve the tag object associated with this ActionMode.
   *
   * <p>
   * Like the tag available to views, this allows applications to associate
   * arbitrary data with an ActionMode for later reference.
   *
   * @return Tag associated with this ActionMode
   *
   * @see #setTag(Object)
   */
  public Object getTag() {
    return mTag;
  }

  /**
   * Set the title of the action mode. This method will have no visible effect
   * if a custom view has been set.
   *
   * @param title Title string to set
   *
   * @see #setTitle(int)
   * @see #setCustomView(View)
   */
  public abstract void setTitle(CharSequence title);

  /**
   * Set the title of the action mode. This method will have no visible effect
   * if a custom view has been set.
   *
   * @param resId Resource ID of a string to set as the title
   *
   * @see #setTitle(CharSequence)
   * @see #setCustomView(View)
   */
  public abstract void setTitle(int resId);

  /**
   * Invalidate the action mode and refresh menu content. The mode's
   * {@link ActionMode.Callback} will have its
   * {@link Callback#onPrepareActionMode(ActionMode, Menu)} method called. If it
   * returns true the menu will be scanned for updated content and any relevant
   * changes will be reflected to the user.
   */
  public abstract void invalidate();

  /**
   * Finish and close this action mode. The action mode's
   * {@link ActionMode.Callback} will have its
   * {@link Callback#onDestroyActionMode(ActionMode)} method called.
   */
  public abstract void finish();

  /**
   * Returns the current title of this action mode.
   *
   * @return Title text
   */
  public abstract CharSequence getTitle();

  /**
   * Returns a {@link MenuInflater} with the ActionMode's context.
   */
  public abstract MenuInflater getMenuInflater();

  /**
   * Returns whether the UI presenting this action mode can take focus or not.
   * This is used by internal components within the framework that would
   * otherwise present an action mode UI that requires focus, such as an
   * EditText as a custom view.
   *
   * @return true if the UI used to show this action mode can take focus
   * @hide Internal use only
   */
  public boolean isUiFocusable() {
    return true;
  }

  /**
   * Callback interface for action modes. Supplied to
   * {@link View#startActionMode(Callback)}, a Callback configures and handles
   * events raised by a user's interaction with an action mode.
   *
   * <p>
   * An action mode's lifecycle is as follows:
   * <ul>
   * <li>{@link Callback#onCreateActionMode(ActionMode, Menu)} once on initial
   * creation</li>
   * <li>{@link Callback#onPrepareActionMode(ActionMode, Menu)} after creation
   * and any time the {@link ActionMode} is invalidated</li>
   * <li>{@link Callback#onActionItemClicked(ActionMode, MenuItem)} any time a
   * contextual action button is clicked</li>
   * <li>{@link Callback#onDestroyActionMode(ActionMode)} when the action mode
   * is closed</li>
   * </ul>
   */
  public interface Callback {
    /**
     * Called when action mode is first created. The menu supplied will be used
     * to generate action buttons for the action mode.
     *
     * @param mode ActionMode being created
     * @param menu Menu used to populate action buttons
     * @return true if the action mode should be created, false if entering this
     *         mode should be aborted.
     */
    public boolean onCreateActionMode(ActionMode mode, Menu menu);

    /**
     * Called to refresh an action mode's action menu whenever it is
     * invalidated.
     *
     * @param mode ActionMode being prepared
     * @param menu Menu used to populate action buttons
     * @return true if the menu or action mode was updated, false otherwise.
     */
    public boolean onPrepareActionMode(ActionMode mode, Menu menu);

    /**
     * Called to report a user click on an action button.
     *
     * @param mode The current ActionMode
     * @param item The item that was clicked
     * @return true if this callback handled the event, false if the standard
     *         MenuItem invocation should continue.
     */
    public boolean onActionItemClicked(ActionMode mode, MenuItem item);

    /**
     * Called when an action mode is about to be exited and destroyed.
     *
     * @param mode The current ActionMode being destroyed
     */
    public void onDestroyActionMode(ActionMode mode);
  }
}
