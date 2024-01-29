import java.io.*;
import java.net.*;

public class ChatProgram {
    public static void main(String[] args) throws IOException {

        // creates ServerSocket class instance & initialize it with a port number 0
        ServerSocket serverSocket = new ServerSocket(0);
        System.out.println("Started on port number " + serverSocket.getLocalPort());
        final StringBuffer user = new StringBuffer();

        Thread wrtThread = new Thread(() -> {
            try {
                System.out.println("Please enter username... ");
                BufferedReader bfreader = new BufferedReader(new InputStreamReader(System.in));
                String uname = bfreader.readLine();

                System.out.println("Please give the port number to establish connection ... ");
                int port_number = Integer.parseInt(bfreader.readLine());
                System.out.println("What do you want to call them? ");
                String other_uname = bfreader.readLine();
                user.append(other_uname);
                //Establish connection with given port number
                Socket socket = new Socket("localhost", port_number);
                System.out.println("Connection to port "+port_number+" is established. All good ! You are set to start chatting with "+other_uname);
                DataOutputStream data_out_stream = new DataOutputStream(socket.getOutputStream());

                //This is the while loop to read messages
                while (true) {
                    //System.out.print(uname+": ");
                    String msg_command = bfreader.readLine();

                    //if the command is transfer, then send the file
                    if (msg_command.contains("transfer ")) 
                    {
                        String filename = msg_command.substring(9);
                        File fileins = new File(filename);
                        if(fileins.exists())
                        {
                            FileInputStream file_in_stream = new FileInputStream(fileins);
                            File tempfile = new File("temp.txt");
                            FileOutputStream temp = new FileOutputStream(tempfile);
                            temp.write(("transfer-"+filename).getBytes());
                            temp.close();
                            byte[] buffer = new byte[1024];
                            int bytesRead = 0;
                            while ((bytesRead = file_in_stream.read(buffer)) > 0) {
                                data_out_stream.write(buffer, 0, bytesRead);
                            }
                            file_in_stream.close();
                            System.out.println("Done..File transferred successfully!");
                        }
                        else
                        {
                            System.out.println("File does not exist! Please recheck");
                        }

                    } 
                    else if(msg_command.contains("quit")){
                        socket.close();
                        System.exit(0);
                    }
                    else 
                        {
                        data_out_stream.write(msg_command.getBytes());
                        }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        wrtThread.start();
        
        // Accept incoming connection
        Socket connectionSocket = serverSocket.accept();

        //Reading Thread
        Thread readThread = new Thread(() -> {
            try {
                byte[] chunk = new byte[1024];
                int buffercount = 0;
                byte[] tempchunk = new byte[1024];
                int buffercount1 = 0;
                DataInputStream data_in_stream = new DataInputStream(connectionSocket.getInputStream());
                while ((buffercount = data_in_stream.read(chunk)) > 0) {
                String filename="";
                File instance = new File("temp.txt");
                File f_temp = null;
                String msg_command = new String(chunk, 0, buffercount);
                if (instance.exists()){
                    FileInputStream temp_file = new FileInputStream("temp.txt");
                    buffercount1 = temp_file.read(tempchunk);
                    filename= new String(tempchunk, 0, buffercount1);
                    if(filename.contains("transfer-")){
                        filename= filename.replace("transfer-","").strip();
                        String filename2="new"+filename;
                        File oldfile = new File(filename);
                        f_temp = new File(filename2);
                        FileOutputStream file_out_stream = new FileOutputStream(f_temp);
                        while (true) {
                            file_out_stream.write(chunk, 0, buffercount);
                            if(f_temp.length()!=oldfile.length())
                            buffercount = connectionSocket.getInputStream().read(chunk);
                            else
                            break;
                        }
                        buffercount = 0;
                        file_out_stream.close();
                        temp_file.close();
                        instance.delete();
                        System.out.println(user.toString()+" sent "+filename +" and it was received as "+filename2+"!");
                    }
                    else{
                        System.out.println("\n"+user.toString()+" texted: "+msg_command+" \n");
                    }
                }
                else{
                    System.out.println("\n"+user.toString()+" texted: "+msg_command+" \n");
                }
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        readThread.start();
        
    }
}