package DiscreteMemory;

import java.util.ArrayList;

public class GetPageArrayList {
    public ArrayList<Page> pageArrayList = new ArrayList<Page>();

    public ArrayList<Page> getPageArrayList(Pages_LInkList pages_lInkList) {
        Page page = pages_lInkList.head;
        while (page != null) {
            pageArrayList.add(page);
            page = page.next;
        }
        return pageArrayList;
    }
}
