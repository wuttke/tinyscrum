package eu.wuttke.tinyscrum.domain;

/**
 * Time units (for stories and tasks)
 * @author Matthias Wuttke
 */
public enum TimeUnit {

	/**
	 * Story points
	 */
	POINTS,
	
	/**
	 * Hours
	 */
	HOURS,
	
	/**
	 * Days
	 */
	DAYS;
	
	public String toString() {
		switch (this) {
			case DAYS: return "d";
			case HOURS: return "h";
			case POINTS: return "pts";
			default: return "?";
		}
	}
}
