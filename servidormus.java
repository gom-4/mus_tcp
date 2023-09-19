import java.io.*;
import java.util.*;
import java.net.*;

public class servidormus {
	static int contador1=0, contador2=0;
	public static void main(String[] argv) throws IOException 
	{
		
		ServerSocket serverSocket = null; //Socket del servidor
		
		try {
			serverSocket = new ServerSocket(Integer.parseInt(argv[0])); 
		}
		catch(IOException e){
			System.err.println("No ha sido posible escuchar en el puerto: " + argv[0]); 
			System.exit(-1);
		}
		
		
		System.out.println("Bienvenido al juego de Mus, usted es el Jugador 1. \nEsperando al jugador 2...");
		Socket clientSocket = serverSocket.accept();
		System.out.println("Jugador 2 conectado\n");
		
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); //Para escuchar al cliente 1
		BufferedReader c2in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //Escuchar al cliente 2
		DataOutputStream c2out = new DataOutputStream(clientSocket.getOutputStream()); //Para enviar al cliente 2
		
		int ronda=1, bet=0, betgrande=0, betpequena=0, betjuego=0,ganador;
		String entrada;
		ArrayList<String> mano1 = new ArrayList<String>();
		ArrayList<String> mano2 = new ArrayList<String>();
		ArrayList<String> manos = new ArrayList<String>();
		
		c2out.writeBytes("Bienvenido al juego de Mus, usted es el Jugador 2.\n");
		
