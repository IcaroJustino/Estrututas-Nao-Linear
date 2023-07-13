public class Node {
    private int value;
    private Node leftchild;
    private Node rightchild;
    private Node father;

    public Node(int valor) {
        this.value = valor;
        this.leftchild = null;
        this.rightchild = null;
        this.father = null;
    }

    // getters e setters para os atributos do No

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getLeftchild() {
        return leftchild;
    }

    public void setLeftchild(Node leftchild) {
        this.leftchild = leftchild;
    }

    public Node getRightchild() {
        return rightchild;
    }

    public void setRightchild(Node rightchild) {
        this.rightchild = rightchild;
    }

    public Node getFather() {
        return father;
    }

    public void setFather(Node father) {
        this.father = father;
    }
}