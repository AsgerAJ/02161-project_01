package domain.Interfaces;
/* @author Niklas
* Interface for purpose of making disallowing change of dataPackage. Used in the viewer
* */
public interface FinalUserCount {

    int getCount();
    String getUserID();
    default String UserCountToString() {
        return getUserID() + ":" + getCount();
    }
}
