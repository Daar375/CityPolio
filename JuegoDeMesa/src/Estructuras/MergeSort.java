package Estructuras;
 
import java.util.ArrayList;

import Control.Jugador;
 
public class MergeSort {
    private ArrayList<Jugador> inputArray;
     
    public ArrayList<Jugador> getSortedArray() {
        return inputArray;
    }
 
    public MergeSort(ArrayList<Jugador> inputArray){
        this.inputArray = inputArray;
    }
     
    public void sortGivenArray(){       
        divide(0, this.inputArray.size()-1);
    }
     
    public void divide(int startIndex,int endIndex){
         
        //Saca el indice del medio, y manda las 2 mitades a divir y ordena el array
        if(startIndex<endIndex && (endIndex-startIndex)>=1){
            int mid = (endIndex + startIndex)/2;
            divide(startIndex, mid);
            divide(mid+1, endIndex);        
             
            //merging Sorted array produce above into one sorted array
            merger(startIndex,mid,endIndex);            
        } 
    }
      
     
    public void merger(int startIndex,int midIndex,int endIndex){
         
  
        ArrayList<Jugador> mergedSortedArray = new ArrayList<Jugador>();
         
        int leftIndex = startIndex;
        int rightIndex = midIndex+1;
         
        while(leftIndex<=midIndex && rightIndex<=endIndex){
            if(inputArray.get(leftIndex).getPointsLife()>=inputArray.get(rightIndex).getPointsLife()){
                mergedSortedArray.add(inputArray.get(leftIndex));
                leftIndex++;
            }else{
                mergedSortedArray.add(inputArray.get(rightIndex));
                rightIndex++;
            }
        }       
         
        //Either of below while loop will execute
        while(leftIndex<=midIndex){
            mergedSortedArray.add(inputArray.get(leftIndex));
            leftIndex++;
        }
         
        while(rightIndex<=endIndex){
            mergedSortedArray.add(inputArray.get(rightIndex));
            rightIndex++;
        }
         
        int i = 0;
        int j = startIndex;
        //Se reacomoda el array
        while(i<mergedSortedArray.size()){
            inputArray.set(j, mergedSortedArray.get(i++));
            j++;
        }
    }
}
    