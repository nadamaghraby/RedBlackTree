package RedBlackTree;

public class RedBlackTree {
    private Node Root;
    private final Node NIL;

    public RedBlackTree() {
        NIL = new Node();
        NIL.NodeColor = 0;
        NIL.LeftNode = null;
        NIL.RightNode = null;
        Root = NIL;
    }

    public void rightRotate(Node right) {
        Node left = right.LeftNode;
        right.LeftNode = left.RightNode;
        if (left.RightNode != NIL) {
            left.RightNode.parent = right;
        }
        left.parent = right.parent;
        if (right.parent == null) {
            this.Root = left;
        } else if (right == right.parent.RightNode) {
            right.parent.RightNode = left;
        } else {
            right.parent.LeftNode = left;
        }
        left.RightNode = right;
        right.parent = left;
    }

    public void leftRotate(Node left) {
        Node right = left.RightNode;
        left.RightNode = right.LeftNode;
        if (right.LeftNode != NIL) {
            right.LeftNode.parent = left;
        }
        right.parent = left.parent;
        if (left.parent == null) {
            this.Root = right;
        } else if (left == left.parent.LeftNode) {
            left.parent.LeftNode = right;
        } else {
            left.parent.RightNode = right;
        }
        right.LeftNode = left;
        left.parent = right;
    }

    public Node Minimum(Node min) {
        while (min.LeftNode != NIL) {
            min = min.LeftNode;
        }
        return min;
    }

    public Node Maximum(Node max) {
        while (max.RightNode != NIL) {
            max = max.RightNode;
        }
        return max;
    }

    public void insert(int New) {
        Node NewNode = new Node();
        NewNode.parent = null;
        NewNode.element = New;
        NewNode.LeftNode = NIL;
        NewNode.RightNode = NIL;
        NewNode.NodeColor = 1;

        Node y = null;
        Node x = this.Root;

        while (x != NIL) {
            y = x;
            if (NewNode.element < x.element) {
                x = x.LeftNode;
            } else {
                x = x.RightNode;
            }
        }
        NewNode.parent = y;
        if (y == null) {
            Root = NewNode;
        } else if (NewNode.element < y.element) {
            y.LeftNode = NewNode;
        } else {
            y.RightNode = NewNode;
        }
        if (NewNode.parent == null) {
            NewNode.NodeColor = 0;
            return;
        }
        if (NewNode.parent.parent == null) {
            return;
        }
        Node u;
        while (NewNode.parent.NodeColor == 1) {
            if (NewNode.parent == NewNode.parent.parent.RightNode) {
                u = NewNode.parent.parent.LeftNode;
                if (u.NodeColor == 1) {
                    u.NodeColor = 0;
                    NewNode.parent.NodeColor = 0;
                    NewNode.parent.parent.NodeColor = 1;
                    NewNode = NewNode.parent.parent;
                } else {
                    if (NewNode == NewNode.parent.LeftNode) {
                        NewNode = NewNode.parent;
                        rightRotate(NewNode);
                    }
                    NewNode.parent.NodeColor = 0;
                    NewNode.parent.parent.NodeColor = 1;
                    leftRotate(NewNode.parent.parent);
                }
            } else {
                u = NewNode.parent.parent.RightNode;
                if (u.NodeColor == 1) {
                    u.NodeColor = 0;
                    NewNode.parent.NodeColor = 0;
                    NewNode.parent.parent.NodeColor = 1;
                    NewNode = NewNode.parent.parent;
                } else {
                    if (NewNode == NewNode.parent.RightNode) {
                        NewNode = NewNode.parent;
                        leftRotate(NewNode);
                    }
                    NewNode.parent.NodeColor = 0;
                    NewNode.parent.parent.NodeColor = 1;
                    rightRotate(NewNode.parent.parent);
                }
            }
            if (NewNode == Root) {
                break;
            }
        }
        Root.NodeColor = 0;
    }

    private void Transplant(Node x, Node y) {
        if (x.parent == null) {
            Root = y;
        } else if (x == x.parent.LeftNode) {
            x.parent.LeftNode = y;
        } else {
            x.parent.RightNode = y;
        }
        y.parent = x.parent;
    }

