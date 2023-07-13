import java.util.Arrays;

public class RedBlackTree {
    /*
    *  Conceito: Arvore Binaria de Pesquisa que implementa um esquema de cores para que ela fique balanceada e tenha um tempo de operação geral Log (O)n
    *  Principios da Árvore Vermelho Preto
    *  Cada Nó deve possuir a cor Vermelho ou Preto
    *  A ÁRVORE VERMELHO PRETO só é valida se seguir os seguintes critérios
    *  1. Nó externo == Vermelho
    *  2. Nó raiz == Preto
    *  3. Se Nó for vermelho ambos os filhos precisam ser pretos
    *  4. Para cada nó, todos os caminhos da raiz às folhas contêm o mesmo número de nós pretos.
    * */

    private RedBlackNode root;


    //verificar se o Nó é folha;
    public Boolean isLeaf(RedBlackNode node) {
        if(node != null){
            if(node.getRightChild() == null && node.getLeftChild() == null){
                return true;
            } else{
                return false;
            }
        }
        return false;
    }


    public Boolean isLeftChild(RedBlackNode node){
        RedBlackNode parent = node.getParent();
        if(parent !=null && parent.getLeftChild() == node){
            return  true;
        }{
            return false;
        }
    }


    public Boolean isRightChild(RedBlackNode node){
        RedBlackNode parent = node.getParent();
        if(parent !=null && parent.getRightChild() == node){
            return  true;
        }{
            return false;
        }
    }


    public RedBlackNode search(int data) {
        RedBlackNode current = root;
        while (current != null) {
            if (data == current.getData()) {
                return current; // Node found
            } else if (data < current.getData()) {
                current = current.getLeftChild();
            } else {
                current = current.getRightChild();
            }
        }
        return null; // Node not found
    }


    public RedBlackNode insert(int data) throws Exception {

        RedBlackNode newNode = new RedBlackNode(data); // novo nó é sempre vermelho


        if (root == null) {
            root = newNode;
            return root;
        }
        if(search(data) !=null) {
            throw new Exception("Valor ja existente na Arvore");
        }
        else {
            RedBlackNode parent = null;
            RedBlackNode current = root;
            while (current != null) {
                parent = current;
                if (data < current.getData()) {
                    current = current.getLeftChild();
                } else {
                    current = current.getRightChild();
                }
            }
            if (data < parent.getData()) {
                parent.setLeftChild(newNode);
            } else {
                parent.setRightChild(newNode);
            }
            newNode.setParent(parent);
        }
        fixInsertion(newNode);
        root.setColor(false); // a raiz é sempre preta
        return newNode;
    }

    private void fixInsertion(RedBlackNode node) {
        RedBlackNode parent, grandparent;
        while (node.getParent() != null && node.getParent().getColor()) {
            parent = node.getParent();
            grandparent = parent.getParent();

            if (grandparent == null) {
                break;
            }

            boolean parentIsLeftChild = grandparent.getLeftChild() == parent;
            boolean nodeIsLeftChild = parent.getLeftChild() == node;

            if (parentIsLeftChild == nodeIsLeftChild) {
                RedBlackNode uncle = parentIsLeftChild ? grandparent.getRightChild() : grandparent.getLeftChild();
                if (uncle != null && uncle.getColor()) {
                    grandparent.setColor(true);
                    parent.setColor(false);
                    uncle.setColor(false);
                    node = grandparent;
                } else {
                    if (nodeIsLeftChild != parentIsLeftChild) {
                        if (parentIsLeftChild) {
                            rightRotate(parent);
                        } else {
                            leftRotate(parent);
                        }
                        parent = node.getParent();
                    }
                    grandparent.setColor(true);
                    parent.setColor(false);
                    if (parentIsLeftChild) {
                        rightRotate(grandparent);
                    } else {
                        leftRotate(grandparent);
                    }
                }
            } else {
                break;
            }
        }
        root.setColor(false);
    }




    private void leftRotate(RedBlackNode node) {
        RedBlackNode newParent = node.getRightChild();
        if (newParent == null) {
            return;
        }
        node.setRightChild(newParent.getLeftChild());
        if (newParent.getLeftChild() != null) {
            newParent.getLeftChild().setParent(node);
        }
        newParent.setParent(node.getParent());
        if (node.getParent() == null) {
            root = newParent;
        } else if (node == node.getParent().getLeftChild()) {
            node.getParent().setLeftChild(newParent);
        } else {
            node.getParent().setRightChild(newParent);
        }
        newParent.setLeftChild(node);
        node.setParent(newParent);
    }

    private void rightRotate(RedBlackNode node) {
        RedBlackNode newParent = node.getLeftChild();
        if (newParent == null) {
            return;
        }
        node.setLeftChild(newParent.getRightChild());
        if (newParent.getRightChild() != null) {
            newParent.getRightChild().setParent(node);
        }
        newParent.setParent(node.getParent());
        if (node.getParent() == null) {
            root = newParent;
        } else if (node == node.getParent().getLeftChild()) {
            node.getParent().setLeftChild(newParent);
        } else {
            node.getParent().setRightChild(newParent);
        }
        newParent.setRightChild(node);
        node.setParent(newParent);
    }




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

    private void addNodeToMatrix(String[][] matrix, RedBlackNode node, int level, int left, int right) {
        if (node == null) {
            return;
        }
        // Adiciona o nó na posição correta da matriz
        int mid = (left + right) / 2;
        matrix[level][mid] = node.getData() + (node.getColor() ? "R" : "B");

        // Recursivamente adiciona os filhos do nó na matriz
        addNodeToMatrix(matrix, node.getLeftChild(), level + 1, left, mid - 1);
        addNodeToMatrix(matrix, node.getRightChild(), level + 1, mid + 1, right);
    }

    private int getHeight(RedBlackNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getHeight(node.getLeftChild()), getHeight(node.getRightChild()));
    }
}
