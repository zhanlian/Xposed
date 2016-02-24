package de.robv.android.xposed.examples.monitorfile;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import android.graphics.Color;
import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Monitorfile implements IXposedHookLoadPackage {
    private String mPkgName;
    private String[] mRemovePkgPrefix = { "android", "com.android", "eu.chainfire", "de.robv.android" };

    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        mPkgName = lpparam.packageName;
        for (String pkgPrefix : mRemovePkgPrefix) {
            if (mPkgName.startsWith(pkgPrefix)) {
                Log.i("lyf", "filter pkg = " + lpparam.packageName);
                return;
            }
        }
        Log.i("lyf", "hook pkg = " + mPkgName + ";processName=" + lpparam.processName);
        // if (!lpparam.packageName.equals("com.example.testmonitorfile")){
        // Log.i("lyf","filter pkg = "+lpparam.packageName);
        // return;
        // }else{
        // Log.i("lyf","hook pkg = "+lpparam.packageName);
        // }

        findAndHookMethod("libcore.io.Posix", lpparam.classLoader, "mkdir", String.class, int.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        String path = (String) param.args[0];
                        Log.i("lyf", "action= mkdir;path = " + path + ";pkgname=" + mPkgName);
                    }
                });
        // findAndHookMethod("libcore.io.ForwardingOs", lpparam.classLoader, "mkdir",String.class,int.class, new
        // XC_MethodHook() {
        // @Override
        // protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        // String path = (String)param.args[0];
        // Log.i("lyf","ForwardingOs mkdir = "+path);
        // }
        // });
        // findAndHookMethod("java.io.File", lpparam.classLoader, "mkdir", new XC_MethodHook() {
        // @Override
        // protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        // File file = (File)param.thisObject;
        // Log.i("lyf","File mkdir = "+file.getAbsolutePath());
        // }
        // });
        // findAndHookMethod("libcore.io.BlockGuardOs", lpparam.classLoader, "open",String.class,int.class,int.class,
        // new XC_MethodHook() {
        // @Override
        // protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        // String path = (String)param.args[0];
        // int flag = (Integer)param.args[1];
        // int mode = (Integer)param.args[2];
        // Log.i("lyf","action="
        // +(0==mode?"read":"write")+";path = "+path+";flag ="+flag+";mode ="+mode+";pkgname="+mPkgName);
        // }
        // });

        findAndHookMethod("libcore.io.Posix", lpparam.classLoader, "open", String.class, int.class, int.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        String path = (String) param.args[0];
                        int flag = (Integer) param.args[1];
                        int mode = (Integer) param.args[2];
                        Log.i("lyf", "action=" + (0 == mode ? "read" : "write") + ";path = " + path + ";flag =" + flag
                                + ";mode =" + mode + ";pkgname=" + mPkgName);
                    }
                });
    }
}
