import java.util.ArrayList;



public class Intersection implements Comparable<Intersection>{
	private Intersection switch_label;
	private boolean maintenance;
	private int trainNumber;
	private int id;
	Intersection preStation=null;
	private String name;
	private String[] parseString;
	double minimumDist = Float.MAX_VALUE;
	private ArrayList<Intersection> incoming=new ArrayList<Intersection>();
	private ArrayList<Intersection> outgoing=new ArrayList<Intersection>();
	static int counter=0;
	private int crossCounter;


	
	public Intersection(String name) {
		this.setMaintenance(false);
		this.setName(name);
		this.setId(counter);
		this.setCrossCounter(0);
		counter++;
	}
	
	public int compareTo(Intersection other){
		return Double.compare(minimumDist, other.minimumDist);
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the incoming
	 */
	public ArrayList<Intersection> getIncoming() {
		return incoming;
	}

	/**
	 * @param incoming the incoming to set
	 */
	public void addIncoming(Intersection newStation) {
		this.incoming.add(newStation);
	}

	/**
	 * @return the outgoing
	 */
	public ArrayList<Intersection> getOutgoing() {
		return outgoing;
	}

	/**
	 * @param outgoing the outgoing to set
	 */
	public void addOutgoing(Intersection newStation) {
		this.outgoing.add(newStation);
	}

	/**
	 * @return the trainNumber
	 */
	public int getTrainNumber() {
		return trainNumber;
	}

	/**
	 * @param trainNumber the trainNumber to set
	 */
	public void setTrainNumber(int trainNumber) {
		this.trainNumber = trainNumber;
	}

	/**
	 * @return the maintenance
	 */
	public boolean isMaintenance() {
		return maintenance;
	}

	/**
	 * @param maintenance the maintenance to set
	 */
	public void setMaintenance(boolean maintenance) {
		this.maintenance = maintenance;
	}

	/**
	 * @return the switch_label
	 */
	public Intersection getSwitch_label() {
		return switch_label;
	}

	/**
	 * @param switch_label the switch_label to set
	 */
	public void setSwitch_label(Intersection switch_label) {
		this.switch_label = switch_label;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}



	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	/**
	 * @return the parseString
	 */
	public String[] getParseString() {
		return parseString;
	}



	



	public void setParseString(String[] parseString2) {
		this.parseString = parseString2;		
	}

	/**
	 * @return the crossCounter
	 */
	public int getCrossCounter() {
		return crossCounter;
	}

	/**
	 * @param crossCounter the crossCounter to set
	 */
	public void setCrossCounter(int crossCounter) {
		this.crossCounter = crossCounter;
	}



	

}
