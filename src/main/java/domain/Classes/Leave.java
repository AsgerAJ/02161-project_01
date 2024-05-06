package domain.Classes;

import domain.exceptions.InvalidDateException;

import java.util.Calendar;

/*
 @Author: Nikolaj Vorndran Thygesen
 */
public class Leave extends PeriodEvent {

    public Leave(String name, Calendar startdate, Calendar deadline) throws InvalidDateException { // Author: Nikolaj Vorndran Thygesen
        super(name);
        this.setStartdate(startdate);
        this.setDeadline(deadline);
    }

    @Override
    public boolean timeLockdown() { // Author: Niklas Emil Lysdal
        return true;
    }
}
