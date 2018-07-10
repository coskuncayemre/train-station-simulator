import java.util.ArrayList;
import java.util.List;

public class Functions {
	public static double velocity;
	public static double switchTime;
	public static int switchCounter=0;
	public static ArrayList<Rail> railFunction=new ArrayList<Rail> ();
	public static ArrayList<Intersection> intersectionFunction= new ArrayList<Intersection> ();

	public static void commandBuilder(ArrayList<String> commandList,ArrayList<Rail> railList,double switchTime,ArrayList<Intersection> intersectionList,Graph graph,ArrayList<Rail> originalRailList) {
		for(int i=0;i<commandList.size();i++) {
			String command = commandList.get(i);
			String[] parseCommand= commandList.get(i).split(" ");
			System.out.println("COMMAND IN PROCESS >> " + command);
			if(parseCommand[0].equals("ROUTE")) {
				double velocity = Double.parseDouble(parseCommand[2]);
				Functions.velocity=velocity;
				Functions.switchTime=switchTime;
				String[] parseRoute=parseCommand[1].split(">");
				calculateTime(railList,velocity,switchTime);
				Intersection from = stringToStation(parseRoute[0],intersectionList);
				Intersection to = stringToStation(parseRoute[1],intersectionList);
				List<String> path = graph.runTrain(from,to);
				switchCalculator(Graph.bestTeam);
				if(Graph.bestTeam.isEmpty()) {
					System.out.println("\tNo route from "+ from.getName() + " to " +  to.getName()+ " found currently!");
				}
				else {
					System.out.printf("\tTime (in min): %.3f " , Graph.totalTime);
					System.out.println();
					System.out.println("\tTotal # of switch changes: " + switchCounter);
					System.out.print("\tRoute from "+ from.getName() + " to " + to.getName()+": ");
					for(Intersection s : Graph.bestTeam) {
						System.out.print(s.getName()+" ");
					}
					System.out.println();
				}
				System.out.println("\tCommand \""+ command+ "\"  has been executed successfully!");
				activeNewRail(intersectionList, Graph.bestTeam,railList);
				Graph.bestTeam.clear();
				resetRail(railList);
				Graph.totalTime=0;
				switchCounter=0;
			}
			else if(parseCommand[0].equals("MAINTAIN")) {
				stringToStation(parseCommand[1], intersectionList).setMaintenance(true);
				maintainRail(stringToStation(parseCommand[1], intersectionList), railList);
				System.out.println("\tCommand \""+ command+ "\"  has been executed successfully!");
			}
			else if(parseCommand[0].equals("SERVICE")) {
				stringToStation(parseCommand[1], intersectionList).setMaintenance(false);
				serviceRail(stringToStation(parseCommand[1], intersectionList), railList);
				System.out.println("\tCommand \""+ command+ "\"  has been executed successfully!");
			}

			else if(parseCommand[0].equals("BREAK")) {
				String[] parseRoute=parseCommand[1].split(">");
				Rail tempRail = stringToRail(parseRoute[0], parseRoute[1],railList);
				tempRail.setBroken(true);
				System.out.println("\tCommand \""+ command+ "\"  has been executed successfully!");
			}

			else if(parseCommand[0].equals("REPAIR")) {
				String[] parseRoute=parseCommand[1].split(">");
				Rail tempRail = stringToRail(parseRoute[0], parseRoute[1], railList);
				tempRail.setBroken(false);
				System.out.println("\t Command \""+ command+ "\"  has been executed successfully!");
			}

			else if(parseCommand[0].equals("ADD")) {
				Intersection newStation = new Intersection(parseCommand[1]);
				Functions.intersectionFunction.add(newStation);
				intersectionList.add(newStation);
				Graph.intersectionsList.add(newStation);
				System.out.println("\t Command \""+ command+ "\"  has been executed successfully!");
			}

			else if(parseCommand[0].equals("LINK")) {
				String[] parseSource=parseCommand[1].split(":");
				Intersection source=stringToStation(parseSource[0], intersectionList);
				String[] parseSwitch= parseSource[1].split(">");
				Intersection switchStation= stringToStation(parseSwitch[1], intersectionList);
				source.setSwitch_label(switchStation);
				String[] parseRails=parseSwitch[0].split(",");
				for(String s : parseRails) {
					String[] parseDest=s.split("-");
					Intersection destination=stringToStation(parseDest[0], intersectionList);
					Double distance=Double.parseDouble(parseDest[1]);
					Rail newTempRail = new Rail(source,destination);
					Rail newTempRail2= new Rail(destination,source);
					if(destination.equals(switchStation)) {
						newTempRail.setActive(true);
					}
//					System.out.println(source.getName()+"->"+destination.getName()+":"+distance);
					source.addIncoming(destination);
					source.addOutgoing(destination);
					destination.addIncoming(source);
					destination.addOutgoing(source);
					newTempRail.setDistance(distance);
					Assignment4.org.add(newTempRail);
					Assignment4.org.add(newTempRail2);
//					System.out.println(newTempRail.getSource().getName()+"->"+newTempRail.getDestination().getName()+":"+newTempRail.getDistance());
					Functions.railFunction.add(newTempRail);
					Functions.railFunction.add(newTempRail2);
					railList.add(newTempRail);
					railList.add(newTempRail2);
					System.out.println(railList.size());
				}
				System.out.println("\t Command \""+ command+ "\"  has been executed successfully!");
			}

			else if(parseCommand[0].equals("LISTROUTESFROM")) {
				System.out.print("\tRoutes from " + parseCommand[1]+ ": ");
				for(Intersection x : stringToStation(parseCommand[1], intersectionList).getOutgoing()) {
					System.out.print(x.getName()+" ");
				}
				System.out.println();
				System.out.println("\tCommand \""+ command+ "\"  has been executed successfully!");
			}

			else if(parseCommand[0].equals("LISTMAINTAINS")) {
				System.out.print("\tIntersections under maintenance: ");
				for(Intersection x : intersectionList) {
					if(x.isMaintenance()) {
						System.out.print(x.getName()+" ");
					}
				}
				System.out.println();
				System.out.println("\tCommand \""+ command+ "\"  has been executed successfully!");
			}

			else if(parseCommand[0].equals("LISTACTIVERAILS")) {
				System.out.print("\tActive Rails: ");
				for(Intersection s : intersectionList) {
					System.out.print(s.getName()+">"+s.getSwitch_label().getName()+" ");
				}
				System.out.println();
				System.out.println("\tCommand \""+ command+ "\"  has been executed successfully!");
			}
			else if(parseCommand[0].equals("LISTBROKENRAILS")) {
				System.out.print("\tBroken rails: ");
				for(Rail r : railList) {
					if(r.isBroken()) {
						System.out.print(r.getSource().getName()+">"+r.getDestination().getName()+" ");
					}
				}
				System.out.println();
				System.out.println("\tCommand \""+ command+ "\"  has been executed successfully!");
			}

			else if(parseCommand[0].equals("LISTCROSSTIMES")) {
				System.out.print("\t# of cross times: ");
				for(Intersection s : intersectionList) {
					if(s.getCrossCounter()!=0) {
						System.out.print(s.getName()+":"+s.getCrossCounter()+" ");
					}
				}
				System.out.println();
				System.out.println("\tCommand \""+ command+ "\"  has been executed successfully!");
			}

			else if(parseCommand[0].equals("TOTALNUMBEROFJUNCTIONS")) {
				System.out.println("\tTotal # of junctions: " + intersectionList.size());
				System.out.println("\tCommand \""+ command+ "\"  has been executed successfully!");
			}
			else if(parseCommand[0].equals("TOTALNUMBEROFRAILS")) {
				System.out.println("\tTotal # of rails: " + railList.size());
				System.out.println("\tCommand \""+ command+ "\"  has been executed successfully!");
			}
			else{
				System.out.println("\tUnrecognized command \""+ command+ "\"!");
			}
			
		}
	}
	
