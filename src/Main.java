public class Main {
    public static void main(String[] args) {

        bstTings();
        avlTings();
    }

    public static void bstTings()
    {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        tree.insert(25);
        tree.insert(15);
        tree.insert(22);
        tree.insert(18);
        tree.insert(10);
        tree.insert(12);
        tree.insert(4);
        tree.insert(50);
        tree.insert(35);
        tree.insert(31);
        tree.insert(44);
        tree.insert(70);
        tree.insert(66);
        tree.insert(90);
        tree.insert(24);
        tree.insert(33);
        tree.insert(32);
        tree.insert(34);

        System.out.println(tree);
//        System.out.println(tree.contains(15));
//        tree.delete(15);
//        System.out.println(tree.contains(15));
//        System.out.println(tree);
    }

    public static void avlTings()
    {
        AVLTree<Integer> tree = new AVLTree<>();

        tree.insert(25);
        tree.insert(15);
        tree.insert(22);
        tree.insert(18);
        tree.insert(10);
        tree.insert(12);
        tree.insert(4);
        tree.insert(50);
        tree.insert(35);
        tree.insert(31);
        tree.insert(44);
        tree.insert(70);
        tree.insert(66);
        tree.insert(90);
        tree.insert(24);
        tree.insert(33);
        tree.insert(32);
        //tree.insert(34);

        System.out.println(tree);
        System.out.println(tree.printTree());
        //System.out.println(tree.contains(22));
        //tree.delete(22);
        //System.out.println(tree.contains(22));
    }
}
