package user_interface;

import A_General.OS;

public class TaskWinCheck {
    public TaskWinCheck() {
        if (OS.MemoryAllocateFLAG == 0) {
            new TaskWin();
        } else if (OS.MemoryAllocateFLAG == 1) {
            new TaskWinDiscrete();
        }
    }
}
