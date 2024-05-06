package domain.Classes;

import domain.Interfaces.SuccessCount;
import domain.Interfaces.UserCount;
/*@author Niklas

*This class is used in both dependency inversion, and interface segregation of the SOLID principles.
* also Single responsibilty, as it only purpose is to package the variables
 */
public class DataPackage implements SuccessCount, UserCount {// Author: Niklas Emil Lysdal
    private boolean truthValue = false;

    private User user;
    private int count=0;

    public DataPackage() {
    }
    //---- Get Methods ----------------------------------------------

    @Override
    public User getUser() {return this.user;}// Author: Niklas Emil Lysdal

    @Override
    public int getCount() {return this.count;}// Author: Niklas Emil Lysdal

    @Override
    public String getUserID() {
        return this.user.getUserId();
    }// Author: Niklas Emil Lysdal
    @Override
    public boolean isTrue() {
        return this.truthValue;
    }// Author: Niklas Emil Lysdal

    //------ Set methods -------------------------------------------------------------------
    @Override
    public void setCount(int i) {
        this.count = i;
    }// Author: Niklas Emil Lysdal

    @Override
    public void setTruthValue(boolean v) {
        this.truthValue = v;
    }// Author: Niklas Emil Lysdal

    @Override
    public void setUser(User u) {
        this.user = u;
    }// Author: Niklas Emil Lysdal


    //----- Functional ----------------------------------------------------------------------
    @Override
    public void increaseCount(int i) {
        this.count += i;
    }// Author: Niklas Emil Lysdal
}
