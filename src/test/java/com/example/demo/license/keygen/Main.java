/*
 * Copyright (C) 2005 - 2019 Schlichtherle IT Services.
 * All rights reserved. Use is subject to license terms.
 */
/* Generated from Velocity template at 2021年10月6日 下午10:30:58 - DO NOT EDIT! */
package com.example.demo.license.keygen;

import global.namespace.fun.io.api.*;
import global.namespace.truelicense.api.*;
import global.namespace.truelicense.api.codec.Codec;

import java.util.*;

import static global.namespace.fun.io.bios.BIOS.*;

import static java.lang.System.*;

public enum Main {

    USAGE {
        @Override
        void run(final Deque<String> params) { throw new IllegalArgumentException(); }
    },

    HELP {
        @Override
        void run(final Deque<String> params) { out.printf(Main.valueOf(params.pop().toUpperCase(Locale.ROOT)).help()); }
    },

    VERSION {
        @Override
        void run(final Deque<String> params) { out.printf(message("version"), Main.class.getSimpleName()); }
    },

    GENERATE {
        @Override
        void run(final Deque<String> params) throws Exception {
            new Object() {

                final Map<CreateOption, String> options = Main.parse(params, CreateOption.class);
                VendorLicenseManager manager;
                LicenseManagerParameters parameters;

                {
                    final String licenseKeyPath = params.poll();
                    if (null != licenseKeyPath) {
                        options.put(CreateOption.KEY, licenseKeyPath);
                    }
                    Main.parse(params, CreateOption.class, options);
                    if (!params.isEmpty()) {
                        throw new IllegalArgumentException();
                    }
                }

                void run() throws Exception {
                    final License input = createOrDecodeInputLicense();
                    final License output = manager()
                            .generateKeyFrom(input)
                            .saveTo(store())
                            .license();
                    if (verbose()) {
                        err.println(output);
                    }
                    maybeEncodeOutputLicense(output);
                }

                License createOrDecodeInputLicense() throws Exception {
                    final String path = options.get(CreateOption.INPUT);
                    return null == path
                            ? parameters().licenseFactory().license()
                            : (License) codec()
                                .decoder("-".equals(path) ? stdin() : file(path))
                                .decode(License.class);
                }

                void maybeEncodeOutputLicense(final License license) throws Exception {
                    final String path = options.get(CreateOption.OUTPUT);
                    if (null != path) {
                        codec() .encoder("-".equals(path) ? stdout() : file(path))
                                .encode(license);
                    }
                }

                Codec codec() {
                    return parameters().codec();
                }

                LicenseManagerParameters parameters() {
                    final LicenseManagerParameters p = parameters;
                    return null != p ? p : (parameters = manager().parameters());
                }

                VendorLicenseManager manager() {
                    final VendorLicenseManager m = manager;
                    return null != m ? m : (manager = GenLicenseManager.valueOf(editionName()));
                }

                Store store() {
                    final String path = options.get(CreateOption.KEY);
                    return null == path ? memory() : file(path); // send to /dev/null if no path
                }

                String editionName() {
                    final String name = options.get(CreateOption.EDITION);
                    if (null != name) {
                        return name;
                    }
                    final GenLicenseManager[] managers = GenLicenseManager.values();
                    if (1 != managers.length) {
                        throw new IllegalArgumentException();
                    }
                    return managers[0].name();
                }

                boolean verbose() {
                    final String value = options.get(CreateOption.VERBOSE);
                    return null != value ? Boolean.parseBoolean(value) : false;
                }
            }.run();
        }
    };

    /**
     * Runs this command.
     * Implementations are free to modify the given deque.
     *
     * @param params the command parameters.
     */
    abstract void run(Deque<String> params) throws Exception;

    public static void main(String... args) { exit(processAndHandleExceptions(args)); }

    @SuppressWarnings("CallToThreadDumpStack")
    private static int processAndHandleExceptions(final String... args) {
        int status;
        try {
            process(args);
            status = 0;
        } catch (final IllegalArgumentException ex) {
            printUsage();
            status = 1;
        } catch (final NoSuchElementException ex) {
            printUsage();
            status = 1;
        } catch (final Throwable ex) {
            ex.printStackTrace();
            status = 2;
        }
        return status;
    }

    public static void process(final String... args) throws Exception {
        final Deque<String> params = new LinkedList<String>(Arrays.asList(args));
        final String command = upperCase(params.pop());
        valueOf(command).run(params);
    }

    private static String upperCase(String s) { return s.toUpperCase(Locale.ENGLISH); }

    private static void printUsage() {
        final StringBuilder builder = new StringBuilder(25 * 80);
        for (final Main main : values()) builder.append(main.usage());
        err.println(builder.toString());
    }

    private String usage() { return String.format(message("usage"), Main.class.getSimpleName()); }

    private String help() { return message("help"); }

    String message(String key) {
        return ResourceBundle
                .getBundle(Main.class.getName())
                .getString(name() + "." + key);
    }

    /**
     * Parses the given command parameters for options of the given class with
     * a string parameter.
     * As a side effect, any found options are popped off the parameter stack.
     *
     * @param  <T> the type of the enum class for the options.
     * @param  params the command parameters.
     * @param  type the enum class for the options.
     * @return an enum map with the options and parameters found.
     */
    private static <T extends Enum<T>> EnumMap<T, String> parse(
            final Deque<String> params,
            final Class<T> type) {
        return parse(params, type, new EnumMap<T, String>(type));
    }

    private static <T extends Enum<T>, M extends Map<T, String>> M parse(
            final Deque<String> params,
            final Class<T> type,
            final M options) {
        for (   String param;
                null != (param = params.peek()) && '-' == param.charAt(0);
                ) {
            params.pop(); // consume
            param = upperCase(param.substring(1));
            options.put(valueOf(type, param), params.pop());
        }
        return options;
    }

    private enum CreateOption { KEY, INPUT, OUTPUT, EDITION, VERBOSE }
}
