package com.test.agilemusic;

import com.test.agilemusic.utilities.UtilityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class UtilityManagerTest {

    @Test
    public void check_yearLength() throws Exception {

        UtilityManager utilityManager = new UtilityManager();
        int expectedLength = 4;
        assert (utilityManager.getYearFromDate("2007-12-18T08:00:00Z").length() == expectedLength);

    }

    @Test
    public void check_year() throws Exception {

        UtilityManager utilityManager = new UtilityManager();
        String dateTime = "2007-12-18T08:00:00Z";
        assertEquals(dateTime.substring(0, 4), utilityManager.getYearFromDate(dateTime));

    }

    @Test
    public void check_empty_date() throws Exception {

        UtilityManager utilityManager = new UtilityManager();
//        String dateTime = "";
        String dateTime = null;
        assertEquals("", utilityManager.getYearFromDate(dateTime));

    }


}
