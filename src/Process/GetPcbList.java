package Process;

import java.util.ArrayList;

/**
 * 将PCB_list按序输出
 */
public class GetPcbList {
    public static ArrayList<PCB> pcbArrayList;

    public static ArrayList<PCB> getPcbArrayList(PCB_LinkList pcb_linkList) {
        pcbArrayList = new ArrayList<PCB>();
        PCB p = pcb_linkList.head;
        while (p != null) {
            pcbArrayList.add(p);
            p = p.next;
        }
        return pcbArrayList;
    }
}