	public static void railActivator(ArrayList<Rail> railList) {
		for(int i=0;i<railList.size();i++) {
			stringToRail(railList.get(i).getSource().getName(),railList.get(i).getSource().getSwitch_label().getName(), railList).setActive(true);
		}
	}
	
	public static void createIn(ArrayList<String> networkList,ArrayList<Intersection> intersectionsList)  {
		for(int i=0;i<networkList.size();i++) {
			String[] parseString=networkList.get(i).split(":");
			Intersection newStation = new Intersection(parseString[0]);
			newStation.setParseString(parseString);
			intersectionsList.add(newStation);
			Functions.intersectionFunction.add(newStation);
		}
		
	}
	
	public static ArrayList<Rail> calculateDistance(ArrayList<String> distanceList,ArrayList<Rail> railList) {
		for(int i=0;i<distanceList.size();i++) {
			String[] parseDistance=distanceList.get(i).split(" ");
			String[] parsePair=parseDistance[0].split("-");
			stringToRail(parsePair[0],parsePair[1], railList).setDistance(Float.parseFloat(parseDistance[1]));
			stringToRail(parsePair[1],parsePair[0], railList).setDistance(Float.parseFloat(parseDistance[1]));
		}
		return railList;
	}
	
	public static void createGraph(Graph graph) {
		for(int i =0;i<Graph.intersectionsList.size();i++) {
			for(int j=0;j<Graph.intersectionsList.get(i).getOutgoing().size();j++) {
				graph.add(Graph.intersectionsList.get(i), Graph.intersectionsList.get(i).getOutgoing().get(j));
			}
		}
	}
	
