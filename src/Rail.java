
public class Rail  {
	private Intersection source;
	private Intersection destination;
	private double distance;
	private boolean active;
	private boolean maintain;
	private boolean broken;
	
	public Rail(Intersection source,Intersection destination) {
		this.setSource(source);
		this.setDestination(destination);
		this.setBroken(false);
	}
	
	
	
	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}
	/**
	 * @return the destination
	 */
	public Intersection getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(Intersection destination) {
		this.destination = destination;
	}
	/**
	 * @return the source
	 */
	public Intersection getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(Intersection source) {
		this.source = source;
	}
	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	/**
	 * @return the broken
	 */
	public boolean isBroken() {
		return broken;
	}
	/**
	 * @param broken the broken to set
	 */
	public void setBroken(boolean broken) {
		this.broken = broken;
	}



	/**
	 * @return the maintain
	 */
	public boolean isMaintain() {
		return maintain;
	}



	/**
	 * @param maintain the maintain to set
	 */
	public void setMaintain(boolean maintain) {
		this.maintain = maintain;
	}
	
}
