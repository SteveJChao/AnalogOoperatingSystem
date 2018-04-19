package Process;

import A_General.OS;

/**
 * 根据OS.PCBtable使用PID获取PCB
 */
public class GetPcbByPID {

    public PCB getPCB(int PID) {
        PCB pcb = OS.PCBtable.get(PID);
        return pcb;
    }
}
