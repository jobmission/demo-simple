/*
 * Copyright (C) 2005 - 2019 Schlichtherle IT Services.
 * All rights reserved. Use is subject to license terms.
 */
/* Generated from Velocity template at 2021年10月6日 下午10:31:11 - DO NOT EDIT! */
package com.example.demo.license.keymgr;

import global.namespace.fun.io.api.Source;
import global.namespace.truelicense.api.ConsumerLicenseManager;
import global.namespace.truelicense.api.License;
import global.namespace.truelicense.api.LicenseManagementContext;
import global.namespace.truelicense.api.LicenseManagementException;
import global.namespace.truelicense.api.LicenseManagerParameters;
import global.namespace.truelicense.api.passwd.PasswordProtection;
import global.namespace.truelicense.core.passwd.ObfuscatedPasswordProtection;
import global.namespace.truelicense.obfuscate.Obfuscate;
import global.namespace.truelicense.obfuscate.ObfuscatedString;
import global.namespace.truelicense.v4.V4;

public enum MgrLicenseManager implements ConsumerLicenseManager {

    enterprise {
        @Override
        ConsumerLicenseManager newManager() {
            return _managementContext
                .consumer()
                .encryption()
                .protection(protection(new long[]{0x9f8993c693ab32feL, 0x21368772cd544664L, 0xaefcb95fbf82b82L}) /* => "unsafe2020" */)
                .up()
                .authentication()
                .alias(name())
                .loadFromResource(KEY_STORE_FILE)
                .storeProtection(protection(new long[]{0x418926a8d8387b2fL, 0x9a4b47c1ed797e33L, 0x1fd0cf9c3579b3a4L}) /* => "unsafe2020" */)
                .up()
                .storeInUserPreferences(Main.class) // must be a non-obfuscated class!
                .build();
        }
    },

    standard {
        @Override
        ConsumerLicenseManager newManager() {
            return _managementContext
                .consumer()
                .authentication()
                .alias(name())
                .loadFromResource(KEY_STORE_FILE)
                .storeProtection(protection(new long[]{0xf9fd80752c6bad72L, 0xb94796ca5508204fL, 0xf65a35b0355a25c7L}) /* => "unsafe2020" */)
                .up()
                .parent(MgrLicenseManager.enterprise)
                .storeInUserPreferences(Main.class) // must be a non-obfuscated class!
                .build();
        }
    },

    ftp {
        @Override
        ConsumerLicenseManager newManager() {
            return _managementContext.consumer()
                .ftpDays(30)
                .authentication()
                .alias(name())
                .loadFromResource(KEY_STORE_FILE)
                .storeProtection(protection(new long[]{0x187320091f6a9a4L, 0x6d81484bceef2203L, 0xd03fe9d7cb0cad99L}) /* => "unsafe2020" */)
                .up()
                .parent(MgrLicenseManager.standard)
                //.storeInUserPreferences(sun.security.provider.Sun.class)
                .build();
        }
    };

    @Obfuscate
    private static final String KEY_STORE_FILE = "public.ks";

    @Obfuscate
    private static final String SUBJECT = "StarGazer 2021";

    private static final LicenseManagementContext _managementContext = V4
        .builder()
        .subject(SUBJECT)
        .validation(new CustomLicenseValidation())
        .build();

    private volatile ConsumerLicenseManager _manager;

    /**
     * Returns the first license manager in the configured
     * chain-of-responsibility, which is {@link #ftp}.
     * <p>
     * By default, this manager should be used to
     * {@linkplain #install(Source source) install}, {@linkplain #load() load} and
     * {@linkplain #uninstall() uninstall} license keys.
     */
    public static MgrLicenseManager get() {
        return ftp;
    }

    private ConsumerLicenseManager manager() {
        // No need to synchronize because managers are virtually stateless.
        final ConsumerLicenseManager m = _manager;
        return null != m ? m : (_manager = newManager());
    }

    abstract ConsumerLicenseManager newManager();

    private static PasswordProtection protection(long[] obfuscated) {
        return new ObfuscatedPasswordProtection(new ObfuscatedString(obfuscated));
    }

    @Override
    public LicenseManagerParameters parameters() {
        return manager().parameters();
    }

    @Override
    public void install(Source source) throws LicenseManagementException {
        manager().install(source);
    }

    @Override
    public License load() throws LicenseManagementException {
        return manager().load();
    }

    @Override
    public void verify() throws LicenseManagementException {
        manager().verify();
    }

    @Override
    public void uninstall() throws LicenseManagementException {
        manager().uninstall();
    }
}
