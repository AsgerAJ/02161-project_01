package domain;

import java.util.Calendar;

public abstract class PeriodEvent {
    private Calendar deadline;
    private Calendar startdate;
    private String name;

    public PeriodEvent(String name) {
        this.name = name;
    }

    public void setDeadline(Calendar date) throws InvalidDateException {

        if (this.startdate == null || date.after(this.startdate)) {
            this.deadline = date;
        } else {
            throw new InvalidDateException("deadline before start date");
        }
    }
    public void setStartdate(Calendar date) throws InvalidDateException {
        if (this.deadline == null || date.before(this.deadline)) {
            this.startdate = date;
        } else {
            throw new InvalidDateException("Startdate after deadline");
        }
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
