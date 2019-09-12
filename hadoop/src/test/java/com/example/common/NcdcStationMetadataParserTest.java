package com.example.common;

import com.example.CommonTest;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author xYang
 * @date 2019/9/12 0012 17:51
 * @purchase //TODO 一句话说明
 */
public class NcdcStationMetadataParserTest extends CommonTest {

    private NcdcStationMetadataParser parser;

    @Before
    public void setUp() {
        parser = new NcdcStationMetadataParser();
    }

    @Test
    public void parsesValidRecord() {
        assertThat(parser.parse("715390 99999 MOOSE JAW CS                  CN CA SA CZMJ  +50317 -105550 +05770"), is(true));
        assertThat(parser.getStationId(), is("715390-99999"));
        assertThat(parser.getStationName().trim(), is("MOOSE JAW CS"));
    }

    @Test
    public void parsesHeader() {
        assertThat(parser.parse("Integrated Surface Database Station History, November 2007"), is(false));
    }

    public void parsesBlankLine() {
        assertThat(parser.parse(""), is(false));
    }

}