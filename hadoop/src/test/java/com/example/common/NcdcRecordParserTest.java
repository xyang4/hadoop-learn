package com.example.common;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author xYang
 * @date 2019/9/12 0012 17:44
 * @purchase //TODO 一句话说明
 */
public class NcdcRecordParserTest {

    private NcdcRecordParser parser;

    @Before
    public void setUp() {
        parser = new NcdcRecordParser();
    }

    /**
     * test record valid
     */
    @Test
    public void parsesValidRecord() {
        parser.parse("0043011990999991950051512004+68750+023550FM-12+038299999V0203201N00671220001CN9999999N9+00221+99999999999");
        assertThat(parser.getStationId(), is("011990-99999"));
        assertThat(parser.getYear(), is("1950"));
        assertThat(parser.getAirTemperature(), is(22));
        assertThat(parser.getAirTemperatureString(), is("0022"));
        assertThat(parser.isValidTemperature(), is(true));
        assertThat(parser.getQuality(), is("1"));
    }

    /**
     * test parsesMissingTemperature
     */
    @Test
    public void parsesMissingTemperature() {
        parser.parse("0043011990999991950051512004+68750+023550FM-12+038299999V0203201N00671220001CN9999999N9+99991+99999999999");
        assertThat(parser.getAirTemperature(), is(9999));
        assertThat(parser.getAirTemperatureString(), is("9999"));
        assertThat(parser.isValidTemperature(), is(false));
    }

    /**
     * test parse
     */
    @Test(expected = NumberFormatException.class)
    public void cannotParseMalformedTemperature() {
        parser.parse("0043011990999991950051512004+68750+023550FM-12+038299999V0203201N00671220001CN9999999N9+XXXX1+99999999999");
    }
}
