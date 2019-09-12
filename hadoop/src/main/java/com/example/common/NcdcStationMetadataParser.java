package com.example.common;

import lombok.Getter;
import org.apache.hadoop.io.Text;

/**
 * @author xYang
 * @date 2019/9/12 0012 17:43
 * @purchase //TODO 一句话说明
 */
@Getter
public class NcdcStationMetadataParser {

    private String stationId;
    private String stationName;

    public boolean parse(String record) {
        if (record.length() < 42) { // header
            return false;
        }
        String usaf = record.substring(0, 6);
        String wban = record.substring(7, 12);
        stationId = usaf + "-" + wban;
        stationName = record.substring(13, 42);
        try {
            Integer.parseInt(usaf); // USAF identifiers are numeric
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean parse(Text record) {
        return parse(record.toString());
    }
}

