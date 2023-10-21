package com.carlca.pluginmanager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import org.javatuples.Triplet;
import org.jetbrains.annotations.NotNull;
import org.javatuples.Pair;

public class PluginManager extends JFrame {
	private JTable pluginGrid;
	private JPanel formPanel;
	private JPanel headerPanel;
	private JButton updateButton;
	private JProgressBar progressBar;
	private JLabel progressCaption;
	private JLabel progressPercent;
	private JLabel progressThisOfThat;
	private JComboBox pluginTypeSelector;
	private JPanel footerPanel;
	private JPanel gridPanel;
	private JScrollPane scrollPane;
	private JTable pluginTable;
	private JTree pluginTree;
	private PluginManagerScanner scanner;

	public static void main(String[] args) {
		PluginManager form = new PluginManager();
	}

	public PluginManager() {
		initializeUI();
		initializeClasses();
		initializeEvents();
	}

	public void initializeUI() {
		setSystemLookAndFeel();
		setContentPane(formPanel);
		setTitle("Plugin Manager");
		setSize(800, 600);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		removeDummyTreeNodes();
		setVisible(true);



		pluginTable.setRowHeight(20);
		DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) pluginTypeSelector.getModel();
		for (String type : getPluginTypes()) {
			model.addElement(type);
		}
	}

	private void setSystemLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void removeDummyTreeNodes() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		DefaultTreeModel emptyModel = new DefaultTreeModel(root);
		pluginTree.setModel(emptyModel);
	}

	private void initializeClasses() {
		scanner = new PluginManagerScanner();
	}

	private void initializeEvents() {
		updateButton.addActionListener(e -> {
			// Disable the updateButton to prevent multiple clicks
			updateButton.setEnabled(false);
			// Set the cursor to the wait cursor
   		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			// Allow selectedPlugInType to be usable by worker thread
			final AtomicReference<String> selectedPluginType = new AtomicReference<>("");
			// set selectedPluginType to the chosen type
			selectedPluginType.set(Objects.requireNonNull(pluginTypeSelector.getSelectedItem()).toString());
			// Set the progressCaption accordingly
			progressCaption.setText("Scanning for " + selectedPluginType + " plugins");
			// Create a separate instance of PluginManagerScanner for each plugin type
			SwingWorker<Void, Void> worker = getSwingWorker(selectedPluginType);
			worker.execute();
		});
	}

	@NotNull
	private SwingWorker<Void, Void> getSwingWorker(AtomicReference<String> selectedPluginType) {
		// Construct a SwingWorker thread to carry out the work without delaying the UI
		return new SwingWorker<>() {
			@Override
			protected Void doInBackground() throws Exception {
				scanner.processPlugins(selectedPluginType.get());
				return null;
			}
			@Override
			protected void done() {
				// Create the root node
				DefaultMutableTreeNode root = new DefaultMutableTreeNode("Plugins");
				// Iterate over the plugins and create child nodes for each manufacturer and plugin
				for (Triplet<String, String, String> plugin : scanner.getPlugins()) {
					String manufacturer = plugin.getValue0();
					String ident = plugin.getValue1();
					String pluginName = plugin.getValue2();
					// Find or create the manufacturer node
					DefaultMutableTreeNode manufacturerNode = findOrCreateManufacturerNode(root, manufacturer);
					// Create the plugin node
					DefaultMutableTreeNode pluginNode = new DefaultMutableTreeNode(new Pair<>(ident, pluginName));
					// Add the plugin node to the manufacturer node
					manufacturerNode.add(pluginNode);
					pluginNode.setAllowsChildren(false);
				}

				// OLD WAY USING JTREE
				// Create the tree table model using the root node
				DefaultTreeModel treeModel = new DefaultTreeModel(root);
				// Assign the tree model to pluginTree
				pluginTree.setModel(treeModel);


				// NEW WAY USING JTABLE
				// Create the tree table model using the root node
				PluginManagerTableModel treeTableModel = new PluginManagerTableModel(root, false);
				// Assign the pluginTable to the tree model
				treeTableModel.bindTable(pluginTable);
				updatePluginTableColWidths(treeTableModel);
				pluginTable.repaint();
				// listen for pluginTable mouse events
				pluginTable.getTableHeader().addMouseListener( new MouseAdapter() {
					public void mouseReleased(MouseEvent arg0) {
						updatePluginTableColWidths(treeTableModel);
						pluginTable.repaint();
					}});
 				// clear progress caption
				progressCaption.setText("");
				// Set the cursor back to the default cursor
        setCursor(Cursor.getDefaultCursor());
				// Enable the updateButton again	
				updateButton.setEnabled(true);
			}
		};
	}

	private void updatePluginTableColWidths(PluginManagerTableModel treeTableModel) {
		treeTableModel.clearColWidths();
		for (TableColumn column : Collections.list(pluginTable.getColumnModel().getColumns())) {
			treeTableModel.getColWidths().add(column.getWidth());
		}
	}

	// Helper method to find or create the manufacturer node
	private static DefaultMutableTreeNode findOrCreateManufacturerNode(DefaultMutableTreeNode root, String manufacturer) {
		Enumeration<TreeNode> enumeration = root.children();
		while (enumeration.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumeration.nextElement();
			if (node.getUserObject().equals(manufacturer)) {
				return node;
			}
		}
		DefaultMutableTreeNode manufacturerNode = new DefaultMutableTreeNode(manufacturer);
		root.add(manufacturerNode);
		return manufacturerNode;
	}

	private ArrayList<String> getPluginTypes() {
		return new ArrayList<>(Arrays.asList("VST","VST3","CLAP","DEMO"));
	}

	private void createUIComponents() {
		// TODO: place custom component creation code here
		pluginTable = new PluginManagerJTable();
	}
}
