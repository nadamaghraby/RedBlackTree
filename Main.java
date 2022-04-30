package RedBlackTree;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        RedBlackTree RBT = new RedBlackTree();
        boolean choice = true;
        while ( choice ) {
            System.out.println("\nOperations:\n");
            System.out.println("1-Insert Element");
            System.out.println("2-Search For Element");
            System.out.println("3-Delete Element");
            System.out.println("4-Get Root of Tree");
            System.out.println("5-Print Tree");
            int number = scan.nextInt();
            switch (number) {
                case 1:
                    System.out.println("Enter integer element to insert");
                    RBT.insert(scan.nextInt());
                    break;
                case 2:
                    System.out.println("Enter integer element to search");
                    System.out.println("Search result : " + RBT.Search(scan.nextInt()));
                    break;
                case 3:
                    System.out.println("Enter the element you want to delete");
                    RBT.Delete(scan.nextInt());
                    break;
                case 4:
                    System.out.println("The Root is : " + RBT.GetRoot());
                    break;
                case 5:
                    RBT.Print();
                    break;
                default:
                    System.out.println("Wrong Entry \n ");
                    break;
            }
            System.out.print("\nTree postorder Traversal: ");
            RBT.Postorder();
            System.out.print("\nTree preorder Traversal : ");
            RBT.Preorder();
            System.out.print("\nTree inorder Traversal : ");
            RBT.Inorder();
            System.out.println("\nDo you want to continue? (Yes or No) \n");
            String c = scan.next();
            if (c.equals("Yes") || c.equals("yes")) {
                choice = true;
            }else if (c.equals("No") || c.equals("no")){
                choice = false;
            }else {
                System.out.println("Not a valid answer");
            }
        }
    }
}


