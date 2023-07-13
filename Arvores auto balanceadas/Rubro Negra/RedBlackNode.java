public class RedBlackNode {
    private int data;
    private boolean color;
    private RedBlackNode leftChild;
    private RedBlackNode rightChild;
    private RedBlackNode parent;

    public RedBlackNode(int data) {
        this.data = data;
        this.color = true; // o estádo inicial do nó sempre será rubro
    }

    // Getters and setters for data, isRed, leftChild, rightChild, and parent

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public boolean getColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public RedBlackNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(RedBlackNode leftChild) {
        this.leftChild = leftChild;
    }

    public RedBlackNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(RedBlackNode rightChild) {
        this.rightChild = rightChild;
    }

    public RedBlackNode getParent() {
        return parent;
    }

    public void setParent(RedBlackNode parent) {
        this.parent = parent;
    }
}