    private void DeleteNode(Node node, int delete) {
        Node z = NIL;
        Node x, y;
        while (node != NIL) {
            if (node.element == delete) {
                z = node;
            }
            if (node.element <= delete) {
                node = node.RightNode;
            } else {
                node = node.LeftNode;
            }
        }
        if (z == NIL) {
            System.out.println("Element is not in the Tree");
            return;
        }
        y = z;
        int yColor = y.NodeColor;
        if (z.LeftNode == NIL) {
            x = z.RightNode;
            Transplant(z, z.RightNode);
        } else if (z.RightNode == NIL) {
            x = z.LeftNode;
            Transplant(z, z.LeftNode);
        } else {
            y = Minimum(z.RightNode);
            yColor = y.NodeColor;
            x = y.RightNode;
            if (y.parent == z) {
                x.parent = y;
            } else {
                Transplant(y, y.RightNode);
                y.RightNode = z.RightNode;
                y.RightNode.parent = y;
            }
            Transplant(z, y);
            y.LeftNode = z.LeftNode;
            y.LeftNode.parent = y;
            y.NodeColor = z.NodeColor;
        }
        if (yColor == 0) {
            Node v;
            while (x != Root && x.NodeColor == 0) {
                if (x == x.parent.LeftNode) {
                    v = x.parent.RightNode;
                    if (v.NodeColor == 1) {
                        v.NodeColor = 0;
                        x.parent.NodeColor = 1;
                        leftRotate(x.parent);
                        v = x.parent.RightNode;
                    }
                    if (v.LeftNode.NodeColor == 0 && v.RightNode.NodeColor == 0) {
                        v.NodeColor = 1;
                        x = x.parent;
                    } else {
                        if (v.RightNode.NodeColor == 0) {
                            v.LeftNode.NodeColor = 0;
                            v.NodeColor = 1;
                            rightRotate(v);
                            v = x.parent.RightNode;
                        }
                        v.NodeColor = x.parent.NodeColor;
                        x.parent.NodeColor = 0;
                        v.RightNode.NodeColor = 0;
                        leftRotate(x.parent);
                        x = Root;
                    }
                } else {
                    v = x.parent.LeftNode;
                    if (v.NodeColor == 1) {
                        v.NodeColor = 0;
                        v.parent.NodeColor = 1;
                        rightRotate(x.parent);
                        v = x.parent.LeftNode;
                    }
                    if (v.RightNode.NodeColor == 0 && v.RightNode.NodeColor == 0) {
                        v.NodeColor = 1;
                        x = x.parent;
                    } else {
                        if (v.LeftNode.NodeColor == 0) {
                            v.RightNode.NodeColor = 0;
                            v.NodeColor = 1;
                            leftRotate(v);
                            v = x.parent.LeftNode;
                        }
                        v.NodeColor = x.parent.NodeColor;
                        x.parent.NodeColor = 0;
                        v.LeftNode.NodeColor = 0;
                        rightRotate(x.parent);
                        x = Root;
                    }
                }
            }
            x.NodeColor = 0;
        }
    }

    public void Delete(int data) {
        DeleteNode(this.Root, data);
    }

    public int GetRoot() {return this.Root.element;}
    public boolean Search(int val) {
        return Search(Root , val)
    }
    private boolean Search(Node r, int val){
        boolean found = false;
        while ((r != NIL) && !found){
            int key = r.element;
            if(val < key)
                r = r.LeftNode;
            else if (val > key)
                r = r.RightNode;
            else{
                found = true;
                break;
            }
            found = Search(r , val);
        }
        return found;
    }

    private void PreOrderTraversal(Node node) {
        if (node != NIL) {
            System.out.print(node.element + " ");
            PreOrderTraversal(node.LeftNode);
            PreOrderTraversal(node.RightNode);
        }
    }

    public void Preorder() {
        PreOrderTraversal(this.Root);
    }

    private void InOrderTraversal(Node node) {
        if (node != NIL) {
            InOrderTraversal(node.LeftNode);
            System.out.print(node.element + " ");
            InOrderTraversal(node.RightNode);
        }
    }

    public void Inorder() {
        InOrderTraversal(this.Root);
    }

    private void PostOrderTraversal(Node node) {
        if (node != NIL) {
            PostOrderTraversal(node.LeftNode);
            PostOrderTraversal(node.RightNode);
            System.out.print(node.element + " ");
        }
    }

    public void Postorder() {
        PostOrderTraversal(this.Root);
    }

    private void printTree(Node r, String i, boolean bool) {
        if (r != NIL) {
            System.out.print(i);
            if (bool) {
                System.out.print("R---");
                i += "     ";
            } else {
                System.out.print("L---");
                i += "|    ";
            }
            String Color = r.NodeColor == 1?"Red":"Black";
            System.out.println(r.element + "(" + Color + ")");
            printTree(r.LeftNode, i, false);
            printTree(r.RightNode, i, true);
        }
    }

    public void Print() {
        printTree(this.Root, "", true);
    }
}
