package Project;
class AutoSaveThread extends Thread {
    private final StudentManagementSystem sys;
    private volatile boolean running = true;
    private final long intervalMillis;

    public AutoSaveThread(StudentManagementSystem sys, long intervalMillis) {
        this.sys = sys;
        this.intervalMillis = intervalMillis;
        setDaemon(true);
    }
   public void terminate(){
    	running = false;
    
    interrupt(); 
    }
    //Override run method
    public void run() {
        while (running) {
            try {
                Thread.sleep(intervalMillis);
                sys.saveToFile();
            } catch (Exception e) {
                System.out.println("[AutoSave] Failed: " + e.getMessage());
            } }
    }
}

