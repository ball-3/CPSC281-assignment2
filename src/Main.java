public class Main {
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        tree.insert(25);
        tree.insert(15);
        tree.insert(22);
        tree.insert(18);
        tree.insert(10);
        tree.insert(12);
        tree.insert(4);


        System.out.println(tree);
        System.out.println(tree.contains(22));
        //tree.delete(22);
        System.out.println(tree.contains(22));
    }
}
