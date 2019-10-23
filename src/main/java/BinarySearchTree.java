import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree {
    private class Node {
        private int data;
        private Node leftChild;
        private Node rightChild;

        public Node(int data) {
            this.data = data;
        }
    }

    private Node root;

    public void add(int data) {
        root = addToChild(root, data);
    }

    private Node addToChild(Node n, int data) {
        if (n == null) {
            n = new Node(data);
        } else {
            if (data <= n.data) {
                n.leftChild = addToChild(n.leftChild, data);
            } else {
                n.rightChild = addToChild(n.rightChild, data);
            }
        }

        return n;
    }

    public boolean remove(int data) {
        root = findToRemove(root, data);
        return true;
    }

    private Node findToRemove(Node n, int data) {
        if (n.data > data) {
            n.leftChild = findToRemove(n.leftChild, data);
        } else if (n.data < data) {
            n.rightChild = findToRemove(n.rightChild,data);
        } else {
            if (n.leftChild == null && n.rightChild ==null) {
                n =  null;
            } else if (n.leftChild == null) {
                return n.rightChild;
            } else if (n.rightChild == null) {
                return n.leftChild;
            } else {
                int replacementValue = maxNode(n.leftChild).data;
                n.data = replacementValue;
                n.leftChild = findToRemove(n.leftChild,replacementValue);
            }
        }

        return n;
    }

    private Node minNode(Node root) {
        Node current = root;
        while (current.leftChild != null) {
            current = current.leftChild;
        }

        return current;
    }

    private Node maxNode(Node root) {
        Node current = root;
        while (current.rightChild != null) {
            current = current.rightChild;
        }

        return current;
    }

    public void preOrderTraversal() {
        preOrder(root);
    }

    private void preOrder(Node n) {
        if (n == null) return;
        System.out.println(n.data);
        preOrder(n.leftChild);
        preOrder(n.rightChild);
    }

    public void inOrderTraversal() {
        inOrder(root);
    }

    private void inOrder(Node n) {
        if (n == null) return;
        inOrder(n.leftChild);
        System.out.println(n.data);
        inOrder(n.rightChild);
    }

    public void postOrderTraversal() {
        postOrder(root);
    }

    private void postOrder(Node n) {
        if (n == null) return;
        postOrder(n.leftChild);
        System.out.println(n.data);
        postOrder(n.rightChild);
    }

    public void levalOrderTraversal() {
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(root);
        while (queue.size() > 0) {
            Node node = queue.poll();
            System.out.println(node.data);
            if (node.leftChild != null) queue.add(node.leftChild);
            if (node.rightChild != null) queue.add(node.rightChild);
        }
    }

    public static void main(String[] args) {
        int[] data = new int[]{7, 20, 5, 15, 10, 4, 4, 33, 2, 25, 6};
        BinarySearchTree bst = new BinarySearchTree();
        for (int i : data) {
            bst.add(i);
        }
        System.out.println("Preorder traversal");
        bst.preOrderTraversal();
        System.out.println("Inorder traversal");
        bst.inOrderTraversal();
        System.out.println("Postorder traversal");
        bst.postOrderTraversal();
        System.out.println("Level order traversal");
        bst.levalOrderTraversal();
        bst.remove(20);
        System.out.println("Inorder order traversal after 20 removal");
        bst.inOrderTraversal();
    }
}
