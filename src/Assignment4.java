import java.io.FileNotFoundException;
import java.util.ArrayList;
/*
 * 
 * 	EMRE COSKUNCAY
 * 		21526806
 * BBM204 - Assignment4
 * 
 */


public class Assignment4 {
	public static ArrayList<Rail> org = new ArrayList<Rail>() ;
	public static void main(String[] args) throws FileNotFoundException {
		readFile readObj = new readFile();
		ArrayList<String> networkList = readObj.read_file(args[0]);
		ArrayList<String> distanceList = readObj.read_file(args[1]);
		ArrayList<String> commandList = readObj.read_file(args[2]);
		double switchTime=Float.parseFloat(args[3]);
		ArrayList<Intersection> intersectionsList=new ArrayList<Intersection>();
		ArrayList<Rail> railList;

		Functions.createIn(networkList,intersectionsList);
		Functions.createNeighbour(intersectionsList);
		
		Graph newGraph = new Graph(intersectionsList,switchTime);
		Functions.createGraph(newGraph);
		
		railList=Functions.calculateDistance(distanceList,Graph.railList);
		for(int i=0;i<railList.size();i++) {
			Rail newRail = new Rail(railList.get(i).getSource(), railList.get(i).getDestination());
			newRail.setDistance(returnDistance(railList, newRail.getSource(), newRail.getDestination()));
			org.add(newRail);
		}
		Functions.railActivator(railList);
		Functions.commandBuilder(commandList,railList,switchTime,intersectionsList,newGraph,railList);
	}
	
	public static double returnDistance(ArrayList<Rail> rails,Intersection from,Intersection to) {
		for(int i=0;i<rails.size();i++) {
			if(rails.get(i).getSource().equals(from) && rails.get(i).getDestination().equals(to)) {
				return rails.get(i).getDistance();
			}
		}
		return 0;
	}

}
