package Process;
import Jobs.*;

import java.util.ArrayList;

/**
 * 将JCB_List按序输出
 */
public class GetJcbList {
    public static ArrayList<JCB> jcbArrayList;
    public static ArrayList<JCB> getJcbArrayList(JCB_LinkList jcb_linkList) {
        jcbArrayList = new ArrayList<JCB>();
        JCB jcb = jcb_linkList.head;
        while(jcb != null) {
            jcbArrayList.add(jcb);
            jcb = jcb.next;
        }
        return jcbArrayList;
    }
}
