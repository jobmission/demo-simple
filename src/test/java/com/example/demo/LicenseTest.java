package com.example.demo;

import com.example.demo.license.keygen.GenLicenseManager;
import com.example.demo.license.keymgr.MgrLicenseManager;
import global.namespace.fun.io.api.Sink;
import global.namespace.fun.io.api.Source;
import global.namespace.truelicense.api.ConsumerLicenseManager;
import global.namespace.truelicense.api.License;
import global.namespace.truelicense.api.LicenseManagementException;
import global.namespace.truelicense.api.VendorLicenseManager;
import global.namespace.truelicense.obfuscate.ObfuscatedString;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static global.namespace.fun.io.bios.BIOS.file;

/**
 * https://truelicense.namespace.global/guide/example-configurations.html#free-trial-period
 * https://github.com/christian-schlichtherle/truelicense
 */
public class LicenseTest {

    @Test
    @Disabled
    public void obfuscatePassword() {
        String result = ObfuscatedString.obfuscate("unsafe2020");
        System.out.format("password:  %s\n", result);

        String source = new global.namespace.truelicense.obfuscate.ObfuscatedString(new long[]{0x5c6af2e0b9351022L, 0x273ff88a2941f7a6L, 0xea1941323787a0bL}).toString();
        System.out.println("source:" + source);
    }

    @Test
    @Disabled
    void generateLicense() throws LicenseManagementException {
        VendorLicenseManager manager = GenLicenseManager.enterprise;
        License input = manager.context().licenseFactory().license();
        // Free Trial Period
        input.setNotAfter(Date.from(LocalDateTime.now().plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant()));
        Sink sink = file("F:\\license\\license.lic");
        Map<String, String> extraData = new HashMap<>();
        extraData.put("mac", "1:2:3:4:5:6");
        extraData.put("ip", "192.168.1.1");
        input.setExtra(extraData);
        input.setInfo("enterprise");
        License output = manager.generateKeyFrom(input).saveTo(sink).license();
    }

    @Test
    @Disabled
    void installLicense() throws LicenseManagementException {
//        Source source = file("F:\\license\\public.ks");
        Source source = file("F:\\license\\license.lic");
        ConsumerLicenseManager manager = MgrLicenseManager.get();
        /**
         * 打开regedit工具;
         * Under HKEY_CURRENT_USER/Software/JavaSoft/Prefs/{$the.package.of.your.install().class}.
         * 如，HKEY_CURRENT_USER\Software\JavaSoft\Prefs\com\example\demo\license\keymgr
         */
        manager.install(source);
        License bean = manager.load();
        System.out.println("===" + bean.getSubject());
    }

    @Test
    @Disabled
    void loadLicense() throws LicenseManagementException {
        ConsumerLicenseManager manager = MgrLicenseManager.get();
        License bean = manager.load();
        System.out.println("===" + bean.getSubject());
    }

    @Test
    @Disabled
    void verifyLicense() throws LicenseManagementException {
        MgrLicenseManager.get().verify();
    }

    @Test
    @Disabled
    void uninstallLicense() throws LicenseManagementException {
        MgrLicenseManager.get().uninstall();
    }
}
