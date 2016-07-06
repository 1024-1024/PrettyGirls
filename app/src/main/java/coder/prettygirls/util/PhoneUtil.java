package coder.prettygirls.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * �ֻ�������ù�����
 *
 * @author jingle1267@163.com
 */
public final class PhoneUtil {
    private static long lastClickTime;

    /**
     * Don't let anyone instantiate this class.
     */
    private PhoneUtil() {
        throw new Error("Do not need instantiate!");
    }


    /**
     * ����ϵͳ�����Ž���
     *
     * @param activity    Activity
     * @param phoneNumber �ֻ�����
     * @param smsContent  ��������
     */
    public static void sendMessage(Context activity, String phoneNumber, String smsContent) {
        if (phoneNumber == null || phoneNumber.length() < 4) {
            return;
        }
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", smsContent);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(it);
    }


    /**
     * �ж��Ƿ�Ϊ����
     *
     * @return boolean
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * ��ȡ�ֻ��ͺ�
     *
     * @param context ������
     * @return String
     */
    public static String getMobileModel(Context context) {
        try {
            String model = android.os.Build.MODEL; // �ֻ��ͺ�
            return model;
        } catch (Exception e) {
            return "δ֪";
        }
    }

    /**
     * ��ȡ�ֻ�Ʒ��
     *
     * @param context ������
     * @return String
     */
    public static String getMobileBrand(Context context) {
        try {
            String brand = android.os.Build.BRAND; // androidϵͳ�汾��
            return brand;
        } catch (Exception e) {
            return "δ֪";
        }
    }


    /**
     * ���մ��������
     *
     * @param requestcode ����ֵ
     * @param activity    ������
     * @param fileName    ���ɵ�ͼƬ�ļ���·��
     */
    public static void toTakePhoto(int requestcode, Activity activity, String fileName) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("camerasensortype", 2);// ����ǰ������ͷ
        intent.putExtra("autofocus", true);// �Զ��Խ�
        intent.putExtra("fullScreen", false);// ȫ��
        intent.putExtra("showActionIcons", false);
        try {//����һ����ǰ����id���ļ�Ȼ���������������Ƭ�ĺ�·���������ļ�����������uuid��ʱ����������idȥ��·����
            File file = new File(fileName);
            if (!file.exists()) {//�������ļ������ھʹ���һ���ļ��У�
                file.mkdirs();
            }
            Uri uri = Uri.fromFile(new File(fileName));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            activity.startActivityForResult(intent, requestcode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * �����
     *
     * @param requestcode ��Ӧ��
     * @param activity    ������
     */
    public static void toTakePicture(int requestcode, Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        activity.startActivityForResult(intent, requestcode);
    }
}