/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easykampf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Omar
 */
public class GUI {

    private static JFrame mainFrame = new JFrame("Rohaldors Dojo");
    private static final JButton saveAsFile = new JButton("Speichern");
    private static final JButton loadFile = new JButton("Öffnen");
    private static final JCheckBox ignoreWounds = new JCheckBox("Wunden ignorieren");
    private JLabel versionDisplay = new JLabel();
    private JPopupMenu contextMenu = new JPopupMenu();
    private JTable table;
    private static JTextField dmgInput = new JTextField(4);
    private static JTextField roundCounter = new JTextField(4);
    private static JButton nextRound = new JButton("Nächste Runde");
    private static JButton resetRound = new JButton("Runden zurücksetzen");
    private static final JButton doDmg = new JButton("Schaden setzen");
    private int round = 1;

    public GUI() {

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setSize(1280, 720);
        table = new JTable(PersonCollection.getInstance()) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                row = convertRowIndexToModel(row);
                JComponent jc = (JComponent) c;
                if (!isRowSelected(row)) {
                    Color bc = PersonCollection.getInstance().getRowColour(row);
                    c.setBackground(bc);
                }
                return c;
            }
        };
        updatePersonModel();
        //table.setAutoCreateRowSorter(true);
        TableRowSorter<PersonCollection> sorter = new TableRowSorter<>(PersonCollection.getInstance());
        table.setRowSorter(sorter);
        sorter.setSortsOnUpdates(true);
        table.getRowSorter().toggleSortOrder(3);
        JScrollPane mainPane = new JScrollPane(table);
        mainPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        //mainPane.setToolTipText("Klicken Sie hier, um ein Kontextmenü für ein Buch anzuzeigen!");
        Box southBox = Box.createHorizontalBox();
        Box eastBox = Box.createVerticalBox();

        versionDisplay.setText(Version.getVersion());
        versionDisplay.setPreferredSize(new Dimension(50, 20));

        roundCounter.setText(Integer.toString(round));

        dmgInput.setMaximumSize(new Dimension(400, 30));
        eastBox.add(dmgInput);
        eastBox.add(ignoreWounds);
        eastBox.add(doDmg);

        southBox.add(roundCounter);
        southBox.add(nextRound);
        southBox.add(resetRound);

        southBox.add(loadFile);
        southBox.add(saveAsFile);
        southBox.add(versionDisplay);
        //mainFrame.add(table);
        mainFrame.add(mainPane);
        mainFrame.add(southBox, BorderLayout.SOUTH);
        mainFrame.add(eastBox, BorderLayout.EAST);

        setActionListeners();

        //mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(GetImage.getImage("bookpane.png")));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        new GUI();
    }

    private void updatePersonModel() {
        PersonCollection.getInstance().fireTableDataChanged();
    }

    private void setActionListeners() {
        doDmg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRows = table.getSelectedRow();
                if (selectedRows < 0) {
                    return;
                }

                String s = dmgInput.getText();

                int dmg = Integer.parseInt(s);

                int selectedRow = table.convertRowIndexToModel(selectedRows);

                PersonCollection.getInstance().applyPersonDmg(selectedRow, dmg, !ignoreWounds.isSelected());
                System.out.println(ignoreWounds.isSelected());
            }
        });

        table.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_DELETE || e.getKeyChar() == KeyEvent.VK_MINUS) {
                    deletePersons();
                } else if (e.getKeyChar() == '+') {
                    PersonCollection.getInstance().addPerson();
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            private void deletePersons() {

                int[] selectedRows = table.getSelectedRows();
                //Arrays.sort(selectedRows);
                //ArrayUtils.reverse(selectedRows);
                if (selectedRows.length == 0) {
                    return;
                }

                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    int selectedRow = table.convertRowIndexToModel(selectedRows[i]);
                    PersonCollection.getInstance().removePerson(selectedRow);

                }

                updatePersonModel();

            }

        });

        loadFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    //BookCollection1.setInstance((BookCollection1) Load.load("mbuecher.ser"));
                    PersonCollection.persons = (ArrayList<Person>) Load.load("kampf.ser");
                } catch (FileNotFoundException e1) {
                    try {
                        Save.saveAsFile("kampf.ser", null);
                    } catch (FileNotFoundException e2) {
                        e2.printStackTrace();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                } catch (IOException e1) {
                    try {
                        Save.saveAsFile("kampf.ser", null);
                    } catch (FileNotFoundException e2) {
                        e2.printStackTrace();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                updatePersonModel();
            }
        });
        saveAsFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    Save.saveAsFile("kampf.ser", PersonCollection.getInstance().persons);
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        nextRound.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                round++;
                roundCounter.setText(Integer.toString(round));
                PersonCollection.getInstance().removeTmpPersons();
                updatePersonModel();
            }

        });

        resetRound.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                round = 1;
                roundCounter.setText(Integer.toString(round));
                PersonCollection.getInstance().removeTmpPersons();
                updatePersonModel();
            }

        });

    }

}
