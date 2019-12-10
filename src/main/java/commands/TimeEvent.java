package commands;

import java.util.GregorianCalendar;

public class TimeEvent
{
    //если научимся добавлять такие штуки в расписание, то будет легко работать с рецептом
    private GregorianCalendar dateTime; //time and date of event
    private String description;

    public TimeEvent(String description, GregorianCalendar dateTime)
    {
        this.description = description;
        this.dateTime = dateTime;
    }
}