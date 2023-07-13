import java.util.Arrays;

public class AVLTree {
    private NodeAVL root;

    public AVLTree() {
        root = null;
    }


    //método para pegar a altura de um nó na arvore
    private int height(NodeAVL node) {
        if (node == null) {
            return -1;
        } else {
            return node.getBalance();
        }
    }


    private  boolean isLeaf(NodeAVL node){
        if(node.getRightchild() == null && node.getLeftchild() == null){
            return  true;
        }else{
            return  false;
        }
    }

    private boolean isLeftChild(NodeAVL node){
        NodeAVL parent = node.getFather();
        if(parent == null ){
            return  false;
        }
        if(parent.getLeftchild() == null){
            return  false;
        }else{
            return  true;
        }

    }

    private boolean isRightChild(NodeAVL node){
        NodeAVL parent = node.getFather();

        if(parent == null ){
            return  false;
        }
        if(parent.getRightchild() == null){
            return  false;
        }else{
            return  true;
        }

    }

    private NodeAVL Search(NodeAVL node,int value){
        //verificar se o Nó é folha
        if(isLeaf(node)){
            return node;
        }
        if(value < node.getValue()){
            return Search(node.getLeftchild(),value);
        }
        if (value == node.getValue()) {
            return node;
        }
        if(value > node.getValue()){
            return Search(node.getRightchild(),value);
        }
        return  null;
    }

    public NodeAVL Insert(int value) throws Exception{
        if(root == null){
            root = new NodeAVL(value);
            return  root;
        }
        NodeAVL node = Search(root,value);
        if(node.getValue() == value){
            throw new Exception("Valor ja existente na Arvore");
        }else{
            NodeAVL newNode = new NodeAVL(value);
            newNode.setFather(node);
            if(isRightChild(newNode)){
                node.setRightchild(newNode);
            }else{
                node.setLeftchild(newNode);
            }
            //agora que foi inserido é necessario balancear
            return newNode;
        }
    }


    private NodeAVL simpleLeftRotate (NodeAVL B, boolean isRightChild){
       NodeAVL A = B.getRightchild();
       A.setFather(B.getFather());
       if(B.getFather() !=null){
           if(isRightChild == false){
               B.getFather().setRightchild(A);
           }
           else{
               B.getFather().setLeftchild(A);
           }
           B.setFather(A);
           B.setRightchild(A.getLeftchild());
       }
       if(A.getLeftchild() !=null){
           A.getLeftchild().setFather(B);
       }
       A.setLeftchild(B);

       int NewBalB = B.getBalance() + 1 - Math.min(A.getBalance(),0);
       int NewBalA = A.getBalance() + 1 - Math.max(NewBalB,0);

       B.setBalance(NewBalB);
       A.setBalance(NewBalA);

       return  A;
    }

    private NodeAVL simpleRightRotate (NodeAVL B, boolean isLeftChild){
        NodeAVL A = B.getLeftchild();
        A.setFather(B.getFather());
        if(B.getFather() !=null){
            if(isLeftChild == false){
                B.getFather().setLeftchild(A);
            }
            else{
                B.getFather().setRightchild(A);
            }
            B.setFather(A);
            B.setLeftchild(A.getRightchild());
        }
        if(A.getRightchild() !=null){
            A.getRightchild().setFather(B);
        }
        A.setRightchild(B);

        int NewBalB = B.getBalance() + 1 - Math.min(A.getBalance(),0);
        int NewBalA = A.getBalance() + 1 - Math.max(NewBalB,0);

        B.setBalance(NewBalB);
        A.setBalance(NewBalA);

        return  A;
    }


    private NodeAVL doubleLeftRotate(NodeAVL node){
        NodeAVL tmp = node.getRightchild();
        simpleRightRotate(tmp,isRightChild(node));
        node = simpleLeftRotate(node,isRightChild(node));
        return  node;
    }

    private NodeAVL doubleRightRotate(NodeAVL node){
        NodeAVL tmp = node.getLeftchild();
        simpleLeftRotate(tmp,isLeftChild(node));
        node = simpleRightRotate(node,isLeftChild(node));
        return  node;
    }



    //------------------ Métodos Para Imprimir a Arvore --------------------------- //

    // método para retornar a altura de um nó para printar ele na árvore
    private int getHeight(NodeAVL node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getHeight(node.getLeftchild()), getHeight(node.getRightchild()));
    }

    private void addNodeToMatrix(String[][] matrix, NodeAVL node, int level, int left, int right) {
        if (node == null) {
            return;
        }
        // Adiciona o nó na posição correta da matriz
        int mid = (left + right) / 2;
        String result =  node.getValue() + "("+ node.getBalance() + ")";
        matrix[level][mid] = result;

        // Recursivamente adiciona os filhos do nó na matriz
        addNodeToMatrix(matrix, node.getLeftchild(), level + 1, left, mid - 1);
        addNodeToMatrix(matrix, node.getRightchild(), level + 1, mid + 1, right);
    }

    //metodo de printar a árvore
    public void printTree() {
        // Obtém a altura da árvore
        int height = getHeight(root);

        // Cria uma matriz para armazenar os nós da árvore
        int width = (int) Math.pow(2, height) - 1;
        String[][] matrix = new String[height][width];
        for (String[] row : matrix) {
            Arrays.fill(row, " ");
        }

        // Adiciona os nós da árvore na matriz
        addNodeToMatrix(matrix, root, 0, 0, width - 1);

        // Imprime a matriz com os nós da árvore
        for (String[] row : matrix) {
            for (String cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }


}