		while(contador1<25 && contador2<25) {
			System.out.println("Ronda "+ronda+"."+"\n");
			c2out.writeBytes("Ronda "+ronda+"."+"\n");
			////////////////////////Inicio////////////////////////
			manos=Repartir_mus(c2out,c2in);	
			for(int i=0;i<=3;i++) {
				mano1.add(manos.get(i));
			}
			for(int i=4;i<=7;i++) {
				mano2.add(manos.get(i));
			}
			////////////////////////Inicio////////////////////////
			
			////////////////////////Grande////////////////////////
			System.out.println("Inicia la ronda de Grande."+"\n");
			System.out.println("Jugador 1, ¿quieres apostar? (Escribe: 'si' o 'no')");
			entrada=inFromUser.readLine();	
			if (entrada.equals("si")) {
				System.out.println("¿Cuántas piedras quieres apostar?");
				bet=Integer.parseInt(inFromUser.readLine());
				c2out.writeBytes("#Jugador 2, Jugador 1 ha apostado "+bet+" piedras, si quieres aceptar escribe: 'acepto', si quieres pasar escribe: 'paso', si quieres apostar más escribe: 'mas'.\n");
				entrada=c2in.readLine();
				if(entrada.equals("acepto")) {
					betgrande=bet;
					Grande(mano1, mano2, betgrande, c2out);
				}
				if(entrada.equals("paso")) {
					System.out.println("No se juega la ronda de Grande, se añade una piedra a Jugador 1."+"\n");
					c2out.writeBytes("No se juega la ronda de Grande, se añade una piedra a Jugador 1."+"\n");
					contador1=contador1+1;
				}
				if(entrada.equals("mas")) {
					c2out.writeBytes("#Introduzca el nuevo número total de piedras a apostar:\n");
					bet=Integer.parseInt(c2in.readLine());
					betgrande=bet;
					Grande(mano1, mano2, betgrande,c2out);
				}
			}
			if (entrada.equals("no")) {
				c2out.writeBytes("#Jugador 2, ¿quieres apostar? (Escribe: 'si' o 'no').\n");
				entrada=c2in.readLine();
				if (entrada.equals("si")) {
					c2out.writeBytes("#¿Cuántas piedras quieres apostar?\n");
					bet=Integer.parseInt(c2in.readLine());
					System.out.println("Jugador 1, Jugador 2 ha apostado "+bet+" piedras, si quieres aceptar escribe: 'acepto', si quieres pasar escribe: 'paso', si quieres apostar más escribe: 'mas'.");
					entrada=inFromUser.readLine();
					if(entrada.equals("acepto")) {
						betgrande=bet;
						Grande(mano1, mano2, betgrande, c2out);
					}
					if(entrada.equals("paso")) {
						System.out.println("No se juega la ronda de Grande, se añade una piedra a Jugador 2.\n"); 
						c2out.writeBytes("No se juega la ronda de Grande, se añade una piedra a Jugador 2.\n");
						contador2=contador2+1;
					}
					if(entrada.equals("mas")) {
						System.out.println("Introduzca el nuevo número total de piedras a apostar:");
						bet=Integer.parseInt(inFromUser.readLine());
						betgrande=bet;
						Grande(mano1, mano2, betgrande, c2out);
					}
				}
				if(entrada.equals("no")) {
					System.out.println("No se juega la ronda de Grande."+"\n");
					c2out.writeBytes("No se juega la ronda de Grande.\n");
				}
			}
			////////////////////////Grande////////////////////////
			
			////////////////////////Pequeña////////////////////////
			System.out.println("Inicia la ronda de Pequeña.\n");
			c2out.writeBytes("Inicia la ronda de Pequeña.\n");
			System.out.println("Jugador 1, ¿quieres apostar? (Escribe: 'si' o 'no')");
			entrada=inFromUser.readLine();	
			if (entrada.equals("si")) {
				System.out.println("¿Cuántas piedras quieres apostar?");
				bet=Integer.parseInt(inFromUser.readLine());
				c2out.writeBytes("#Jugador 2, Jugador 1 ha apostado "+bet+" piedras, si quieres aceptar escribe: 'acepto', si quieres pasar escribe: 'paso', si quieres apostar más escribe: 'mas'.\n");
				entrada=c2in.readLine();
				if(entrada.equals("acepto")) {
					betpequena=bet;
					Pequena(mano1, mano2, betpequena, c2out);
				}
				if(entrada.equals("paso")) {
					System.out.println("No se juega la ronda de Pequeña, se añade una piedra a Jugador 1."+"\n");
					c2out.writeBytes("No se juega la ronda de Pequeña, se añade una piedra a Jugador 1."+"\n");
					contador1=contador1+1;
				}
				if(entrada.equals("mas")) {
					c2out.writeBytes("#Introduzca el nuevo número total de piedras a apostar:\n");
					bet=Integer.parseInt(c2in.readLine());
					betpequena=bet;
					Pequena(mano1, mano2, betpequena, c2out);
				}
			}
			if (entrada.equals("no")) {
				c2out.writeBytes("#Jugador 2, ¿quieres apostar? (Escribe: 'si' o 'no').\n");
				entrada=c2in.readLine();
				if (entrada.equals("si")) {
					c2out.writeBytes("#¿Cuántas piedras quieres apostar?\n");
					bet=Integer.parseInt(c2in.readLine());
					System.out.println("Jugador 1, Jugador 2 ha apostado "+bet+" piedras, si quieres aceptar escribe: 'acepto', si quieres pasar escribe: 'paso', si quieres apostar más escribe: 'mas'.");
					entrada=inFromUser.readLine();
					if(entrada.equals("acepto")) {
						betpequena=bet;
						Pequena(mano1, mano2, betpequena, c2out);
					}
					if(entrada.equals("paso")) {
						System.out.println("No se juega la ronda de Pequeña, se añade una piedra a Jugador 2."+"\n"); 
						c2out.writeBytes("No se juega la ronda de Pequeña, se añade una piedra a Jugador 2."+"\n"); 
						contador2=contador2+1;
					}
					if(entrada.equals("mas")) {
						System.out.println("Introduzca el nuevo número total de piedras a apostar:");
						bet=Integer.parseInt(inFromUser.readLine());
						betpequena=bet;
						Pequena(mano1, mano2, betpequena, c2out);
					}
				}
				if(entrada.equals("no")) {
					System.out.println("No se juega la ronda de Pequeña."+"\n");
					c2out.writeBytes("No se juega la ronda de Pequeña."+"\n");
				}
			}
			////////////////////////Pequeña////////////////////////
			
			////////////////////////Juego////////////////////////
			System.out.println("Inicia la ronda de Juego, comprobando si los jugadores tienen Juego."+"\n");
			c2out.writeBytes("Inicia la ronda de Juego, comprobando si los jugadores tienen Juego."+"\n");
			
			String[] valor1, valor2;
			int cont1=0, cont2=0;
			for (int i=0; i<=3; i++){
				valor1=(mano1.get(i)).split(" ");
				valor2=(mano2.get(i)).split(" ");
				
				if(Integer.parseInt(valor1[0])>=10) {
					cont1=cont1+10;
				}
				if(Integer.parseInt(valor2[0])>=10) {
					cont2=cont2+10;
				}
				
				if(Integer.parseInt(valor1[0])<10) {
					cont1=cont1+Integer.parseInt(valor1[0]);
				}
				if(Integer.parseInt(valor2[0])<10) {
					cont2=cont2+Integer.parseInt(valor2[0]);
				}
			}
			if (cont1>30 && cont2>30) {
				
				System.out.println("Jugador 1, ¿quieres apostar? (Escribe: 'si' o 'no')");
				entrada=inFromUser.readLine();	
				if (entrada.equals("si")) {
					System.out.println("¿Cuántas piedras quieres apostar?");
					bet=Integer.parseInt(inFromUser.readLine());
					c2out.writeBytes("#Jugador 2, Jugador 1 ha apostado "+bet+" piedras, si quieres aceptar escribe: 'acepto', si quieres pasar escribe: 'paso', si quieres apostar más escribe: 'mas'.\n");
					entrada=c2in.readLine();
					if(entrada.equals("acepto")) {
						betjuego=bet;
						Juego(mano1, mano2, c2out, cont1, cont2, betjuego);
					}
					if(entrada.equals("paso")) {
						System.out.println("No se juega la ronda de juego, se añade una piedra a Jugador 1."+"\n"); 
						c2out.writeBytes("No se juega la ronda de juego, se añade una piedra a Jugador 1."+"\n"); 
						contador1=contador1+1;
						
					}
					if(entrada.equals("mas")) {
						c2out.writeBytes("#Introduzca el nuevo número total de piedras a apostar:\n");
						bet=Integer.parseInt(c2in.readLine());
						betjuego=bet;
						Juego(mano1, mano2, c2out, cont1, cont2, betjuego);
					}
				}
				if (entrada.equals("no")) {
					c2out.writeBytes("#Jugador 2, ¿quieres apostar? (Escribe: 'si' o 'no').\n");
					entrada=c2in.readLine();
					if (entrada.equals("si")) {
						c2out.writeBytes("#¿Cuántas piedras quieres apostar?\n");
						bet=Integer.parseInt(c2in.readLine());
						System.out.println("Jugador 1, Jugador 2 ha apostado "+bet+" piedras, si quieres aceptar escribe: 'acepto', si quieres pasar escribe: 'paso', si quieres apostar más escribe: 'mas'.");
						entrada=inFromUser.readLine();
						if(entrada.equals("acepto")) {
							betjuego=bet;
							Juego(mano1, mano2, c2out, cont1, cont2, betjuego);
						}
						if(entrada.equals("paso")) {
							System.out.println("No se juega la ronda de juego, se añade una piedra a Jugador 2."+"\n"); 
							c2out.writeBytes("No se juega la ronda de juego, se añade una piedra a Jugador 2."+"\n"); 
							contador2=contador2+1;
						}
						if(entrada.equals("mas")) {
							System.out.println("Introduzca el nuevo número total de piedras a apostar:");
							bet=Integer.parseInt(inFromUser.readLine());
							betpequena=bet;
							Juego(mano1, mano2, c2out, cont1, cont2, betjuego);
						}
					}
					if(entrada.equals("no")) {
						System.out.println("No se juega la ronda de juego."+"\n");
						c2out.writeBytes("No se juega la ronda de juego."+"\n");
					}
				
				}
				
			}
			//////////////////////////////Punto///////////////////////////////
			else {
				System.out.println("No hay Juego, se juega al punto."+"\n");
				c2out.writeBytes("No hay Juego, se juega al punto."+"\n");
				
				System.out.println("Jugador 1, ¿quieres apostar? (Escribe: 'si' o 'no')");
				entrada=inFromUser.readLine();	
				if (entrada.equals("si")) {
					System.out.println("¿Cuántas piedras quieres apostar?");
					bet=Integer.parseInt(inFromUser.readLine());
					c2out.writeBytes("#Jugador 2, Jugador 1 ha apostado "+bet+" piedras, si quieres aceptar escribe: 'acepto', si quieres pasar escribe: 'paso', si quieres apostar más escribe: 'mas'.\n");
					entrada=c2in.readLine();
					if(entrada.equals("acepto")) {
						betjuego=bet;
						Punto(mano1, mano2, c2out, cont1, cont2, betjuego);
					}
					if(entrada.equals("paso")) {
						System.out.println("Jugador 2 rechaza el envite, se añade punto y miedo al Jugador 1."+"\n"); 
						c2out.writeBytes("Jugador 2 rechaza el envite, se añade punto y miedo al Jugador 1."+"\n"); 
						contador1=contador1+2;
						
					}
					if(entrada.equals("mas")) {
						c2out.writeBytes("#Introduzca el nuevo número total de piedras a apostar:\n");
						bet=Integer.parseInt(c2in.readLine());
						betjuego=bet;
						Punto(mano1, mano2, c2out, cont1, cont2, betjuego);
					}
				}
				if (entrada.equals("no")) {
					c2out.writeBytes("#Jugador 2, ¿quieres apostar? (Escribe: 'si' o 'no').\n");
					entrada=c2in.readLine();
					if (entrada.equals("si")) {
						c2out.writeBytes("#¿Cuántas piedras quieres apostar?\n");
						bet=Integer.parseInt(c2in.readLine());
						System.out.println("Jugador 1, Jugador 2 ha apostado "+bet+" piedras, si quieres aceptar escribe: 'acepto', si quieres pasar escribe: 'paso', si quieres apostar más escribe: 'mas'.");
						entrada=inFromUser.readLine();
						if(entrada.equals("acepto")) {
							betjuego=bet;
							Punto(mano1, mano2, c2out, cont1, cont2, betjuego);
						}
						if(entrada.equals("paso")) {
							System.out.println("Jugador 1 rechaza el envite, se añade punto y miedo al Jugador 2."+"\n"); 
							c2out.writeBytes("Jugador 1 rechaza el envite, se añade punto y miedo al Jugador 2."+"\n"); 
							contador2=contador2+2;
						}
						if(entrada.equals("mas")) {
							System.out.println("Introduzca el nuevo número total de piedras a apostar:");
							bet=Integer.parseInt(inFromUser.readLine());
							betpequena=bet;
							Punto(mano1, mano2, c2out, cont1, cont2, betjuego);
						}
					}
					if(entrada.equals("no")) {
						System.out.println("No se juega la ronda de juego."+"\n");
						c2out.writeBytes("No se juega la ronda de juego."+"\n");
					}
				}
			}
			////////////////////////Punto////////////////////////
			////////////////////////Juego////////////////////////
			
			System.out.println("Recuento total de Piedras en la Ronda "+ronda+":");
			System.out.println("Piedras del Jugador 1: "+contador1+".");
			System.out.println("Piedras del Jugador 2: "+contador2+"."+"\n");
			c2out.writeBytes("Recuento total de Piedras en la Ronda "+ronda+":\n");
			c2out.writeBytes("Piedras del Jugador 1: "+contador1+".\n");
			c2out.writeBytes("Piedras del Jugador 2: "+contador2+"."+"\n");
			ronda=ronda+1;
		}
		ganador=Math.max(contador1, contador2);
		if (ganador==contador1) {
			System.out.println("El Ganador del Mus es el Jugador 1.");
			c2out.writeBytes("El Ganador del Mus es el Jugador 1.\n");
		}
		if (ganador==contador2) {
			System.out.println("El Ganador del Mus es el Jugador 2.");
			c2out.writeBytes("El Ganador del Mus es el Jugador 2.\n");
		}
		
