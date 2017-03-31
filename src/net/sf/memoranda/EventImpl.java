/**
 * EventImpl.java
 * Created on 08.03.2003, 13:20:13 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Local;
import nu.xom.Attribute;
import nu.xom.Element;

/**
 *
 */
/*$Id: EventImpl.java,v 1.9 2004/10/06 16:00:11 ivanrise Exp $*/
public class EventImpl implements Event, Comparable<Event> {

	private Element _elem = null;

	/**
	 * Constructor for EventImpl.
	 */
	public EventImpl(Element elem) {
		_elem = elem;
	}


	/**
	 * @see net.sf.memoranda.Event#getHour()
	 */
	public int getHour() {
		return new Integer(_elem.getAttribute("hour").getValue()).intValue();
	}

	/**
	 * @see net.sf.memoranda.Event#getMinute()
	 */
	public int getMinute() {
		return new Integer(_elem.getAttribute("min").getValue()).intValue();
	}

	public String getTimeString() {
		return Local.getTimeString(getHour(), getMinute());
	}


	/**
	 * @see net.sf.memoranda.Event#getText()
	 */
	public String getText() {
		return _elem.getValue();
	}

	/**
	 * @see net.sf.memoranda.Event#getContent()
	 */
	public Element getContent() {
		return _elem;
	}
	/**
	 * @see net.sf.memoranda.Event#isRepeatable()
	 */
	public boolean isRepeatable() {
		return getStartDate() != null;
	}
	/**
	 * @see net.sf.memoranda.Event#getStartDate()
	 */
	public CalendarDate getStartDate() {
		Attribute a = _elem.getAttribute("startDate");
		if (a != null) return new CalendarDate(a.getValue());
		return null;
	}
	/**
	 * @see net.sf.memoranda.Event#getEndDate()
	 */
	public CalendarDate getEndDate() {
		Attribute a = _elem.getAttribute("endDate");
		if (a != null) return new CalendarDate(a.getValue());
		return null;
	}
	/**
	 * @see net.sf.memoranda.Event#getPeriod()
	 */
	public int getPeriod() {
		Attribute a = _elem.getAttribute("period");
		if (a != null) return new Integer(a.getValue()).intValue();
		return 0;
	}
	/**
	 * @see net.sf.memoranda.Event#getId()
	 */
	public String getId() {
		Attribute a = _elem.getAttribute("id");
		if (a != null) return a.getValue();
		return null;
	}
	/**
	 * @see net.sf.memoranda.Event#getRepeat()
	 */
	public int getRepeat() {
		Attribute a = _elem.getAttribute("repeat-type");
		if (a != null) return new Integer(a.getValue()).intValue();
		return 0;
	}
	/**
	 * @see net.sf.memoranda.Event#getTime()
	 */
	public Date getTime() {
		Date d = new Date();
		Calendar calendar = new GregorianCalendar(Local.getCurrentLocale());
		calendar.setTime(d);
		calendar.set(Calendar.HOUR_OF_DAY, getHour());
		calendar.set(Calendar.MINUTE, getMinute());
		calendar.set(Calendar.SECOND, 0);
		d = calendar.getTime();
		return d;
	}

	/**
	 * @see net.sf.memoranda.Event#getWorkinDays()
	 */
	public boolean getWorkingDays() {
		Attribute a = _elem.getAttribute("workingDays");
		if (a != null && a.getValue().equals("true")) return true;
		return false;
	}

	public int compareTo(Event e) {
		return (getHour() * 60 + getMinute()) - (e.getHour() * 60 + e.getMinute());
	}

	public boolean equals(Event e) {
		return getId().equals(e.getId());
	}

}
