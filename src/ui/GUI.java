package ui;

import RedoLogic.ICommand;
import RedoLogic.RedoCommander;
import easykampf.Person;
import easykampf.PersonCollection;
import easykampf.PersonComboListModel;
import easykampf.SavedPersonCollection;
import easykampf.TextLogger;
import easykampf.Version;
import erinnerung.ErinnerungCollection;
import io.GetImage;
import io.Load;
import io.Save;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import javax.swing.RowSorter;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import logic.IsInteger;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.ListModel;

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
    private PersonCollection personCollection;
    private SavedPersonCollection savedPersonsCollection = new SavedPersonCollection();

    Font font = new Font("Courier", Font.PLAIN, 16);
    Font boldFont = new Font("Courier", Font.BOLD, 10);
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
    private javax.swing.JButton d20Button;
    private javax.swing.JButton d6Button;
    private javax.swing.JButton nextRoundButton;
    private javax.swing.JButton addSavedPersonButton;
    private javax.swing.JButton savePersonButton;
    private javax.swing.JButton redoButton;
    private javax.swing.JButton helpButton;
    private javax.swing.JButton resetRoundButton;
    private javax.swing.JLabel roundLabel;
    private javax.swing.JTextField rundenInput;
    private javax.swing.JButton saveButton;
    private JPopupMenu headerMenu;
    private RXTable table;
    private javax.swing.JList<String> erinnerungList;
    private JEditorPane textLog;
    private javax.swing.JButton undoButton;
    private javax.swing.JLabel versionLabel;
        private javax.swing.JScrollPane jScrollPane1;
        private JToolBar jToolBar1;
    JCheckBoxMenuItem ignoreLPItem;
    JCheckBoxMenuItem ALLYItem;
    JSeparator jSeparator3;
    JLabel savedPersonLabel;
    JButton saveSavedPersonButton;
    JButton loadSavedPersonButton;
    JPopupMenu popupMenu;

    DefaultCellEditor allyCE;
    DefaultCellEditor foeCE;

    javax.swing.JScrollPane jScrollPane3;

    PersonComboListModel foeModel;
    PersonComboListModel allyModel;

    // End of variables declaration
    /**
     * Creates new form NewJFrame
     */
    public GUI() {
        initComponents();
        loadButton.setMinimumSize(saveButton.getSize());

        setActionListeners();
        loadSavedPersons();
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
        
       
        personCollection = new PersonCollection();
        foeModel = new PersonComboListModel(personCollection.getFoeList());
        allyModel = new PersonComboListModel(personCollection.getAllyList());

        JComboBox<String> allyBox = new JComboBox<String>();
        allyBox.setModel(allyModel);
        allyCE = new DefaultCellEditor(allyBox);

        JComboBox<String> foeBox = new JComboBox<String>();

        foeBox.setModel(foeModel);
        foeCE = new DefaultCellEditor(foeBox);
        personCollection.setComboBoxModels(foeModel, allyModel);

        table = new RXTable(personCollection) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {

                Component c = super.prepareRenderer(renderer, row, column);

                int prow = convertRowIndexToModel(row);
                Color bc = personCollection.getRowColour(prow, isRowSelected(row));
                c.setBackground(bc);

                //c.setFont(c.getFont().deriveFont(16.0f), Font.BOLD);
                if (column == 0) {
                    c.setFont(boldFont);
                } else {
                    c.setFont(font);
                }
                return c;
            }

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                int modelColumn = convertColumnIndexToModel(column);
                int personRow = convertRowIndexToModel(row);
                //System.out.println("row " + personRow + " col " + modelColumn);

                if (modelColumn == personCollection.getKAMPF_GEGENColumnIndex()) {
                    Person p = personCollection.getPerson(personRow);
                    if (p.getALLY() == 0) {
                        return allyCE;
                    } else {
                        return foeCE;
                    }

                }
                return super.getCellEditor(row, column);

            }

        };

        table.setSelectAllForEdit(true);

        //table.setComponentPopupMenu(popupMenu);
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

        //hide the ally column
        table.getColumnModel().getColumn(personCollection.getColumnIndex("ALLY")).setMinWidth(0);
        table.getColumnModel().getColumn(personCollection.getColumnIndex("ALLY")).setMaxWidth(0);

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
         jScrollPane1 = new javax.swing.JScrollPane();
        savedPersonList = new javax.swing.JList();
        savedPersonList.setCellRenderer(new PersonListRenderer());
        saveButton = new javax.swing.JButton();
        loadButton = new javax.swing.JButton();
        erinnerungField = new HintTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jToolBar1 = new javax.swing.JToolBar();
        erinnerungList = new javax.swing.JList<String>();
        erinnerungInput = new javax.swing.JTextField(2);
        erinnerungButton = new javax.swing.JButton();
        textLog = new javax.swing.JEditorPane();
        versionLabel = new javax.swing.JLabel();
        undoButton = new javax.swing.JButton();
        redoButton = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        savedPersonLabel = new javax.swing.JLabel();
        saveSavedPersonButton = new javax.swing.JButton();
        loadSavedPersonButton = new javax.swing.JButton();
        d6Button = new javax.swing.JButton();
        d20Button = new javax.swing.JButton();
        helpButton = new JButton();
        jSeparator4 = new javax.swing.JSeparator();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        headerMenu = new JPopupMenu();
        
        
          for (String s : personCollection.getColumnNames()) {
            MenuItem item = new MenuItem(s);
            int colIndex = personCollection.getColumnIndex(s);
            TableColumn column = table.getColumnModel().getColumn(colIndex);
            item.setColumn(column);
            item.setMaxWidth(column.getMaxWidth());
            item.setMinWidth(column.getMinWidth());
            item.width = column.getPreferredWidth();

            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MenuItem item = (MenuItem) e.getSource();
                    TableColumn column = item.getColumn();

                    if (column.getPreferredWidth() == 0) {
                        column.setMaxWidth(item.getMaxWidth());
                        column.setMinWidth(item.getMinWidth());

                        column.setPreferredWidth(item.width);
                    } else {
                        column.setMaxWidth(0);
                        column.setMinWidth(0);
                        column.setPreferredWidth(0);
                    }


                }

            });

            headerMenu.add(item);
        }
        
        
        setTitle("Rohaldors Dojo");

        //logger = new TextLogger(textLog);
        undoButton.setVisible(true);
        redoButton.setVisible(true);

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

        d6Button.setText("W6 rollen");
        d20Button.setText("W20 rollen");

        //loadButton.setSize(saveButton.getSize());
        erinnerungField.setHint("Erinnerung hier setzen");
        erinnerungInput.setText("1");

        erinnerungButton.setText("Erinnern in Runden");
        erinnerungList.setModel(ErinnerungCollection.getInstance());
        textLog.setText("<i>Runde 1</i>");
        textLog.setEditable(false);
        //textLog.setColumns(20);
        //textLog.setRows(5);
        jScrollPane3.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);


        addSavedPersonButton.setText("←");

        savePersonButton.setText("→");

        textLog.setEditable(false);
        jScrollPane3.setViewportView(textLog);
        
         erinnerungList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(erinnerungList);

      javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1107, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 843, Short.MAX_VALUE)
                            .addComponent(erinnerungField))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(erinnerungInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(erinnerungButton)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(savedPersonLabel)
                                .addGap(174, 182, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(dmgLabel)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(rundenInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(ignoreWS)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ignoreRS))
                            .addComponent(jSeparator4)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(saveSavedPersonButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(loadSavedPersonButton))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(60, 60, 60)
                                        .addComponent(calcDmgButton))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(59, 59, 59)
                                        .addComponent(dmgInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(addSavedPersonButton)
                                        .addGap(7, 7, 7)
                                        .addComponent(savePersonButton))
                                    .addComponent(roundLabel)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(nextRoundButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(resetRoundButton)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(d6Button)
                                .addGap(18, 18, 18)
                                .addComponent(d20Button)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(versionLabel)))
                        .addContainerGap())))
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
                            .addComponent(addSavedPersonButton)))
                    .addComponent(dmgLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveSavedPersonButton)
                    .addComponent(loadSavedPersonButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(versionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(d6Button)
                        .addComponent(d20Button)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(erinnerungButton)
                            .addComponent(erinnerungInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(erinnerungField, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jToolBar1.setBorderPainted(false);

        loadButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/open.png"))); // NOI18N
        loadButton.setFocusable(false);
        loadButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        loadButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(loadButton);

        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/save.png"))); // NOI18N
        saveButton.setFocusable(false);
        saveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(saveButton);

        undoButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/undo.png"))); // NOI18N
        undoButton.setFocusable(false);
        undoButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        undoButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(undoButton);

        redoButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/redo.png"))); // NOI18N
        redoButton.setFocusable(false);
        redoButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        redoButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(redoButton);

        helpButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/questionmark.png"))); // NOI18N
        helpButton.setFocusable(false);
        helpButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        helpButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(helpButton);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                GUI g = new GUI();
                g.setIconImage(Toolkit.getDefaultToolkit().getImage(GetImage.getImage("list.png")));
                g.setVisible(true);
                g.setResizable(false);
            }
        });
    }

    private void loadSavedPersons() {
        try {
            savedPersonsCollection.persons = (ArrayList<Person>) Load.load("persons.ser");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        updateSavedPersonListModel();

    }

    private boolean couldPastePerson(String data) {
        String[] parts = data.split("\\t");
        int plen = parts.length;
        //letzte spalte vorhanden, aber leer
        if (data.endsWith("\t")) {
            plen++;
        }

        if (plen != personCollection.getColumnCount()) {
            System.out.println("pa " + parts.length + " col " + personCollection.getColumnCount());
            return false;
        }
        String pName = pName = parts[0];
        String pTp = null;
        //letzte spalte vorhanden, aber leer
        if (parts.length == personCollection.getColumnCount()) {
            pTp = parts[17];
        }

        ArrayList<Integer> cData = new ArrayList<>();
        for (int i = 1; i < parts.length; i++) {
            //alle zeilen außer dem namen sind vollzahlen. todo getColumnClass

            //13 kampf, 17 tp
            if (i != 13 && i != 17 && !IsInteger.isInteger(parts[i])) {
                return false;
            } else if (i != 13 && i != 17) {
                cData.add(Integer.parseInt(parts[i]));
            }

        }//TODO fight against = null
        Person p = new Person(pName, cData.get(0), cData.get(1), cData.get(2), cData.get(3), cData.get(4), cData.get(5), cData.get(6), cData.get(7), cData.get(8), cData.get(9), cData.get(10), cData.get(11), cData.get(12), cData.get(13), cData.get(14), pTp);
        ICommand redoit = () -> {
            personCollection.addPerson(p);
            updatePersonModel();
            return false;
        };
        ICommand undoit = () -> {
            personCollection.removePerson(p);
            //updatePersonModel(); todo inconsistent table update design in add/remove person
            return false;
        };
        RedoCommander.getInstance().addCommand(undoit, redoit);
        redoit.apply();

        //updatePersonModel();
        return true;

    }

    private void updatePersonModel() {
        personCollection.fireTableDataChanged();
    }

    private void showHelpFrame() {
        HelpFrame.showHelpFrame();

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
        for (Person p : SavedPersonCollection.persons) {
            model.addElement(p);
        }
        this.savedPersonList.setModel(model);
    }

    private void setActionListeners() {
        
              table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent m) {

                headerMenu.show(table.getTableHeader(), m.getX(), m.getY());

            }

        });
        
        
       
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                table.requestFocus();
                
                
                int row = table.rowAtPoint(e.getPoint());
   System.out.println("outside table, would be row " + row );

        //return this.table.rowAtPoint(p);            
    
                
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
                personCollection.fireRowUpdated(selectedRow);
                System.out.println(ignoreWS.isSelected());
            }
        });

        savedPersonList.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_MINUS) {
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

        table.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {

                boolean rightMouseButton = SwingUtilities.isRightMouseButton(e);

                if (!rightMouseButton) {
                    return;
                }

                int r = table.rowAtPoint(e.getPoint());
                if (r >= 0 && r < table.getRowCount()) {
                    table.setRowSelectionInterval(r, r);
                } else {
                    table.clearSelection();
                }

                int rowindex = table.getSelectedRow();
                if (rowindex < 0) {
                    return;
                }
                if (e.isPopupTrigger() && e.getComponent() instanceof RXTable) {

                    final JPopupMenu pMenu = new JPopupMenu();
                    final JCheckBoxMenuItem ignorelpItem = new JCheckBoxMenuItem("niedrige LP ignorieren");
                    final JCheckBoxMenuItem allyItem = new JCheckBoxMenuItem("Ally");
                    final JCheckBoxMenuItem resetItem = new JCheckBoxMenuItem("LP/Wunden/INI wiederherstellen");

                    pMenu.add(ignorelpItem);
                    pMenu.add(allyItem);
                    pMenu.add(resetItem);

                    int selectedRow = table.getSelectedRow();
                    if (selectedRow < 0) {
                        return;
                    }

                    selectedRow = table.convertRowIndexToModel(selectedRow);
                    Person p = PersonCollection.getPersonAt(selectedRow);
                    int allyNum = p.getALLY();
                    if (allyNum == 0) {
                        System.out.println("NO ALL");
                        allyItem.setSelected(false);
                    } else {
                        allyItem.setSelected(true);
                    }

                    ignorelpItem.setSelected(p.isIgnoreLowLP());

                    pMenu.show(e.getComponent(), e.getX(), e.getY());
                    table.setRowSelectionInterval(r, r);

                    allyItem.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int selectedRow = table.getSelectedRow();
                            if (selectedRow < 0) {
                                return;
                            }

                            int cselectedRow = table.convertRowIndexToModel(selectedRow);
                            Person p = PersonCollection.getPersonAt(cselectedRow);

                            //int allyNum = p.getALLY();
                            boolean isSelectedAlly = allyItem.isSelected();
                            if (isSelectedAlly) {
                                ICommand redoit = () -> personCollection.setPersonAlly(p, 1);
                                ICommand undoit = () -> personCollection.setPersonAlly(p, 0);
                                redoit.apply();
                                RedoCommander.getInstance().addCommand(undoit, redoit);
                                //personCollection.setPersonAlly(p, 1);
                                //p.setALLY(1);
                            } else {
                                ICommand redoit = () -> personCollection.setPersonAlly(p, 0);
                                ICommand undoit = () -> personCollection.setPersonAlly(p, 1);
                                redoit.apply();
                                RedoCommander.getInstance().addCommand(undoit, redoit);
                                //p.setALLY(0);
                            }
                            // PersonCollection.fireRowUpdated(selectedRow);
                            //table.setRowSelectionInterval(0, 0);

                            //workaround fuer bug, farbe hinter popup nicht geupdated
                            table.clearSelection();
                            table.setRowSelectionInterval(selectedRow, selectedRow);
                            //allyItem.setSelected(true);
                            //Point p = e.getSource();

                            // get the row index that contains that coordinate
                            //int rowNumber = table.rowAtPoint( p );
                            // Get the ListSelectionModel of the JTable
                            //ListSelectionModel model = table.getSelectionModel();
                            // set the selected interval of rows. Using the "rowNumber"
                            // variable for the beginning and end selects only that one row.
                            //model.setSelectionInterval( rowNumber, rowNumber );
                        }
                    });

                    ignorelpItem.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int selectedRow = table.getSelectedRow();
                            if (selectedRow < 0) {
                                return;
                            }

                            int cselectedRow = table.convertRowIndexToModel(selectedRow);
                            Person p = personCollection.getPersonAt(cselectedRow);

                            boolean isSelected = ignorelpItem.isSelected();
                            p.setIgnoreLowLP(isSelected);
