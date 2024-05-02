package domain.Interfaces;
/* @author Niklas
* Interface for purpose of making disallowing change of dataPackage. Used in the viewer
* */
public interface FinalUserCount {

    public int getCount();
    public String getUserID();
    public default String UserCountToString() {
        return getUserID() + ":" + getCount();
    }
}
