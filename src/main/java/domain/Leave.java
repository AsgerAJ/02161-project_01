package domain;

import java.util.Calendar;

public class Leave extends PeriodEvent {


    public Leave (String name) {
        super(name);
    }





    @Override
    public boolean timeLockdown() {
        return true;
    }
}
