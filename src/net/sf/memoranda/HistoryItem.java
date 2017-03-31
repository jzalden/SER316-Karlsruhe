/**
 * HistoryItem.java
 * Created on 07.03.2003, 18:31:39 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;

/**
 *
 */
/*$Id: HistoryItem.java,v 1.4 2004/10/06 19:15:43 ivanrise Exp $*/
public class HistoryItem {

	private CalendarDate _date;
	private Project _project;
	/**
	 * Constructor for HistoryItem.
	 */
	public HistoryItem(CalendarDate date, Project project) {
		_date = date;
		_project = project;
	}

	public HistoryItem(Note note) {
		_date = note.getDate();
		_project = note.getProject();
	}

	public CalendarDate getDate() {
		return _date;
	}

	public Project getProject() {
		return _project;
	}

	public boolean equals(HistoryItem i) {
		return (i.getDate()).equals(_date) &&
				(i.getProject().getID()).equals(_project.getID());
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof HistoryItem )
			return this.equals((HistoryItem) o);
		else
			return false;
	}

	@Override
	public int hashCode() {
		int hashCode = 1;
		hashCode = 31 * hashCode + (_date == null ? 0 : _date.hashCode());
		hashCode = 31 * hashCode + (_project == null ? 0 : _project.hashCode());
		return hashCode;
	}
}
