package domain.Classes;

import domain.exceptions.InvalidDateException;

import java.util.Calendar;
/*@author Niklas */
public abstract class PeriodEvent {
    private Calendar deadline;
    private Calendar startdate;
    private final String name;

    public PeriodEvent(String name) {
        this.name = name;
    }

    //----- Get Methods -------------------------------------------------

    public String getName() {return this.name;} // Author Niklas Emil Lysdal

    public Calendar getDeadline() {return this.deadline;}// Author Niklas Emil Lysdal

    public Calendar getStartdate() {return this.startdate;}// Author Niklas Emil Lysdal
    public boolean timeLockdown() {
        return false;
    }// Author Niklas Emil Lysdal

    //------ Sets ----------------------------------------------------------------------------


    public void setDeadline(Calendar date) throws InvalidDateException {// Author Niklas Emil Lysdal

        if (this.startdate == null || date.after(this.startdate)) {
            this.deadline = date;
        } else {
            throw new InvalidDateException("deadline before start date");
        }
    }
    public void setStartdate(Calendar date) throws InvalidDateException {// Author Niklas Emil Lysdal
        if (this.deadline == null || date.before(this.deadline)) {
            this.startdate = date;
        } else {
            throw new InvalidDateException("Startdate after deadline");
        }
    }

    //-----------------Checks --------------------------------------------------------

    public boolean timeOverlap(PeriodEvent event) {// Author Niklas Emil Lysdal
        return ((event.getDeadline().after(startdate) && event.getStartdate().before(deadline)))
                ||(deadline.after(event.getStartdate()) && startdate.before(event.getDeadline()));
    }


}
