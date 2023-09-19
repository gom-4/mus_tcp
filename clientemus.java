import java.io.*;
import java.net.*;

public class clientemus {

	public static void main(String[] argv) throws Exception{
		boolean juega=true;
		
		Socket clientSocket = new Socket (argv[0],Integer.parseInt(argv[1]));
		
		DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream()); //enviar al servidor
		BufferedReader in = new BufferedReader (new InputStreamReader(clientSocket.getInputStream())); //escuchar al servidor
		BufferedReader inFromUser = new BufferedReader (new InputStreamReader(System.in)); //escuchar al cliente
		
		String lineaserver;
		System.out.println((lineaserver=in.readLine())+"\n"); //Imprimo linea de presentacion
		
		while(juega) {
			System.out.println(lineaserver=in.readLine()); //A la espera de interacciones por parte del servidor, si comienza con # es que quiere que responda
			if(lineaserver.contains("#")) {
				out.writeBytes(inFromUser.readLine()+"\n");
			}
		}
		clientSocket.close();
	}
}
