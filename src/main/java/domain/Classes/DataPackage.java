package domain.Classes;

import domain.Interfaces.SuccessCount;
import domain.Interfaces.UserCount;
import domain.Interfaces.FinalUserCount;
/*@author Niklas
*This class is used in both dependency inversion, and interface segregation of the SOLID principles.
* also Single responsibilty, as it only purpose is to package the variables
 */
public class DataPackage implements SuccessCount, UserCount {
    private boolean truthValue = false;

    private User user;
    private int count=0;

    public DataPackage() {
    }
    //---- Get Methods ----------------------------------------------

    @Override
    public User getUser() {return this.user;}

    @Override
    public int getCount() {return this.count;}

    @Override
    public String getUserID() {
        return this.user.getUserId();
    }
    @Override
    public boolean isTrue() {
        return this.truthValue;
    }

    //------ Set methods -------------------------------------------------------------------
    @Override
    public void setCount(int i) {
        this.count = i;
    }

    @Override
    public void setTruthValue(boolean v) {
        this.truthValue = v;
    }

    @Override
    public void setUser(User u) {
        this.user = u;
    }




    //----- Functional ----------------------------------------------------------------------


    @Override
    public void increaseCount(int i) {
        this.count += i;
    }


}
