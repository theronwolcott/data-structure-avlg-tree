# AVL-G Trees

## Overview
This project implements **AVL-G Trees**, a generalization of AVL Trees that allows tunable balance constraints through a parameter \( G \). AVL-G Trees maintain a balance factor (difference in height between left and right subtrees) of at most \( G \) for every node. This flexibility can reduce the frequency of rotations compared to classic AVL Trees (\( G = 1 \)), making AVL-G Trees suitable for applications where insertion and deletion performance is critical.

## Features

### Core Functionality
- **Insertion**: Adds a key to the AVL-G Tree while ensuring the balance condition (\( |\text{balance}| \leq G \)) is maintained. Triggers rotations if necessary.
- **Deletion**: Removes a key from the AVL-G Tree while maintaining the balance condition and updating heights appropriately.
- **Search**: Efficiently retrieves a key or confirms its absence.
- **Balancing Rotations**: Implements single and double rotations to restore balance after insertions or deletions.

### Utility Methods
- **isBST**: Verifies if the tree satisfies the binary search tree property.
- **isAVLGBalanced**: Confirms that the tree adheres to the balance constraints defined by \( G \).
- **Height Calculation**: Accurately computes the height of the tree or any subtree.

### Error Handling
- **Custom Exceptions**: Provides exceptions for invalid operations, such as attempting to delete a non-existent key or inserting a duplicate key.

## Key Concepts

### Height of a Binary Tree
- A null (empty) tree has a height of \( -1 \).
- A leaf node has a height of \( 0 \).
- A non-empty tree’s height equals the maximum height of its subtrees plus one.

### Balance Factor
- Defined as the difference in height between the left and right subtrees of a node.
- For AVL-G Trees, the balance factor \( |\text{balance}| \leq G \).

### Rotations
- **Single Rotation**: Performed when the imbalance can be corrected with a single rotation in one direction (left or right).
- **Double Rotation**: Required when the imbalance necessitates two rotations in opposite directions.

## Design Principles
- **Modularity**: Encapsulated methods for rotations, height updates, and balance checks.
- **Efficiency**: Minimized computational overhead by reducing rotations in trees with higher \( G \) values.
- **Robustness**: Thorough exception handling for edge cases and invalid operations.

## Project Highlights

### Code Structure
- **AVLGTree**: Core class implementing the AVL-G Tree logic.
- **Exceptions**: Package for custom exception handling to support robust operations.
- **StudentTests**: Unit tests for validating correctness and performance.

### Testing
- Comprehensive test cases for insertion, deletion, search, and balance checks.
- Used utility methods like `isBST` and `isAVLGBalanced` to verify tree integrity.
- Covered edge cases, including empty trees, single-node trees, and extreme \( G \) values.

### Challenges Overcome
- Managing rotations efficiently by identifying the correct case (single or double).
- Dynamically updating heights after structural changes.
- Ensuring that the tree remains a valid BST while meeting \( G \)-balance constraints.

## Applications
AVL-G Trees are well-suited for:
- **Databases**: Indexing and range queries.
- **Networking**: Routing table optimizations.
- **Real-Time Systems**: Balancing insertion/deletion latency and search performance.

## Future Improvements
- Implement additional balancing strategies to further optimize performance.
- Extend support for AVL-G Trees with dynamic \( G \) adjustment during runtime.
- Visualize rotations and tree structure changes for better debugging and understanding.

