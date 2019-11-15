import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> {
    protected class Node {
        public T data;
        public Node leftChild;
        public Node rightChild;

        public Node(T data) {
            this.data = data;
        }
    }

    private class TraversalIterator implements Iterator<T> {
        private Queue<T> queue;

        TraversalIterator(Queue queue) {
            this.queue = queue;
        }

        @Override
        public boolean hasNext() {
            return queue.size() > 0;
        }

        @Override
        public T next() {
            return queue.poll();
        }
    }

    protected Node root;
    protected int size = 0;

    public void add(T data) {
        root = addToChild(root, data);
        size++;
    }

    private Node addToChild(Node n, T data) {
        if (n == null) {
            return new Node(data);
        } else {
            if (data.compareTo(n.data) <= 0) {
                n.leftChild = addToChild(n.leftChild, data);
            } else {
                n.rightChild = addToChild(n.rightChild, data);
            }
        }

        return n;
    }

    public void remove(T data) {
        root = removeAndReplace(root, data);
        size--;
    }

    private Node removeAndReplace(Node n, T data) {
        if (n == null) return null;
        if (n.data.compareTo(data) > 0) {
            // If target data is smaller than current node
            // Probably it's the left child that should be removed
            n.leftChild = removeAndReplace(n.leftChild, data);
        } else if (n.data.compareTo(data) < 0) {
            n.rightChild = removeAndReplace(n.rightChild, data);
        } else {
            if (n.leftChild == null && n.rightChild == null) {
                // If the node with target data does not have any child
                // Simply remove it, no replacement needed
                n = null;
            } else if (n.leftChild == null) {
                // If the node with target data has a right child
                // Replace the node with its right child node
                return n.rightChild;
            } else if (n.rightChild == null) {
                return n.leftChild;
            } else {
                // If it has both
                // Swap the value with the max of left sub tree or the min of right sub tree
                // Then remove the node whose value is swapped
                T replacementValue = maxNode(n.leftChild).data;
                n.data = replacementValue;
                n.leftChild = removeAndReplace(n.leftChild, replacementValue);
            }
        }

        return n;
    }

    protected int height() {
        return subTreeHeight(root);
    }

    protected int subTreeHeight(Node n) {
        if (n == null) return -1;
        return 1 + Math.max(subTreeHeight(n.leftChild), subTreeHeight(n.rightChild));
    }

    protected Node minNode(Node root) {
        Node current = root;
        while (current.leftChild != null) {
            current = current.leftChild;
        }

        return current;
    }

    protected Node maxNode(Node root) {
        Node current = root;
        while (current.rightChild != null) {
            current = current.rightChild;
        }

        return current;
    }

    public Iterator<T> preOrderTraversal() {
        Queue<T> queue = new LinkedList<T>();
        preOrder(queue, root);
        return new TraversalIterator(queue);
    }

    private void preOrder(Queue<T> q, Node n) {
        if (n == null) return;
        q.add(n.data);
        preOrder(q, n.leftChild);
        preOrder(q, n.rightChild);
    }

    public Iterator<T> inOrderTraversal() {
        Queue<T> queue = new LinkedList<T>();
        inOrder(queue, root);
        return new TraversalIterator(queue);
    }

    private void inOrder(Queue<T> q, Node n) {
        if (n == null) return;
        inOrder(q, n.leftChild);
        q.add(n.data);
        inOrder(q, n.rightChild);
    }

    public Iterator<T> postOrderTraversal() {
        Queue<T> queue = new LinkedList<T>();
        postOrder(queue, root);
        return new TraversalIterator(queue);
    }

    private void postOrder(Queue<T> q, Node n) {
        if (n == null) return;
        postOrder(q, n.leftChild);
        postOrder(q, n.rightChild);
        q.add(n.data);
    }

    public Iterator<T> levelOrderTraversal() {
        Queue<Node> queue = new LinkedList<>();
        Queue<T> resultQueue = new LinkedList<>();
        queue.add(root);
        while (queue.size() > 0) {
            Node node = queue.poll();
            resultQueue.add(node.data);
            if (node.leftChild != null) queue.add(node.leftChild);
            if (node.rightChild != null) queue.add(node.rightChild);
        }

        return new TraversalIterator(resultQueue);
    }

    public static void main(String[] args) {
        int[] data = new int[]{7, 20, 5, 15, 10, 4, 4, 33, 2, 25, 6};
        BinarySearchTree bst = new BinarySearchTree();
        for (int i : data) {
            bst.add(i);
        }
        System.out.println("Preorder traversal");
        Iterator<Integer> preIterator = bst.preOrderTraversal();
        while (preIterator.hasNext()) {
            System.out.println(preIterator.next());
        }
        System.out.println("Inorder traversal");
        Iterator<Integer> inIterator = bst.inOrderTraversal();
        while (inIterator.hasNext()) {
            System.out.println(inIterator.next());
        }
        System.out.println("Postorder traversal");
        Iterator<Integer> postIterator = bst.postOrderTraversal();
        while (postIterator.hasNext()) {
            System.out.println(postIterator.next());
        }
        System.out.println("Level order traversal");
        Iterator<Integer> levelIterator = bst.levelOrderTraversal();
        while (levelIterator.hasNext()) {
            System.out.println(levelIterator.next());
        }
        bst.remove(20);
        System.out.println("Inorder order traversal after 20 removal");
        inIterator = bst.inOrderTraversal();
        while (inIterator.hasNext()) {
            System.out.println(inIterator.next());
        }
        System.out.println("Height");
        System.out.println(bst.height());
    }
}
