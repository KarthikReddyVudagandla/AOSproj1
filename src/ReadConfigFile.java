import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadConfigFile {

   public static NodeInfo readConfigFile(int id,String name) throws IOException
   {
	 NodeInfo file=new NodeInfo();
	 file.id=id;
	 int node_count=0,next=0;
	 int curNode=0;
	// int ConnCount=0;
	 
	 String fileName=System.getProperty("user.dir") + "/" + name;
	 
	 String line=null;
	 try {
		 BufferedReader br=new BufferedReader(new FileReader(fileName)); 
			
			while((line = br.readLine()) != null) {
				//System.out.println(line);
				if(line.length() == 0 || line.startsWith("#"))
					continue;
				// Ignore comments and consider only those lines which are not comments
				String[] config_input;
				if(line.contains("#")){
					String[] config_input_comment = line.split("#.*$"); //Ignore text after # symbol
					config_input = config_input_comment[0].split("\\s+");
				}
				else {
					config_input = line.split("\\s+");
				}
				
				

				if(next==0 && config_input.length == 1){
					//System.out.println(config_input[0]);
     				file.numOfNodes= Integer.parseInt(config_input[0]);
     				file.adjMtx = new int[file.numOfNodes][file.numOfNodes];//init adj matrix
     				file.ClientConnectionCount= new int[file.numOfNodes];
     				next++;
				}
				else if(next == 1 && node_count < file.numOfNodes) {
				    System.out.println(Integer.parseInt(config_input[0])+" "+config_input[1]+" "+Integer.parseInt(config_input[2]));
					file.nodes.add(new Node(Integer.parseInt(config_input[0]),config_input[1],Integer.parseInt(config_input[2])));
					node_count++;
					if(node_count == file.numOfNodes){
						next = 2;
					}									  
				}
				else if(next == 2) {
					for(String i : config_input){
						if(curNode != Integer.parseInt(i)) {
							file.adjMtx[curNode][Integer.parseInt(i)] = 1;
							file.adjMtx[Integer.parseInt(i)][curNode] = 1;
							if(file.id==curNode) {
								//ConnCount++;
							file.ClientConnectionCount[file.id]= config_input.length-1;}
								//file.ConnCount++;
						}
					}
					curNode++;
					
					//System.out.println(ConnCount);
					//file.ClientConnectionCount[file.id]= config_input.length-1;
					
				}
				
			}
			System.out.println(file.id+"conn "+file.ClientConnectionCount[file.id]);
			br.close(); 		 
		 
	 }catch(FileNotFoundException e) {
		 System.out.println("Unable to open file '" + fileName + "'");
		 
	 }
	 catch(IOException e) {
		 System.out.println("Error reading file '" + fileName + "'"); 	 
		 
	 }
	   
	return file;   
   }
   
}
