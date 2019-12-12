package commands;

import java.util.GregorianCalendar;

public class TimeEvent
{
    private GregorianCalendar dateTime; //time and date of event
    private String description;

    public TimeEvent(String description, GregorianCalendar dateTime)
    {
        this.description = description;
        this.dateTime = dateTime;
    }
}