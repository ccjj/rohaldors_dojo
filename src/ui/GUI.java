package ui;

import erinnerung.ErinnerungCollection;
import io.Load;
import easykampf.Person;
import easykampf.PersonCollection;
import io.Save;
import easykampf.SavedPersonsCollection;
import easykampf.TextLogger;
import easykampf.Version;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.RowSorter;

import javax.swing.SortOrder;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Omar
 */
public class GUI extends javax.swing.JFrame {

    private int round = 1;
    private PersonCollection personCollection = new PersonCollection();
    private SavedPersonsCollection savedPersonsCollection = new SavedPersonsCollection();

    Font font = new Font("Courier", Font.PLAIN, 16);
    Font boldFont = new Font("Courier", Font.BOLD, 16);
    // Variables declaration - do not modify                     
    private javax.swing.JButton calcDmgButton;
    private javax.swing.JTextField dmgInput;
    private javax.swing.JLabel dmgLabel;
    private javax.swing.JButton erinnerungButton;
    HintTextField erinnerungField;
    private javax.swing.JTextField erinnerungInput;
    private javax.swing.JCheckBox ignoreRS;
    private javax.swing.JCheckBox ignoreWS;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JList savedPersonList;
    private javax.swing.JButton loadButton;
    private javax.swing.JButton nextRoundButton;
    private javax.swing.JButton addSavedPersonButton;
    private javax.swing.JButton savePersonButton;
    private javax.swing.JButton redoButton;
    private javax.swing.JButton resetRoundButton;
    private javax.swing.JLabel roundLabel;
    private javax.swing.JTextField rundenInput;
    private javax.swing.JButton saveButton;
    private RXTable table;
    private javax.swing.JTextArea textLog;
    private javax.swing.JButton undoButton;
    private javax.swing.JLabel versionLabel;
    JSeparator jSeparator3;
    JLabel savedPersonLabel;
    JButton saveSavedPersonButton;
    JButton loadSavedPersonButton;
    javax.swing.JScrollPane jScrollPane1;

    javax.swing.JScrollPane jScrollPane3;

    // End of variables declaration  
    /**
     * Creates new form NewJFrame
     */
    public GUI() {
        initComponents();
        setActionListeners();
        table.requestFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table = new RXTable(personCollection) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {

                Component c = super.prepareRenderer(renderer, row, column);

                JComponent jc = (JComponent) c;
                if (!isRowSelected(row)) {
                    row = convertRowIndexToModel(row);
                    Color bc = personCollection.getRowColour(row);
                    c.setBackground(bc);
                }
                //c.setFont(c.getFont().deriveFont(16.0f), Font.BOLD);
                if (column == 0) {
                    c.setFont(boldFont);
                } else {
                    c.setFont(font);
                }
                return c;
            }
        };

        table.setSelectAllForEdit(true);

