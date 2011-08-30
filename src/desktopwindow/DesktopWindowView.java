package desktopwindow;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import javax.swing.JFileChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.util.*;


public class DesktopWindowView extends FrameView {

  static int length = 0;
  static int pathy[];
  static int[][] gmatrix, tempg;

  public static int pathFinder(int grapho[][], int s, int t) {

        int d[] = new int[grapho.length];
        int prev[] = new int[grapho.length];
        boolean flags[] = new boolean[grapho.length];

        Arrays.fill(d, Integer.MAX_VALUE);
        Arrays.fill(flags, true);
        Arrays.fill(prev, -1);

        d[s] = 0;

        int i = 0, min = Integer.MAX_VALUE, cur = 0;
        while (i < grapho.length) {

            for (int j = 0; j < flags.length; j++) {
                if (min > d[j] && flags[j] != false) {
                    min = d[j];
                    cur = j;
                }
            }
            flags[cur] = false;

            for (int j = 0; j < grapho.length; j++) {
                if (grapho[cur][j] != 0 && d[j] > grapho[cur][j] + d[cur]) {
                    d[j] = grapho[cur][j] + d[cur];
                    prev[j] = cur;
                }
            }
            i++;
            min = Integer.MAX_VALUE;
        }

        Stack invpath = new Stack();

        pathPusher(prev, t, invpath);
        int[] path = new int[invpath.size() + 1];
        path[path.length - 1] = t;
        for (int j = 0; j < path.length - 1; j++) path[j] = (Integer) invpath.pop();
        pathy = path;
        if (d[t] != Integer.MAX_VALUE) return 0;
        else return 1;
    }

  public static void pathPusher(int[] prev, int to, Stack invpath) {
      if (prev[to] != -1) {
            invpath.push(prev[to]);
            pathPusher(prev, prev[to], invpath);
        }
    }

