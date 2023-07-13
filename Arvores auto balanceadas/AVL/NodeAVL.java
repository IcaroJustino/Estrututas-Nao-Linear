public class NodeAVL {
    private int value;
    private int balance;
    private NodeAVL leftchild;
    private NodeAVL rightchild;
    private NodeAVL father;


    public NodeAVL(int valor) {
        this.value = valor;
        this.balance = 0;
        this.leftchild = null;
        this.rightchild = null;
        this.father = null;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public NodeAVL getLeftchild() {
        return leftchild;
    }

    public void setLeftchild(NodeAVL leftchild) {
        this.leftchild = leftchild;
    }

    public NodeAVL getRightchild() {
        return rightchild;
    }

    public void setRightchild(NodeAVL rightchild) {
        this.rightchild = rightchild;
    }

    public NodeAVL getFather() {
        return father;
    }

    public void setFather(NodeAVL father) {
        this.father = father;
    }

    public int getBalance(){
        return balance;
    }

    public void setBalance(int value) {
        this.balance = value;
    }
}