        //table.setAutoCreateRowSorter(true);
        TableRowSorter<PersonCollection> sorter = new TableRowSorter<>(personCollection);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(4, SortOrder.DESCENDING));

        sorter.setSortKeys(sortKeys);

        //sorter.setComparator(3, new IntComparator());
        table.setRowSorter(sorter);
        sorter.setSortsOnUpdates(true);
        table.getRowSorter().toggleSortOrder(3);
        table.setRowHeight(40);
        //personCollection.addPerson();
        updatePersonModel();
        dmgLabel = new javax.swing.JLabel();
        dmgInput = new javax.swing.JTextField(3);
        ignoreWS = new javax.swing.JCheckBox();
        ignoreRS = new javax.swing.JCheckBox();
        calcDmgButton = new javax.swing.JButton();
        addSavedPersonButton = new javax.swing.JButton();
        savePersonButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        rundenInput = new javax.swing.JTextField(2);
        roundLabel = new javax.swing.JLabel();
        nextRoundButton = new javax.swing.JButton();
        resetRoundButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        savedPersonList = new javax.swing.JList();
        saveButton = new javax.swing.JButton();
        loadButton = new javax.swing.JButton();
        erinnerungField = new HintTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        erinnerungInput = new javax.swing.JTextField(2);
        erinnerungButton = new javax.swing.JButton();
        textLog = new javax.swing.JTextArea();
        versionLabel = new javax.swing.JLabel();
        undoButton = new javax.swing.JButton();
        redoButton = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        savedPersonLabel = new javax.swing.JLabel();
        saveSavedPersonButton = new javax.swing.JButton();
        loadSavedPersonButton = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rohaldors Dojo");

        //logger = new TextLogger(textLog);

        undoButton.setVisible(false);
        redoButton.setVisible(false);
        
        TextLogger.getInstance().setTextField(textLog);
        jScrollPane2.setViewportView(table);

        dmgLabel.setText("Schaden");
        dmgLabel.setFont(boldFont);

        dmgInput.setText("0");

        ignoreWS.setText("Wunden ignorieren");

        ignoreRS.setText("RS ignorieren");

        calcDmgButton.setText("Schaden setzen");

        versionLabel.setText(Version.getVersion());
        savedPersonLabel.setText("Personen speichern/laden");
        savedPersonLabel.setFont(boldFont);
        saveSavedPersonButton.setText("Personen speichern");
        loadSavedPersonButton.setText("Personen laden");
        rundenInput.setText("1");
        rundenInput.setEditable(false);

        roundLabel.setText("Runden");
        roundLabel.setFont(boldFont);

        nextRoundButton.setText("nächste Runde");

        resetRoundButton.setText("Runden zurücksetzen");

        jScrollPane4.setViewportView(savedPersonList);

        saveButton.setText("Kampf speichern");

        loadButton.setText("Kampf laden");
 
        //loadButton.setSize(saveButton.getSize());

        erinnerungField.setHint("Erinnerung hier setzen"); 
        erinnerungInput.setText("1");

        erinnerungButton.setText("Erinnern in Runden:");

        textLog.setText("Runde 1");
        textLog.setEditable(false);
        textLog.setColumns(20);
        textLog.setRows(5);
        jScrollPane1.setViewportView(textLog);
        undoButton.setText("↺");

        redoButton.setText("↻");

        addSavedPersonButton.setText("←");

        savePersonButton.setText("→");

      javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 944, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(erinnerungField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(erinnerungInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(erinnerungButton)
                        .addGap(79, 79, 79))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(saveButton)
                            .addComponent(loadButton, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(roundLabel)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(nextRoundButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(resetRoundButton)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(ignoreWS)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ignoreRS))
                                    .addComponent(jSeparator4))))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator2)
                                    .addComponent(jScrollPane4)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(60, 60, 60)
                                                .addComponent(calcDmgButton))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(59, 59, 59)
                                                .addComponent(dmgInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(saveSavedPersonButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(loadSavedPersonButton))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(8, 8, 8)
                                                .addComponent(addSavedPersonButton)
                                                .addGap(7, 7, 7)
                                                .addComponent(savePersonButton)))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(undoButton)
                                .addGap(18, 18, 18)
                                .addComponent(redoButton)
                                .addGap(50, 50, 50)
                                .addComponent(versionLabel)
                                .addGap(28, 28, 28))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(savedPersonLabel)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(dmgLabel)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(rundenInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(dmgInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ignoreWS)
                            .addComponent(ignoreRS))
                        .addGap(6, 6, 6)
                        .addComponent(calcDmgButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(roundLabel)
                        .addGap(24, 24, 24)
                        .addComponent(rundenInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nextRoundButton)
                            .addComponent(resetRoundButton))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(savedPersonLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(savePersonButton)
                            .addComponent(addSavedPersonButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(loadSavedPersonButton)
                            .addComponent(saveSavedPersonButton))
                        .addContainerGap(39, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(dmgLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(erinnerungField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(erinnerungButton)
                                        .addComponent(erinnerungInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(versionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(redoButton)
                                        .addComponent(undoButton))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(saveButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(loadButton)))
                        .addGap(11, 11, 11))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    
    private boolean couldPastePerson(String data){
                            String[] parts = data.split("\\t");
                    if (parts.length != personCollection.getColumnCount()) {
                        return false;
                    }

                    String pName = parts[0];
                    ArrayList<Integer> cData = new ArrayList<>();
                    for (int i = 1; i < parts.length; i++) {
                        //alle zeilen außer dem namen sind vollzahlen. todo getColumnClass
                        if (!isInteger(parts[i])) {
                            return false;
                        }
                        cData.add(Integer.parseInt(parts[i]));
                    }
                    Person p = new Person(pName, cData.get(0), cData.get(1), cData.get(2), cData.get(3), cData.get(4), cData.get(5), cData.get(6), cData.get(7), cData.get(8), cData.get(9), cData.get(10), cData.get(11));
                    personCollection.addPerson(p);
                    updatePersonModel();
        return true;
        
        
    }
    
    
    private void updatePersonModel() {
        personCollection.fireTableDataChanged();
    }

             private void deleteSavedPersons() {
                int[] selectedRows = savedPersonList.getSelectedIndices();

                if (selectedRows.length == 0) {
                    return;
                }

                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    //int selectedRow = table.convertRowIndexToModel(selectedRows[i]);
                    savedPersonsCollection.removePerson(selectedRows[i]);
                }

                updateSavedPersonListModel();
            }
                
    public void updateSavedPersonListModel() {
        //fireContentsChanged(savedPersonsCollection, 1, 1);
        DefaultListModel model = new DefaultListModel();
        for (Person p : SavedPersonsCollection.persons) {
            model.addElement(p);
        }
        this.savedPersonList.setModel(model);
    }

    private void setActionListeners() {
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                table.requestFocus();
            }
        });

        table.getParent().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                table.requestFocus();
            }
        });

        calcDmgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRows = table.getSelectedRow();
                if (selectedRows < 0) {
                    return;
                }

                String s = dmgInput.getText();

                int dmg = Integer.parseInt(s);

                int selectedRow = table.convertRowIndexToModel(selectedRows);

                personCollection.applyPersonDmg(selectedRow, dmg, !ignoreWS.isSelected(), !ignoreRS.isSelected());
                System.out.println(ignoreWS.isSelected());
            }
        });

        savedPersonList.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_DELETE || e.getKeyChar() == KeyEvent.VK_MINUS) {
                    deleteSavedPersons();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
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
                    personCollection.addPerson();
                } else if ((e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
                    String data = null;
                    try {
                        data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                    } catch (UnsupportedFlavorException | IOException ex) {
                        Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (data == null) {
                        return;
                    }
                    String[] lines = data.split("\\n");
                    for(String line : lines){
                        if (!couldPastePerson(line))
                            return;
                    }
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
                    personCollection.removePerson(selectedRow);

                }

                updatePersonModel();

            }

        });

        savePersonButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow < 0) {
                    return;
                }
                selectedRow = table.convertRowIndexToModel(selectedRow);
                Person row = personCollection.getPerson(selectedRow);
                savedPersonsCollection.addPerson(row);
                updateSavedPersonListModel();

            }
        });

        addSavedPersonButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int[] selectedRows = savedPersonList.getSelectedIndices();
                if (selectedRows.length < 1) {
                    return;
                }

                for (int i = 0; i < selectedRows.length; i++) {
                    Person row = savedPersonsCollection.getPerson(selectedRows[0]);
                    PersonCollection.persons.add(row);

                }
                updatePersonModel();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                    //ArrayList<Person> p = new ArrayList<>();
                    //personCollection.persons = null;
                    personCollection.persons = (ArrayList<Person>) Load.load("kampf.ser");
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

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    Save.saveAsFile("kampf.ser", personCollection.persons);
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        loadSavedPersonButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    savedPersonsCollection.persons = (ArrayList<Person>) Load.load("persons.ser");
                } catch (FileNotFoundException e1) {
                    try {
                        Save.saveAsFile("persons.ser", null);
                    } catch (FileNotFoundException e2) {
                        e2.printStackTrace();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                } catch (IOException e1) {
                    try {
                        Save.saveAsFile("persons.ser", null);
                    } catch (FileNotFoundException e2) {
                        e2.printStackTrace();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                updateSavedPersonListModel();
            }
        });

        saveSavedPersonButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    Save.saveAsFile("persons.ser", savedPersonsCollection.persons);
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        erinnerungButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s = erinnerungField.getText();
                if(s == null || s.isEmpty())
                    return;
                String input = erinnerungInput.getText();
                int r;
                try {
                    r = Integer.parseInt(input);
                    if (r < 1) {
                        return;
                    }
                } catch (NumberFormatException ex) {
                    return;
                }
                addErinnerung(r + round, s);
                erinnerungField.setText(null);
            }

        });

        nextRoundButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                round++;
                rundenInput.setText(Integer.toString(round));
                personCollection.removeTmpPersons();
                updatePersonModel();
                TextLogger.getInstance().add("Runde " + Integer.toString(round));
                ArrayList<String> er =  ErinnerungCollection.getInstance().getErinnerungFromRunde(round);
                for(String s : er){
                    TextLogger.getInstance().add("Erinnerung: " + s);
                    //textLog.setText("Erinnerung: " + s + "\n");
                }
            }

        });

        resetRoundButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                round = 1;
                rundenInput.setText(Integer.toString(round));
                personCollection.removeTmpPersons();
                ErinnerungCollection.getInstance().clear();
                TextLogger.getInstance().clear();
                TextLogger.getInstance().add("Runden zurückgesetzt auf 1.");
                //textLog.setText("Runden zurückgesetzt auf 1." + "\n");
                updatePersonModel();
            }

        });

    }

    //TODO erinnerungen zurücksetzen bei runden zurücksetzen
    // bei nächster runde, erinnerungen -1 anzeigen
    // liste für erinnerungen
    public void addErinnerung(int totalrunden, String msg) {
        //textLog.setText(Integer.toString(runden)); //TODO round umbenennen
        ErinnerungCollection.getInstance().add(totalrunden, msg);
        //todo
    }

    //todo move
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

}