    public static int[][] fileRead() {

        JFileChooser fch = new JFileChooser();

        File f;
        try {
            f = new File(new File(".").getCanonicalPath());
            fch.setCurrentDirectory(f);
        } catch (IOException ex) {
            Logger.getLogger(DesktopWindowView.class.getName()).log(Level.SEVERE, null, ex);
        }

        int state = fch.showOpenDialog(fch);
        f = fch.getSelectedFile();

        if (f != null && state == JFileChooser.APPROVE_OPTION) {

            int[][] grapho = null;

            try {
                Scanner sc = new Scanner(f);

                length = sc.nextInt();
                grapho = new int[length][length];
                for (int i = 0; i < length; i++) {
                    for (int j = 0; j < length; j++) {
                        grapho[i][j] = sc.nextInt();
                    }
                }
                sc.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e, "Ошибка чтения файла", JOptionPane.ERROR_MESSAGE);
            }

            return grapho;
        } else {
            return null;
        }
    }

    public static void fileWriteGraph(int[][] grapho) {
        JFileChooser fch = new JFileChooser();

        File f;
        try {
            f = new File(new File(".").getCanonicalPath());
            fch.setCurrentDirectory(f);
        } catch (IOException ex) {
            Logger.getLogger(DesktopWindowView.class.getName()).log(Level.SEVERE, null, ex);
        }

        int state = fch.showSaveDialog(fch);
        f = fch.getSelectedFile();

        if (f != null && state == JFileChooser.APPROVE_OPTION) {

            try {
                PrintWriter pw = new PrintWriter(f);

                pw.println(length);
                for (int i = 0; i < length; i++) {
                    for (int j = 0; j < length; j++) {
                        pw.print(grapho[i][j] + " ");
                    }
                    pw.println();
                }
                pw.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e, "Ошибка записи файла", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public static void fileWriteText(String oa) {
        JFileChooser fch = new JFileChooser();

        File f;
        try {
            f = new File(new File(".").getCanonicalPath());
            fch.setCurrentDirectory(f);
        } catch (IOException ex) {
            Logger.getLogger(DesktopWindowView.class.getName()).log(Level.SEVERE, null, ex);
        }

        int state = fch.showSaveDialog(fch);
        f = fch.getSelectedFile();

        if (f != null && state == JFileChooser.APPROVE_OPTION) {

            try {
                PrintWriter pw = new PrintWriter(f);

                pw.println(oa);

                pw.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e, "Ошибка записи файла", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void fileRand() {
       int[][] grapho = new int[100][100];

       Random r = new Random();

        for (int i = 0; i < grapho.length; i++) {
            for (int j = 0; j < grapho.length; j++) {
                if (i == j) grapho[i][j] = 0;
                else grapho[i][j] = r.nextInt(100);
            }
        }

        try {
           PrintWriter pw = new PrintWriter ("100.txt");

            pw.print(grapho.length + "\n");
            for (int i = 0; i < grapho.length; i++){
                for (int j = 0; j < grapho.length; j++){
                    pw.print(grapho[i][j] + " ");
                    }
                pw.println();
            }
            pw.close();
       }

       catch (IOException e) {
           JOptionPane.showMessageDialog(null, e, "Ошибка записи файла", JOptionPane.ERROR_MESSAGE);
       }
    }

  public static int getMin2Max(int[][] grapho, int k, int l) {

      int Pi = 0;

      int maxK = 0;
      for (int i = 0; i < grapho.length; i++) {
          if (grapho[k][i] > maxK) maxK = grapho[k][i];
      }

      int maxL = 0;
      for (int i = 0; i < grapho.length; i++) {
          if (grapho[i][l] > maxL) maxL = grapho[i][l];
      }

      Pi = (maxK > maxL) ? maxL : maxK;      

      return Pi;
  }

  public static int[][] graphoMod(int[][] grapho, int Pi) {
      int[][] gMod = new int[grapho.length][grapho.length];
        for (int i = 0; i < gMod.length; i++) {
            for (int j = 0; j < gMod.length; j++) {
                if (grapho[i][j] < Pi && i != j) {
                    gMod[i][j] = 0;
                }
                else {
                    gMod[i][j] = grapho[i][j];
                }
            }
        }
        return gMod;
    }

  public static void printG(int[][] grapho) {
      System.out.println("---------------");
        for (int i = 0; i < grapho.length; i++) {
            for (int j = 0; j < grapho.length; j++) {
                System.out.print(grapho[i][j] + " ");
            }
            System.out.println();
        }
      System.out.println("---------------");
    }

  public static int[][] copyG(int[][] source, int[][] dest) {
    dest = new int[source.length][source.length];
    for (int i = 0; i < source.length; i++) {
        System.arraycopy(source[i], 0, dest[i], 0, source.length);
    }
    return dest;
  }

  public void table(final int[][] g) {

            final int[] names = new int [length];
            for (int i = 0; i < length; i++) {
                names[i] = i;
            }

            AbstractTableModel model = new AbstractTableModel() {

            public int getColumnCount() {
                return g.length;
            }

            public int getRowCount() {
                return g.length;
            }

            @Override
            public String getColumnName(int col) {
                return String.valueOf(names[col]);
            }

            public String getRowName(int col) {
                return String.valueOf(names[col]);
            }

            public Object getValueAt(int row, int col) {
                return g[row][col];
            }

            @Override
            public void setValueAt(Object obj, int row, int col) {
                Integer numba = new Integer(obj.toString());
                if (row == col) numba = 0;
                else {
                    if(numba.intValue() >= 0) g[row][col] = numba.intValue();
                    else JOptionPane.showMessageDialog(null, "Только положительные числа!", "Ошибка", JOptionPane.WARNING_MESSAGE);
                }
                fireTableCellUpdated(row, col);
            }

            @Override
            public boolean isCellEditable(int row, int col) {
                return true;
            }

            @Override
            public Class getColumnClass(int c) {
                return getValueAt(0, c).getClass();
            }
        };

        jTable1.setModel(model);
  }

  public void algo(int k, int l) {
    int succes, zeroCheckK, zeroCheckL, totalSpeed = 0;
    int[][] grapho = gmatrix;
    int[][] gMod = null;

    tempg = copyG(grapho, tempg);

    int Pi = getMin2Max(tempg, k, l);

    do {

      do {
          gMod = graphoMod(tempg, Pi);
          succes = pathFinder(gMod, k, l);
          Pi--;
      }
      while (succes == 1);

      Pi++;

      for (int i = 0; i < pathy.length - 1; i++) {
          tempg[pathy[i]][pathy[i+1]] -= Pi;
      }

      succes = 1;
      

      outarea.append("Маршрут: [ ");
      for (int i = 0; i < pathy.length - 1; i++) {
          outarea.append(pathy[i] + " -> ");
      }
      outarea.append(pathy[pathy.length - 1] + " ]\n");

        if (totalSpeed == 0) {
            outarea.append("Максимальная пропускная способность = " + Pi + "\n\n");
        }
        else {
            outarea.append("Пропускная способность = " + Pi + "\n\n");
        }
      totalSpeed += Pi;

      // Проверка
      zeroCheckK = 0;
      for (int i = 0; i < grapho.length; i++) {
          if (tempg[k][i] == 0) zeroCheckK++;
      }

      zeroCheckL = 0;
      for (int j = 0; j < grapho.length; j++) {
          if (tempg[j][l] == 0) zeroCheckL++;
      }
    }
    while (zeroCheckK != grapho.length && zeroCheckL != grapho.length);

    outarea.append("\nОбщая пропускная способность = " + totalSpeed + "\n\n");
    }

    public DesktopWindowView(SingleFrameApplication app) {
        super(app);

        initComponents();

    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = DesktopWindowApp.getApplication().getMainFrame();
            aboutBox = new DesktopWindowAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        DesktopWindowApp.getApplication().show(aboutBox);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        outarea = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        startButton = new javax.swing.JButton();
        open = new javax.swing.JButton();
        saveResult = new javax.swing.JButton();
        saveFile = new javax.swing.JButton();
        createRand = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();

        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(725, 350));

        jScrollPane1.setName("jScrollPane1"); // NOI18N
        jScrollPane1.setPreferredSize(new java.awt.Dimension(300, 100));

        jTable1.setName("jTable1"); // NOI18N
        jScrollPane1.setViewportView(jTable1);

        jScrollPane2.setName("jScrollPane2"); // NOI18N
        jScrollPane2.setPreferredSize(new java.awt.Dimension(325, 100));

        outarea.setColumns(20);
        outarea.setFont(new java.awt.Font("Segoe UI", 0, 12));
        outarea.setRows(5);
        outarea.setFocusable(false);
        outarea.setName("outarea"); // NOI18N
        jScrollPane2.setViewportView(outarea);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(desktopwindow.DesktopWindowApp.class).getContext().getResourceMap(DesktopWindowView.class);
        jTextField1.setText(resourceMap.getString("jTextField1.text")); // NOI18N
        jTextField1.setName("jTextField1"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jTextField2.setText(resourceMap.getString("jTextField2.text")); // NOI18N
        jTextField2.setName("jTextField2"); // NOI18N

        startButton.setText(resourceMap.getString("startButton.text")); // NOI18N
        startButton.setName("startButton"); // NOI18N
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        open.setText(resourceMap.getString("open.text")); // NOI18N
        open.setName("open"); // NOI18N
        open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openActionPerformed(evt);
            }
        });

        saveResult.setText(resourceMap.getString("saveResult.text")); // NOI18N
        saveResult.setName("saveResult"); // NOI18N
        saveResult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveResultActionPerformed(evt);
            }
        });

        saveFile.setText(resourceMap.getString("saveFile.text")); // NOI18N
        saveFile.setName("saveFile"); // NOI18N
        saveFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveFileActionPerformed(evt);
            }
        });

        createRand.setText(resourceMap.getString("createRand.text")); // NOI18N
        createRand.setName("createRand"); // NOI18N
        createRand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createRandActionPerformed(evt);
            }
        });

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jTextField3.setText(resourceMap.getString("jTextField3.text")); // NOI18N
        jTextField3.setName("jTextField3"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                        .addGap(18, 18, 18))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(82, 82, 82)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(startButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(open, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                        .addGap(41, 41, 41)))
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(4, 4, 4)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(createRand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(saveFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(saveResult, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(createRand)
                    .addComponent(open)
                    .addComponent(saveFile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startButton)
                    .addComponent(saveResult))
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE))
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N
        menuBar.setPreferredSize(new java.awt.Dimension(650, 21));

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem1);

        jMenuItem2.setText(resourceMap.getString("jMenuItem2.text")); // NOI18N
        jMenuItem2.setName("jMenuItem2"); // NOI18N
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem2);

        jMenuItem3.setText(resourceMap.getString("jMenuItem3.text")); // NOI18N
        jMenuItem3.setName("jMenuItem3"); // NOI18N
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem3);

        jSeparator1.setName("jSeparator1"); // NOI18N
        fileMenu.add(jSeparator1);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(desktopwindow.DesktopWindowApp.class).getContext().getActionMap(DesktopWindowView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        jMenuItem4.setText(resourceMap.getString("jMenuItem4.text")); // NOI18N
        jMenuItem4.setName("jMenuItem4"); // NOI18N
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem5.setText(resourceMap.getString("jMenuItem5.text")); // NOI18N
        jMenuItem5.setName("jMenuItem5"); // NOI18N
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        menuBar.add(jMenu1);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setComponent(mainPanel);
        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        if (this.jTextField1.getText().isEmpty() || this.jTextField2.getText().isEmpty() || length == 0) {
            JOptionPane.showMessageDialog(null, "Введите значения", "Ошибка", JOptionPane.WARNING_MESSAGE);
        }
        else {
            int start = Integer.parseInt(this.jTextField1.getText());
            int end = Integer.parseInt(this.jTextField2.getText());
            if (start == end) {
                JOptionPane.showMessageDialog(null, "Источник и приемник совпадают", "Ошибка", JOptionPane.WARNING_MESSAGE);
            }
            else {
                if (start < 0 || end < 0) {
                    JOptionPane.showMessageDialog(null, "Номер вершины должен быть положительным", "Ошибка", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    if (start >= length || end >= length) {
                        JOptionPane.showMessageDialog(null, "Слишком большой номер вершины", "Ошибка", JOptionPane.WARNING_MESSAGE);
                    }
                    else {
                        outarea.setText("");
                        algo(start, end);
                    }
                }
            }
        }
    }//GEN-LAST:event_startButtonActionPerformed

    private void openActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openActionPerformed
        int[][] grapho = fileRead();

        if (grapho != null) {        
            table(grapho);

            gmatrix = grapho;
            }
    }//GEN-LAST:event_openActionPerformed

    private void saveResultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveResultActionPerformed
        String oa = outarea.getText();

        fileWriteText(oa);
    }//GEN-LAST:event_saveResultActionPerformed

    private void saveFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveFileActionPerformed
        fileWriteGraph(gmatrix);
    }//GEN-LAST:event_saveFileActionPerformed

    private void createRandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createRandActionPerformed
        if (this.jTextField3.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Введите размер", "Ошибка", JOptionPane.WARNING_MESSAGE);
        }
        else {
            if (Integer.parseInt(this.jTextField3.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "Количество вершин должно быть положительным", "Ошибка", JOptionPane.WARNING_MESSAGE);
            }
            else {
            int size = Integer.parseInt(this.jTextField3.getText());

            int[][] grapho = new int[size][size];

            Random r = new Random();

            for (int i = 0; i < grapho.length; i++) {
                for (int j = 0; j < grapho.length; j++) {
                    if (i == j) grapho[i][j] = 0;
                    else grapho[i][j] = r.nextInt(100);
                }
            }

            length = grapho.length;

            table(grapho);

            gmatrix = grapho;
            }
        }
    }//GEN-LAST:event_createRandActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        openActionPerformed(evt);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        saveFileActionPerformed(evt);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        createRandActionPerformed(evt);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        startButtonActionPerformed(evt);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        saveResultActionPerformed(evt);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createRand;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JButton open;
    private javax.swing.JTextArea outarea;
    private javax.swing.JButton saveFile;
    private javax.swing.JButton saveResult;
    private javax.swing.JButton startButton;
    // End of variables declaration//GEN-END:variables

    private JDialog aboutBox;
}
