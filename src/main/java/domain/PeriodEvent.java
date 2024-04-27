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
        if (event.getStartdate()!=null && event.getStartdate()!=null && this.deadline !=null && this.startDate!=null) {
            return ((event.getDeadline().after(this.startDate) && event.getStartdate().before(this.deadline))
                    || (this.deadline.after(event.getStartdate()) && this.startDate.before(event.getDeadline())));
        } else {
            return false;
        }
        //TODO: consider actions when date values arent set



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
