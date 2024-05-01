package domain.Classes;

import domain.exceptions.InvalidDateException;

import java.util.Calendar;

public class Leave extends PeriodEvent {

    public Leave(String name, Calendar startdate, Calendar deadline) throws InvalidDateException {
        super(name);
        this.setStartdate(startdate);
        this.setDeadline(deadline);
    }

    @Override
    public boolean timeLockdown() {
        return true;
    }
}
