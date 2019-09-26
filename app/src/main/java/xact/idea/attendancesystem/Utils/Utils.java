package xact.idea.attendancesystem.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import xact.idea.attendancesystem.R;


public class Utils {
    private static Calendar sCalendar = Calendar.getInstance();
    private static CustomProgressDialog sPdLoading = null;
    public static final int REQUEST_EXTERNAL_STORAGE = 1001;
    public static boolean is_home=true;
    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };



    public static boolean isAndroid5() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String Base64Encode(String key) {
        String base64 = "";
        byte[] data = new byte[0];
        try {
            data = key.getBytes("UTF-8");
            base64 = Base64.encodeToString(data, Base64.NO_WRAP);

            Log.e("key", key + "\n" + base64);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return base64;


    }



    public static void showLoadingProgress(final Context context) {

        if (CustomProgressDialog.sPdCount <= 0) {
            CustomProgressDialog.sPdCount = 0;
            sPdLoading = null;
            sPdLoading = new CustomProgressDialog(context, R.style.CustomDialogTheme);
            if (!sPdLoading.isShowing())
                sPdLoading.show();
            if (Build.VERSION.SDK_INT > 10) {
                LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View loadingV = inflator.inflate(R.layout.layout_dialog_spinner, null);
                CorrectSizeUtil.getInstance((Activity) context).correctSize(loadingV);
                sPdLoading.setContentView(loadingV);
            } else {
                String message = "Loading...";
            }
            CustomProgressDialog.sPdCount++;
        } else {
            CustomProgressDialog.sPdCount++;
        }


    }

    public static void dismissLoadingProgress() {


        if (CustomProgressDialog.sPdCount <= 1) {
            if (sPdLoading != null && sPdLoading.isShowing())
                sPdLoading.dismiss();
            CustomProgressDialog.sPdCount--;
        } else {
            CustomProgressDialog.sPdCount--;
        }
    }


    private static final int SERVER_TIME_ZONE = 9;

    private static long getTimeInZone(long second) {
        return (SERVER_TIME_ZONE * 3600 + second) * 1000;
    }

    public static String getDateFromSecond(long second) {
        try {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            cal.setTimeInMillis(getTimeInZone(second));
            String res = "";
            res += cal.get(Calendar.YEAR);
            res += "." + String.format("%02d", (cal.get(Calendar.MONTH) + 1));
            res += "." + String.format("%02d", (cal.get(Calendar.DAY_OF_MONTH)));
            return res;
        } catch (Exception e) {
            return "-";
        }
    }

    public static String getDateFromSecondMyTimeZone(long second) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(second * 1000);
            String res = "";
            res += cal.get(Calendar.YEAR);
            res += "." + String.format("%02d", (cal.get(Calendar.MONTH) + 1));
            res += "." + String.format("%02d", (cal.get(Calendar.DAY_OF_MONTH)));
            return res;
        } catch (Exception e) {
            return "-";
        }
    }

    public static String getSlashDateFromSecond(Long second) {
        if (second != null) {
            try {
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                cal.setTimeInMillis(getTimeInZone(second));
                String res = "";
                res += cal.get(Calendar.YEAR);
                res += "/" + String.format("%02d", (cal.get(Calendar.MONTH) + 1));
                res += "/" + String.format("%02d", (cal.get(Calendar.DAY_OF_MONTH)));
                return res;
            } catch (Exception e) {
                return "-";
            }
        }
        return "";
    }

    /**
     * for params can be null from API
     *
     * @param second
     * @return
     */
    public static String getSlashDateFromSecond(String second) {
        if (second != null) {
            try {
                Long secondL = Long.parseLong(second);
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                cal.setTimeInMillis(getTimeInZone(secondL));
                String res = "";
                res += cal.get(Calendar.YEAR);
                res += "/" + String.format("%02d", (cal.get(Calendar.MONTH) + 1));
                res += "/" + String.format("%02d", (cal.get(Calendar.DAY_OF_MONTH)));
                return res;
            } catch (Exception e) {
                return "-";
            }
        }
        return "-";
    }

    public static String getSlashDateFromSecond(long second) {
        try {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            cal.setTimeInMillis(getTimeInZone(second));
            String res = "";
            res += cal.get(Calendar.YEAR);
            res += "/" + String.format("%02d", (cal.get(Calendar.MONTH) + 1));
            res += "/" + String.format("%02d", (cal.get(Calendar.DAY_OF_MONTH)));
            return res;
        } catch (Exception e) {
            return "-";
        }
    }

    public static String getDayFromSecond(long second, String[] days) {
        if (days == null) {
            return "-";
        }
        if (days.length != 7) {
            return "-";
        }
        try {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            cal.setTimeInMillis(getTimeInZone(second));
            String res = "";
            int day = cal.get(Calendar.DAY_OF_WEEK);

            switch (day) {
                case Calendar.SUNDAY:
                    res = days[0];
                    break;
                case Calendar.MONDAY:
                    res = days[1];
                    break;
                case Calendar.TUESDAY:
                    res = days[2];
                    break;
                case Calendar.WEDNESDAY:
                    res = days[3];
                    break;
                case Calendar.THURSDAY:
                    res = days[4];
                    break;
                case Calendar.FRIDAY:
                    res = days[5];
                    break;
                case Calendar.SATURDAY:
                    res = days[6];
                    break;
            }

            return res;
        } catch (Exception e) {
            return "-";
        }
    }

    public static String getDayFromSecondMyTimeZone(long second, String[] days) {
        if (days == null) {
            return "-";
        }
        if (days.length != 7) {
            return "-";
        }
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(second * 1000);
            String res = "";
            int day = cal.get(Calendar.DAY_OF_WEEK);

            switch (day) {
                case Calendar.SUNDAY:
                    res = days[0];
                    break;
                case Calendar.MONDAY:
                    res = days[1];
                    break;
                case Calendar.TUESDAY:
                    res = days[2];
                    break;
                case Calendar.WEDNESDAY:
                    res = days[3];
                    break;
                case Calendar.THURSDAY:
                    res = days[4];
                    break;
                case Calendar.FRIDAY:
                    res = days[5];
                    break;
                case Calendar.SATURDAY:
                    res = days[6];
                    break;
            }

            return res;
        } catch (Exception e) {
            return "-";
        }
    }

    public static String getMonthFromSecondMyTimeZone(long second) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(second * 1000);
            String res = "";
            res += cal.get(Calendar.MONTH) + 1;
            return res;
        } catch (Exception e) {
            return "-";
        }
    }

    public static String getDayFromSecondMyTimeZone(long second) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(second * 1000);
            String res = "";
            res += cal.get(Calendar.DAY_OF_MONTH);
            return res;
        } catch (Exception e) {
            return "-";
        }
    }

    public static String getHourMinuteSecond(long second) {
        try {
            Calendar cal = Calendar.getInstance();
//            cal.setTimeInMillis(getTimeInZone(second));
            cal.setTimeInMillis(second * 1000);
            String res = "";
            res += cal.get(Calendar.HOUR_OF_DAY);
            res += ":" + String.format("%02d", (cal.get(Calendar.MINUTE)));
            return res;
        } catch (Exception e) {
            return "-";
        }
    }


    public static String getChatSeparateDateFromMilliSecond(long milliSecond) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTimeInMillis(milliSecond);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = dateFormat.format(cal.getTime());
        return formattedDate;
    }

    public static String getChatTimeFromMilliSecond(long milliSecond) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTimeInMillis(milliSecond);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String formattedTime = dateFormat.format(cal.getTime());
        return formattedTime;
    }

    public static String getDateTimeFromMilliSecond(long milliSecond) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(milliSecond);
            String res = "";
            res += String.format("%02d", (cal.get(Calendar.MONTH) + 1));
            res += "-" + String.format("%02d", (cal.get(Calendar.DAY_OF_MONTH)));
            res += " " + String.format("%02d", (cal.get(Calendar.HOUR_OF_DAY)));
            res += ":" + String.format("%02d", (cal.get(Calendar.MINUTE)));
            return res;
        } catch (Exception e) {
            return "0000-00-00";
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }



    public static boolean isNetworkConnected(Context activity) {
        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }



    public static String getCommentPosition(int position) {
        NumberFormat nf = new DecimalFormat("0000");
        return nf.format(position);
    }

    public static final void showSoftKeyBoard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public static File createImageFile(Context context) throws IOException {
        // Create an image file name
        String imageFileName = createFileImageName();
        File storageDir;
        if (context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) != null) {
            storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        } else {
            storageDir = context.getFilesDir();
        }
        storageDir.mkdirs();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        //mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public static String createFileImageName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return "JPEG_" + timeStamp + "_";
    }

    public static String getRealPathFromURI(Uri uri, Activity activity) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



    public static boolean isStoragePermissionGranted(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public static void verifyStoragePermissions(Activity activity) {
        if (isStoragePermissionGranted(activity)) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public static String buildChatRoom(String myID) {
        //this is Master chat
        return "-3_-2_-1_" + myID;
    }

    public static String buildChatRoom(String friendID, String myID) {
        try {
            int friend = Integer.parseInt(friendID);
            int my = Integer.parseInt(myID);
            if (friend > my) {
                return myID + "_" + friendID;
            } else {
                return friendID + "_" + myID;
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static String pointValueToString(String point) {
        String result = "";
        while (point.length() > 3) {
            String subStr = point.substring(point.length() - 3, point.length());
            result = "," + subStr + result;
            point = point.substring(0, point.lastIndexOf(subStr));
        }
        result = point + result;
        return result;
    }



    public static void setTextViewDifferentStyleString(TextView textView, String str1, String str2, int textSize1, int textSize2, int color) {
        String StringSum = str1 + str2;
        SpannableString spannableString = new SpannableString(StringSum);
        spannableString.setSpan(new RelativeSizeSpan(textSize1 / (textSize2 * 1.0f)), 0, str1.length(), 0);
        spannableString.setSpan(new ForegroundColorSpan(color), 0, str1.length(), 0);
        textView.setText(spannableString);
    }

    public static CharSequence getStringData(String data) {
        if ((data != null) && (!TextUtils.isEmpty(data)) && (!data.equals("null"))) {
            return getFromHtml(data);
        }
        return "";
    }




    public static boolean checkUnicode(Character.UnicodeBlock unicodeBlock, String s) {
        if (s == null) {
            return true;
        }

        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (!Character.UnicodeBlock.of(s.charAt(i)).equals(unicodeBlock)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isMatchWithRegex(String text, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(text);
        while (matcher.find()) {
            return true;
        }
        return false;
    }

    public static boolean containsEmojiCharacters(String displayName) {
        final int nameLength = displayName.length();

        for (int i = 0; i < nameLength; i++) {
            final char hs = displayName.charAt(i);

            if (0xd800 <= hs && hs <= 0xdbff) {
                final char ls = displayName.charAt(i + 1);
                final int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;

                if (0x1d000 <= uc && uc <= 0x1f77f) {
                    return true;
                }
            } else if (Character.isHighSurrogate(hs)) {
                final char ls = displayName.charAt(i + 1);

                if (ls == 0x20e3) {
                    return true;
                }
            } else {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c || hs == 0x2b1b || hs == 0x2b50) {
                    return true;
                }
            }
        }
        return false;
    }



    public static CharSequence getFromHtml(String data) {
        if (Build.VERSION.SDK_INT < 24) {
            return Html.fromHtml(data);
        } else {
            return Html.fromHtml(data, Html.FROM_HTML_MODE_LEGACY);
        }
    }

    public static GradientDrawable  getGradientColor(Context context){
        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{ContextCompat.getColor(context, R.color.first1),
                        ContextCompat.getColor(context, R.color.first2),
                        ContextCompat.getColor(context, R.color.first3),
                        ContextCompat.getColor(context, R.color.first4),
                        ContextCompat.getColor(context, R.color.first5),
                        ContextCompat.getColor(context, R.color.first6)});

        return gradientDrawable;

    }

}
