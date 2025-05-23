// This class was generated from com.iiordanov.bVNC.ISentText by a tool
// Do not edit this file directly! PLX THX
package com.iiordanov.bVNC;

public class SentTextBean extends com.antlersoft.android.dbimpl.IdImplementationBase implements ISentText {

    public static final String GEN_TABLE_NAME = "SENT_TEXT";
    public static final int GEN_COUNT = 2;

    // Field constants
    public static final String GEN_FIELD__ID = "_id";
    public static final int GEN_ID__ID = 0;
    public static final String GEN_FIELD_SENTTEXT = "SENTTEXT";
    public static final int GEN_ID_SENTTEXT = 1;
    public static final com.antlersoft.android.dbimpl.NewInstance<SentTextBean> GEN_NEW = new com.antlersoft.android.dbimpl.NewInstance<SentTextBean>() {
        public SentTextBean get() {
            return new SentTextBean();
        }
    };
    // SQL Command for creating the table
    public static String GEN_CREATE = "CREATE TABLE SENT_TEXT (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "SENTTEXT TEXT" +
            ")";
    // Members corresponding to defined fields
    private long gen__Id;
    private String gen_sentText;

    public String Gen_tableName() {
        return GEN_TABLE_NAME;
    }

    // Field accessors
    public long get_Id() {
        return gen__Id;
    }

    public void set_Id(long arg__Id) {
        gen__Id = arg__Id;
    }

    public String getSentText() {
        return gen_sentText;
    }

    public void setSentText(String arg_sentText) {
        gen_sentText = arg_sentText;
    }

    public android.content.ContentValues Gen_getValues() {
        android.content.ContentValues values = new android.content.ContentValues();
        values.put(GEN_FIELD__ID, Long.toString(this.gen__Id));
        values.put(GEN_FIELD_SENTTEXT, this.gen_sentText);
        return values;
    }

    /**
     * Return an array that gives the column index in the cursor for each field defined
     *
     * @param cursor Database cursor over some columns, possibly including this table
     * @return array of column indices; -1 if the column with that id is not in cursor
     */
    public int[] Gen_columnIndices(android.database.Cursor cursor) {
        int[] result = new int[GEN_COUNT];
        result[0] = cursor.getColumnIndex(GEN_FIELD__ID);
        // Make compatible with database generated by older version of plugin with uppercase column name
        if (result[0] == -1) {
            result[0] = cursor.getColumnIndex("_ID");
        }
        result[1] = cursor.getColumnIndex(GEN_FIELD_SENTTEXT);
        return result;
    }

    /**
     * Populate one instance from a cursor
     */
    public void Gen_populate(android.database.Cursor cursor, int[] columnIndices) {
        if (columnIndices[GEN_ID__ID] >= 0 && !cursor.isNull(columnIndices[GEN_ID__ID])) {
            gen__Id = cursor.getLong(columnIndices[GEN_ID__ID]);
        }
        if (columnIndices[GEN_ID_SENTTEXT] >= 0 && !cursor.isNull(columnIndices[GEN_ID_SENTTEXT])) {
            gen_sentText = cursor.getString(columnIndices[GEN_ID_SENTTEXT]);
        }
    }

    /**
     * Populate one instance from a ContentValues
     */
    public void Gen_populate(android.content.ContentValues values) {
        gen__Id = values.getAsLong(GEN_FIELD__ID);
        gen_sentText = values.getAsString(GEN_FIELD_SENTTEXT);
    }
}
