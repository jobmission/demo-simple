package com.example.demo;

import com.example.demo.license.keygen.GenLicenseManager;
import com.example.demo.license.keymgr.MgrLicenseManager;
import global.namespace.fun.io.api.Sink;
import global.namespace.fun.io.api.Source;
import global.namespace.truelicense.api.ConsumerLicenseManager;
import global.namespace.truelicense.api.License;
import global.namespace.truelicense.api.LicenseManagementException;
import global.namespace.truelicense.api.VendorLicenseManager;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static global.namespace.fun.io.bios.BIOS.file;

public class LicenseTest {
    @Test
    @Disabled
    void generateLicense() throws LicenseManagementException {
        VendorLicenseManager manager = GenLicenseManager.enterprise;
        License input = manager.context().licenseFactory().license();
        Sink sink = file("F:\\license\\license.lic");
        License output = manager.generateKeyFrom(input).saveTo(sink).license();

    }

    @Test
    @Disabled
    void installLicense() throws LicenseManagementException {
//        Source source = file("F:\\license\\public.ks");
        Source source = file("F:\\license\\license.lic");
        ConsumerLicenseManager manager = MgrLicenseManager.get();
        manager.install(source);
        License bean = manager.load();
        System.out.println("===" + bean.getSubject());

    }

    @Test
    @Disabled
    void verifyLicense() throws LicenseManagementException {
        ConsumerLicenseManager manager = MgrLicenseManager.get();
        manager.verify();
    }
}
