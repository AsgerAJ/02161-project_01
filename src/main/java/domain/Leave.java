package domain;

import java.util.Calendar;

public class Leave extends PeriodEvent {

    private Calendar startDate;
    private Calendar deadline;

    public Leave (String name, Calendar startDate, Calendar endDate) {
        super(name);
        this.startDate = startDate;
        this.deadline = endDate;
    }





    @Override
    public boolean timeLockdown() {
        return true;
    }
}
