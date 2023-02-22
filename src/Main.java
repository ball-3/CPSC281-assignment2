public class Main {
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        
        tree.insert(17);
        tree.insert(23);
        tree.insert(39);
        tree.insert(1);
        tree.insert(4);
        tree.insert(9);

        System.out.println(tree.toString());
    }
}
