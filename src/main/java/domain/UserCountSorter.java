package domain;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class UserCountSorter {
    public static ArrayList<UserCount> sortUsers(ArrayList<UserCount> list ) {
        UserCount[] tempList = new UserCount[list.size()];
        for (int i = 0; i<list.size();i++) {
            tempList[i]=list.get(i);
        }
        quickSort(tempList,0,list.size()-1);
        ArrayList<UserCount> sortedList = new ArrayList<>(Arrays.asList(tempList));
        return sortedList;
    }
    private static void quickSort (UserCount[] ucArr,int begin, int end) {
        if (begin<end) {
            int pivot = partition(ucArr,begin,end); //define pivot element
            quickSort(ucArr,begin,pivot-1);
            quickSort(ucArr,pivot+1,end);
        }
    }
    private static void swap(UserCount[] ucArr,int index1, int index2) {
        UserCount tempU = ucArr[index1];
        ucArr[index1]=ucArr[index2];
        ucArr[index2]=tempU;
    }

    private static int partition(UserCount[] ucArr, int begin, int end) {
        int leqIndex = begin-1; //correctPlace of pivot in list.
        int pivotValue=ucArr[end].getCount();

        for (int check = begin;check<end;check++) {
            if (ucArr[check].getCount()<=pivotValue) {
                leqIndex++;
                swap(ucArr,leqIndex,check); // swap checkValue into rightMost position of smaller than section
            }
        }
        swap(ucArr,leqIndex+1,end); //swap pivot into
        return (leqIndex+1); //return position of pivot
    }
}
