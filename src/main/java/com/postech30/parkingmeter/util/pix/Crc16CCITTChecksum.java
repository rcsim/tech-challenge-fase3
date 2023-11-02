/*
 * Imported from https://github.com/mattnicee7/PixQRCodeGenerator
 */
package com.postech30.parkingmeter.util.pix;

import jakarta.validation.constraints.NotNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Crc16CCITTChecksum {
    public String crc(@NotNull String string) {
        final int polynomial = 0x1021;
        final byte[] bytes = string.getBytes();

        int crc = 0xFFFF;

        for (byte b : bytes) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7-i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit)
                    crc ^= polynomial;
            }
        }

        crc &= 0xffff;

        return Integer.toHexString(crc);
    }

}
