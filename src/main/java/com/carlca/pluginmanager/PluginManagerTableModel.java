package com.carlca.pluginmanager;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;

import net.byteseek.swing.treetable.TreeTableHeaderRenderer;
import net.byteseek.swing.treetable.TreeTableModel;
import net.byteseek.swing.treetable.TreeUtils;

public class PluginManagerTableModel extends TreeTableModel {

	private Icon leafIcon;              // tree node that allows no children.
	private Icon openIcon;              // tree node displaying children.
	private Icon closedIcon;            // tree node not displaying children.

	private final DefaultTreeModel treeModel;
	private final ArrayList<String> columnNames;

	public PluginManagerTableModel(TreeNode rootNode, boolean showRoot) {
		super(rootNode, showRoot);
		// Create the DefaultTreeModel with the root node
		treeModel = new DefaultTreeModel(rootNode);

		// Define the column names
		columnNames = new ArrayList<String>();
		columnNames.add("Manufacturer");
		columnNames.add("Ident");
		columnNames.add("Plugin");
	}

	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		return false; // No cells are editable
	}

	@Override
	public Class<?> getColumnClass(final int columnIndex) {
		if (columnIndex >= 0 && columnIndex <= 2) {
			return String.class;
		}
		return Object.class;
	}

	@Override
	public Object getColumnValue(TreeNode node, int column) {
		return TreeUtils.getUserObject(node);
	}

	@Override
	protected TableColumnModel createTableColumnModel() {
		TableColumnModel result = new DefaultTableColumnModel();
		result.addColumn(createColumn(0, "manufacturer"));
		result.addColumn(createColumn(1, "ident"));
		result.addColumn(createColumn(2, "plugin"));
		return result;
	}

	@Override
	public Icon getNodeIcon(TreeNode node) {
		if (node != null) {
			if (node.getAllowsChildren()) {
				return isExpanded(node) ? openIcon : closedIcon;
			}
			return leafIcon;
		}
		return null;
	}

	private void setIcons() {
		if (UIManager.getLookAndFeel().getID().equals("GTK")) {
			setLeafIcon(UIManager.getIcon("FileView.fileIcon"));
			setOpenIcon(UIManager.getIcon("FileView.directoryIcon"));
			setClosedIcon(UIManager.getIcon("FileView.directoryIcon"));
		} else {
			// Leaf, open and closed icons not available in all look and feels...not in GTK, but is in metal...
			setLeafIcon(UIManager.getIcon("Tree.leafIcon"));
			setOpenIcon(UIManager.getIcon("Tree.openIcon"));
			setClosedIcon(UIManager.getIcon("Tree.closedIcon"));
		}
	}

	public void setLeafIcon(final Icon leafIcon) {
		this.leafIcon = leafIcon;
	}

	public void setClosedIcon(final Icon closedIcon) {
		this.closedIcon = closedIcon;
	}

	public void setOpenIcon(final Icon openIcon) {
		this.openIcon = openIcon;
	}

	@Override
	public int getColumnCount() {
		return columnNames.size();
	}

	@Override
	public String getColumnName(int column) {
		return columnNames.get(column);
	}
}

