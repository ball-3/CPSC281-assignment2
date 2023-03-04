public class AVLTree<T extends Comparable <T>> {

    public static class Node<T extends Comparable<T>>
    {
        T data;
        Node leftChild;
        Node rightChild;

        public Node(T data)
        {
            this.data = data;
        }
    }

    private Node<T> root;
    private StringBuilder s;
    private int bf;

    public AVLTree()
    {
        root = null;
        bf = 0;
    }

    private void bstInsert(T data)
    {
        Node newNode = new Node(data);

        if (root == null)
        {
            root = newNode;
            return;
        }

        Node parent = root;
        while ((parent.data).compareTo(data) != 0)
        {
            while ((parent.data).compareTo(data) < 0)   //parent.data < data
            {
                if (parent.rightChild == null)
                {
                    parent.rightChild = newNode;
                    return;
                    //put newNode as the rightChild here
                }
                parent = parent.rightChild;
            }

            while ((parent.data).compareTo(data) > 0)   //parent.data > data
            {
                if (parent.leftChild == null)
                {
                    parent.leftChild = newNode;
                    return;
                    //put newNode as the leftChild here
                }
                parent = parent.leftChild;
            }
        }
        //does not insert if data is repeated
    }

    //this is just bst insert so far :(
    public void insert(T data)
    {
        bstInsert(data);
        findBF();
        if (bf == 0 || bf == 1 || bf == -1) {return;}
        if (bf > 1)
        {

        }
        if (bf < 1)
        {

        }
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

    private void deleteTraversal(T data, Node start, Node parent)
    {
        Node lchild = start.leftChild;
        Node rchild = start.rightChild;

        if (lchild != null)
        {
            if ((start.data).compareTo(data) > 0)   //start.data > data
            {
                deleteTraversal(data, lchild, start);
                return;
            }
        }
        if (rchild != null)
        {
            if ((start.data).compareTo(data) < 0)   //start.data < data
            {
                deleteTraversal(data, rchild, start);
                return;
            }
        }
        if ((start.data).compareTo(data) == 0)
        {
            deleteNode(start,parent);
        }
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

    public void delete(T data) {
        if (root == null) {
            return; //nothing has been deleted because the tree is empty
        }

        Node working = root;
        deleteTraversal(data, root, null);

    }

    //bst contains but should be the same ?
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

    private void findBF()
    {
        findBF(root);
    }

    private void findBF(Node start)
    {
        bf = rightHeight(start) - leftHeight(start);
    }

    private int leftHeight(Node start)
    {
        Node lchild = start.leftChild;
        if (lchild == null)
        {
            return 0;
        }
        return Math.max(leftHeight(lchild), rightHeight(lchild)) + 1;
    }

    private int rightHeight(Node start)
    {
        Node rchild = start.rightChild;
        if (rchild == null)
        {
            return 0;
        }
        return Math.max(leftHeight(rchild), rightHeight(rchild)) + 1;
    }
    public int getBf()
    {
        findBF();
        return bf;
    }

}
