package org.hvdw.sofiaservernokill;

import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import static org.hvdw.sofiaservernokill.SettingsFragment.PREF_NO_KILL;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class SofiaServerNoKill implements IXposedHookLoadPackage, IXposedHookZygoteInit {
    public static XSharedPreferences pref;
    public static final String TAG = "SofiaServerNoKill";
    private boolean noKillEnabled;

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.sofiaservernokill");
        sharedPreferences.makeWorldReadable();
        noKillEnabled = sharedPreferences.getBoolean(PREF_NO_KILL, true);
    }

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        //XposedBridge.log("" + noKillEnabled);
        //  XposedBridge.log("Loaded app: " + lpparam.packageName);

        if (lpparam.packageName.equals("com.syu.ms")) {

            if(noKillEnabled == true) {
                findAndHookMethod("app.ToolkitApp", lpparam.classLoader, "killAppWhenSleep", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                        XposedBridge.log("Loaded app: " + lpparam.packageName + " skipping method killAppWhenSleep");
                        Log.d(TAG, "skipping method killAppWhenSleep");
                        param.setResult(null);
                    }
                });

            }
            return;

        }
        }
    }

