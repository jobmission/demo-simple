package com.example.demo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.prefs.Preferences;

/**
 * 在Windows平台中，对应注册表，Preferences.userRoot()用户参数项在注册表中的根节点是 HKEY_CURRENT_USER/Software/JavaSoft/Prefs
 * 在Windows平台中，对应注册表，Preferences.systemRoot()用户参数项在注册表中的根节点是 HKEY_LOCAL_MACHINE/SOFTWARE/JavaSoft/Prefs
 * 在Linux平台中，对应注册表，Preferences.userRoot()用户参数项在${user.home}/.java/.userPrefs
 * 在Linux平台中，对应注册表，Preferences.systemRoot()用户参数项在/etc/.java
 * 我们指定的节点路径是位于这些根节点之下
 * 如，HKEY_CURRENT_USER\Software\JavaSoft\Prefs\com\example\demo\license\keymgr
 */
public class PreferencesTest {
    public static final String KEY_FIRST_RUN = "is_first_run";

    @Test
    @Disabled
    public void testPreferences() {
        String absolutePath = "/com/example/demo";
        Preferences preferences = Preferences.userRoot().node(absolutePath);
        Preferences preferences2 = Preferences.userNodeForPackage(PreferencesTest.class);
        preferences2.putInt("test_key", 123);
        //判断程序是否是第一次运行
        boolean isFirstRun = preferences.getBoolean(KEY_FIRST_RUN, true);
        if (isFirstRun) {
            System.out.println("这是第一次运行。");
            preferences.putBoolean(KEY_FIRST_RUN, false);
        } else {
            System.out.println("这已经不是第一次运行了！");
        }
    }
}