		serverSocket.close(); //Cerramos el socket
	}
	
	
	///////////////////////////////////DECLARACION DE FUNCIONES//////////////////////////////////////
	
	private static ArrayList<String> Repartir_mus(DataOutputStream c2out,BufferedReader c2in) throws IOException{
		ArrayList<String> baraja = new ArrayList<String>();
		for (int i=1;i<13;i++) {
			if(i!=8 && i!=9) {
				baraja.add(i+" oros");
			}
		}
		
		for (int i=1;i<13;i++) {
			if(i!=8 && i!=9) {
				baraja.add(i+" copas");
			}
		}
		
		for (int i=1;i<13;i++) {
			if(i!=8 && i!=9) {
				baraja.add(i+" espadas");
			}
		}
		
		for (int i=1;i<13;i++) {
			if(i!=8 && i!=9) {
				baraja.add(i+" bastos");
			}
		}
		
		ArrayList <String> cartas = (ArrayList<String>) baraja.clone(); //copia de la baraja para quitarle cartas al repartir
		
		Random rnd =new Random();
		
		//Mano del jugador 1
		ArrayList<String> mano1 = new ArrayList<String>();
		
		for (int i=0;i<4;i++){
			mano1.add(cartas.get(rnd.nextInt(cartas.size())));
			cartas.remove(mano1.get(i));
		}
		
		//Mano del jugador 2
		ArrayList<String> mano2 = new ArrayList<String>();
		
		for (int i=0;i<4;i++){
			mano2.add(cartas.get(rnd.nextInt(cartas.size())));
			cartas.remove(mano2.get(i));
		}
		
		System.out.println("La mano del jugador 1 es: "+mano1);
		c2out.writeBytes("La mano del jugador 2 es: "+mano2+"\n");
		//System.out.println("Las cartas restantes son: "+cartas);
		//System.out.println("Es decir un total de "+cartas.size()+" cartas."+"\n");
		
		String haymus1, haymus2;		
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("¿Jugador 1 quieres realizar mus?, si es así escriba: 'si', por el contrario escriba: 'no'.");
		haymus1 = inFromUser.readLine();
		c2out.writeBytes("#¿Jugador 2 quieres realizar mus?, si es así escriba: 'si', por el contrario escriba: 'no'.\n");
		haymus2 = c2in.readLine();
		
		if(haymus1.equals("si") && haymus2.equals("si")) {
			String descartes1, descartes2;
			//En caso de mus, se debe indicar las cartas que te quieres quitar (posicion) con espacios entre medio (te puedes no quitar ninguna)
			System.out.println("Jugador 1 introduzca la posición de las cartas que desea eliminar separado con espacios, o si quiere puede no eliminar ninguna.");
			descartes1 = inFromUser.readLine();
			
			c2out.writeBytes("#Jugador 2 introduzca la posición de las cartas que desea eliminar separado con espacios, o si quiere puede no eliminar ninguna.\n");
			descartes2 = c2in.readLine();
		
			String[] quitar1,quitar2;
			
			quitar1=descartes1.split(" ");
			quitar2=descartes2.split(" ");
			
			if(!descartes1.equals("")) {
				for (int i=0;i<quitar1.length;i++) { //quito descartes a la mano 1
					mano1.remove(Integer.parseInt(quitar1[i])-1-i);
				}
				//Dar cartas otra vez
				for (int i=0;i<quitar1.length;i++) { 
					mano1.add(cartas.get(rnd.nextInt(cartas.size())));
					cartas.remove(mano1.get(4-quitar1.length+i));
				}
			}
				
			if(!descartes2.equals("")) {
				for (int i=0;i<quitar2.length;i++) { //quito descartes a la mano 2
					mano2.remove(Integer.parseInt(quitar2[i])-1-i);
				}
				//Doy cartas
				for (int i=0;i<quitar2.length;i++) { 
					mano2.add(cartas.get(rnd.nextInt(cartas.size())));
					cartas.remove(mano2.get(4-quitar2.length+i));
				}
			}
			
			System.out.println("La nueva mano del jugador 1 es: "+mano1);
			c2out.writeBytes("La nueva mano del jugador 2 es: "+mano2+"\n");
			//System.out.println("Las cartas restantes tras el mus son: "+cartas);
			//System.out.println("Es decir un total de "+cartas.size()+" cartas."+"\n");
			System.out.println("Comienza la partida."+"\n");
			c2out.writeBytes("Comienza la partida."+"\n");
		}
		else {
			System.out.println("Al menos un jugador no quiere mus, la partida empieza."+"\n");
			c2out.writeBytes("Al menos un jugador no quiere mus, la partida empieza."+"\n");
		}
		
		
		ArrayList <String> manos=new ArrayList <String>(0);
		
		for(int i=0; i<=3;i++) {
			manos.add(mano1.get(i));
		}
		for(int i=0; i<=3;i++) {
			manos.add(mano2.get(i));
		}
		
		return manos;
	}
	
	private static void Grande (ArrayList <String> m1, ArrayList <String> m2, int apuesta, DataOutputStream c2out) throws IOException {
		String[] valor1, valor2;
		int[] cartas1={0, 0, 0, 0}, cartas2={0, 0, 0, 0};
		boolean ganador1=false, ganador2=false;
		for (int i=0; i<=3; i++){
			valor1=(m1.get(i)).split(" ");
			valor2=(m2.get(i)).split(" ");
			cartas1[i]=Integer.parseInt(valor1[0]);
			cartas2[i]=Integer.parseInt(valor2[0]);
		}
		
		Arrays.sort(cartas1);
		Arrays.sort(cartas2);
		
		if(cartas1[3]>cartas2[3]) {
			ganador1=true;
			ganador2=false;
		}
		if (cartas1[3]<cartas2[3]) {
			ganador1=false;
			ganador2=true;
		}
		if(cartas1[3]==cartas2[3]) {
			if(cartas1[2]>cartas2[2]) {
				ganador1=true;
				ganador2=false;
			}
			if (cartas1[2]<cartas2[2]) {
				ganador1=false;
				ganador2=true;
			}
			if(cartas1[2]==cartas2[2]) {
				if(cartas1[1]>cartas2[1]) {
					ganador1=true;
					ganador2=false;
				}
				if (cartas1[1]<cartas2[1]) {
					ganador1=false;
					ganador2=true;
				}
				if(cartas1[1]==cartas2[1]) {
					if(cartas1[0]>cartas2[0]) {
						ganador1=true;
						ganador2=false;
					}
					if (cartas1[0]<cartas2[0]) {
						ganador1=false;
						ganador2=true;
					}
				}
			}
		}
		if (ganador1==true) {
			System.out.println("El jugador 1 ha ganado la Grande obteniendo "+apuesta+" piedras."+"\n");
			c2out.writeBytes("El jugador 1 ha ganado la Grande obteniendo "+apuesta+" piedras."+"\n");
			contador1=contador1+apuesta;
		}
		if (ganador2==true) {
			System.out.println("El jugador 2 ha ganado la Grande obteniendo "+apuesta+" piedras."+"\n");
			c2out.writeBytes("El jugador 2 ha ganado la Grande obteniendo "+apuesta+" piedras."+"\n");
			contador2=contador2+apuesta;
		}
	}	
	
	private static void Pequena (ArrayList <String> m1, ArrayList <String> m2, int apuesta,DataOutputStream c2out) throws IOException {
		String[] valor1, valor2;
		int[] cartas1={0, 0, 0, 0}, cartas2={0, 0, 0, 0};
		boolean ganador1=false, ganador2=false;
		for (int i=0; i<=3; i++){
			valor1=(m1.get(i)).split(" ");
			valor2=(m2.get(i)).split(" ");
			cartas1[i]=Integer.parseInt(valor1[0]);
			cartas2[i]=Integer.parseInt(valor2[0]);
		}
		
		Arrays.sort(cartas1);
		Arrays.sort(cartas2);
		
		if(cartas1[0]<cartas2[0]) {
			ganador1=true;
			ganador2=false;
		}
		if (cartas1[0]>cartas2[0]) {
			ganador1=false;
			ganador2=true;
		}
		if(cartas1[0]==cartas2[0]) {
			if(cartas1[1]<cartas2[1]) {
				ganador1=true;
				ganador2=false;
			}
			if (cartas1[1]>cartas2[1]) {
				ganador1=false;
				ganador2=true;
			}
			if(cartas1[1]==cartas2[1]) {
				if(cartas1[2]<cartas2[2]) {
					ganador1=true;
					ganador2=false;
				}
				if (cartas1[2]>cartas2[2]) {
					ganador1=false;
					ganador2=true;
				}
				if(cartas1[2]==cartas2[2]) {
					if(cartas1[3]<cartas2[3]) {
						ganador1=true;
						ganador2=false;
					}
					if (cartas1[3]>cartas2[3]) {
						ganador1=false;
						ganador2=true;
					}
				}
			}
		}
		if (ganador1==true) {
			System.out.println("El jugador 1 ha ganado la Pequeña obteniendo "+apuesta+" piedras."+"\n");
			c2out.writeBytes("El jugador 1 ha ganado la Pequeña obteniendo "+apuesta+" piedras."+"\n");
			contador1=contador1+apuesta;
		}
		if (ganador2==true) {
			System.out.println("El jugador 2 ha ganado la Pequeña obteniendo "+apuesta+" piedras."+"\n");
			c2out.writeBytes("El jugador 2 ha ganado la Pequeña obteniendo "+apuesta+" piedras."+"\n");
			contador2=contador2+apuesta;
		}
	}
	
	private static void Juego (ArrayList <String> m1, ArrayList <String> m2, DataOutputStream c2out, int cont1, int cont2, int apuesta) throws IOException {
			
		int ganador=0;
		
		if (cont1>=33 && cont2>=33) {			//Ambos con más de 32 puntos, gana el que tenga más puntos.
			ganador=Math.max(cont1, cont2);
		}
		
		if (cont1<33 && cont2<33) {				//Ambos con menos de 33 puntos, gana el que tenga menos puntos.
			ganador=Math.min(cont1, cont2);
		}
		
		if (cont1<33 && cont2>=33) {			//Jugador 1 con menos de 33 puntos y Jugador 2 con más de 32 puntos, gana Jugador 1.
			ganador=cont1;
		}
		
		if (cont2<33 && cont1>=33) {			//Jugador 2 con menos de 33 puntos y Jugador 1 con más de 32 puntos, gana Jugador 1.
			ganador=cont2;
		}
		
		if (ganador==cont1) {
			if(ganador==31) {
				contador1=contador1+3+apuesta;
				System.out.println("El jugador 1 ha ganado el Juego obteniendo 3 piedras por tener 31 y "+apuesta+" piedras de envites."+"\n");
				c2out.writeBytes("El jugador 1 ha ganado el Juego obteniendo 3 piedras por tener 31 y "+apuesta+" piedras de envites."+"\n");
			}
			if(ganador!=31) {
				contador1=contador1+2+apuesta;
				System.out.println("El jugador 1 ha ganado el Juego obteniendo 2 piedras por tener 32 y "+apuesta+" piedras de envites."+"\n");
				c2out.writeBytes("El jugador 1 ha ganado el Juego obteniendo 2 piedras por tener 32 y "+apuesta+" piedras de envites."+"\n");
			}
		}
		if (ganador==cont2) {
			if(ganador==31) {
				contador2=contador2+3+apuesta;
				System.out.println("El jugador 2 ha ganado el Juego obteniendo 3 piedras por tener 31 y "+apuesta+" piedras de envites."+"\n");
				c2out.writeBytes("El jugador 2 ha ganado el Juego obteniendo 3 piedras por tener 31 y "+apuesta+" piedras de envites."+"\n");
			}
			if(ganador!=31) {
				contador2=contador2+2+apuesta;
				System.out.println("El jugador 2 ha ganado el Juego obteniendo 2 piedras por tener 32 y "+apuesta+" piedras de envites."+"\n");
				c2out.writeBytes("El jugador 2 ha ganado el Juego obteniendo 2 piedras por tener 32 y "+apuesta+" piedras de envites."+"\n");
			}
		}
		//Vemos que jugador es ganador siendo el último quien lleve la mano ya que será el último en actualizarse en caso de empate.

	}
	
	private static void Punto (ArrayList <String> m1, ArrayList <String> m2, DataOutputStream c2out, int cont1, int cont2, int apuesta) throws IOException {
		
		if (cont1>=cont2) {
			contador1=contador1+apuesta;
			System.out.println("El jugador 1 ha ganado el Punto obteniendo "+apuesta+" piedras de envites."+"\n");
			c2out.writeBytes("El jugador 1 ha ganado el Punto obteniendo "+apuesta+" piedras de envites."+"\n");
		}
		else {
			contador2=contador2+apuesta;
			System.out.println("El jugador 2 ha ganado el Punto obteniendo "+apuesta+" piedras de envites."+"\n");
			c2out.writeBytes("El jugador 2 ha ganado el Punto obteniendo "+apuesta+" piedras de envites."+"\n");
		}	
	}
}

