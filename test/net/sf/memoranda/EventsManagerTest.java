package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;

import java.util.ArrayList;
import java.util.Vector;

import static org.junit.Assert.*;

/**
 * Created by Andrew on 2/22/2017.
 */
public class EventsManagerTest {
    ArrayList<Event> events;
    ArrayList<CalendarDate> dummyDates;
    Vector<Event> firstWeekEvents, firstMonthEvents, secondMonthEvents;

    @org.junit.Before
    public void setUp() throws Exception {
        EventsManager.hardReset();
        dummyDates = new ArrayList<>();
        CalendarDate currentDate = new CalendarDate(1, 0, 2017);
        firstWeekEvents = new Vector<>();
        firstMonthEvents = new Vector<>();
        secondMonthEvents = new Vector<>();
        events = new ArrayList<Event>();
        for (int i = 0; i < 60; i++) {
            events.add(EventsManager.createEvent(currentDate,14, 30,"Test Event i=" + i));
            if (i < 7) firstWeekEvents.add(events.get(i));
            if (currentDate.getMonth() == 0) firstMonthEvents.add(events.get(i));
            if (currentDate.getMonth() == 1) secondMonthEvents.add(events.get(i));
            currentDate = currentDate.dateFromNow(1);
        }

    }

    @org.junit.After
    public void tearDown() throws Exception {
        for(int i = 0; i < events.size(); i++) {
            EventsManager.removeEvent(events.get(i));
        }
    }

    @org.junit.Test
    public void getEventsForWeek() throws Exception {
        Vector<Event> first, second, third;
        // test starting front, middle, and end of week
        first = (Vector<Event>)EventsManager.getEventsForWeek(new CalendarDate(1, 0, 2017),1, dummyDates);
        second = (Vector<Event>)EventsManager.getEventsForWeek(new CalendarDate(3, 0, 2017),1, dummyDates);
        third = (Vector<Event>)EventsManager.getEventsForWeek(new CalendarDate(7, 0, 2017),1, dummyDates);

        for (int i = 0; i < 7; i++) {
            assertTrue(first.get(i).equals(firstWeekEvents.get(i)));
            assertTrue(second.get(i).equals(firstWeekEvents.get(i)));
            assertTrue(third.get(i).equals(firstWeekEvents.get(i)));
        }
    }

    @org.junit.Test
    public void getEventsForMonth() throws Exception {
        Vector<Event> first, second;
        first = (Vector<Event>) EventsManager.getEventsForMonth(new CalendarDate(1, 0, 2017), dummyDates);
        second = (Vector<Event>) EventsManager.getEventsForMonth(new CalendarDate(1, 1, 2017), dummyDates);

        for (int i = 0; i < 59; i++) {
            if (i < 31) assertTrue(first.get(i).equals(firstMonthEvents.get(i)));
            else assertTrue(second.get(i-31).equals(secondMonthEvents.get(i-31)));
        }
    }

}