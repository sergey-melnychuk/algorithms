package edu.algs.custom;

/*

Set of initially 0..n-1 integers with following features:
- remove(x) removes element from a set
- next(x) finds successor y of x that is minimum item y in the set that y >= x

next(x) returns -1 if no successor is available in the set

 */
public interface SuccessorSet {
    void remove(int x);
    int next(int x);
}

