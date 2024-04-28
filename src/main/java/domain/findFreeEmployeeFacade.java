package domain;

import java.util.ArrayList;

public abstract class findFreeEmployeeFacade {


    public static ArrayList<UserCount> findFreeEmployees(ArrayList<User> userList, PeriodEvent a) {
        //assert activity!=null
        ArrayList<UserCount> returnList = new ArrayList<>();
        for (User user : userList) {
            SuccessAmount result = user.isAvailable(activity);

            if (result.isTrue()) {
                UserCount data = new DataPackage();
                data.setUser(user);
                data.setCount(result.amount());
                returnList.add(data);
            }

        }

        return UserCountSorter.sortUsers(returnList);
    }
}
