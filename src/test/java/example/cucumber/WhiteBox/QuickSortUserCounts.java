package example.cucumber.WhiteBox;

import domain.Interfaces.UserCount;

import java.util.ArrayList;
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


    //public to allow cucumber/Junit direct access
    public int partition(ArrayList<UserCount> ucArr, int begin, int end) {
        //place all elements smaller than element at end index before the element
        //And greater than the element after
        assert ucArr!=null;
        assert !ucArr.isEmpty();
        assert begin>=0;
        assert end<ucArr.size();
        assert begin<=end;

        ArrayList<UserCount> preList = new ArrayList<>(ucArr); //for purpose of assert postcondition

        int leqIndex = begin-1; //1 //correctPlace of pivot in list.
        int pivotValue=ucArr.get(end).getCount();//2 //store value to compare to

        for (int check = begin;check<end;check++) { //3
            if (ucArr.get(check).getCount()<=pivotValue) { //4
                leqIndex++; //5 //move rightmost smallest value index
                Collections.swap(ucArr,leqIndex,check);// 6 // swap checkValue into rightMost position of smaller than section
            }
        }
        Collections.swap(ucArr,leqIndex+1,end); //7 //swap pivot into
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
        return (leqIndex+1); //8 //return position of pivot
    }
}







