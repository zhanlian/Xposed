package de.robv.android.xposed.examples.monitorfile;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class Utils {
    //软件类型判断软件  
    //未知软件类型  
    public static final int UNKNOW_APP = 0;  
    //用户软件类型  
    public static final int USER_APP = 1;  
    //系统软件  
    public static final int SYSTEM_APP = 2;  
    //系统升级软件  
    public static final int SYSTEM_UPDATE_APP = 4;  
    //系统+升级软件  
    public static final int SYSTEM_REF_APP = SYSTEM_APP | SYSTEM_UPDATE_APP; 
    
    public static int checkAppType(Context context, String pkgName) {  
        try {  
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(pkgName, 0);  
            // 是系统软件或者是系统软件更新  
            if (isSystemApp(pInfo) || isSystemUpdateApp(pInfo)) {  
                return SYSTEM_REF_APP;  
            } else {  
                return USER_APP;  
            }  
  
        } catch (NameNotFoundException e) {  
            e.printStackTrace();  
        }  
        return UNKNOW_APP;  
    }  
    /** 
     * 是否是系统软件或者是系统软件的更新软件 
     * @return 
     */  
    public static boolean isSystemApp(PackageInfo pInfo) {  
        return ((pInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);  
    }  
  
    public static boolean isSystemUpdateApp(PackageInfo pInfo) {  
        return ((pInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0);  
    }  
  
    public static boolean isUserApp(PackageInfo pInfo) {  
        return (!isSystemApp(pInfo) && !isSystemUpdateApp(pInfo));  
    }  
}
