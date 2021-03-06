/* instance start stop*//*
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.StartInstancesRequest;
import software.amazon.awssdk.services.ec2.model.StopInstancesRequest;
*/
import java.util.Iterator;
import java.util.Scanner;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;

import com.amazonaws.services.ec2.model.DescribeRegionsResult;
import com.amazonaws.services.ec2.model.Region;
import com.amazonaws.services.ec2.model.AvailabilityZone;


import com.amazonaws.services.ec2.model.DryRunSupportedRequest;
import com.amazonaws.services.ec2.model.StopInstancesRequest;

import com.amazonaws.services.ec2.model.StartInstancesRequest;


import com.amazonaws.services.ec2.model.InstanceType;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;

import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.CreateTagsResult;


import com.amazonaws.services.ec2.model.RebootInstancesRequest;
import com.amazonaws.services.ec2.model.RebootInstancesResult;

import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;



import com.amazonaws.services.ec2.model.DescribeImagesRequest;
import com.amazonaws.services.ec2.model.DescribeImagesResult;
import com.amazonaws.services.ec2.model.Image;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.InstanceState;

//describeSecurityGrops
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsRequest;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsResult;
import com.amazonaws.services.ec2.model.SecurityGroup;

//DeleteSecurityGroup
import com.amazonaws.services.ec2.model.DeleteSecurityGroupRequest;
import com.amazonaws.services.ec2.model.DeleteSecurityGroupResult;

//MonitorInstance
import com.amazonaws.services.ec2.model.MonitorInstancesRequest;
import com.amazonaws.services.ec2.model.UnmonitorInstancesRequest;
import com.amazonaws.services.ec2.model.DryRunResult;
import com.amazonaws.services.ec2.model.DryRunSupportedRequest;


import com.amazonaws.util.XpathUtils;
//unMonitorInstance


//terminate
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;


/*
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.CreateTagsResult;
*/


