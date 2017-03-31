/**
 * EventsManager.java Created on 08.03.2003, 12:35:19 Alex Package:
 * net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net Copyright (c) 2003
 *         Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import java.util.*;


import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParentNode;

/**
 *
 */
/*$Id: EventsManager.java,v 1.11 2004/10/06 16:00:11 ivanrise Exp $*/
public class EventsManager {
	/*	public static final String NS_JNEVENTS =
		"http://www.openmechanics.org/2003/jnotes-events-file";
	 */
	public static final int NO_REPEAT = 0;
	public static final int REPEAT_DAILY = 1;
	public static final int REPEAT_WEEKLY = 2;
	public static final int REPEAT_MONTHLY = 3;
	public static final int REPEAT_YEARLY = 4;

	public static Document _doc = null;
	static Element _root = null;

	static {
		CurrentStorage.get().openEventsManager();
		if (_doc == null) {
			_root = new Element("eventslist");
			_doc = new Document(_root);
		} else
			_root = _doc.getRootElement();

	}

	public static void createSticker(String text, int prior) {
		Element el = new Element("sticker");
		el.addAttribute(new Attribute("id", Util.generateId()));
		el.addAttribute(new Attribute("priority", prior+""));
		el.appendChild(text);
		_root.appendChild(el);
	}

	public static Map<String, Element> getStickers() {
		Map<String, Element> m = new HashMap<String, Element>();
		Elements els = _root.getChildElements("sticker");
		for (int i = 0; i < els.size(); i++) {
			Element se = els.get(i);
			m.put(se.getAttribute("id").getValue(), se);
		}
		return m;
	}

	public static void removeSticker(String stickerId) {
		Elements els = _root.getChildElements("sticker");
		for (int i = 0; i < els.size(); i++) {
			Element se = els.get(i);
			if (se.getAttribute("id").getValue().equals(stickerId)) {
				_root.removeChild(se);
				break;
			}
		}
	}

	public static boolean isNREventsForDate(CalendarDate date) {
		Day d = getDay(date);
		if (d == null)
			return false;
		if (d.getElement().getChildElements("event").size() > 0)
			return true;
		return false;
	}

	public static Collection<Event> getEventsForDate(CalendarDate date) {
		Vector<Event> v = getEventsForDateHelper(date);
		return v;
	}

	private static Vector<Event> getEventsForDateHelper(CalendarDate date) {
		Vector<Event> v = new Vector<Event>();
		Day d = getDay(date);
		if (d != null) {
			Elements els = d.getElement().getChildElements("event");
			for (int i = 0; i < els.size(); i++) {
				v.add(new EventImpl(els.get(i)));
			}
		}
		Collection<Event> r = getRepeatableEventsForDate(date);
		if (r.size() > 0)
			v.addAll(r);
		Collections.sort(v);

		return v;
	}

	private static Vector<Event> getEventsForDateHelper(CalendarDate date, ArrayList<CalendarDate> dates) {
		Vector<Event> v = new Vector<Event>();
		Day d = getDay(date);
		if (d != null) {
			Elements els = d.getElement().getChildElements("event");
			for (int i = 0; i < els.size(); i++) {
				v.add(new EventImpl(els.get(i)));
				dates.add(date);
			}
		}
		Collection<Event> r = getRepeatableEventsForDate(date, dates);
		if (r.size() > 0)
			v.addAll(r);
		Collections.sort(v);

		return v;
	}

	/**
	 * Finds all events for the week of the given date
	 * @param date determines the week
	 * @param dayOfWeekStart the day that the week starts, 1=Sunday
	 * @param dates indexed list to occupy with dates corresponding to events
	 * @return A collection of the events for the week
	 */
	public static Collection<Event> getEventsForWeek(CalendarDate date, int dayOfWeekStart, ArrayList<CalendarDate> dates) {
		Vector<Event> v = new Vector<Event>();
		/* In java calendar, Sunday is considered to be day 7.
			To account for this, 7 is changed to 0 */
		int dayOfWeek = date.getDayOfWeek();
		int difference = dayOfWeekStart - dayOfWeek; // days between given date and first in week

		CalendarDate currentDate = date.dateFromNow(difference);

		for (int i = 0; i < 7; i++) {
			v.addAll(getEventsForDateHelper(currentDate, dates));
			currentDate = currentDate.dateFromNow(1);
		}

		return v;
	}

