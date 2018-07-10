import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Graph {
	
	public enum Mark { UNMARKED, MARKED, DONE };
	public static ArrayList<Intersection> intersectionsList;
	public static ArrayList<Rail> railList;
	private double switchTime;
	public static ArrayList<Intersection> bestTeam=new ArrayList<Intersection>();
	public static float totalTime;
	
	
	public Graph(ArrayList<Intersection> intersectionList,double switchTime) {
		intersectionsList = intersectionList;
		this.setSwitchTime(switchTime);
		railList = new ArrayList<>();
	}
	
	public void add(Intersection source, Intersection destination){
		Rail temp = findRail(source, destination);
		if (temp != null){
			System.out.println("Edge " + source + "," + destination + " already exists.");
		}
		else{
			Rail newRail = new Rail(source, destination);
			Functions.railFunction.add(newRail);
			railList.add(newRail);
		}
	}
	
	public static Intersection findStation(Intersection station){
		for (int i=0;i<intersectionsList.size();i++){
			if (intersectionsList.get(i).equals(station)) {
				return intersectionsList.get(i);
			}
		}
		return null;
	}
	
	public static Rail findRail(Intersection fromIntersection, Intersection toIntersection){
		for (int i=0;i<railList.size();i++){
			if (railList.get(i).getSource().equals(fromIntersection) && railList.get(i).getDestination().equals(toIntersection)){
				return railList.get(i);
			}
		}
		return null;
	}
	
	private boolean Dijkstra(Intersection v1){
		if (intersectionsList.isEmpty()) return false;
		distanceReset();
		Intersection source = findStation(v1);
		if (source==null) return false;
		source.minimumDist = 0;
		PriorityQueue<Intersection> pq = new PriorityQueue<>();
		pq.add(source);
		while (!pq.isEmpty()){
			Intersection u = pq.poll();
			if(u.isMaintenance()) {
				continue;
			}
			else {
				for (Intersection v : u.getOutgoing()){
					Rail e = findRail(u, v);
					if(e.isBroken() || e.isMaintain()) {
						continue;
					}
					if (e==null ) { return false;}
					double totalDistance = u.minimumDist + e.getDistance();
					if (totalDistance < v.minimumDist){
						pq.remove(v);
						v.minimumDist = totalDistance;
						v.preStation = u;
						pq.add(v);
					}
				}
			}
		}
		return true;
	}

	private List<String> bestPath(Intersection target){
		List<String> path = new ArrayList<String>();
		if (target.minimumDist==Integer.MAX_VALUE){
			path.add("No path found");
			return path;
		}
		for (Intersection v = target; v !=null; v = v.preStation){
			bestTeam.add(v);
			path.add(v.getName() + ":" + v.minimumDist);
		}
		Collections.reverse(bestTeam);
		Collections.reverse(path);
		return path;
	}
	
	private void distanceReset(){
		for (Intersection each : intersectionsList){
			each.minimumDist = Integer.MAX_VALUE;
			each.preStation = null;
		}
	}
	
	public List<String> runTrain(Intersection from, Intersection to){
		boolean test = Dijkstra(from);
		if (test==false) return null;
		List<String> path = bestPath(findStation(to));
		timeCalculator(path);
		return path;
	}

	
	public static void timeCalculator(List<String> path) {
		if(!bestTeam.isEmpty()) {
			totalTime=Float.parseFloat(path.get(path.size()-1).split(":")[1]);
		}
	}
	/**
	 * @return the switchTime
	 */
	public double getSwitchTime() {
		return switchTime;
	}

	/**
	 * @param switchTime the switchTime to set
	 */
	public void setSwitchTime(double switchTime) {
		this.switchTime = switchTime;
	}


}
