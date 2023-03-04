public class AVLTree<T extends Comparable <T>> {
    BinarySearchTree<T> tree;
    int bf;

    public AVLTree()
    {
        tree = new BinarySearchTree<>();
        bf = 0;
    }

    public void insert(T data)
    {

    }

    public void delete(T data)
    {

    }

    public boolean Contains(T data)
    {
        return tree.contains(data);
    }

    public String toString()
    {
        return tree.toString();
    }

    private void findBF()
    {

    }
}
