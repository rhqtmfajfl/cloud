import java.util.Scanner;

//start
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.StartInstancesRequest;


public class awsTest{
/** Cloud Computing, Data Computing Laboratory* Department of Computer Science * ChungbukNational University*/
static AmazonEC2      ec2;
private static void init() throws Exception {
/** The ProfileCredentialsProviderwill return your [default]* credential profile by reading from the credentials file located at* (~/.aws/credentials).*/

ProfileCredentialsProvider credentialsProvider= new ProfileCredentialsProvider();
try {
credentialsProvider.getCredentials();
} catch (Exception e) {

throw new AmazonClientException(
"Cannot load the credentials from the credential profiles file. " +
"Please make sure that your credentials file is at the correct " +
"location (~/.aws/credentials), and is in valid format.",
e);

}

ec2 = AmazonEC2ClientBuilder.standard()
.withCredentials(credentialsProvider)
.withRegion("us-east-2")/* check the region at AWS console */
.build();
}

public static void main(String[] args) throws Exception {

init();

Scanner menu = new Scanner(System.in);
Scanner id_string= new Scanner(System.in);
int number = 0;

while(true)
{
System.out.println("                                                            ");
System.out.println("                                                            ");
System.out.println("------------------------------------------------------------");
System.out.println("           Amazon AWS Control Panel using SDK               ");
System.out.println("                                                            ");
System.out.println("  Cloud Computing, Computer Science Department              ");
System.out.println("                           at ChungbukNational University   ");
System.out.println("------------------------------------------------------------");
System.out.println("  1. list instance                2. available zones        ");
System.out.println("  3. start instance               4. available regions      ");
System.out.println("  5. stop instance                6. create instance        ");
System.out.println("  7. reboot instance              8. list images            ");
System.out.println("                                 99. quit                   ");
System.out.println("------------------------------------------------------------");
System.out.print("Enter an integer: ");


switch(number) {
case 1: 
listInstances();
break;
}

}
	public static void listInstances()
	{
		System.out.println("Listing instances....");
		booleandone = false;

 		DescribeInstancesRequestrequest = new DescribeInstancesRequest();

		while(!done) {
			DescribeInstancesResultresponse = ec2.describeInstances(request);
	
			for(Reservation reservation: response.getReservations()) {
			for(Instance instance: reservation.getInstances()) {
			    System.out.printf(
				"[id] %s, " +
				"[AMI] %s, " +
				"[type] %s, " +
				"[state] %10s, " +
				"[monitoring state] %s",
				instance.getInstanceId(),
				instance.getImageId(),
				instance.getInstanceType(),
				instance.getState().getName(),
				instance.getMonitoring().getState());
			}
			System.out.println();
		}

		request.setNextToken(response.getNextToken());

		if(response.getNextToken() == null) {
		     done = true;
	 	}	
	}

}
}// error line









