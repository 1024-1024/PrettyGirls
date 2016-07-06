package coder.prettygirls.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import coder.prettygirls.R;


/**
 * ����ɾ�����ͼ��
 * <p/>
 * ��ҪȨ��: com.android.launcher.permission.INSTALL_SHORTCUT
 * com.android.launcher.permission.UNINSTALL_SHORTCUT
 */
public final class ShortCutUtil {

    /**
     * Don't let anyone instantiate this class.
     */
    private ShortCutUtil() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * ����Ƿ���ڿ�ݼ�
     *
     * @param activity Activity
     * @return �Ƿ��������ͼ��
     */
    public static boolean hasShortcut(Activity activity) {
        boolean isInstallShortcut = false;
        final ContentResolver cr = activity.getContentResolver();
        final String AUTHORITY = "com.android.launcher.settings";
        final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
                + "/favorites?notify=true");
        Cursor c = cr.query(CONTENT_URI,
                new String[]{"title", "iconResource"}, "title=?",
                new String[]{activity.getString(R.string.app_name).trim()},
                null);
        if (c != null && c.getCount() > 0) {
            isInstallShortcut = true;
        }
        return isInstallShortcut;
    }

    /**
     * Ϊ���򴴽������ݷ�ʽ
     *
     * @param activity Activity
     * @param res      res
     */
    public static void addShortcut(Activity activity, int res) {

        Intent shortcut = new Intent(
                "com.android.launcher.action.INSTALL_SHORTCUT");
        // ��ݷ�ʽ������
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,
                activity.getString(R.string.app_name));
        // �������ظ�����
        shortcut.putExtra("duplicate", false);
        Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
        shortcutIntent.setClassName(activity, activity.getClass().getName());
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        // ��ݷ�ʽ��ͼ��
        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(
                activity, res);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);

        activity.sendBroadcast(shortcut);
    }

    /**
     * ɾ������Ŀ�ݷ�ʽ
     *
     * @param activity Activity
     */
    public static void delShortcut(Activity activity) {

        Intent shortcut = new Intent(
                "com.android.launcher.action.UNINSTALL_SHORTCUT");
        // ��ݷ�ʽ������
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,
                activity.getString(R.string.app_name));
        String appClass = activity.getPackageName() + "."
                + activity.getLocalClassName();
        ComponentName comp = new ComponentName(activity.getPackageName(),
                appClass);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(
                Intent.ACTION_MAIN).setComponent(comp));
        activity.sendBroadcast(shortcut);
    }
}

