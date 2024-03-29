package com.issyhome.JavaMestra.test;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ListDemo extends JFrame implements ListSelectionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = -5018463736188742951L;
    private DroppableList list;
    private JTextField fileName;

    public ListDemo()
    {
        super("ListDemo");

        //Create the list and put it in a scroll pane
        list = new DroppableList();
        @SuppressWarnings("unchecked")
		DefaultListModel listModel = (DefaultListModel<Hashtable<?, ?>>)list.getModel();
        list.setCellRenderer(new CustomCellRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        JScrollPane listScrollPane = new JScrollPane(list);

        String dirName = new String("c:\\");
        String filelist[] = new File(dirName).list();
        for (int i=0; i < filelist.length ; i++ )
        {
            String thisFileSt = dirName+filelist[i];
            File thisFile = new File(thisFileSt);
            if (thisFile.isDirectory())
                continue;
            try {
                listModel.addElement(makeNode(thisFile.getName(),
                        thisFile.toURL().toString(),
                        thisFile.getAbsolutePath()));
            } catch (java.net.MalformedURLException e){
            }
        }

        fileName = new JTextField(50);
        int i = list.getSelectedIndex();
        i = 0;
        String name = listModel.getElementAt(i).toString();
        fileName.setText(name);

        //Create a panel that uses FlowLayout (the default).
        JPanel buttonPane = new JPanel();
        buttonPane.add(fileName);

        Container contentPane = getContentPane();
        contentPane.add(listScrollPane, BorderLayout.CENTER);
        contentPane.add(buttonPane, BorderLayout.NORTH);
    }

    public void valueChanged(ListSelectionEvent e)
    {
        if (e.getValueIsAdjusting() == false)
        {
            fileName.setText("");
            if (list.getSelectedIndex() != -1)
            {
                String name = list.getSelectedValue().toString();
                fileName.setText(name);
            }
        }
    }

    private static Hashtable<String, String> makeNode(String name,
            String url, String strPath)
    {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("name", name);
        hashtable.put("url", url);
        hashtable.put("path", strPath);
        return hashtable;
    }

    public class DroppableList extends JList     
            implements DropTargetListener, DragSourceListener, DragGestureListener
    {
        /**
		 * 
		 */
		private static final long serialVersionUID = -3757003337479444641L;
		DropTarget dropTarget = new DropTarget (this, this);
        DragSource dragSource = DragSource.getDefaultDragSource();

        public DroppableList()
        {
            dragSource.createDefaultDragGestureRecognizer(
                    this, DnDConstants.ACTION_COPY_OR_MOVE, this);
            setModel(new DefaultListModel());
        }

        public void dragDropEnd(DragSourceDropEvent DragSourceDropEvent){}
        public void dragEnter(DragSourceDragEvent DragSourceDragEvent){}
        public void dragExit(DragSourceEvent DragSourceEvent){}
        public void dragOver(DragSourceDragEvent DragSourceDragEvent){}
        public void dropActionChanged(DragSourceDragEvent DragSourceDragEvent){}

        public void dragEnter (DropTargetDragEvent dropTargetDragEvent)
        {
            dropTargetDragEvent.acceptDrag (DnDConstants.ACTION_COPY_OR_MOVE);
        }

        public void dragExit (DropTargetEvent dropTargetEvent) {}
        public void dragOver (DropTargetDragEvent dropTargetDragEvent) {}
        public void dropActionChanged (DropTargetDragEvent dropTargetDragEvent){}

        public synchronized void drop (DropTargetDropEvent dropTargetDropEvent)
        {
            try
            {
                Transferable tr = dropTargetDropEvent.getTransferable();
                if (tr.isDataFlavorSupported (DataFlavor.javaFileListFlavor))
                {
                    dropTargetDropEvent.acceptDrop (
                            DnDConstants.ACTION_COPY_OR_MOVE);
                    java.util.List<Object> fileList = (java.util.List) tr.getTransferData(DataFlavor.javaFileListFlavor);
                    Iterator<Object> iterator = fileList.iterator();
                    while (iterator.hasNext())
                    {
                        File file = (File)iterator.next();
                        if (file.isFile()) {
                            Hashtable<String, String> hashtable = new Hashtable<String, String>();
                            hashtable.put("name",file.getName());
                            hashtable.put("url",file.toURL().toString());
                            hashtable.put("path",file.getAbsolutePath());
                            ((DefaultListModel)getModel()).addElement(hashtable);
                        }
                        else {
                            if (file.isDirectory()) {
                                System.err.println ("Directory is rejected!");
                            }
                        }
                    }
                    dropTargetDropEvent.getDropTargetContext().dropComplete(true);
                } else {
                    System.err.println ("Rejected");
                    dropTargetDropEvent.rejectDrop();
                }
            } catch (IOException io) {
                io.printStackTrace();
                dropTargetDropEvent.rejectDrop();
            } catch (UnsupportedFlavorException ufe) {
                ufe.printStackTrace();
                dropTargetDropEvent.rejectDrop();
            }
        }

        public void dragGestureRecognized(DragGestureEvent dragGestureEvent)
        {
            if (getSelectedIndex() == -1)
                return;
            Object obj = getSelectedValue();
            if (obj == null) {
                // Nothing selected, nothing to drag
                System.out.println ("Nothing selected - beep");
                getToolkit().beep();
            } else {
                Hashtable<?, ?> table = (Hashtable<?, ?>)obj;
                FileSelection transferable =
                    new FileSelection(new File((String)table.get("path")));
                dragGestureEvent.startDrag(
                        DragSource.DefaultCopyDrop,
                        transferable,
                        this);
            }
        }
    }

    public class CustomCellRenderer implements ListCellRenderer
    {
        DefaultListCellRenderer listCellRenderer =
            new DefaultListCellRenderer();
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean selected, boolean hasFocus)
        {
            listCellRenderer.getListCellRendererComponent(
                    list, value, index, selected, hasFocus);
            listCellRenderer.setText(getValueString(value));
            return listCellRenderer;
        }
        private String getValueString(Object value)
        {
            String returnString = "null";
            if (value != null) {
                if (value instanceof Hashtable) {
                    Hashtable h = (Hashtable)value;
                    String name = (String)h.get("name");
                    String url = (String)h.get("url");
                    returnString = name + " ==> " + url;
                } else {
                    returnString = "X: " + value.toString();
                }
            }
            return returnString;
        }
    }

    public class FileSelection extends Vector<Object> implements Transferable
    {
        /**
		 * 
		 */
		private static final long serialVersionUID = -6605279668124589751L;
		final static int FILE = 0;
        final static int STRING = 1;
        final static int PLAIN = 2;
        DataFlavor flavors[] = {DataFlavor.javaFileListFlavor,
                DataFlavor.stringFlavor,
                // 13th January 2024 - deprecated
                DataFlavor.plainTextFlavor};

        public FileSelection(File file)
        {
            addElement(file);
        }
        
        /* Returns the array of flavors in which it can provide the data. */
        public synchronized DataFlavor[] getTransferDataFlavors() {
            return flavors;
        }
        
        /* Returns whether the requested flavor is supported by this object. */
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            boolean b  = false;
            b |= flavor.equals(flavors[FILE]);
            b |= flavor.equals(flavors[STRING]);
            b |= flavor.equals(flavors[PLAIN]);
            return (b);
        }
        /**
         * If the data was requested in the "java.lang.String" flavor,
         * return the String representing the selection.
         */
        public synchronized Object getTransferData(DataFlavor flavor)
        throws UnsupportedFlavorException, IOException {
            if (flavor.equals(flavors[FILE])) {
                return this;
            } else if (flavor.equals(flavors[PLAIN])) {
                return new StringReader(((File)elementAt(0)).getAbsolutePath());
            } else if (flavor.equals(flavors[STRING])) {
                return((File)elementAt(0)).getAbsolutePath();
            } else {
                throw new UnsupportedFlavorException(flavor);
            }
        }
    }

    public static void main(String s[])
    {
        JFrame frame = new ListDemo();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.pack();
        frame.setVisible(true);
    }
}
