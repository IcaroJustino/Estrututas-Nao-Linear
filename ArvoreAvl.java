package arvore;

import java.io.*;
import java.util.*;

public class ArvoreAvl {
    public class Node {
        private Node left, right;
        private int height = 1;
        private int value;

        private Node(int val) {
            this.value = val;
        }
    }

    private int height(Node N) {
        if (N == null)
            return 0;
        return N.height;
    }

    private Node insert(Node node, int value) {
        /* 1. Realiza uma inserção normal como uma arvore binaria */
        if (node == null) {
            return (new Node(value));
        }

        if (value < node.value)
            node.left = insert(node.left, value);
        else
            node.right = insert(node.right, value);

        /* 2. Atualiza a altura do seu nó anterior */
        node.height = Math.max(height(node.left), height(node.right)) + 1;

        /*
         * Pegar o Fator de balanceamento desse novo nó para ver se ele está
         * desbalanceado
         */
        int balance = getFatorBalanceamento(node);

        // Casos de nó desbalanceado e as rotações possiveis

        // Left Left Case (Rotação simples para direita )
        if (balance > 1 && value < node.left.value)
            return rightRotate(node);

        // Right Right Case (Rotação simples para esquerda )
        if (balance < -1 && value > node.right.value)
            return leftRotate(node);

        // Left Right Case (Rotação dupla Esquerda > Direita )
        if (balance > 1 && value > node.left.value) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case (Rotação dupla Direita > Esquerda )
        if (balance < -1 && value < node.right.value) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        /* Retorna o Nó no final */
        return node;
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Realiza a rotação para a direita
        x.right = y;
        y.left = T2;

        // Atualiza a altura do nó
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        // Retorna a nova raiz
        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Realiza a rotação para a esquerda
        y.left = x;
        x.right = T2;

        // Atualiza a altura do nó
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        // Retorna a nova raiz
        return y;
    }

    // Pegar o fator de balanceamento do Nó
    private int getFatorBalanceamento(Node N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    public void preOrder(Node root) {
        if (root != null) {
            preOrder(root.left);
            System.out.printf("%d ", root.value);
            preOrder(root.right);
        }
    }

    private Node minValueNode(Node node) {
        Node atual = node;
        /* loop para achar o nó folha com o menor valor para fazer o balanceamento */
        while (atual.left != null)
            atual = atual.left;
        return atual;
    }

    private Node deleteNode(Node root, int value) {
        // Primeiro passo: Realizar uma remoção comum igual a arvore binaria

        if (root == null)
            return root;
        // Se o valor deletado for menor que o valor da raiz então esse valor está na
        // sub arvore esquerda
        if (value < root.value)
            root.left = deleteNode(root.left, value);
        // Se o valor deletado for maior que o valor da raiz então o valor está na sub
        // arvore direita
        else if (value > root.value)
            root.right = deleteNode(root.right, value);
        // Se o valor for o mesmo da raiz então esse é o nó a ser deletado
        else {
            // Verifica se o nó tem apenas um filho ou é uma folha
            if ((root.left == null) || (root.right == null)) {

                Node temp;
                if (root.left != null)
                    temp = root.left;
                else
                    temp = root.right;

                // Caso não tenhha filho
                if (temp == null) {
                    temp = root;
                    root = null;
                } else // Caso tenha um filho
                    root = temp; // Copia o conteudo do filho que não ta vazio para essa variavel temporaria
                temp = null;
            } else {
                // No com dois filhos : Realiza uma viajem InOrder e pega o sucessor (menor
                // valor na sub-arvore direita )
                Node temp = minValueNode(root.right);

                // Coloca o sucessor nesse nó temporario
                root.value = temp.value;

                // No fim deleta o no
                root.right = deleteNode(root.right, temp.value);
            }
        }

        // Se a arvore so tiver um nó retorna
        if (root == null)
            return root;

        // Atualizar o valor da altura do nó atual
        root.height = Math.max(height(root.left), height(root.right)) + 1;

        // Verificar o fator de balanceaento desse nó para verificar se está balanceado
        int balance = getFatorBalanceamento(root);

        // Verificador de casos se o nó verificado estiver desbalanceado

        // Left Left (Rotação dupla para esquerda )
        if (balance > 1 && getFatorBalanceamento(root.left) >= 0)
            return rightRotate(root);

        // Left Right (Rotação dupla Esquerda > Direita )
        if (balance > 1 && getFatorBalanceamento(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Right (Rotação dupla para direita )
        if (balance < -1 && getFatorBalanceamento(root.right) <= 0)
            return leftRotate(root);

        // Right Left (Rotação dupla para Direita > Esquerda )
        if (balance < -1 && getFatorBalanceamento(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    public void print(Node root) {
        // Caso a arvore esteja vazia
        if (root == null) {
            System.out.println("(XXXXXX)");
            return;
        }

        int height = root.height,
                width = (int) Math.pow(2, height - 1);

        // Preparando as variaveis para poder percorrer a arvore
        List<Node> atual = new ArrayList<Node>(1),
                next = new ArrayList<Node>(2);
        atual.add(root);

        final int maxHalfLength = 4;
        int elements = 1;

        StringBuilder sb = new StringBuilder(maxHalfLength * width);
        for (int i = 0; i < maxHalfLength * width; i++)
            sb.append(' ');
        String textBuffer;

        // For que passa por cada altura da arvore .
        for (int i = 0; i < height; i++) {

            sb.setLength(maxHalfLength * ((int) Math.pow(2, height - 1 - i) - 1));

            // Criando um espaço para indicar quem é cada filho.
            textBuffer = sb.toString();

            // Pritar os Nos
            for (Node n : atual) {

                System.out.print(textBuffer);

                if (n == null) {

                    System.out.print("        ");
                    next.add(null);
                    next.add(null);

                } else {

                    System.out.printf("(%6d)", n.value);
                    next.add(n.left);
                    next.add(n.right);

                }

                System.out.print(textBuffer);

            }

            System.out.println();
            // Printar todos os nos da arvore passando de um em um.
            if (i < height - 1) {

                for (Node n : atual) {

                    System.out.print(textBuffer);

                    if (n == null)
                        System.out.print("        ");
                    else
                        System.out.printf("%s      %s",
                                n.left == null ? " " : "/", n.right == null ? " " : "\\");

                    System.out.print(textBuffer);

                }

                System.out.println();

            }

            // Renovando arvore para continuar imprimindo .
            elements *= 2;
            atual = next;
            next = new ArrayList<Node>(elements);

        }

    }

    public static void main(String args[]) {
        ArvoreAvl t = new ArvoreAvl();
        Node root = null;
        while (true) {
            System.out.println("(1) Inserir");
            System.out.println("(2) Remover");
            System.out.println("(3) Sair");

            try {
                BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                String s = bufferRead.readLine();

                if (Integer.parseInt(s) == 1) {
                    System.out.print("Insira um Valor: ");
                    root = t.insert(root, Integer.parseInt(bufferRead.readLine()));
                } else if (Integer.parseInt(s) == 2) {
                    System.out.print("Escolha o valor para ser deletado: ");
                    root = t.deleteNode(root, Integer.parseInt(bufferRead.readLine()));
                } else if (Integer.parseInt(s) == 3) {
                    System.out.print("Operação Finalizada: ");
                    break;
                } else {
                    System.out.println("Opção Invalida - Tente novamente ");
                    continue;
                }
                t.print(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}