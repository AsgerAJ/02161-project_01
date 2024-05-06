package domain.Classes;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateServer {// Author: Niklas Emil Lysdal

    public Calendar getDate() {// Author: Niklas Emil Lysdal
        Calendar calendar = new GregorianCalendar();
        Calendar c = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        return c;
    }
}
