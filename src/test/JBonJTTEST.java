package test;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

class ButtonCellRenderer extends AbstractCellEditor implements
                                                   TableCellRenderer,
                                                   TableCellEditor {

    private final JButton button;

    public ButtonCellRenderer(final JTable table, final DefaultTableModel model) {
        this.button = new JButton("Delete");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped(); // これが重要
            }
        });
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                if (table.columnAtPoint(point) == 0) {
                    fireEditingStopped();
                    int row = table.rowAtPoint(point);
                    if (row >= 0) {
                        model.removeRow(row);
                    }
                }
            }
        });
    }

    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int column) {
        return button;
    }

    public Component getTableCellEditorComponent(JTable table,
                                                 Object value,
                                                 boolean isSelected,
                                                 int row,
                                                 int column) {
        return button;
    }

    public Object getCellEditorValue() {
        return button.getText();
    }

}

class JButtonInJTableSample {

    private static void show() {
        String[] names = {"", "shape", "color"};
        Object[][] data = {{"", "sphere", "blue"}, {"", "cone", "red"}};
        DefaultTableModel model = new DefaultTableModel(data, names);
        JTable table = new JTable(model);
        ButtonCellRenderer renderer = new ButtonCellRenderer(table, model);
        TableColumn column0 = table.getColumnModel().getColumn(0);
        column0.setCellEditor(renderer);
        column0.setCellRenderer(renderer);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(table);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                show();
            }
        });
    }

}