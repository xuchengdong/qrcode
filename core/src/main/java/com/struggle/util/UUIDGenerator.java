package com.struggle.util;

import com.struggle.constant.EscapeChar;

import java.util.UUID;

/**
 * @author xuchengdongxcd@126.com on 2016/12/3.
 */
public final class UUIDGenerator {
    private UUIDGenerator() {
    }

    public static String getId(boolean dash) {
        String uuid = UUID.randomUUID().toString();
        return dash ? uuid : uuid.replaceAll(String.valueOf(EscapeChar.DASH), "");
    }
}
