public class AVLTree<T extends Comparable <T>> {

    /**
     * Significant differences from BST, including addition of parent node, due to difficulties with implementation.
     * Using balance factor = height(rightSubtree) - height(leftSubtree), meaning balance factor values are reversed
     * when compared to the notes.
     * @param <T>
     */
    public static class Node<T extends Comparable<T>>
    {
        T data;
        Node parent;
        Node leftChild;
        Node rightChild;
        int lheight = -1;
        int rheight = -1;

        public Node(T data)
        {
            this.data = data;
        }
    }

    private Node<T> root;
    private Node<T> parentOfRoot;
    private StringBuilder s;
    //used to hold node dispaced during deletion of node with single right child (see successor(Node d))
    private Node<T> temp;

    public AVLTree()
    {
        root = null;
    }

    /**
     * AVL insert, including rebalancing.
     * @param data to be inserted
     */
    public void insert(T data)
    {
        bstInsert(data);
        updateHeights(root);
        Node a = nodeA(root);
        rotate(a);
        //for printing purposes:
        updateHeights(root);
    }

    /**
     * BST implementation of insert.
     * @param data to be inserted
     */
    private void bstInsert(T data)
    {
        Node newNode = new Node(data);

        if (root == null)
        {
            newNode.parent = parentOfRoot;
            root = newNode;
            return;
        }

        Node current = root;
        while ((current.data).compareTo(data) != 0)
        {
            while ((current.data).compareTo(data) < 0)   //current.data < data
            {
                if (current.rightChild == null)
                {
                    current.rightChild = newNode;
                    newNode.parent = current;
                    return;
                }
                current = current.rightChild;
            }

            while ((current.data).compareTo(data) > 0)   //current.data > data
            {
                if (current.leftChild == null)
                {
                    current.leftChild = newNode;
                    newNode.parent = current;
                    return;
                }
                current = current.leftChild;
            }
        }
        //does not insert if data is repeated
    }

    /**
     * AVL delete, including rebalancing.
     * @param data to be deleted.
     */
    public void delete(T data)
    {
        deleteTraversal(data,root);
        if (root != null)
        {
            updateHeights(root);
            Node a = nodeA(root);
            rotate(a);
            //for printing purposes:
            updateHeights(root);
        }
    }

    /**
     * Traverses the tree from start until it finds data or exits the tree.
     * If data is located, deletes the Node containing the data.
     * @param data to be removed
     * @param start root of subtree to search
     */
    private void deleteTraversal(T data, Node start)
    {
        Node lchild = start.leftChild;
        Node rchild = start.rightChild;

        if (lchild != null)
        {
            if ((start.data).compareTo(data) > 0)   //start.data > data
            {
                deleteTraversal(data, lchild);
                return;
            }
        }
        if (rchild != null)
        {
            if ((start.data).compareTo(data) < 0)   //start.data < data
            {
                deleteTraversal(data, rchild);
                return;
            }
        }
        if ((start.data).compareTo(data) == 0)
        {
            deleteNode(start);
        }
        //else the node was not found in the tree and nothing is deleted
    }

    /**
     * Deletes node d without rebalancing. Children of d are moved appropriately.
     * @param d node to be deleted
     */
    private void deleteNode(Node d)
    {
        Node dl = d.leftChild;
        Node dr = d.rightChild;
        Node a = d.parent;

        if (a == null)             //d is the root
        {
            if (dl == null)
            {
                root = dr;
                if (dr != null) {dr.parent = null;}
                d.rightChild = null;
                return;
            }
            if (dr == null)
            {
                root = dl;
                dl.parent = null;
                d.leftChild = null;
                return;
            }
            //dl and dr are not null
            //this case does not work
            {
                temp = null;
                Node successor = successor(d);
                root = successor;
                successor.parent = null;
                dl.parent = successor;
                dr.parent = successor;
                successor.leftChild = dl;
                successor.rightChild = dr;
                d.parent = null;
                d.leftChild = null;
                d.rightChild = null;
                if (temp != null)
                {
                    insert(temp.data);
                    temp = null;
                }
            }
        }

        if (dl == null)
        {
            if (a.leftChild == d)
            {
                a.leftChild = dr;          //if dr is also null, this simply sets parent.leftChild to null
                if (dr != null) {dr.parent = a;}
                d.parent = null;
                d.rightChild = null;
                return;
            }
            if (a.rightChild == d)
            {
                a.rightChild = dr;
                if (dr != null) {dr.parent = a;}
                d.parent = null;
                d.rightChild = null;
                return;
            }
        }
        if (dr == null)                         //here, dl is not null, as otherwise method would have returned by now
        {
            if (a.leftChild == d)
            {
                a.leftChild = dl;
                dl.parent = a;
                d.parent = null;
                d.leftChild = null;
                return;
            }
            if (a.rightChild == d)
            {
                a.rightChild = dl;
                dl.parent = a;
                d.parent = null;
                d.leftChild = null;
                return;
            }
        }
        //dl and dr are not null
        //this case does not work
        {
            updateHeights(d);
            //case height(wl) and height(wr) == 0
            //intended to fix the looping issue with successor, but this if is not entered at appropriate time
            if (d.lheight == 0 && d.rheight == 0)
            {
                if (a.leftChild == d)
                {
                    a.leftChild = dl;
                }
                if (a.rightChild == d)
                {
                    a.rightChild = dl;
                }

                dl.rightChild = dr;
                dl.leftChild = null;
                dl.parent = a;
            }

            Node successor = successor(d);

            if (a.leftChild == d)
            {
                a.leftChild = successor;
            }
            if (a.rightChild == d)
            {
                a.rightChild = successor;
            }

            temp = null;
            successor.parent = a;
            dl.parent = successor;
            dr.parent = successor;
            successor.leftChild = dl;
            successor.rightChild = dr;
            d.parent = null;
            d.leftChild = null;
            d.rightChild = null;
            if (temp != null)
            {
                insert(temp.data);
                temp = null;
            }
        }
    }

