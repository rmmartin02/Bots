package util;

/** * Credit goes to TheScrub */
public class Timer {    
	private long startTime = 0; 
	private long pauseTime = 0;   
	public Timer() {      
		startTime = System.currentTimeMillis();  
		}   
	public long getTimeElapsed() { 
			return pauseTime == 0 ? System.currentTimeMillis() - startTime   
					: pauseTime - startTime; 
			}   
	public long getSecondsElapsed() {  
		return getTimeElapsed() / 1000 % 60;   
		}   
	public long getMintuesElapsed() {  
		return getTimeElapsed() / 1000 / 60 % 60;   
		} 
	public long getHoursElapsed() {    
		return getTimeElapsed() / 1000 / 60 / 60 % 24;   
		}   
	public long getDaysElapsed() { 
		return getTimeElapsed() / 1000 / 60 / 60 / 24 % 7; 
		} 
	public void reset() {     
		startTime = System.currentTimeMillis();  
		pauseTime = 0;  
		}  
	public String getFormattedTime() {   
		return getDaysElapsed() > 0 ? "" + getDaysElapsed() + ":" 
	+ getHoursElapsed() + ":" + getMintuesElapsed() + ":"    
				+ getSecondsElapsed() + "" : getHoursElapsed() + ":"     
	+ getMintuesElapsed() + ":" + getSecondsElapsed();   
		}  
	public void pause() {    
		pauseTime = System.currentTimeMillis(); 
  }  
	public void resume() {    
		if (isPaused()) {  
			long timePaused = (System.currentTimeMillis() - pauseTime) + 1000;  
			System.out.println("Time Paused: " + timePaused);     
			startTime = System.currentTimeMillis() - timePaused;       
			pauseTime = 0;      
			}  
		}  
	public double calculatePerHour(double gained) {
		return (gained * 3600000) / getTimeElapsed();    
		}   
	public boolean isPaused() {    
		return pauseTime > 0;  
		}
	}