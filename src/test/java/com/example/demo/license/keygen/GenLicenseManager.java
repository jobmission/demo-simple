/*
 * Copyright (C) 2005 - 2019 Schlichtherle IT Services.
 * All rights reserved. Use is subject to license terms.
 */
/* Generated from Velocity template at 2021年10月6日 下午10:30:58 - DO NOT EDIT! */
package com.example.demo.license.keygen;

import global.namespace.truelicense.api.License;
import global.namespace.truelicense.api.LicenseKeyGenerator;
import global.namespace.truelicense.api.LicenseManagementContext;
import global.namespace.truelicense.api.LicenseManagementException;
import global.namespace.truelicense.api.LicenseManagerParameters;
import global.namespace.truelicense.api.VendorLicenseManager;
import global.namespace.truelicense.api.passwd.PasswordProtection;
import global.namespace.truelicense.core.passwd.ObfuscatedPasswordProtection;
import global.namespace.truelicense.obfuscate.Obfuscate;
import global.namespace.truelicense.obfuscate.ObfuscatedString;
import global.namespace.truelicense.v4.V4;

import javax.security.auth.x500.X500Principal;

/**
 * The enumeration of the vendor license managers for StarGazer 2020 license keys.
 * The managers are named like each configured edition and ordered from
 * superset to subset.
 * Each manager is configured with the algorithms and parameters for generating
 * license keys for the respective edition.
 * <p>
 * This class is immutable and hence trivially thread-safe.
 *
 * @author Christian Schlichtherle
 */
public enum GenLicenseManager implements VendorLicenseManager {

    enterprise {
        @Override
        VendorLicenseManager newManager() {
            return _managementContext
                .vendor()
                .encryption()
                .protection(protection(new long[]{0xd1cfc73fe034facdL, 0x3f426a5c97b7699aL, 0xff877744cebc2e0cL}) /* => "unsafe2020" */)
                .up()
                .authentication()
                .alias(name())
                .loadFromResource(KEY_STORE_FILE)
                .storeProtection(protection(new long[]{0xfe7ac09c29586ac1L, 0x31d7e1b47242854fL, 0x8332d6154ae38b7L}) /* => "unsafe2020" */)
                .up()
                .build();
        }
    },

    standard {
        @Override
        VendorLicenseManager newManager() {
            return _managementContext
                .vendor()
                .encryption()
                .protection(protection(new long[]{0xb6682efa03e5575cL, 0xb626cc1b8a5eea06L, 0xbe1520bbf83fc5a4L}) /* => "unsafe2020" */)
                .up()
                .authentication()
                .alias(name())
                .loadFromResource(KEY_STORE_FILE)
                .storeProtection(protection(new long[]{0xbc05d90d70109e9eL, 0xe478f54b7fcd03e0L, 0x6b3579e6bc6ab21dL}) /* => "unsafe2020" */)
                .up()
                .build();
        }
    };

    @Obfuscate
    private static final String DISTINGUISHED_NAME = "CN=Company Inc.";

    @Obfuscate
    private static final String KEY_STORE_FILE = "private.ks";

    @Obfuscate
    private static final String SUBJECT = "StarGazer 2020";

    private static final LicenseManagementContext _managementContext = V4
        .builder()
        .initialization(bean -> {
            if (null == bean.getIssuer()) {
                bean.setIssuer(new X500Principal(DISTINGUISHED_NAME));
            }
        })
        .subject(SUBJECT)
        .build();

    private volatile VendorLicenseManager _manager;

    private VendorLicenseManager manager() {
        // No need to synchronize because managers are virtually stateless.
        final VendorLicenseManager m = _manager;
        return null != m ? m : (_manager = newManager());
    }

    abstract VendorLicenseManager newManager();

    private static PasswordProtection protection(long[] obfuscated) {
        return new ObfuscatedPasswordProtection(new ObfuscatedString(obfuscated));
    }

    @Override
    public LicenseManagerParameters parameters() {
        return manager().parameters();
    }

    @Override
    public LicenseKeyGenerator generateKeyFrom(License bean) throws LicenseManagementException {
        return manager().generateKeyFrom(bean);
    }
}