//TODO ADD UNDO
                            table.setRowSelectionInterval(selectedRow, selectedRow);

                        }
                    });
                    
                    resetItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int selectedRow = table.getSelectedRow();
                            if (selectedRow < 0) {
                                return;
                            }

                            int cselectedRow = table.convertRowIndexToModel(selectedRow);
                            Person p = personCollection.getPersonAt(cselectedRow);
                            personCollection.resetPerson(p);
                             //table.setRowSelectionInterval(selectedRow, selectedRow);
                            
                        }
                        
                    });
                    

                }

            }
        });

        table.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_MINUS) {
                    deletePersons();
                } else if (e.getKeyCode() == 107) {
                    Person p = new Person();

                    ICommand redoit = () -> {
                        personCollection.addPerson(p);
                        updatePersonModel();
                        return false;
                    };

                    ICommand undoit = () -> {
                        personCollection.removePerson(p);
                        updatePersonModel();
                        return false;
                    };
                    RedoCommander.getInstance().addCommand(undoit, redoit);
                    redoit.apply();

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
                    for (String line : lines) {
                        if (!couldPastePerson(line)) {
                            return;
                        }
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

                ArrayList<Person> pToDelete = new ArrayList<>();
                for (int i = selectedRows.length - 1; i >= 0; i--) {

                    int selectedRow = table.convertRowIndexToModel(selectedRows[i]);
                    Person p = PersonCollection.getPersonAt(selectedRow);
                    pToDelete.add(p);

                }

                ICommand redoit = () -> {
                    for (Person p : pToDelete) {
                        personCollection.removePerson(p);
                    }
                    updatePersonModel();
                    return false;
                };

                ICommand undoit = () -> {
                    for (Person p : pToDelete) {
                        personCollection.addPerson(p);
                    }
                    updatePersonModel();
                    return false;
                };
                RedoCommander.getInstance().addCommand(undoit, redoit);
                redoit.apply();

                //updatePersonModel();
            }

        });

        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHelpFrame();

            }
        });
        
        d6Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int d6 = ThreadLocalRandom.current().nextInt(1, 7);
                                    TextLogger.getInstance().add("<b>" + d6 + "</b> gewürfelt mit einem W6-Würfel");

            }
        });
                
        d20Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                               int d20 = ThreadLocalRandom.current().nextInt(1, 7);
                                    TextLogger.getInstance().add("<b>" + d20 + "</b> gewürfelt mit einem W20-Würfel");

            }
        });
        

        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RedoCommander.getInstance().undoLast();
            }
        });

        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RedoCommander.getInstance().redoLast();
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

        addSavedPersonButton.addActionListener((ActionEvent e) -> {
            int[] selectedRows = savedPersonList.getSelectedIndices();
            if (selectedRows.length < 1) {
                return;
            }

            for (int i = 0; i < selectedRows.length; i++) {
                try {
                    Person row = (Person) savedPersonsCollection.getPerson(selectedRows[0]).clone();
                    int pGeneration = personCollection.getEnumPersonName(row);
                    row.setGeneration(pGeneration + 1);
                    personCollection.addPerson(row);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            updatePersonModel();
        });

        loadButton.addActionListener((ActionEvent e) -> {
            try {

                ArrayList<Person> loaded = (ArrayList<Person>) Load.load("kampf.ser");
                //personCollection.persons = null;
                personCollection.setPersons(loaded);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException | IOException ex) {
                ex.printStackTrace();
            }
            updatePersonModel();
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    Save.saveAsFile("kampf.ser", personCollection.getPersons());
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        loadSavedPersonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSavedPersons();
                updateSavedPersonListModel();
            }
        });

        saveSavedPersonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    Save.saveAsFile("persons.ser", savedPersonsCollection.persons);
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        erinnerungButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = erinnerungField.getText();
                if (s == null || s.isEmpty()) {
                    return;
                }
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
                addErinnerung(r + round,r, s);
                ErinnerungCollection.getInstance().update();
                erinnerungInput.setText("1");
                erinnerungField.setText(null);
            }

        });

        nextRoundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                round++;
                rundenInput.setText(Integer.toString(round));
                personCollection.removeTmpPersons();
                personCollection.resetAngPar();
                updatePersonModel();
                TextLogger.getInstance().add("<i>Runde " + Integer.toString(round) + "</i>");
                ArrayList<String> er = ErinnerungCollection.getInstance().getErinnerungFromRunde(round);
                for (String s : er) {
                    TextLogger.getInstance().add("Erinnerung: " + s, "#cc3300");
                    //textLog.setText("Erinnerung: " + s + "\n");
                }
                ErinnerungCollection.getInstance().updateRoundsLeft(round);
                 ErinnerungCollection.getInstance().update();
            }

        });

        resetRoundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                round = 1;
                rundenInput.setText(Integer.toString(round));
                personCollection.removeTmpPersons();
                ErinnerungCollection.getInstance().clear();
                TextLogger.getInstance().clear();
                TextLogger.getInstance().add("<i>Runden zurückgesetzt.<br>Runde 1</i>");
                //textLog.setText("Runden zurückgesetzt auf 1." + "\n");
                ErinnerungCollection.getInstance().update();
                updatePersonModel();
            }

        });
        
         erinnerungList.addMouseMotionListener(new MouseMotionAdapter() {
        @Override
        public void mouseMoved(MouseEvent e) {
            JList l = (JList)e.getSource();
            ListModel m = l.getModel();
            int index = l.locationToIndex(e.getPoint());
            if( index>-1 ) {
                l.setToolTipText(ErinnerungCollection.getInstance().getElementAt(index).toString());
            }
        }
    });

    }

    // liste für erinnerungen anzeigen todo
    public void addErinnerung(int totalrunden, int rundenLeft, String msg) {
        //textLog.setText(Integer.toString(runden)); //TODO round umbenennen
        ErinnerungCollection.getInstance().add(totalrunden, rundenLeft, msg);
        //todo
    }

    public void showDetails(Person p) {

        final JFrame detailFrame = new JFrame(p.toString());
        detailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailFrame.setSize(400, 400);
        detailFrame.setResizable(false);
        JTextArea deluxeInfos = new JTextArea(p.toString());
        deluxeInfos.setSize(400, 170);
        deluxeInfos.setLineWrap(true);
        deluxeInfos.setWrapStyleWord(true);
        deluxeInfos.setEditable(false);
        final JList notices = new JList();
        JButton addNotice = new JButton("Notiz hinzufügen");
        addNotice.setSize(100, 30);
        JButton removeNotice = new JButton("Notiz entfernen");
        removeNotice.setSize(100, 30);
        JButton close = new JButton("Schließen");
        removeNotice.setSize(30, 30);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setSize(400, 60);
        buttonPanel.add(addNotice);
        buttonPanel.add(removeNotice);
        buttonPanel.add(close);
        JScrollPane scrollList = new JScrollPane(notices);
        scrollList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollList.setMaximumSize(new Dimension(400, 170));
        Box contentBox = Box.createVerticalBox();
        contentBox.setSize(400, 400);
        contentBox.add(deluxeInfos);
        JLabel lblnotices = new JLabel("Notizen:");
        contentBox.add(lblnotices);
        contentBox.add(scrollList);
        contentBox.add(buttonPanel);
        detailFrame.add(contentBox);
        detailFrame.setVisible(true);

    }

}
