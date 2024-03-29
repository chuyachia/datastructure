import java.util.Iterator;

public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {


    private int balanceFactor(Node n) {
        return subTreeHeight(n.rightChild) - subTreeHeight(n.leftChild);
    }

    public void add(T data) {
        root = addToChild(root, data);
        size++;
    }

    private Node addToChild(Node n, T data) {
        if (n == null) return new Node(data);
        if (data.compareTo(n.data) < 0) {
            n.leftChild = addToChild(n.leftChild, data);
        } else if (data.compareTo(n.data) > 0) {
            n.rightChild = addToChild(n.rightChild, data);
        } else {
            throw new IllegalArgumentException(String.format("Data already exists"));
        }

        return balance(n);
    }

    public void remove(T data) {
        root = removeAndReplace(root, data);
        size--;
    }

    private Node removeAndReplace(Node n, T data) {
        if (n == null) return null;
        if (data.compareTo(n.data) < 0) {
            n.leftChild = removeAndReplace(n.leftChild, data);
        } else if (data.compareTo(n.data) > 0) {
            n.rightChild = removeAndReplace(n.rightChild, data);
        } else {
            if (n.leftChild == null && n.rightChild == null) {
                return null;
            } else if (n.leftChild == null) {
                return n.rightChild;
            } else if (n.rightChild == null) {
                return n.leftChild;
            } else {
                T replacementValue = maxNode(n.leftChild).data;
                n.data = replacementValue;
                n.leftChild = removeAndReplace(n.leftChild, replacementValue);
            }
        }

        return balance(n);
    }

    private Node balance(Node n) {
        // TODO save balanceFactor in Node so we don't recalculate for example in balanceFactor(n.leftChild)
        if (n == null) return null;
        int balanceFactor = balanceFactor(n);
        if (balanceFactor == -2) {
            if (balanceFactor(n.leftChild) <= 0) {
                // left left case
                n = rightRotate(n);
            } else {
                // left right case
                n.leftChild = leftRotate(n.leftChild);
                n = rightRotate(n);
            }
        } else if (balanceFactor == 2) {
            if (balanceFactor(n.rightChild) >= 0) {
                // right right case
                n = leftRotate(n);
            } else {
                // right left case
                n.rightChild = rightRotate(n.rightChild);
                n = leftRotate(n);
            }
        }

        return n;
    }


    private Node rightRotate(Node n) {
        Node leftChild = n.leftChild;
        n.leftChild = leftChild.rightChild;
        leftChild.rightChild = n;

        return leftChild;
    }

    private Node leftRotate(Node n) {
        Node rightChild = n.rightChild;
        n.rightChild = rightChild.leftChild;
        rightChild.leftChild = n;

        return rightChild;
    }

    public static void main(String[] args) {
        AVLTree avl = new AVLTree();
        int[] data = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        for (int i : data) {
            avl.add(i);
        }
        System.out.println("Level order traversal");
        Iterator<Integer> levelIterator = avl.levelOrderTraversal();
        while (levelIterator.hasNext()) {
            System.out.println(levelIterator.next());
        }
        avl.remove(1);
        avl.remove(5);
        System.out.println("Level order traversal after removing 5");
        Iterator<Integer> levelIterator2 = avl.levelOrderTraversal();
        while (levelIterator2.hasNext()) {
            System.out.println(levelIterator2.next());
        }
    }
}
