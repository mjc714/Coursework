/*
 CSE 17
 Matthew Chin
 mjc714
 Program #5 DEADLINE: April 23, 2015
 Program Description: Digital Music Library with Advanced Sorting
 */

import java.util.*;

/**ComparatorSorts class, contains static generic methods to merge sort
  * and quick sort ArrayLists using comparators*/
public class ComparatorSorts<T> implements Comparator<T>, java.io.Serializable{
  
  /**override compare method*/
  @Override
  public int compare(T o1, T o2){
    int dec = 0;
    if(compare(o1, o2) == -1){
      dec = -1;
    }//if
    else if((compare(o1,o2)) == 0){
      dec = 0;
    }//else if
    else if((compare(o1,o2)) == 1){
      dec = 1;
    }//else if
    return dec;
  }//end compare
  
  /**mergeSort() method*/
  public static <T> void mergeSort(ArrayList<T> list, Comparator<T> sortOrder){
    if(list.size() > 1){
      //merse sort first half
      ArrayList<T> firstHalf = new ArrayList<T>(list.size()/2);
      //copy first half
      for(int i = 0; i < (list.size()/2); i++){
        firstHalf.add(list.get(i));
      }//end for
      mergeSort(firstHalf, sortOrder);
      
      //merge sort second half
      ArrayList<T> secondHalf = new ArrayList<T>(list.size() - (list.size()/2));
      //copy second half
      for(int j = list.size()/2; j < list.size(); j++){
        secondHalf.add(list.get(j));
      }//end for
      mergeSort(secondHalf, sortOrder);
      
      //merge two halves
      ArrayList<T> temp = merge(firstHalf, secondHalf, sortOrder);
      for(int k = 0; k < temp.size(); k++){
        list.set(k, temp.get(k));
      }
    }//end if
  }//end mergeSort()
  
  /**merge, helper method for mergeSort*/
  private static <T> ArrayList<T> merge(ArrayList<T> list1, ArrayList<T> list2, Comparator<T> sortOrder){
    ArrayList<T> temp = new ArrayList<T>(list1.size() + list2.size());
    int current1 = 0; //left index
    int current2 = 0; //right index
    int current3 = 0; //index in temp
    while(current1 < list1.size() && current2 < list2.size()){
      if((sortOrder.compare(list1.get(current1), list2.get(current2))) < 0){
        temp.add(list1.get(current1++));  //*when I tried to use set() for these operations, the index was always stuck at 0 despite incrementing??
      }//end if
      else{
        temp.add(list2.get(current2++));  //*
      }//end else
    }//end while
    
    while(current1 < list1.size()){
      temp.add(list1.get(current1++));  //*
    }//end while
    
    while(current2 < list2.size()){
      temp.add(list2.get(current2++));  //*
    }//end while
    return temp;
  }//end merge() method
  
  /**quickSort() method*/
  public static <T> void quickSort(ArrayList<T> list, Comparator<T> sortOrder){
    quickSort(list, 0, list.size()-1, sortOrder);
  }//end quickSort() method
  
  /**quickSort() helper method*/
  private static <T> void quickSort(ArrayList<T> list, int first, int last, Comparator<T> sortOrder){
    if(last > first){
      int pivotIndex = partition(list, first, last, sortOrder);
      quickSort(list, first, pivotIndex -1, sortOrder);
      quickSort(list, pivotIndex +1, last, sortOrder);
    }//end if
  }//end quickSort helper method
  
  /**Partition() method, choose a pivot and partition the list into two sets, 
    * the elements less than the pivot and the elements greater than the pivot. Place
    * the pivot between these two sets and return its index. */
  private static <T> int partition(ArrayList<T> list, int first, int last, Comparator<T> sortOrder){
    T pivot = list.get(first);
    int low = first +1;
    int high = last;
    
    while(high > low){
      while(low <= high && (sortOrder.compare(pivot, list.get(low)) == 1)){
        low++;
      }//end while
      while(low <= high && (sortOrder.compare(pivot, list.get(high)) == -1)){
        high--;
      }//end while
      if(high > low){
        T temp = list.get(high);
        list.set(high, list.get(low));
        list.set(low, temp);
      }//end if
    }//end while
    while(high > first && (sortOrder.compare(list.get(high), pivot) == 1)){
      high--;
    }//end while
    if(sortOrder.compare(pivot, list.get(high)) == 1){
      list.set(first, list.get(high));
      list.set(high, pivot);
      return high;
    }//end if
    else{
      return first;
    }//end else
  }//end partition method
}//end ComparatorSorts class
