package com.example.demo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.ComputerSystem;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;
import oshi.util.Constants;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Attempts to create a unique computer identifier. Note that serial numbers
 * won't work on Linux without user cooperation.
 */
public class OSHITest {

    NumberFormat numberFormat = new DecimalFormat("0.00");

    @Disabled
    @Test
    public void hardwareTest() {
        System.out.println("Here's a unique (?) id for your computer.");
        System.out.println("ComputerIdentifier--" + getComputerIdentifier());
        System.out.println("If any field is " + Constants.UNKNOWN
            + " then I couldn't find a serial number or uuid, and running as sudo might change this.");
        getMem();
        getIpAndMac();
    }

    /**
     * Generates a Computer Identifier, which may be part of a strategy to construct
     * a licence key. (The identifier may not be unique as in one case hashcode
     * could be same for multiple values, and the result may differ based on whether
     * the program is running with sudo/root permission.) The identifier string is
     * based upon the processor serial number, vendor, processor identifier, and
     * total processor count.
     *
     * @return A string containing four hyphen-delimited fields representing the
     * processor; the first 3 are 32-bit hexadecimal values and the last one
     * is an integer value.
     */
    public String getComputerIdentifier() {
        SystemInfo systemInfo = new SystemInfo();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
        CentralProcessor centralProcessor = hardwareAbstractionLayer.getProcessor();
        ComputerSystem computerSystem = hardwareAbstractionLayer.getComputerSystem();

        String vendor = operatingSystem.getManufacturer();
        String processorSerialNumber = computerSystem.getSerialNumber();
        String uuid = computerSystem.getHardwareUUID();
        String processorIdentifier = centralProcessor.getProcessorIdentifier().getIdentifier();
        int processors = centralProcessor.getLogicalProcessorCount();

        String delimiter = "-";

        return String.format("%08x", vendor.hashCode()) + delimiter
            + String.format("%08x", processorSerialNumber.hashCode()) + delimiter
            + String.format("%08x", uuid.hashCode()) + delimiter
            + String.format("%08x", processorIdentifier.hashCode()) + delimiter + processors;
    }

    public void getMem() {
        SystemInfo systemInfo = new SystemInfo();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
        GlobalMemory globalMemory = hardwareAbstractionLayer.getMemory();

        System.out.println("OS --" + operatingSystem.getVersionInfo().toString());
        long tot = globalMemory.getTotal();
        long ava = globalMemory.getAvailable();
        System.out.println("Tot--" + numberFormat.format(tot / 1024 / 1024 / 1024.0) + "G");
        System.out.println("Ava--" + numberFormat.format(ava / 1024 / 1024 / 1024.0) + "G");
        System.out.println("Use--" + numberFormat.format((tot - ava) * 1.0 / tot * 100) + "%");

    }

    public void getIpAndMac() {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
        hardwareAbstractionLayer.getNetworkIFs().forEach(networkIF -> {
            System.out.println(networkIF.getName() + "--" + networkIF.getIfOperStatus() + "------" + networkIF.getMacaddr() + "-" + String.join(",", networkIF.getIPv4addr()));
        });
    }
}