    /**
     * Finds and returns the successor of the node to be deleted, d.
     * @param d
     * @return successor of d
     */
    private Node successor(Node d)
    {
        Node wl = d.rightChild;
        Node working = d;

        while (wl != null)
        {
            working = wl;
            wl = working.leftChild;
        }
        if (working.rightChild != null)
        {
            //because the tree is avl, rightChild should be a leaf node
            temp = working.rightChild;
            temp.parent = null;
        }
        //wl (left child of working) is null, right child of working is stored in temp to be inserted if it exists
        working.rightChild = null;

        if (working.parent.leftChild == working) {working.parent.leftChild = null;}
        else if (working.parent.rightChild == working) {working.parent.rightChild = null;}

        return working;
    }

    /**
     * Finds and returns node 'a' to be rotated at.
     * Returns null if no rotations are needed.
     * @param working is the node to start search at
     */
    private Node nodeA(Node working) {

        /*
        if (working == null) 
        {
            return null;
        }
        */

        int bf = working.rheight - working.lheight;
        Node a;

        //right subtree is greater by more than one
        if (bf > 1)                                                                //(*)
        {
            //working.rightChild cannot be null, as it has height 1 or greater (from (*))
            //therefore a will be set to null iff working.rightChild has bf {-1,0,1}
            //when a is null (not a valid a), working (last acceptable a) is returned
            a = nodeA(working.rightChild);
            if (a == null)
            {
                return working;
            }
            return a;
        }

        //left subtree is greater by more than one
        if (bf < -1)                                                              //(*)
        {
            //working.leftChild cannot be null, as it has height 1 or greater (from (*))
            //therefore a will be set to null iff working.leftChild has bf {-1,0,1}
            //when a is null (not a valid a), working (last acceptable a) is returned
            a = nodeA(working.leftChild);
            if (a == null)
            {
                return working;
            }
            return a;
        }
        //if bf is {-1,0,1}, return null (no node a because no rotations are needed)
        return null;
    }

    /**
     * Finds and performs applicable rotation at 'a'.
     * If a is null, no rotation is performed.
     * @param a node to rotate ('a' node in notes)
     */
    private void rotate(Node a)
    {
        if (a == null) {return;}

        int bf = a.rheight - a.lheight;

        //right subtree is greater by more than one
        if (bf > 1)
        {
            int rbf = a.rightChild.rheight - a.rightChild.lheight;
            if (rbf >= 0)
            {
                System.out.println(treeDetails());
                rrRotation(a);
                System.out.println(treeDetails());
                return;
            }
            {
                System.out.println(treeDetails());
                rlRotation(a);
                System.out.println(treeDetails());
                return;
            }
        }

        //left subtree is greater by more than one
        if (bf < -1)
        {
            int lbf = a.leftChild.rheight - a.leftChild.lheight;
            if (lbf <= 0)
            {
                System.out.println(treeDetails());
                llRotation(a);
                System.out.println(treeDetails());
                return;
            }
            {
                System.out.println(treeDetails());
                lrRotation(a);
                System.out.println(treeDetails());
                return;
            }
        }
        //if bf {-1,0,1} do nothing
    }

    private void llRotation(Node a)
    {
        Node b = a.leftChild;
        Node br = b.rightChild;          //bl may be null depending on tree structure

        fixParent(a,b);
        b.rightChild = a;
        a.parent = b;
        a.leftChild = br;
        if (br != null) {br.parent = a;}
    }

    private void lrRotation(Node a)
    {
        Node b = a.leftChild;
        Node c = b.rightChild;
        Node cl = c.leftChild;          //cl may be null depending on tree structure
        Node cr = c.rightChild;         //cr may be null depending on tree structure

        fixParent(a,c);
        c.leftChild = b;
        b.parent = c;
        b.rightChild = cl;
        c.rightChild = a;
        a.parent = c;
        a.leftChild = cr;
        if (cl != null) {cl.parent = b;}
        if (cr != null) {cr.parent = a;}
    }

