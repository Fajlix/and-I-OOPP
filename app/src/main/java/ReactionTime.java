public class ReactionTime {
    private long startTime;
    private long endTime;
    private long waitTime;
    //max wait time in ms
    private int maxWaitTime = 3000;
    //min wait time in ms
    private int minWaitTime = 500;
    public ReactionTime(){
    }

    //Call this to start a new reactionTest
    public void StartTest() throws InterruptedException {
        //sets startTime to current time
        startTime = System.currentTimeMillis();
        // a random time between min- and maxWaitTime
        waitTime = Math.round(Math.random() * maxWaitTime) + minWaitTime;
        //TODO this is just for testing
        //TODO here we should add some call to change view
        System.out.println("ready");
        Thread.sleep(waitTime);
        System.out.println("NOW");

    }
    public String StopTest(){
        String result;
        endTime = System.currentTimeMillis();
        if (endTime < (startTime + waitTime)){
            result = "You clicked to early";
        }
        else {
            result = "Your reaction time  was " + (endTime -startTime - waitTime) + " ms.";
        }
        return result;
    }
    public long getWaitTime(){
        return waitTime;
    }
}
