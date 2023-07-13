

public class BinaryTree{
    Node root;

    //estado inicial da árvore
    BinaryTree() {
        root = null;
    }

    //checar se é filho esquerdo
    public Boolean isLeftChild(Node node){
        Node parent = node.getFather();

        //caso você esteja na Raiz
        if(parent == null){
            return  false;
        }

        if(parent.getValue() > node.getValue() ){
            return true;
        }else{
            return false;
        }
    }


    //checar se é filho direito;
    public Boolean isRightChild(Node node){
        Node parent = node.getFather();

        //caso você esteja na Raiz
        if(parent == null){
            return  false;
        }

        if(parent.getValue() < node.getValue() ){
            return true;
        }else{
            return false;
        }

    }

    public Boolean OnlyOneChild(Node node){
        if(node.getLeftchild() !=null && node.getRightchild() !=null){
            return  false;
        }else{
            return  true;
        }
    }

    //verificar se o Nó é folha;
    public Boolean isLeaf(Node node) {
        if(node != null){
            if(node.getRightchild() == null && node.getLeftchild() == null){
                return true;
            } else{
                return false;
            }
        }
        return false;
    }

    //encontrar o sucessor para o caso que ele possua mais de um filho (Utilizado por enquanto apenas na remoção)
    public Node getSucessor(Node no) {
        if (no.getLeftchild() == null) {
            return no;
        } else {
            return getSucessor(no.getLeftchild());
        }
    }

    //método de pesquisa na árvore
    private Node Search(Node node,int value){
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

    public int Search(int value){
        Node teste = Search(root,value);
        return teste.getValue();
    }

    public Node Insert(int value) throws Exception{
        if(root == null){
            root = new Node(value);
            return  root;
        }
        Node node = Search(root,value);
        if(node.getValue() == value){
            throw new Exception("Valor ja existente na Arvore");
        }else{
            Node newNode = new Node(value);
            newNode.setFather(node);
            if(isRightChild(newNode)){
                node.setRightchild(newNode);
            }else{
                node.setLeftchild(newNode);
            }
            return newNode;
        }
    }



    //método de remoção
    public int Remove(int value) throws Exception{
        Node node = Search(root,value);
        int res = node.getValue();
        if (node == null){
            throw new Exception("Valor não existente na Arvore");
        }
        //caso seja um Nó Folha
        if(isLeaf(node)){
            int result = node.getValue();
            if(isLeftChild(node)){
                node.getFather().setLeftchild(null);
            }
            if(isRightChild(node)){
                node.getFather().setRightchild(null);
            }
            return result;
        }
        //caso possua Um filho
        if(OnlyOneChild(node)){
            Node tempchild = null;
            //possui apenas um filho na esquerda
            if(node.getLeftchild() !=null){
                tempchild = node.getLeftchild();
            }
            //possui apenas um filho na direita
            if(node.getRightchild() !=null){
                tempchild = node.getRightchild();
            }

            //checar se é filho direito ou esquerdo do seu Pai para fazer a realocação do Nó


            //é filho direito
            if(isRightChild(node)){
                node.getFather().setRightchild(tempchild);
            }

            //é filho esquerdo
            else{
                node.getFather().setLeftchild(tempchild);
            }
            tempchild.setFather(node.getFather());
            return node.getValue();
        }
        if(!OnlyOneChild(node)){
            Node sucessor = getSucessor(node.getRightchild());
            Remove(sucessor.getValue()); // certeza que é assim?
            node.setValue(sucessor.getValue()); // certeza que é assim?
        }
        return res;
    }

    //pegar a altura de um Nó
    public static int getHeight(Node node) {
        if (node == null) {
            return -1;
        } else {
            int leftHeigth = getHeight(node.getLeftchild());
            int rightHeigth = getHeight(node.getRightchild());

            if (leftHeigth > rightHeigth) {
                return leftHeigth + 1;
            } else {
                return rightHeigth + 1;
            }
        }
    }

    //retornar a profundidade de um Nó
    public static int getDepth(Node node) {
        if (node == null) {
            return 0;
        }
        int leftDepth = getDepth(node.getLeftchild());
        int rightDepth = getDepth(node.getRightchild());
        return 1 + Math.max(leftDepth, rightDepth);
    }

        public static void fillMatrix(Node node, int[][] matrix, int depth, int left, int right) {
            if (node == null) {
                return;
            }

            int mid = (left + right) / 2;
            matrix[depth][mid] = node.getValue();
            fillMatrix(node.getLeftchild(), matrix, depth + 1, left, mid - 1);
            fillMatrix(node.getRightchild(), matrix, depth + 1, mid + 1, right);
        }

        public void printTree(Node root) {
            int height = getHeight(root);
            int depth = getDepth(root);
            int width = (int) Math.pow(2, depth) - 1;
            int[][] matrix = new int[height][width];

            fillMatrix(root, matrix, 0, 0, width - 1);

            for (int[] row : matrix) {
                for (int val : row) {
                    if (val == 0) {
                        System.out.print("  ");
                    } else {
                        System.out.print(val + " ");
                    }
                }
                System.out.println();
            }
        }

}
