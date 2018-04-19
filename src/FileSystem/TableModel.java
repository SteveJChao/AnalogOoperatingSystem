package FileSystem;

import javax.swing.table.AbstractTableModel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * TableModel类
 */
public class TableModel extends AbstractTableModel {
    //tableDate
    private Vector content = null;
    //columnName
    private String[] title_name = { "File Name", "File Path", "File Type", "File Volume/KB", "Last Update"};

    public TableModel(){
        content = new Vector();
    }

    public void addRow(MyFiles myFile){
        //每一行为一个vector
        Vector v = new Vector();
        //格式化为
        DecimalFormat format=new DecimalFormat("#0.00");
        v.add(0, myFile.getFileName());
        v.add(1, myFile.getFilePath());
        //区分文件类型
        if (myFile.getMyFile().isFile()){
            v.add(2, "File");
            //文件大小
            v.add(3, format.format(myFile.getSpace()));
        }else {
            v.add(2, "Directory");
            v.add(3, "-");
        }

        long time = myFile.getMyFile().lastModified();
        //修改时间
        String ctime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(time));
        v.add(4, ctime);

        content.add(v);
    }

    /**
     *
     * @param name
     */
    public void removeRow(String name) {
        for (int i = 0; i < content.size(); i++){
            if (((Vector)content.get(i)).get(0).equals(name)){
                content.remove(i);
                break;
            }
        }
    }

    /**
     *
     * @param row
     * @param count
     */
    public void removeRows(int row, int count){
        for (int i = 0; i < count; i++){
            if (content.size() > row){
                content.remove(row);
            }
        }
    }


    /**
     * 为该位置元素设置value
     * @param value
     * @param rowIndex
     * @param colIndex
     */
    @Override
    public void setValueAt(Object value, int rowIndex, int colIndex){
        ((Vector) content.get(rowIndex)).remove(colIndex);
        ((Vector) content.get(rowIndex)).add(colIndex, value);
        //通知更改
        this.fireTableCellUpdated(rowIndex, colIndex);
    }

    public String getColumnName(int col) {
        return title_name[col];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return false;
    }

    @Override
    public int getRowCount() {
        return content.size();
    }

    @Override
    public int getColumnCount() {
        return title_name.length;
    }

    /**
     * 返回确定位置的值
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((Vector) content.get(rowIndex)).get(columnIndex);
    }
}