public class awsTest {
/*
* Cloud Computing, Data Computing Laboratory
* Department of Computer Science
* Chungbuk National University
*/
static AmazonEC2 ec2;
private static void init() throws Exception {
/*
* The ProfileCredentialsProvider will return your [default]
* credential profile by reading from the credentials file located at
* (~/.aws/credentials).
*/
ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
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
.withRegion("us-east-1") /* check the region at AWS console */
.build();
}

public static void main(String[] args) throws Exception {
init();
Scanner menu = new Scanner(System.in);
Scanner id_string = new Scanner(System.in);




//String a;

int number = 0;


while(true)
{
System.out.println(" ");
System.out.println(" ");
System.out.println("------------------------------------------------------------");
System.out.println(" Amazon AWS Control Panel using SDK ");
System.out.println(" ");
System.out.println(" Cloud Computing, Computer Science Department ");
System.out.println(" at Chungbuk National University ");
System.out.println("------------------------------------------------------------");
System.out.println(" 1. list instance 2. available zones ");
System.out.println(" 3. start instance 4. available regions ");
System.out.println(" 5. stop instance 6. create instance ");
System.out.println(" 7. reboot instance 8. list images ");
System.out.println(" 9. DescribeSecurityGroups 10. DeleteSecurityGroup ");
System.out.println(" 11.MonitorInstance 12. unmonitorInstance ");
System.out.println(" 13. terminate ");
System.out.println(" 99. quit ");
System.out.println("------------------------------------------------------------");
System.out.print("Enter an integer: ");

System.out.println("------------------------------------------------------------");
         
           
            number = menu.nextInt();
           
            switch(number) {
            case 1:
                listInstances();
                break;
                
                case 2: 
		availableZones();
		break;

case 3:
	System.out.println("Enter an instance id : ");
	

	startInstance(id_string.next());
	break;

case 4:
	availableRegions();
	break;

case 5:
	System.out.println("Enter an instance id : ");
	
	stopInstance(id_string.next());
	break;

case 6:
	System.out.println("Enter an instance id : ");
	

	createInstance(id_string.next());
	break;
case 7:
	System.out.println("Enter an instance id : ");

	rebootInstance(id_string.next());	
case 8:
	listImages();
	break;
case 9:
	DescribeSecurityGroups();
	break;

case 10:
	System.out.println("Enter an deletesecuritygroup id : ");
	 DeleteSecurityGroup(id_string.next());
	break;

case 11:
	System.out.println("MonitorInstance id : ");
	 MonitorInstance(id_string.next());
	 
case 12:
	System.out.println("unMonitorInstance id : ");
	unMonitorInstance(id_string.next());

case 13: 
	System.out.println("Enter an instance id : ");
	terminateInstance(id_string.next());
case 99:
	System.exit(0);
	break;
                }
        }
    }           
    


public static void listInstances()
{
System.out.println("Listing instances....");
boolean done = false;
DescribeInstancesRequest request = new DescribeInstancesRequest();
while(!done) {
DescribeInstancesResult response = ec2.describeInstances(request);
for(Reservation reservation : response.getReservations()) {
for(Instance instance : reservation.getInstances()) {
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


public static void startInstance(String instance_id) {
    System.out.println("Starting ..... [ " + instance_id+"]");
        System.out.println(" ");
    System.out.println("Successfuly : start instance [id:"+instance_id+"]");

    StartInstancesRequest request = new StartInstancesRequest()
            .withInstanceIds(instance_id);
           

    ec2.startInstances(request);
}


public static void stopInstance(String instance_id) {
    System.out.println("Stoping ..... [ " + instance_id+"]");
    System.out.println(" ");
    System.out.println("Successfuly : stop instance [id:"+instance_id+"]");

    StopInstancesRequest request = new StopInstancesRequest()
    .withInstanceIds(instance_id);        
            
            
    ec2.stopInstances(request);
}

public static void rebootInstance(String id_string) {
       System.out.println("Rebooting ..... [ " +id_string+"]");
       System.out.println(" ");
		System.out.println("Successfuly :  reboot instance... [id:"+id_string+"]");
		
		RebootInstancesRequest request = new RebootInstancesRequest()
			.withInstanceIds(id_string);
		
		RebootInstancesResult response = ec2.rebootInstances(request);
	}
	
	
public static void availableZones() {
    System.out.println("available zones  ");
    
    // snippet-start:[ec2.java1.describe_region_and_zones.zones]
    DescribeAvailabilityZonesResult zones_response = 
        ec2.describeAvailabilityZones();
        
        for(AvailabilityZone zone : zones_response.getAvailabilityZones()) {
            System.out.printf(
                   "Found availability zone %s " +
                   "with status %s",
                   "in region %s",
                   zone.getZoneName(),
                   zone.getState(),
                   zone.getRegionName());
               System.out.println();    
      //System.out.print("YOU hae access to %d Availability zones.", .zone.getRegionName());
       }
      
       // snippet-end:[ec2.java1.describe_region_and_zones.zones]
  }
  
  

public static void availableRegions() {
		System.out.println("Available regions....");
		
		DescribeRegionsResult regions_response = ec2.describeRegions();

		for(Region region : regions_response.getRegions()) {
		    System.out.printf(
	    		"Found region %s, with endpoint %s", 
	    		region.getRegionName(), region.getEndpoint());
    		}
    		 // snippet-end:[ec2.java1.describe_region_and_zones.regions]
		 System.out.println();
		
	}

public static void  DeleteSecurityGroup(String id_string) {
 System.out.println("Deleting ..... [ " +id_string+"]");
//final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

      DeleteSecurityGroupRequest request = new DeleteSecurityGroupRequest()
            .withGroupId(id_string);

        DeleteSecurityGroupResult response = ec2.deleteSecurityGroup(request);

        System.out.printf(
            "Successfully deleted security group with id %s", id_string);
 }

public static void DescribeSecurityGroups() {
		System.out.println("DescribeSecurityGroups....");
		
        final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

		DescribeSecurityGroupsRequest request =
            new DescribeSecurityGroupsRequest();
                

        DescribeSecurityGroupsResult response =
            ec2.describeSecurityGroups(request);

        for(SecurityGroup group : response.getSecurityGroups()) {
            System.out.printf(
                "Found security group with id %s, " +
                "vpc id %s " +
                "and description %s",
                group.getGroupId(),
                group.getVpcId(),
                group.getDescription());
		 System.out.println();
 		 System.out.println(" ");
	}
}
public static void  MonitorInstance(String id_string) {
 System.out.println("MonitorInstance ..... [ " +id_string+"]");
 
        final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
 
 DryRunSupportedRequest<MonitorInstancesRequest> dry_request =
            () -> {
            MonitorInstancesRequest request = new MonitorInstancesRequest()
                .withInstanceIds(id_string);

            return request.getDryRunRequest();
        };

        DryRunResult dry_response = ec2.dryRun(dry_request);

        if (!dry_response.isSuccessful()) {
            System.out.printf(
                "Failed dry run to enable monitoring on instance %s",
                id_string);

            throw dry_response.getDryRunResponse();
        }

        MonitorInstancesRequest request = new MonitorInstancesRequest()
                .withInstanceIds(id_string);

        ec2.monitorInstances(request);

        System.out.printf(
            "Successfully enabled monitoring for instance %s",
            id_string);
 }
 
public static void  unMonitorInstance(String id_string) {
 System.out.println("unMonitorInstance ..... [ " +id_string+"]");
//final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

        final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

DryRunSupportedRequest<UnmonitorInstancesRequest> dry_request =
            () -> {
            UnmonitorInstancesRequest request = new UnmonitorInstancesRequest()
                .withInstanceIds(id_string);

            return request.getDryRunRequest();
        };

        DryRunResult dry_response = ec2.dryRun(dry_request);

        if (!dry_response.isSuccessful()) {
            System.out.printf(
                "Failed dry run to disable monitoring on instance %s",
                id_string);

            throw dry_response.getDryRunResponse();
        }

        UnmonitorInstancesRequest request = new UnmonitorInstancesRequest()
            .withInstanceIds(id_string);

        ec2.unmonitorInstances(request);

        System.out.printf(
            "Successfully disabled monitoring for instance %s", id_string);
 }


	/*
public static void CreateSecurityGroup(String id_string) {

/*
AmazonEC2 ec2 = new AmazonEC2Client(credentials);
ec2.setEndpoint("ec2.us-east-1.amazonaws.com");
        */
        /*
 CreateSecurityGroupRequest create_request = new
            CreateSecurityGroupRequest()
                .withGroupName(group_name)
                .withDescription(group_desc)
                .withVpcId(vpc_id);

        CreateSecurityGroupResult create_response =
            ec2.createSecurityGroup(create_request);




}
*/

public static void terminateInstance(String id_string) {
		System.out.println("terminate instance... [id:"+id_string+"]");
		
		TerminateInstancesRequest terminateInstancesRequest = new TerminateInstancesRequest();
		terminateInstancesRequest.withInstanceIds( id_string );
		
		ec2.terminateInstances( terminateInstancesRequest );
	}
	
	

	
public static void createInstance(String id_string) {

/*
AmazonEC2 ec2 = new AmazonEC2Client(credentials);
ec2.setEndpoint("ec2.us-east-1.amazonaws.com");
        */
RunInstancesRequest run_request = new RunInstancesRequest()
    .withImageId(id_string)
    .withInstanceType(InstanceType.T2Micro)
    .withMaxCount(1)
    .withMinCount(1)
    .withSecurityGroups("htcondor-security")
    .withKeyName("awscloud");
RunInstancesResult run_response = ec2.runInstances(run_request);




}



public static void listImages() {
  DescribeImagesRequest request = new DescribeImagesRequest();
    request.getFilters().add(new Filter().withName("is-public").withValues("false")); //true -> many Image
    DescribeImagesResult result = ec2.describeImages(request);
    for(Image images :result.getImages()){
			System.out.printf(
				"AIM Name %s, " + 
				"AIM ID %10s " ,
				images.getName(),
				images.getImageId()
				
				
				
			);
			System.out.println();
		}
	}

}
