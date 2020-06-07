package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * 
 * Program that's used for storing and manipulating data in binary search tree format.
 * 
 * @author ilovrencic
 *
 */
public class UniqueNumbers {
	
	private static String END_OF_PROGRAM = "kraj";
	
	/**
	 * 
	 * Class that represents binary search tree node.
	 * 
	 */
	static class TreeNode {
		int value;
		TreeNode left;
		TreeNode right;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		TreeNode head = null;
		
		while(true) {
			System.out.print("Unesite broj > ");
			
			if(sc.hasNextInt()) {
				int value = sc.nextInt();
				
				if(containsValue(head, value)) {
					System.out.println("Broj već postoji. Preskačem!");
				} else {
					head = addNode(head,value);
					System.out.println("Dodano.");
				}
			} else {
				String element = sc.next();
				
				if(element.equals(END_OF_PROGRAM)) {
					printNodes(head);
					break;
				}
				
				System.out.format("'%s' nije cijeli broj.%n",element);
				
			}
		}
		
		sc.close();
	}
	
	/**
	 * Method used for storing values inside BST (binary search tree)
	 * 
	 * @param node TreeNode object that stores data
	 * @param value Value we want to add to the BST
	 * @return Reference that was passed via @param node 
	 */
	static TreeNode addNode(TreeNode node, int value) {
		if(node == null) {
			node = new TreeNode();
			node.value = value;
		} else {
			TreeNode head = node;
			
			while(true) {
				if( value < head.value ) {
					if( head.left != null ) {
						head = head.left;
					} else {
						head.left = new TreeNode();
						head.left.value = value;
						break;
					}
				} else if( value > head.value ) {
					if( head.right != null ) {
						head = head.right;
					} else {
						head.right = new TreeNode();
						head.right.value = value;
						break;
					}
				} else {
					break;
				}
			}
		}
		return node;
	}
	
	/**
	 * Method that's used for checking if the BST contains certain value
	 * 
	 * @param head node from which we are starting the check
	 * @param value integer value we are searching in BST
	 * @return true or false depending on whether we find the value or not
	 */
	static boolean containsValue(TreeNode head, int value) {
		if( head == null ) {
			return false;
		}
		
		while(true) {
			if( head.value == value ) {
					return true;
			} else if( value < head.value) {
				if( head.left != null) {
					head = head.left;
				} else {
					return false;
				}
			} else {
				if( head.right != null) {
					head = head.right;
				} else {
					return false;
				}
			}
		}
	}
	
	/**
	 * Method that's used for calculating the number nodes in the tree.
	 * 
	 * @param head 
	 * @return Returns an integer that represents the number of nodes in the tree
	 */
	static int treeSize(TreeNode head) {
		int nodeCounter = 0;

		while(true) {
			if(head != null) {
				nodeCounter++;
				if( head.left != null ) {
					nodeCounter += treeSize(head.left);
				}
				if( head.right != null ) {
					nodeCounter += treeSize(head.right);
				}
				return nodeCounter;
			} else {
				return nodeCounter;
			}
		}
	}
	
	/**
	 * Method that prints the nodes in an ascending order.
	 * 
	 * @param head
	 */
	static void printAscendingOrderNodes(TreeNode head) {
		if( head == null ) {
			return;
		} else {
			if( head.left != null ) {
				printAscendingOrderNodes(head.left);
			} 
				
			System.out.format(" %s ",head.value);
				
			if( head.right != null ) {
					printAscendingOrderNodes(head.right);
			}
		}
	}
	
	/**
	 * Method that prints the nodes in a descending order.
	 * 
	 * @param head
	 */
	static void printDescendingOrderNodes(TreeNode head) {
		if( head == null ) {
			return;
		} else {
			if( head.right != null ) {
				printDescendingOrderNodes(head.right);
			} 
				
			System.out.format(" %s ",head.value);
				
			if( head.left != null ) {
					printDescendingOrderNodes(head.left);
			}
		}
	}
	
	/**
	 * Method that print outs all the nodes
	 * 
	 * @param head
	 */
	static void printNodes(TreeNode head) {
		System.out.print("Ispis od najmanjeg: ");
		printAscendingOrderNodes(head);
		System.out.println("");
		System.out.print("Ispis od najveceg: ");
		printDescendingOrderNodes(head);
	}
	
	
}
