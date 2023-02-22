public class BinarySearchTree<T extends Comparable <T>> {

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

public BinarySearchTree()
{
    root = null;
    s = new StringBuilder(100);
}

public void insert(T data)
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

public void delete(T data)
{
    if (root == null)
    {
        return; //nothing has been deleted because the tree is empty
    }


    // :(
    Node parent = root;
    Node working;
    while ((parent.data).compareTo(data) != 0)
    {
        while ((parent.data).compareTo(data) < 0)   //parent.data < data
        {
            if (parent.rightChild == null)
            {
                return; //element does not exist so nothing is deleted
            }
            parent = parent.rightChild;
        }

        while ((parent.data).compareTo(data) > 0)   //parent.data > data
        {
            if (parent.leftChild == null)
            {
                return; //element does not exist so nothing is deleted
            }
            parent = parent.leftChild;
        }
    }
    //delete "parent"
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
        return "empty tree";
    }

    Node working = root;
    do
    {
        while (working.leftChild != null)
        {
            s.append(working.data.toString());
            s.append(" ");
            System.out.println(working.data.toString());
            working = working.leftChild;
        }
        while (working.rightChild != null)
        {
            s.append(working.data.toString());
            s.append(" ");
            System.out.println(working.data.toString());

            if (working.leftChild == null)
            {
                working = working.rightChild;
            }
            else
            {
                working = working.leftChild;
            }
        }
    } while (working.leftChild != null || working.rightChild != null);

    return s.toString();
}

}
