package com.carlca.pluginmanager;

import net.byteseek.swing.treetable.TreeTableModel;

import javax.swing.*;
import javax.swing.tree.TreeNode;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class PluginManagerJTable extends JTable {

	@Override
	protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed) {
		if (ks.getKeyCode() == KeyEvent.VK_LEFT && !isEditing()) {
			if (getSelectedRow() >= 0 && getSelectedColumn() == 0) {
				TreeTableModel treeTableModel = (TreeTableModel)getModel();
				int selectedRow = getSelectedRow();
				// get the selected node
				TreeNode selectedNode = treeTableModel.getSelectedNode();
				if (selectedNode.isLeaf()) {
					TreeNode parentNode = selectedNode.getParent();
					// decrement selectedRow until we find the parent
					while (selectedRow > 0) {
						selectedRow--;
						TreeNode node = treeTableModel.getNodeAtModelIndex(selectedRow);
						if (node == parentNode) {
							// select the parent node
							setRowSelectionInterval(selectedRow, selectedRow);
							// confirm that the key has been processed
							return true;
						}
					}
					// confirm that the key has been processed
					return true;
				}
			}
		}
		return super.processKeyBinding(ks, e, condition, pressed);
	}

	private TreeNode[] getPathToRoot(TreeNode node) {
		List<TreeNode> path = new ArrayList<>();
		while (node != null) {
			path.add(0, node);
			node = node.getParent();
		}
		return path.toArray(new TreeNode[0]);
	}
}

