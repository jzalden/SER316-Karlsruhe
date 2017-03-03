/**
 * EventsTable.java
 * Created on 09.03.2003, 9:52:02 Alex
 * Package: net.sf.memoranda.ui
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda.ui;

import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import net.sf.memoranda.Event;
import net.sf.memoranda.EventsManager;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.date.DateListener;
import net.sf.memoranda.util.Configuration;
import net.sf.memoranda.util.Local;
/**
 *
 */
/*$Id: EventsTable.java,v 1.6 2004/10/11 08:48:20 alexeya Exp $*/
public class EventsTable extends JTable {

    public static final int EVENT = 100;
    public static final int EVENT_ID = 101;

    public static final int DAILY = 1;
    public static final int WEEKLY = 2;
    public static final int MONTHLY = 3;

    private int period;

    private CalendarDate currentDate;
    private ArrayList<CalendarDate> dates;

    Vector events = new Vector();
    /**
     * Constructor for EventsTable.
     */
    public EventsTable() {
        super();
        period = 1;
        setModel(new EventsTableModel());
        initTable(CurrentDate.get());
        this.setShowGrid(false);
        CurrentDate.addDateListener(new DateListener() {
            public void dateChange(CalendarDate d) {
                currentDate = d;
                initTable(d);
            }
        });
    }

    public void initTable(CalendarDate d) {
        switch (period) {
            case DAILY:
                dates = null;
                events = (Vector)EventsManager.getEventsForDate(d);
                break;
            case WEEKLY:
                String firstDay = (String) Configuration.get("FIRST_DAY_OF_WEEK");
                int day = 1;
                if (firstDay != null && firstDay.equalsIgnoreCase("mon")) {
                    day = 2;
                }
                dates = new ArrayList<>();
                events = (Vector)EventsManager.getEventsForWeek(d, day, dates);
                break;
            case MONTHLY:
                dates = new ArrayList<>();
                events = (Vector)EventsManager.getEventsForMonth(d, dates);
                break;
            default:
                events = (Vector)EventsManager.getEventsForDate(d);
                break;
        }
        getColumnModel().getColumn(0).setPreferredWidth(60);
        getColumnModel().getColumn(0).setMaxWidth(60);
        getColumnModel().getColumn(1).setPreferredWidth(60);
        getColumnModel().getColumn(1).setMaxWidth(60);
	    clearSelection();
        updateUI();
    }

    public void refresh() {
        initTable(CurrentDate.get());
    }

     public TableCellRenderer getCellRenderer(int row, int column) {
        return new javax.swing.table.DefaultTableCellRenderer() {

            public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {
                Component comp;
                comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                Event ev = (Event)getModel().getValueAt(row, EVENT);
                comp.setForeground(java.awt.Color.gray);
                if (period == DAILY) {
                    if (ev.isRepeatable())
                        comp.setFont(comp.getFont().deriveFont(Font.ITALIC));
                    if (CurrentDate.get().after(CalendarDate.today())) {
                        comp.setForeground(java.awt.Color.black);
                    } else if (CurrentDate.get().equals(CalendarDate.today())) {
                        if ((dates == null || CurrentDate.get().equals(dates.get(row))) && ev.getTime().after(new Date())) {
                            comp.setForeground(java.awt.Color.black);
                            //comp.setFont(new java.awt.Font("Dialog", 1, 12));
                            comp.setFont(comp.getFont().deriveFont(Font.BOLD));
                        }
                    }
                }
                else {
                    if (ev.isRepeatable()) {
                        comp.setFont(comp.getFont().deriveFont(Font.ITALIC));
                    }
                    if (!CalendarDate.today().after(dates.get(row))) {
                        // events after make black
                        comp.setForeground(java.awt.Color.black);
                    }
                    if (dates.get(row).equals(CurrentDate.get())) {
                        comp.setForeground(java.awt.Color.black);
                        comp.setFont(comp.getFont().deriveFont(Font.BOLD));
                    }
                }
                return comp;
            }
        };

    }
    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        if (period != this.period) {
            this.period = period;
            if (currentDate != null) {
                initTable(currentDate);
            }
            else {
                refresh();
            }
        }

    }

    class EventsTableModel extends AbstractTableModel {

        String[] columnNames = {
            "Date",
            Local.getString("Time"),
                Local.getString("Text")
        };

        EventsTableModel() {
            super();
        }

        public int getColumnCount() {
            return 3;
        }

        public int getRowCount() {
			int i;
			try {
				i = events.size();
			}
			catch(NullPointerException e) {
				i = 1;
			}
			return i;
        }

        public Object getValueAt(int row, int col) {
           Event ev = (Event)events.get(row);
           CalendarDate date;
           if (dates == null) {
               date = currentDate == null ? CurrentDate.get() : currentDate;
           }
           else {
               date = dates.get(row);
           }

           if (col == 0) {
               return date.getShortDateString();
           }
           else if (col == 1)
                //return ev.getHour()+":"+ev.getMinute();
                return ev.getTimeString();
           else if (col == 2)
                return ev.getText();
           else if (col == EVENT_ID)
                return ev.getId();
           else return ev;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }


    }
}
