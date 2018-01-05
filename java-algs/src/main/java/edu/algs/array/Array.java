package edu.algs.array;

public class Array {
    
    /*
    Swapping values of array elements with given indices without extra temporary variable
    
    0. a=X, b=Y
          a = |---|
          b = |-------------|
    1. a = b-a
          a =     |---------|
          b = |-------------|
    2. b = b-a
          a =     |---------|
          b = |---|
    3. a = b+a
          a = |-------------|
          b = |---|
    a=Y, b=X
    
     */
    public static void swap(int a[], int i, int j) {
        a[i] = a[j] - a[i];
        a[j] = a[j] - a[i];
        a[i] = a[j] + a[i];
    }

}
