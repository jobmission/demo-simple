package com.example.demo.license.keymgr;

import global.namespace.truelicense.api.License;
import global.namespace.truelicense.api.LicenseValidation;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomLicenseValidation implements LicenseValidation {
    Logger logger = Logger.getLogger(CustomLicenseValidation.class.getName());

    public void validate(License bean) {
        logger.log(Level.INFO, "Validating license bean: {0}", bean);
        Object extra = bean.getExtra();
        // check hardware identifier
        if (extra == null) {
            logger.log(Level.INFO, "no extra data");
        } else {
            logger.log(Level.INFO, extra.toString());
        }
    }
}