	/**
	 * Finds all events for the month of the given date
	 * @param date determines the month
	 * @param dates indexed list to occupy with dates corresponding to events
	 * @return A collection of the events for the month
	 */
	public static Collection<Event> getEventsForMonth(CalendarDate date, ArrayList<CalendarDate> dates) {
		Vector<Event> v = new Vector<Event>();

		int initYear = date.getYear();
		int initMonth = date.getMonth();

		CalendarDate currentDate = new CalendarDate(1, initMonth, initYear);

		while (currentDate.getMonth() == initMonth) {
			v.addAll(getEventsForDateHelper(currentDate, dates));
			currentDate = currentDate.dateFromNow(1);
		}

		return v;
	}

	public static Event createEvent(
			CalendarDate date,
			int hh,
			int mm,
			String text) {
		Element el = new Element("event");
		el.addAttribute(new Attribute("id", Util.generateId()));
		el.addAttribute(new Attribute("hour", String.valueOf(hh)));
		el.addAttribute(new Attribute("min", String.valueOf(mm)));
		el.appendChild(text);
		Day d = getDay(date);
		if (d == null)
			d = createDay(date);
		d.getElement().appendChild(el);
		return new EventImpl(el);
	}

	public static Event createRepeatableEvent(
			int type,
			CalendarDate startDate,
			CalendarDate endDate,
			int period,
			int hh,
			int mm,
			String text,
			boolean workDays) {
		Element el = new Element("event");
		Element rep = _root.getFirstChildElement("repeatable");
		if (rep == null) {
			rep = new Element("repeatable");
			_root.appendChild(rep);
		}
		el.addAttribute(new Attribute("repeat-type", String.valueOf(type)));
		el.addAttribute(new Attribute("id", Util.generateId()));
		el.addAttribute(new Attribute("hour", String.valueOf(hh)));
		el.addAttribute(new Attribute("min", String.valueOf(mm)));
		el.addAttribute(new Attribute("startDate", startDate.toString()));
		if (endDate != null)
			el.addAttribute(new Attribute("endDate", endDate.toString()));
		el.addAttribute(new Attribute("period", String.valueOf(period)));
		// new attribute for wrkin days - ivanrise
		el.addAttribute(new Attribute("workingDays",String.valueOf(workDays)));
		el.appendChild(text);
		rep.appendChild(el);
		return new EventImpl(el);
	}

	public static Collection<Event> getRepeatableEvents() {
		Vector<Event> v = new Vector<Event>();
		Element rep = _root.getFirstChildElement("repeatable");
		if (rep == null)
			return v;
		Elements els = rep.getChildElements("event");
		for (int i = 0; i < els.size(); i++)
			v.add(new EventImpl(els.get(i)));
		return v;
	}

	public static Collection<Event> getRepeatableEventsForDate(CalendarDate date) {
		Vector<Event> reps = (Vector<Event>) getRepeatableEvents();
		Vector<Event> v = new Vector<Event>();
		for (int i = 0; i < reps.size(); i++) {
			Event ev = (Event) reps.get(i);

			// --- ivanrise
			// ignore this event if it's a 'only working days' event and today is weekend.
			if(ev.getWorkingDays() && (date.getCalendar().get(Calendar.DAY_OF_WEEK) == 1 ||
					date.getCalendar().get(Calendar.DAY_OF_WEEK) == 7)) continue;

			if (date.inPeriod(ev.getStartDate(), ev.getEndDate())) {
				if (ev.getRepeat() == REPEAT_DAILY) {
					int n = date.getCalendar().get(Calendar.DAY_OF_YEAR);
					int ns =
							ev.getStartDate().getCalendar().get(
									Calendar.DAY_OF_YEAR);

					if ((n - ns) % ev.getPeriod() == 0)
						v.add(ev);
				} else if (ev.getRepeat() == REPEAT_WEEKLY) {
					if (date.getCalendar().get(Calendar.DAY_OF_WEEK)
							== ev.getPeriod())
						v.add(ev);
				} else if (ev.getRepeat() == REPEAT_MONTHLY) {
					if (date.getCalendar().get(Calendar.DAY_OF_MONTH)
							== ev.getPeriod())
						v.add(ev);
				} else if (ev.getRepeat() == REPEAT_YEARLY) {
					int period = ev.getPeriod();

					if ((date.getYear() % 4) == 0
							&& date.getCalendar().get(Calendar.DAY_OF_YEAR) > 60)
						period++;

					if (date.getCalendar().get(Calendar.DAY_OF_YEAR) == period)
						v.add(ev);
				}
			}
		}
		return v;
	}

