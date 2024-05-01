package example.cucumber.WhiteBox;

import domain.CountSorting;
import domain.UserCount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class QuickSortUserCounts {


    public QuickSortUserCounts() {}
    public  ArrayList<UserCount> sortUsers(ArrayList<UserCount> list ) throws Exception {
        quickSort(list,0,list.size()-1);
        return list;
    }
    private  void quickSort (ArrayList<UserCount> ucArr,int begin, int end) throws Exception {
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

    //public to allow cucumber/Junit direct access
    public int partition(ArrayList<UserCount> ucArr, int begin, int end) {
        //place all elements smaller than element at end index before the element
        //And greater than the element after
        assert ucArr!=null;
        assert !ucArr.isEmpty();
        assert begin>=0;
        assert end<ucArr.size();
        assert begin<=end;
        ArrayList<UserCount> preList = new ArrayList<>(ucArr);

        int leqIndex = begin-1; //correctPlace of pivot in list.
        int pivotValue=ucArr.get(end).getCount(); //store value to compare to

        for (int check = begin;check<end;check++) {
            if (ucArr.get(check).getCount()<=pivotValue) {
                leqIndex++; //move rightmost smallest value index
                Collections.swap(ucArr,leqIndex,check); // swap checkValue into rightMost position of smaller than section
            }
        }
        Collections.swap(ucArr,leqIndex+1,end); //swap pivot into
        assert preList.containsAll(ucArr) && ucArr.containsAll(preList); //equal in this case
        //assert all lesser than items are before, and bigger than are after
        for (int index = begin; index<end;index++) {
            if (index<leqIndex+1) {
                assert ucArr.get(index).getCount()<=ucArr.get(leqIndex+1).getCount();
            } else if (index>leqIndex+1) {
                assert ucArr.get(index).getCount()>=ucArr.get(leqIndex+1).getCount();

            } else {
                continue; // equal index, no need to compare
            }
        }
        return (leqIndex+1); //return position of pivot
    }
}
