package domain;

import java.util.Calendar;

public class Leave extends PeriodEvent {

    public Leave(String name, Calendar startdate, Calendar deadline) {
        super(name);
        this.setStartdate(startdate);
        this.setDeadline(deadline);
    }

    @Override
    public boolean timeLockdown() {
        return true;
    }
}
