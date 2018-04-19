package ConstantMemory;

import A_General.OS;

import java.util.ArrayList;

/**
 * 从Pieces_linkList中获取Piece信息，封装进ArrayList
 */
public class GetConMemList {
    public ArrayList<Pieces> piecesArrayList = new ArrayList<Pieces>();

    public ArrayList<Pieces> getPiecesArrayList(Pieces_LinkList piecesLinkList) {
        Pieces pieces = piecesLinkList.head;
        while (pieces != null) {
            piecesArrayList.add(pieces);
            pieces = pieces.next;
        }
        return piecesArrayList;
    }

    /**
     * 返回列表的总大小
     * @param piecesArrayList
     * @return
     * @throws Exception
     */
    public int getTotalSize(ArrayList<Pieces> piecesArrayList) {
        if (piecesArrayList.size() <= 0) {
            return 0;
        }
        int totalSize = 0;
        for (int i = 0; i < piecesArrayList.size(); i++) {
            Pieces piece = piecesArrayList.get(i);
            totalSize += piece.size;
        }
        return totalSize;
    }
}