    private void rrRotation(Node a)
    {
        Node b = a.rightChild;
        Node bl = b.leftChild;          //bl may be null depending on tree structure

        fixParent(a,b);
        b.leftChild = a;
        a.parent = b;
        a.rightChild = bl;
        if (bl != null) {bl.parent = a;}
    }

    private void rlRotation(Node a)
    {
        Node b = a.rightChild;
        Node c = b.leftChild;
        Node cl = c.leftChild;          //cl may be null depending on tree structure
        Node cr = c.rightChild;         //cr may be null depending on tree structure

        fixParent(a,c);
        c.leftChild = a;
        a.parent = c;
        a.rightChild = cl;
        c.rightChild = b;
        b.parent = c;
        b.leftChild = cr;
        if (cl != null) {cl.parent = a;}
        if (cr != null) {cr.parent = b;}
    }

    /**
     * Sets a.parent.child to b.
     * This must be called before other moves in a rotation.
     * @param a original child of parent(a)
     * @param b new child of parent(a)
     */
    private void fixParent(Node a, Node b)
    {
        Node parent = a.parent;
        if (parent == null)
        {
            root = b;
            b.parent = null;
            return;
        }
        if (parent.leftChild == a)
        {
            b.parent = parent;
            parent.leftChild = b;
            return;
        }
        if (parent.rightChild == a)
        {
            b.parent = parent;
            parent.rightChild = b;
        }
    }

    /**
     * toString as specified in the assignment.
     * @return String containing the data of nodes, printed in pre order traversal.
     */
    public String toString()
    {
        if (root == null)
        {
            return "";
        }

        s = new StringBuilder(100);
        s.append(root.data);
        s.append(" ");
        toString(root);

        return s.toString();
    }

    /**
     * Helper class for toString().
     * @param start root of the subtree being focused on.
     */
    private void toString(Node start)
    {
        Node lchild = start.leftChild;
        Node rchild = start.rightChild;
        if (lchild != null)
        {
            s.append(lchild.data);
            s.append(" ");
            toString(lchild);
        }
        if (rchild != null)
        {
            s.append(rchild.data);
            s.append(" ");
            toString(rchild);
        }
    }

    /**
     * Used for testing, returns a string containing details for the whole tree.
     * @return String containing the following:
     *          [Root Node Data]  ([height of left subtree],[height of right subtree]) [l [left child]][r [right child]]
     */
    public String treeDetails()
    {
        if (root == null)
        {
            return "";
        }

        s = new StringBuilder(100);
        s.append("      " + root.data);
        s.append("  (" + root.lheight +","+root.rheight+")");
        if (root.leftChild != null)
            s.append(" [l " + root.leftChild.data +"]" );
        if (root.rightChild != null)
            s.append("[r " +root.rightChild.data+"]");
        s.append("\n");
        details(root);

        return s.toString();
    }

    /**
     * Helper class for treeDetails.
     * @param start root of the subtree being focused on.
     */
    private void details(Node start)
    {
        Node lchild = start.leftChild;
        Node rchild = start.rightChild;
        if (lchild != null)
        {
            s.append("      " + lchild.data);
            s.append("  (" + lchild.lheight +","+lchild.rheight+")");
            if (lchild.leftChild != null)
                s.append(" [l " + lchild.leftChild.data +"]" );
            if (lchild.rightChild != null)
                s.append("[r " +lchild.rightChild.data+"]");
            s.append("\n");
            details(lchild);
        }
        if (rchild != null)
        {
            s.append("      " + rchild.data);
            s.append("  (" + rchild.lheight +","+rchild.rheight+")");
            if (rchild.leftChild != null)
                s.append(" [l " + rchild.leftChild.data +"]" );
            if (rchild.rightChild != null)
                s.append("[r " +rchild.rightChild.data+"]");
            s.append("\n");
            details(rchild);
        }
    }

    /**
     * Updates left and right height of start and all its descendants.
     * @param start
     */
    private void updateHeights(Node start)
    {
        resetLeftHeight(start);
        resetRightHeight(start);
    }

    /**
     * Helper class for updateHeights.
     * @param start
     * @return leftHeight of start
     */
    private int resetLeftHeight(Node start)
    {
        Node lchild = start.leftChild;
        if (lchild == null)
        {
            start.lheight = -1;
            return -1;
        }
        int height = Math.max(resetLeftHeight(lchild), resetRightHeight(lchild)) + 1;
        start.lheight = height;
        return height;
    }

    /**
     * Helper class for updateHeights.
     * @param start
     * @return rightHeight of start
     */
    private int resetRightHeight(Node start)
    {
        Node rchild = start.rightChild;
        if (rchild == null)
        {
            start.rheight = -1;
            return -1;
        }
        int height = Math.max(resetLeftHeight(rchild), resetRightHeight(rchild)) + 1;
        start.rheight = height;
        return height;
    }
}
