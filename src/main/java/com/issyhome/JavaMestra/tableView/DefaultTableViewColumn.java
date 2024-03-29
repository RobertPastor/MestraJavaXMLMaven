package com.issyhome.JavaMestra.tableView;

import java.lang.reflect.Method;
import java.text.Format;
import java.util.Comparator;

/**
 * This class provides default implementations for the <code>TableViewColumn</code> interface.
 *
 * @author Stephane Brunner, Last modified by: $Author: stbrunner $ 
 * @version $Revision: 1.12 $ $Date: 2004/09/05 19:22:16 $.
 * Revision history:
 * $Log: DefaultTableViewColumn.java,v $
 * Revision 1.12  2004/09/05 19:22:16  stbrunner
 * add select by first letter
 *
 * Revision 1.11  2004/05/31 14:39:13  stbrunner
 * *** empty log message ***
 *
 * Revision 1.10  2004/02/15 19:29:42  stbrunner
 * *** empty log message ***
 *
 * Revision 1.9  2003/06/06 06:31:32  stbrunner
 * Change description
 *
 */
public class DefaultTableViewColumn extends AbstractTableViewColumn {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1186971108386359784L;
	private final String mName;
    private final java.lang.reflect.Method mGetter;
    // Robert 29th July 2016 - add mGetter argument
    private String mGetterArgument = null;
    
    private final java.lang.reflect.Method mSetter;

    private final Comparator<Object> mComparator;
    private final boolean mDefaultVisible;
    private final java.text.Format mFormat;
    private final Class<Object> mColumnClass;
    private boolean mSortable;
    private boolean mSearchable;

    /**
     * Constructor declaration.
     * @param pName column name
     */
    public DefaultTableViewColumn(String pName) {
        this(pName, null);
    }

    /**
     * Constructor declaration.
     * @param pName column name
     * @param pGetter the getter method
     */
    public DefaultTableViewColumn(String pName, Method pGetter) {
        this(pName, pGetter, null);
    }

    /**
     * Constructor declaration
     *
     * @param pName column name
     * @param pGetter the getter method
     * @param pSetter the setter method
     */
    public DefaultTableViewColumn(String pName, Method pGetter, Method pSetter) {
        this(pName, null, pGetter, pSetter);
    }

    /**
     * Constructor declaration
     *
     * @param pName column name
     * @param pComparator the comparator of rows Objects for the current column
     * @param pGetter the getter method
     * @param pSetter the setter method
     */
    public DefaultTableViewColumn(String pName, Comparator<Object> pComparator,
            Method pGetter, Method pSetter) {
        this(pName, pComparator, pGetter, pSetter, true);
    }

    public DefaultTableViewColumn(String pName, Comparator<Object> pComparator,
    		Method pGetter) {
    	this(pName, pComparator, pGetter, (Method) null);
    }
    /**
     * Constructor declaration
     * @param pName column name
     * @param pComparator the comparator of rows Objects for the current column
     * @param pGetter the getter method
     * @param pSetter the setter method
     * @param pDefaultVisible the default visibility
     */
    public DefaultTableViewColumn(String pName, Comparator<Object> pComparator,
            Method pGetter, Method pSetter, boolean pDefaultVisible) {
        this(pName, pComparator, pGetter, pSetter, pDefaultVisible, null);
    }

    /**
     * Constructor declaration
     * @param pName column name
     * @param pGetter the getter method
     * @param pSetter the setter method
     * @param pDefaultVisible the default visibility
     * @param pFormat the formater
     */
    public DefaultTableViewColumn(String pName, Method pGetter, Method pSetter,
            boolean pDefaultVisible, Format pFormat) {
        this(pName, null, pGetter, pSetter, true, pFormat);
    }

    /**
     * Constructor declaration
     * @param pName column name
     * @param pGetter the getter method
     * @param pSetter the setter method
     * @param pDefaultVisible the default visibility
     */
    public DefaultTableViewColumn(String pName, Method pGetter, Method pSetter,
            boolean pDefaultVisible) {
        this(pName, null, pGetter, pSetter, pDefaultVisible, null);
    }

    /**
     * Constructor declaration<br>
     * column class = String if format not null else return type of getter
     * sortable and searchable = true is getter return type is comparable.
     *
     * @param pName column name
     * @param pComparator the comparator of rows Objects for the current column (if null create a default - use comparable
     * interface)
     * @param pGetter the getter method (if null the getValue must be redefine)
     * @param pSetter the setter method (if null is not editable)
     * @param pDefaultVisible the default visibility
     * @param pFormat the formatter
     */
    public DefaultTableViewColumn(String pName, Comparator<?> pComparator,
            Method pGetter, Method pSetter, boolean pDefaultVisible,
            Format pFormat) {
        Class get = (pGetter == null) ? Object.class : pGetter.getReturnType();
        mFormat = pFormat != null ? pFormat : getFormat(get);
        mColumnClass = (mFormat != null) ? (Class) String.class : get;
        mComparator = (Comparator<Object>) (pComparator != null ? pComparator : getComparator(get));
        mDefaultVisible = pDefaultVisible;
        mName = pName;
        mGetter = pGetter;
        mSetter = pSetter;

        mSortable = true; //Comparable.class.isAssignableFrom(get) || comparator != null;
        mSearchable = mSortable;
    }