	public static Collection<Event> getRepeatableEventsForDate(CalendarDate date, ArrayList<CalendarDate> dates) {
		Vector<Event> reps = (Vector<Event>) getRepeatableEvents();
		Vector<Event> v = new Vector<Event>();
		for (int i = 0; i < reps.size(); i++) {
			Event ev = (Event) reps.get(i);

			// --- ivanrise
			// ignore this event if it's a 'only working days' event and today is weekend.
			if(ev.getWorkingDays() && (date.getCalendar().get(Calendar.DAY_OF_WEEK) == 1 ||
					date.getCalendar().get(Calendar.DAY_OF_WEEK) == 7)) continue;

			if (date.inPeriod(ev.getStartDate(), ev.getEndDate())) {
				if (ev.getRepeat() == REPEAT_DAILY) {
					int n = date.getCalendar().get(Calendar.DAY_OF_YEAR);
					int ns =
							ev.getStartDate().getCalendar().get(
									Calendar.DAY_OF_YEAR);
					if ((n - ns) % ev.getPeriod() == 0) {
						v.add(ev);
						dates.add(date);
					}
				} else if (ev.getRepeat() == REPEAT_WEEKLY) {
					if (date.getCalendar().get(Calendar.DAY_OF_WEEK)
							== ev.getPeriod()) {
						v.add(ev);
						dates.add(date);
					}
				} else if (ev.getRepeat() == REPEAT_MONTHLY) {
					if (date.getCalendar().get(Calendar.DAY_OF_MONTH)
							== ev.getPeriod()) {
						v.add(ev);
						dates.add(date);
					}
				} else if (ev.getRepeat() == REPEAT_YEARLY) {
					int period = ev.getPeriod();

					if ((date.getYear() % 4) == 0
							&& date.getCalendar().get(Calendar.DAY_OF_YEAR) > 60)
						period++;

					if (date.getCalendar().get(Calendar.DAY_OF_YEAR) == period) {
						v.add(ev);
						dates.add(date);
					}
				}
			}
		}
		return v;
	}

	public static Collection<Event> getActiveEvents() {
		return getEventsForDate(CalendarDate.today());
	}


	public static Event getEvent(CalendarDate date, int hh, int mm) {
		Day d = getDay(date);
		if (d == null)
			return null;
		Elements els = d.getElement().getChildElements("event");
		for (int i = 0; i < els.size(); i++) {
			Element el = els.get(i);
			if ((new Integer(el.getAttribute("hour").getValue()).intValue()
					== hh)
					&& (new Integer(el.getAttribute("min").getValue()).intValue()
							== mm))
				return new EventImpl(el);
		}
		return null;
	}

//TODO
//not sure if this is an error?
//the code should produce of NullPointerException if ran?
//
//Commenting for further investigation
/*
	public static void removeEvent(CalendarDate date, int hh, int mm) {
		Day d = getDay(date);
		if (d == null)
			d.getElement().removeChild(getEvent(date, hh, mm).getContent());
	}
*/
	public static void removeEvent(Event ev) {
		ParentNode parent = ev.getContent().getParent();
		parent.removeChild(ev.getContent());
	}

