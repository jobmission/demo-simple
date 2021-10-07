package com.example.demo.license.keymgr;

import global.namespace.truelicense.api.License;
import global.namespace.truelicense.api.LicenseValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CustomLicenseValidation implements LicenseValidation {
    private static final Logger log = LoggerFactory.getLogger(CustomLicenseValidation.class);

    public void validate(License license) {
        log.info("Validating license: {},{},{}", license.getSubject(), license.getIssued(), license.getNotAfter());
        Object extra = license.getExtra();
        // check hardware identifier，防止证书复制或者注册表导出
        if (extra == null) {
            log.info("no extra data");
        } else {
            log.info(extra.toString());
        }
    }
}