    /**
     * Constructor declaration
     * @param pName column name
     * @param pComparator the comparator of rows Objects for the current column
     * @param pGetter the getter method
     * @param pSetter the setter method
     * @param pDefaultVisible the default visibility
     * @param pFormat the formatter
     * @param pSortable is searchable
     * @param pSearchable is sortable
     */
    public DefaultTableViewColumn(String pName, Comparator<Object> pComparator,
            Method pGetter, Method pSetter, boolean pDefaultVisible,
            Format pFormat, boolean pSortable, boolean pSearchable) {
        this(pName, pComparator, pGetter, pSetter, pDefaultVisible, pFormat);

        mSortable = pSortable;
        mSearchable = pSearchable;
    }

    
    /**
     * method added to allow for passing argument to the getter method
     * @since 29th July 2016
     * 
     * @param mestraStyleHeader
     * @param comparator
     * @param declaredMethod
     * @param mestraStyleHeader
     */
    public DefaultTableViewColumn(String pName,
			Comparator<Object> pComparator, 
			Method pGetter,
			String mestraStyleHeader) {
    	this(pName, pComparator, pGetter, (Method) null);
    	this.mGetterArgument = mestraStyleHeader;
	}

	/**
     * Returns the column name.
     * @return the column name
     */
    public String getName() {
        return mName;
    }

    /**
     * Returns the column class.
     * @return the column class
     */
    public Class<Object> getColumnClass() {
        return mColumnClass;
    }

    /**
     * Returns the column comparator.
     * @return the column comparator
     */
    public Comparator<Object> getComparator() {
        return mComparator;
    }

    /**
     * Create default formatter, if getter class is Date use DateFormat.getDateTimeInstance() formatter
     * @param pGet getter class
     * @return the new formatter
     */
    private Format getFormat(Class<Object> pGet) {
        if (java.util.Date.class.isAssignableFrom(pGet)) {
            return java.text.DateFormat.getDateTimeInstance();
        }
        return null;
    }

    /**
     * Create default comparator
     * @param pGet getter class
     * @return the new comparator
     */
    private Comparator<Object> getComparator(Class<Object> pGet) {
        if (Comparable.class.isAssignableFrom(pGet)) {
            return new Comparator<Object>() {
                /**
                 * Compares its two arguments for order.
                 *
                 * @param pO1 the first object to be compared.
                 * @param pO2 the second object to be compared.
                 * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to,
                 *    or greater than the second.
                 */
                public int compare(Object pO1, Object pO2) {
                    try {
                        pO1 = mGetter.invoke(pO1, (Object[])null);
                        pO2 = mGetter.invoke(pO2, (Object[])null);
                        if (pO2 == null && pO1 == null) {
                            return 0;
                        }
                        if (pO2 == null) {
                            return -1;
                        }
                        if (pO1 == null) {
                            return 1;
                        }
                        return ((Comparable<Object>) pO1).compareTo(pO2);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        return 0;
                    }
                }

            };
        }
        else {
            return new Comparator<Object>() {
                /**
                 * Compares its two arguments for order.
                 * @param pO1 the first object to be compared.
                 * @param pO2 the second object to be compared.
                 * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to,
                 *    or greater than the second.
                 */
                public int compare(Object pO1, Object pO2) {
                    return String.valueOf(getValue(pO1)).compareTo(
                            String.valueOf(getValue(pO2)));
                }

            };
        }
    }

    /**
     * Return the cell value.
     * @param pRowObject the row Object
     * @return the cell value
     */
    public Object getValue(Object pRowObject) {
    	Object value = null;
        try {
        	if (mGetter == null) {
        		value = pRowObject;
        	} else {
        		if (this.mGetterArgument == null) {
        			value = mGetter.invoke(pRowObject, (Object[])null);
        		} else {
        			value = mGetter.invoke(pRowObject, (String) (this.mGetterArgument));
        		}
        	}
            if (mFormat == null) {
            	return value;
            } else {
            	return mFormat.format(value);
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
            return pRowObject;
        }
    }

    /**
     * Set the cell value (use formatter).
     * @param pRowObject the row Object
     * @param pValue the new value
     */
    public void setValue(Object pRowObject, Object pValue) {
        try {
            pValue = mFormat == null ? pValue : mFormat.parseObject(String
                    .valueOf(pValue));
            mSetter.invoke(pRowObject, new Object[]{pValue});
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the default visibility.
     * @return the default visibility.
     */
    public boolean isDefaultVisible() {
        return mDefaultVisible;
    }

    /**
     * The sortable status.
     * @return true if the column is sortable
     */
    public boolean isSortable() {
        return mSortable;
    }

    /**
     * The searchable status.
     * @return true if the column is searchable
     */
    public boolean isSearchable() {
        return mSearchable;
    }

    /**
     * Returns setter not null.  This is the default implementation for all cells.
     * @param  pRowObject  the row being queried
     * @return the editable status
     */
    public boolean isCellEditable(Object pRowObject) {
        return mSetter != null;
    }
}