	private static Day createDay(CalendarDate date) {
		Year y = getYear(date.getYear());
		if (y == null)
			y = createYear(date.getYear());
		Month m = y.getMonth(date.getMonth());
		if (m == null)
			m = y.createMonth(date.getMonth());
		Day d = m.getDay(date.getDay());
		if (d == null)
			d = m.createDay(date.getDay());
		return d;
	}

	private static Year createYear(int y) {
		Element el = new Element("year");
		el.addAttribute(new Attribute("year", new Integer(y).toString()));
		_root.appendChild(el);
		return new Year(el);
	}

	private static Year getYear(int y) {
		Elements yrs = _root.getChildElements("year");
		String yy = new Integer(y).toString();
		for (int i = 0; i < yrs.size(); i++)
			if (yrs.get(i).getAttribute("year").getValue().equals(yy))
				return new Year(yrs.get(i));
		return null;
	}

	private static Day getDay(CalendarDate date) {
		Year y = getYear(date.getYear());
		if (y == null)
			return null;
		Month m = y.getMonth(date.getMonth());
		if (m == null)
			return null;
		return m.getDay(date.getDay());
	}

	/**
	 * ONLY FOR TESTING, NEEDED BECAUSE OF POOR DESIGN
	 */
	public static void hardReset() {
		_root = new Element("eventslist");
		_doc = new Document(_root);
	}


	static class Year {
		Element yearElement = null;

		public Year(Element el) {
			yearElement = el;
		}

		public int getValue() {
			return new Integer(yearElement.getAttribute("year").getValue())
					.intValue();
		}

		public Month getMonth(int m) {
			Elements ms = yearElement.getChildElements("month");
			String mm = new Integer(m).toString();
			for (int i = 0; i < ms.size(); i++)
				if (ms.get(i).getAttribute("month").getValue().equals(mm))
					return new Month(ms.get(i));
			return null;
		}

		private Month createMonth(int m) {
			Element el = new Element("month");
			el.addAttribute(new Attribute("month", new Integer(m).toString()));
			yearElement.appendChild(el);
			return new Month(el);
		}

		public Vector<Month> getMonths() {
			Vector<Month> v = new Vector<Month>();
			Elements ms = yearElement.getChildElements("month");
			for (int i = 0; i < ms.size(); i++)
				v.add(new Month(ms.get(i)));
			return v;
		}

		public Element getElement() {
			return yearElement;
		}

	}

	static class Month {
		Element mElement = null;

		public Month(Element el) {
			mElement = el;
		}

		public int getValue() {
			return new Integer(mElement.getAttribute("month").getValue())
					.intValue();
		}

		public Day getDay(int d) {
			if (mElement == null)
				return null;
			Elements ds = mElement.getChildElements("day");
			String dd = new Integer(d).toString();
			for (int i = 0; i < ds.size(); i++)
				if (ds.get(i).getAttribute("day").getValue().equals(dd))
					return new Day(ds.get(i));
			return null;
		}

		private Day createDay(int d) {
			Element el = new Element("day");
			el.addAttribute(new Attribute("day", new Integer(d).toString()));
			el.addAttribute(
					new Attribute(
							"date",
							new CalendarDate(
									d,
									getValue(),
									new Integer(
											((Element) mElement.getParent())
											.getAttribute("year")
											.getValue())
									.intValue())
							.toString()));

			mElement.appendChild(el);
			return new Day(el);
		}

		public Vector<Day> getDays() {
			if (mElement == null)
				return null;
			Vector<Day> v = new Vector<Day>();
			Elements ds = mElement.getChildElements("day");
			for (int i = 0; i < ds.size(); i++)
				v.add(new Day(ds.get(i)));
			return v;
		}

		public Element getElement() {
			return mElement;
		}

	}

	static class Day {
		Element dEl = null;

		public Day(Element el) {
			dEl = el;
		}

		public int getValue() {
			return new Integer(dEl.getAttribute("day").getValue()).intValue();
		}

		public Element getElement() {
			return dEl;
		}
	}
}
