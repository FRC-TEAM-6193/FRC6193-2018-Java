package org.usfirst.frc.team6193.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VisionCommandGroup extends CommandGroup {
	double m_distance;
	double m_angle;
    public VisionCommandGroup(double distance, double rotate) {
    	m_distance = distance;
    	m_angle = rotate;
    	
      /**
       * This is a place holder and documentation for how to use vision to shoot an object.
       * 
       * 1. The robot needs to be manually moved and rotated to a spot that the 
       *    driver thinks the camera can see the vision object.
       * 2. The driver needs to press a button on a controller to initiate the single pass
       *    vision Process loop. 
       * 3. If the vision process loop finds the object it needs to kickoff a command group; that
       *    will drive the remaining distance and rotate to the object.
       * 4. Usually, a second pass of vision process loop is done at this point.
       * 5. The vision process loop needs to indicate to the driver that is did find the
       *    object.
       * 6. If it did not find the object, the driver has the option to either shoot at that point, 
       *    or line up better. This is where the driver needs to determine if he is better/quicker 
       *    than the computer.
       * 7. If it did find the object, the process loop needs to kick off another
       *    move, rotate command group, and shoot when done.
       *    
       *    Questions/Comments
       *    1. This process is called a 2 step vision process. 
       *    2. There may be more commands, like lift, that need to be run in the command group.
       *    3. GRIP is used to generate the code for vision processing.
       *    4. Continuously running vision software on the roboRIO will overload the processor;
       *       this approach is used to limit the processor usage of the vision software.
       *    5. It may be better to do a rotate, then a drive.
       *    6. Without a lift, the distance will need to be determined. To determine the
       *       distance to get the right vertical angle, there is usually tape on the floor to 
       *       help with this distance.
       *    7. To be able to do a second pass, the trigger needs to wait for the first VisionCommandGroup
       *       to be completed, then it can kick off the next VisionCommandGroup. "isFinished"
       *    8. Don't forget to signal the driver, through the smartdashboard, that the vision found the target.
       *      
       *       
       *    PSEUDO CODE: (placed in command that triggers the start of this command group)
       *    GripPipelineShoot gpShoot = new GripPipelineShoot();
       *    gpShoot.process("Matrix of Camera Image")  
       *    "calculate the distance and rotation from vision data"
       *    VisionCommandGroup vcg = new VisionCommandGroup(m_distance,m_angle);
       *    if(isObjectFound){ VisionCommandGroup.start() }
       *    
       *    PSEUDO CODE: This is placed in this file
       *    addsequential(new DrivelineDrive(m_distance, 0.0);
       *    addsequential(new DrivelineRotate(0.0, m_angle);
       *    
       */
    }
}
