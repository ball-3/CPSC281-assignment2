public class AVLTree<T extends Comparable <T>> {

    public static class Node<T extends Comparable<T>>
    {
        T data;
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
    private StringBuilder s;

    public AVLTree()
    {
        root = null;
    }

    //simplify
    public void insert(T data)
    {
        Node newNode = new Node(data);

        if (root == null)
        {
            root = newNode;
            return;
        }

        Node parent = root;
        Node toRotate = null;

        while ((parent.data).compareTo(data) != 0)
        {
            while ((parent.data).compareTo(data) < 0)   //parent.data < data
            {
                parent.rheight++;
                if (parent.rheight - parent.lheight > 1 || parent.rheight - parent.lheight < -1)
                {
                    toRotate = parent;
                }
                if (parent.rightChild == null)
                {
                    parent.rightChild = newNode;
                    insertionRotation(toRotate);
                    return;
                }
                parent = parent.rightChild;
            }

            while ((parent.data).compareTo(data) > 0)   //parent.data > data
            {
                parent.lheight++;
                if (parent.leftChild == null)
                {
                    parent.leftChild = newNode;
                    return;
                }
                parent = parent.leftChild;
            }
        }
        //if data is repeated, decrement heights:
        while ((parent.data).compareTo(data) != 0)
        {
            while ((parent.data).compareTo(data) < 0)   //parent.data < data
            {
                parent.rheight--;
                if (parent.rightChild == null)
                {
                    return;
                }
                parent = parent.rightChild;
            }

            while ((parent.data).compareTo(data) > 0)   //parent.data > data
            {
                parent.lheight--;
                if (parent.leftChild == null)
                {
                    return;
                }
                parent = parent.leftChild;
            }
        }
    }

    private void insertionRotation(Node toRotate)
    {
        if (toRotate == null)
        {
            return;
        }

        int bf = toRotate.rheight - toRotate.lheight;

        if (bf > 1)
        {
            int rbf = toRotate.rightChild.rheight - toRotate.rightChild.lheight;
            if (rbf >= 0)
            {
                rrRotation(toRotate.rightChild, toRotate);
                return;
            }
            {
                rlRotation(toRotate.rightChild, toRotate);
                return;
            }
        }

        if (bf < 1)
        {
            int lbf = toRotate.leftChild.rheight - toRotate.leftChild.lheight;
            if (lbf <= 0)
            {
                llRotation(toRotate.leftChild, toRotate);
                return;
            }
            {
                lrRotation(toRotate.leftChild, toRotate);
                //return;
            }
        }
    }

    private void llRotation(Node base, Node parent)
    {
        Node b = base.leftChild;
        Node br = b.rightChild;
        Node ar = base.rightChild;

        b.rightChild = base;
        base.leftChild = br;
        base.rightChild = ar;

        rotationFixParents(b,base,parent);
    }

    private void lrRotation(Node base, Node parent)
    {
        Node b = base.leftChild;
        Node ar = base.rightChild;
        Node c = b.rightChild;
        Node cl = c.leftChild;
        Node cr = c.rightChild;

        c.leftChild = b;
        c.rightChild = base;
        b.rightChild = cl;
        base.leftChild = cr;
        base.rightChild = ar;

        rotationFixParents(c,base,parent);
    }

    private void rrRotation(Node base, Node parent)
    {
        Node b = base.rightChild;
        Node br = b.rightChild;
        Node bl = base.leftChild;

        b.leftChild = base;
        b.leftChild = br;
        base.rightChild = bl;

        rotationFixParents(b,base,parent);
    }

    private void rlRotation(Node base, Node parent)
    {
        Node b = base.rightChild;
        Node c = b.leftChild;
        Node br = b.rightChild;
        Node cl = c.leftChild;
        Node cr = c.rightChild;

        c.leftChild = base;
        c.rightChild = b;
        b.rightChild = br;
        b.leftChild = cr;
        base.rightChild = cl;

        rotationFixParents(c,base,parent);
    }

    private void insertSubtree(Node start)
    {
        insert((T) start.data);
        Node lchild = start.leftChild;
        Node rchild = start.rightChild;
        if (lchild != null)
        {
            insertSubtree(lchild);
        }
        if (rchild != null)
        {
            insertSubtree(rchild);
        }
    }

    public void delete(T data)
    {
        if (root == null) {
            return; //nothing has been deleted because the tree is empty
        }

        deleteTraversal(data, root, null, null);

    }

    private void deleteNode(Node node, Node parent)
    {
        Node lchild = node.leftChild;
        Node rchild = node.rightChild;

        if (parent == null)
        {
            Node successor = successor(root.rightChild, root);

            successor.leftChild = root.leftChild;

            if (successor.rightChild == null)
            {
                successor.rightChild = root.rightChild;
                root = successor;

                return;
            }

            Node srchild = successor.rightChild;
            successor.rightChild = root.rightChild;

            root = successor;
            insertSubtree(srchild);
            return;
        }

        if (parent.leftChild == node)
        {
            if (lchild == null)
            {
                node.data = null;
                parent.leftChild = rchild;
                return;
            }
            if (rchild == null)
            {
                node.data = null;
                parent.leftChild = lchild;
                return;
            }
            //both children are not null:
            {
                node.data = successor(node.rightChild, node).data;
                return;
            }
        }

        if (parent.rightChild == node)
        {
            if (lchild == null)
            {
                node.data = null;
                parent.rightChild = rchild;
                return;
            }
            if (rchild == null)
            {
                node.data = null;
                parent.rightChild = lchild;
                return;
            }
            //both children are not null:
            {
                node.data = successor(node, parent).data;
                return;
            }
        }
    }

    private Node successor(Node node, Node parent)
    {
        Node lchild = node.leftChild;
        while (lchild != null)
        {
            parent = node;
            node = lchild;
            lchild = node.leftChild;
        }
        if (node.rightChild != null)
        {
            insertSubtree(node.rightChild);
        }
        parent.leftChild = null;
        return node;
    }

    private void deleteTraversal(T data, Node start, Node parent, Node toRotate)
    {
        Node lchild = start.leftChild;
        Node rchild = start.rightChild;

        if (lchild != null)
        {
            if ((start.data).compareTo(data) > 0)   //start.data > data
            {
                start.lheight--;
                if (start.rheight - start.lheight > 1 || start.rheight - start.lheight < -1)
                {
                    toRotate = start;
                }
                deleteTraversal(data, lchild, start, toRotate);
                return;
            }
        }
        if (rchild != null)
        {
            if ((start.data).compareTo(data) < 0)   //start.data < data
            {
                start.rheight--;
                if (start.rheight - start.lheight > 1 || start.rheight - start.lheight < -1)
                {
                    toRotate = start;
                }
                deleteTraversal(data, rchild, start, toRotate);
                return;
            }
        }
        if ((start.data).compareTo(data) == 0)
        {
            deleteNode(start,parent);
            deleteRotation(toRotate, parent);
        }
    }

    private void deleteRotation(Node toRotate, Node parent)
    {
        if (toRotate == null)
        {
            return;
        }

        int bf = toRotate.rheight - toRotate.lheight;

        if (bf > 1)
        {
            int lbf = toRotate.rightChild.rheight - toRotate.rightChild.lheight;
            if (lbf < 0)
            {
                rMinusOneRotation(toRotate, parent);
                return;
            }
            {
                rORotation(toRotate, parent);
                return;
            }
        }

        if (bf < 1)
        {
            int rbf = toRotate.leftChild.rheight - toRotate.leftChild.lheight;
            if (rbf < 0)
            {
                lMinusOneRotation(toRotate, parent);
                return;
            }
            {
                lORotation(toRotate, parent);
                //return;
            }
        }
    }

    private void lORotation(Node base, Node parent)
    {
        Node b = base.rightChild;
        Node br = b.leftChild;

        b.leftChild = base;
        base.rightChild = br;

        rotationFixParents(b, base, parent);
    }

    private void rORotation(Node base, Node parent)
    {
        Node b = base.leftChild;
        Node br = b.rightChild;

        b.rightChild = base;
        base.leftChild = br;

        rotationFixParents(b, base, parent);
    }

    private void rMinusOneRotation(Node base, Node parent)
    {
        Node b = base.leftChild;
        Node c = b.rightChild;
        Node cl = c.leftChild;
        Node cr = c.rightChild;
        
        c.leftChild = b;
        c.rightChild = base;
        base.leftChild = cr;
        b.rightChild = cl;

        rotationFixParents(c, base, parent);
    }
    
    private void lMinusOneRotation(Node base, Node parent)
    {
        Node b = base.rightChild;
        Node c = b.leftChild;
        Node cl = c.rightChild;
        Node cr = c.leftChild;

        c.rightChild = b;
        c.leftChild = base;
        base.rightChild = cr;
        b.leftChild = cl;

        rotationFixParents(c, base, parent);
    }

    private void rotationFixParents(Node b, Node base, Node parent)
    {
        if (parent == null)
        {
            root = b;
            return;
        }
        if (parent.leftChild == base)
        {
            parent.leftChild = b;
            return;
        }
        if (parent.rightChild == base)
        {
            parent.rightChild = b;
            //return;
        }
    }

    public boolean contains(T data)
    {
        if (root == null)
        {
            return false;
        }

        Node parent = root;
        while ((parent.data).compareTo(data) != 0)
        {
            while ((parent.data).compareTo(data) < 0)   //parent.data < data
            {
                if (parent.rightChild == null)
                {
                    return false;
                }
                parent = parent.rightChild;
            }

            while ((parent.data).compareTo(data) > 0)   //parent.data > data
            {
                if (parent.leftChild == null)
                {
                    return false;
                }
                parent = parent.leftChild;
            }
        }
        return true;
    }

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

    public String printTree()
    {
       return ";";
    }

    //testing TODO delete
    private int leftHeight(Node start)
    {
        Node lchild = start.leftChild;
        if (lchild == null)
        {
            return 0;
        }
        return Math.max(leftHeight(lchild), rightHeight(lchild)) + 1;
    }

    //testing TODO delete
    private int rightHeight(Node start)
    {
        Node rchild = start.rightChild;
        if (rchild == null)
        {
            return 0;
        }
        return Math.max(leftHeight(rchild), rightHeight(rchild)) + 1;
    }
}
