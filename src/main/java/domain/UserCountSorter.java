package domain;

import java.util.ArrayList;

public abstract class UserCountSorter {
    public static ArrayList<UserCount> sortUsers(ArrayList<UserCount> list ) {


        if (list.size() == 1 || list.isEmpty()) {
            return list;
        } else {
            ArrayList<UserCount> lower = new ArrayList<>();
            ArrayList<UserCount> higher = new ArrayList<>();
            ArrayList<UserCount> equal = new ArrayList<>();
            int pivot = list.get(0).getCount();
            for (UserCount uc : list) {
                if (uc.getCount() == pivot) {
                    equal.add(uc);
                } else if (uc.getCount() < pivot) {
                    lower.add(uc);
                } else {
                    higher.add(uc);
                }
            }
            lower = sortUsers(lower);
            higher = sortUsers(higher);
            ArrayList<UserCount> sortedList = new ArrayList<>();
            sortedList.addAll(lower);
            sortedList.addAll(equal);
            sortedList.addAll(higher);
            return sortedList;
        }
    }

}
