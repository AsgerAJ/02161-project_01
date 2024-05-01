package domain;

import java.util.Calendar;

public abstract class PeriodEvent {
    private Calendar deadline;
    private Calendar startdate;
    private String name;

    public PeriodEvent(String name) {
        this.name = name;
    }

    public void setStartdate(Calendar cal) {
        this.startdate = cal;
    }

    public void setDeadline(Calendar cal) {
        this.deadline = cal;
    }

    public String getName() {return this.name;}

    public Calendar getDeadline() {return this.deadline;}

    public Calendar getStartdate() {return this.startdate;}

    public boolean timeOverlap(PeriodEvent event) {
        return ((event.getDeadline().after(startdate) && event.getStartdate().before(deadline))
            || (deadline.after(event.getStartdate()) && startdate.before(event.getDeadline())));
    }

    public boolean timeLockdown() {
        return false;
    }
}