	public static void calculateTime(ArrayList<Rail> railList,double velocity,double switchTime) {
		for(int i=0;i<railList.size();i++) {
			double firstStep= (railList.get(i).getDistance()) / velocity;
			double secondStep =  (firstStep *60.00);
			if(railList.get(i).isActive()) {
				railList.get(i).setDistance(secondStep);
			}
			else {
				railList.get(i).setDistance(secondStep+switchTime);
			}

		}		
		
	}
	
	public static void createNeighbour(ArrayList<Intersection> intersectionsList) {
		for(int i=0;i<intersectionsList.size();i++) {
			String[] switchString=intersectionsList.get(i).getParseString()[1].split(">");
			intersectionsList.get(i).setSwitch_label(stringToStation(switchString[1], intersectionsList));
			String[] neighbourString=switchString[0].split(",");
			for(int j=0;j<neighbourString.length;j++) {
				intersectionsList.get(i).addOutgoing(stringToStation(neighbourString[j],intersectionsList));
				intersectionsList.get(i).addIncoming(stringToStation(neighbourString[j],intersectionsList));
			}
		}
	}
	
	public static Intersection stringToStation(String name,ArrayList<Intersection> stations) {
		for(int i=0;i<stations.size();i++) {
			if(stations.get(i).getName().equals(name)) {
				return stations.get(i);
			}
		}
		return null;
	}
	
	public static void resetRail(ArrayList<Rail> railList) {
		for(int i=0;i<railList.size();i++) {
			railList.get(i).setDistance(Assignment4.org.get(i).getDistance());
		}
	}
	
	public static void activeNewRail(ArrayList<Intersection> intersectionList,ArrayList<Intersection> bestTeam,ArrayList<Rail> railList) {
		crossCounter(bestTeam);
		for(int i=0;i<bestTeam.size()-1;i++) {
			if(bestTeam.get(i).getSwitch_label().equals(bestTeam.get(i+1))) {
				
			}
			else {
				switchCounter++;
				returnRail(railList, bestTeam.get(i),bestTeam.get(i).getSwitch_label()).setActive(false);
				bestTeam.get(i).setSwitch_label(bestTeam.get(i+1));
				returnRail(railList, bestTeam.get(i), bestTeam.get(i).getSwitch_label()).setActive(true);
			}
		}
	}
	
	public static void switchCalculator(ArrayList<Intersection> bestTeam) {
		for(int i=0;i<bestTeam.size()-1;i++) {
			if(!bestTeam.get(i).getSwitch_label().equals(bestTeam.get(i+1))) {
				switchCounter++;
			}
		}
	}	
	public static void crossCounter(ArrayList<Intersection> bestTeam) {
		for(int i=0;i<bestTeam.size();i++) {
			int newCross =bestTeam.get(i).getCrossCounter() +1 ;
			bestTeam.get(i).setCrossCounter(newCross);
		}
	}
	public static Rail stringToRail(String from,String to,ArrayList<Rail> rails) {
		for(int i=0;i<rails.size();i++) {
			if(rails.get(i).getSource().getName().equals(from) && rails.get(i).getDestination().getName().equals(to)) {
				return rails.get(i);
			}
		}
		return null;
	}
	public static void maintainRail(Intersection station,ArrayList<Rail> railList) {
		for(Rail r : railList) {
			if(r.getSource().equals(station)) {
				r.setMaintain(true);
			}
			if(r.getDestination().equals(station)) {
				r.setMaintain(true);
			}
		}
	}
	public static void serviceRail(Intersection station,ArrayList<Rail> railList) {
		for(Rail r : railList) {
			if(r.getSource().equals(station)) {
				r.setMaintain(false);
			}
			if(r.getDestination().equals(station)) {
				r.setMaintain(false);
			}
		}
	}
	
	public static Rail returnRail(ArrayList<Rail> rails,Intersection from,Intersection to) {
		for(int i=0;i<rails.size();i++) {
			if(rails.get(i).getSource().equals(from) && rails.get(i).getDestination().equals(to)) {
				return rails.get(i);
			}
		}
		return null;
	}

}