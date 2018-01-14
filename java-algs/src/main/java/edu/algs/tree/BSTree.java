package edu.algs.tree;

// Binary Search Tree
public class BSTree<T extends Comparable<T>> extends BinTree<T> {

    @Override
    void postInsert(Node<T> last) {
        Node<T> n = last.parent;
        while (n != null) {
            int ds = n.ds();
            if (Math.abs(ds) > 1) {
                if (ds < 0) n = moveH(n); else n = moveL(n);
            }
            n = n.parent;
        }
    }

    /*                  to=C
        at=A      ->      / \
         /  \         at=A  cr
        /    \          / \
       B   to=C        B   cl
      / \    / \      / \
    bl  bh cl  ch   bl  bh
     */
    Node<T> moveH(Node<T> at) {
        Node<T> to = at.hi;
        to.parent = at.parent;
        at.parent = to;
        at.hi = to.lo;
        if (to.lo != null) to.lo.parent = at;
        to.lo = at;
        at.touch();
        to.touch();
        put(to, to.parent);
        return to;
    }

    /*                     B=to
        at=A      ->      / \
         /  \           bl   A=at
        /    \              / \
    to=B      C           bh   C
      / \    / \              / \
    bl  bh cl  ch           cl  ch
     */
    Node<T> moveL(Node<T> at) {
        Node<T> to = at.lo;
        to.parent = at.parent;
        at.parent = to;
        at.lo = to.hi;
        if (to.hi != null) to.hi.parent = at;
        to.hi = at;
        at.touch();
        to.touch();
        put(to, to.parent);
        return to;
    }

    /*
        at=A     =>      C=to
         /  \          /  \
       al    \        /    \
           m=B    at=A    m=B
            / \     / \    / \
        to=C   bh  al cl  ch bh
          / \
        cl  ch
     */
    Node<T> moveHL(Node<T> at) {
        Node<T> m = at.hi;
        Node<T> to = at.hi.lo;
        to.parent = at.parent;

        at.hi = to.lo;
        if (to.lo != null) to.lo.parent = at;

        m.lo = to.hi;
        if (to.hi != null) to.hi.parent = m;

        to.lo = at;
        to.hi = m;
        m.parent = to;
        at.parent = to;

        at.touch();
        m.touch();
        put(to, to.parent);
        return to;
    }

    /*
        at=A     =>      C=to
         /  \          /  \
        /   ah        /    \
     m=B           m=B      A=at
      / \           / \    / \
     bl  C=to      bl cl  ch ah
        / \
       cl ch
     */
    Node<T> moveLH(Node<T> at) {
        Node<T> m = at.lo;
        Node<T> to = m.hi;
        to.parent = at.parent;

        m.hi = to.lo;
        if (m.hi != null) m.hi.parent = m;

        at.lo = to.hi;
        if (at.lo != null) at.lo.parent = at;

        to.lo = m;
        to.hi = at;
        m.parent = to;
        at.parent = to;

        at.touch();
        m.touch();
        put(to, to.parent);
        return to;
    }
}
