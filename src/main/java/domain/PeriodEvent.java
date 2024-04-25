package domain;

import java.util.Calendar;

public abstract class PeriodEvent {
    private Calendar deadline;
    private Calendar startDate;
    private String name;


    public PeriodEvent(String name) {
        this.name=name;
    }

    public String getName() {return this.name;}

    public boolean timeOverlap(PeriodEvent event) {
        return (event.getDeadline().after(this.startDate) && event.getStartdate().before(this.deadline))
                || this.deadline.after(event.getStartdate()) && this.startDate.before(event.getDeadline());
    }
    public Calendar getDeadline() {
        return this.deadline;
    }
    public Calendar getStartdate() {
        return this.startDate;
    }



    public boolean timeLockdown() {
        return false;
    }



}
